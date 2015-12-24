package me.az1.dblab.Common;

import me.az1.dblab.Server.CorbaServer;

import java.io.IOException;
import java.rmi.RemoteException;

public class DatabaseControllerCorbaAdapter implements DatabaseController {
    protected DatabaseControllerCorba controller;
    public DatabaseControllerCorbaAdapter(DatabaseControllerCorba controller) {
        this.controller = controller;
    }

    @Override
    public boolean IsOpened() throws RemoteException {
        return controller.IsOpened();
    }

    @Override
    public void NewDatabase() throws RemoteException {
        controller.NewDatabase();
    }

    @Override
    public void LoadDatabase(String path) throws IOException, ClassNotFoundException {
        controller.LoadDatabase(path);
    }

    @Override
    public void SaveDatabase(String path) throws IOException {
        controller.SaveDatabase(path);
    }

    @Override
    public void CloseDatabase() throws RemoteException {
        controller.CloseDatabase();
    }

    @Override
    public long[] DatabaseGetTableVersions() throws RemoteException {
        return CorbaServer.ToLongArray(controller.DatabaseGetTableVersions());
    }

    @Override
    public long DatabaseAddEmptyTable(Scheme scheme, String name) throws RemoteException {
        return CorbaServer.ToLong(controller.DatabaseAddEmptyTable(scheme.ToString(), name));
    }

    @Override
    public boolean DatabaseRemoveTable(long version) throws RemoteException {
        return controller.DatabaseRemoveTable(CorbaServer.FromLong(version));
    }

    @Override
    public long[] DatabaseGetTableRowVersions(long version) throws RemoteException {
        return CorbaServer.ToLongArray(controller.DatabaseGetTableRowVersions(CorbaServer.FromLong(version)));
    }

    @Override
    public int DatabaseSize() throws RemoteException {
        return controller.DatabaseSize();
    }

    @Override
    public int DatabaseTableSize(long version) throws RemoteException {
        return controller.DatabaseTableSize(CorbaServer.FromLong(version));
    }

    @Override
    public String DatabaseTableName(long version) throws RemoteException {
        return CorbaServer.UnescapeNull(controller.DatabaseTableName(CorbaServer.FromLong(version)));
    }

    @Override
    public int DatabaseTableRowLength(long version) throws RemoteException {
        return controller.DatabaseTableRowLength(CorbaServer.FromLong(version));
    }

    @Override
    public long DatabaseTableAddEmptyRow(long version) throws RemoteException {
        return CorbaServer.ToLong(controller.DatabaseTableAddEmptyRow(CorbaServer.FromLong(version)));
    }

    @Override
    public boolean DatabaseTableRemoveRow(long tableVersion, long rowVersion) throws RemoteException {
        return controller.DatabaseTableRemoveRow(CorbaServer.FromLong(tableVersion), CorbaServer.FromLong(rowVersion));
    }

    @Override
    public byte[] DatabaseTableGetField(long tableVersion, long rowVersion, int column) throws RemoteException {
        return controller.DatabaseTableGetField(CorbaServer.FromLong(tableVersion), CorbaServer.FromLong(rowVersion), column);
    }

    @Override
    public String DatabaseTableGetFieldString(long tableVersion, long rowVersion, int column) throws RemoteException {
        return CorbaServer.UnescapeNull(controller.DatabaseTableGetFieldString(CorbaServer.FromLong(tableVersion), CorbaServer.FromLong(rowVersion), column));
    }

    @Override
    public void DatabaseTableSetField(long tableVersion, long rowVersion, int column, byte[] value) throws RemoteException {
        controller.DatabaseTableSetFieldData(CorbaServer.FromLong(tableVersion), CorbaServer.FromLong(rowVersion), column, value);
    }

    @Override
    public void DatabaseTableSetFieldStr(long tableVersion, long rowVersion, int column, String strValue) throws RemoteException {
        controller.DatabaseTableSetFieldString(CorbaServer.FromLong(tableVersion), CorbaServer.FromLong(rowVersion), column, CorbaServer.EscapeNull(strValue));
    }

    @Override
    public long DatabaseTableFind(long tableVersion, String pattern) throws RemoteException {
        return CorbaServer.ToLong(controller.DatabaseTableFind(CorbaServer.FromLong(tableVersion), pattern));
    }

    @Override
    public long DatabaseTableRemoveDuplicates(long tableVersion) throws RemoteException {
        return CorbaServer.ToLong(controller.DatabaseTableRemoveDuplicates(CorbaServer.FromLong(tableVersion)));
    }
}
