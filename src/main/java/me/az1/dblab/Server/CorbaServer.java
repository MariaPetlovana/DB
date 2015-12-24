package me.az1.dblab.Server;

import me.az1.dblab.Common.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.nio.ByteBuffer;
import java.rmi.RemoteException;

public class CorbaServer extends DatabaseControllerCorbaPOA {
    protected DatabaseController controller;
    protected ORB orb;

    public CorbaServer() {
        super();

        controller = new ServerController();
    }

    public void setORB(ORB orb) {
        this.orb = orb;
    }

    public boolean IsOpened() {
        try {
            return controller.IsOpened();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void NewDatabase () {
        try {
            controller.NewDatabase();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void LoadDatabase (String path) {
        try {
            controller.LoadDatabase(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void SaveDatabase (String path) {
        try {
            controller.SaveDatabase(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void CloseDatabase () {
        try {
            controller.CloseDatabase();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int[][] DatabaseGetTableVersions () {
        try {
            return FromLongArray(controller.DatabaseGetTableVersions());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int[] DatabaseAddEmptyTable (String scheme, String name) {
        try {
            return FromLong(controller.DatabaseAddEmptyTable(Scheme.ParseFromString(scheme), name));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean DatabaseRemoveTable (int[] version) {
        try {
            return controller.DatabaseRemoveTable(ToLong(version));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int[][] DatabaseGetTableRowVersions (int[] version) {
        try {
            return FromLongArray(controller.DatabaseGetTableRowVersions(ToLong(version)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int DatabaseSize () {
        try {
            return controller.DatabaseSize();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int DatabaseTableSize (int[] version) {
        try {
            return controller.DatabaseTableSize(ToLong(version));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public String DatabaseTableName (int[] version) {
        try {
            return EscapeNull(controller.DatabaseTableName(ToLong(version)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int DatabaseTableRowLength (int[] version) {
        try {
            return controller.DatabaseTableRowLength(ToLong(version));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int[] DatabaseTableAddEmptyRow (int[] version) {
        try {
            return FromLong(controller.DatabaseTableAddEmptyRow(ToLong(version)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean DatabaseTableRemoveRow (int[] tableVersion, int[] rowVersion) {
        try {
            return controller.DatabaseTableRemoveRow(ToLong(tableVersion), ToLong(rowVersion));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] DatabaseTableGetField (int[] tableVersion, int[] rowVersion, int column) {
        try {
            return controller.DatabaseTableGetField(ToLong(tableVersion), ToLong(rowVersion), column);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public String DatabaseTableGetFieldString (int[] tableVersion, int[] rowVersion, int column) {
        try {
            return EscapeNull(controller.DatabaseTableGetFieldString(ToLong(tableVersion), ToLong(rowVersion), column));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void DatabaseTableSetFieldData (int[] tableVersion, int[] rowVersion, int column, byte[] value) {
        try {
            controller.DatabaseTableSetField(ToLong(tableVersion), ToLong(rowVersion), column, value);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void DatabaseTableSetFieldString (int[] tableVersion, int[] rowVersion, int column, String strValue) {
        try {
            controller.DatabaseTableSetFieldStr(ToLong(tableVersion), ToLong(rowVersion), column, UnescapeNull(strValue));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int[] DatabaseTableFind (int[] version, String pattern) {
        try {
            return FromLong(controller.DatabaseTableFind(ToLong(version), pattern));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int[] DatabaseTableRemoveDuplicates (int[] version) {
        try {
            return FromLong(controller.DatabaseTableRemoveDuplicates(ToLong(version)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static long ToLong(int[] array) {
        if (array == null || array.length != 2) {
            throw new RuntimeException("Can't cast CORBA type.");
        }

        return ByteBuffer.wrap(ByteBuffer.allocate(8).putInt(array[0]).putInt(array[1]).array()).getLong();
    }

    public static long[] ToLongArray(int[][] array) {
        long[] result = new long[array.length];
        for (int index = 0; index < array.length; ++index) {
            result[index] = ToLong(array[index]);
        }

        return result;
    }

    public static int[] FromLong(long value) {
        ByteBuffer buffer = ByteBuffer.wrap(ByteBuffer.allocate(8).putLong(value).array());
        return new int[] {buffer.getInt(), buffer.getInt()};
    }

    public static int[][] FromLongArray(long[] array) {
        int[][] result = new int[array.length][];
        for (int index = 0; index < array.length; ++index) {
            result[index] = FromLong(array[index]);
        }

        return result;
    }

    public static String EscapeNull(String value) {
        return value == null ? NULL_STRING_VALUE : value;
    }

    public static String UnescapeNull(String value) {
        return value.equals(NULL_STRING_VALUE) ? null : value;
    }

    private final static String NULL_STRING_VALUE = WebUtils.NULL_STRING_VALUE;

    public static void main(String args[]) {
        try{
            ORB orb = ORB.init(args, null);
            POA rootPoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPoa.the_POAManager().activate();
            CorbaServer controllerCorba = new CorbaServer();
            controllerCorba.setORB(orb);
            org.omg.CORBA.Object ref = rootPoa.servant_to_reference(controllerCorba);
            DatabaseControllerCorba href = DatabaseControllerCorbaHelper.narrow(ref);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            NameComponent path[] = ncRef.to_name("CorbaServer");
            ncRef.rebind(path, href);
            System.out.println("Corba server is ready and waiting...");
            orb.run();
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }

        System.out.println("HelloServer Exiting ...");
    }
}
