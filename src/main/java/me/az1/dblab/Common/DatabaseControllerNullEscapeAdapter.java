package me.az1.dblab.Common;

import me.az1.dblab.Server.CorbaServer;

import javax.jws.WebService;
import java.io.IOException;
import java.rmi.RemoteException;

@WebService(serviceName = "DatabaseControllerService",
            endpointInterface = "me.az1.dblab.Common.DatabaseController")
public class DatabaseControllerNullEscapeAdapter implements DatabaseController {
    private DatabaseController controller;

    public DatabaseControllerNullEscapeAdapter(DatabaseController controller) {
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
        return controller.DatabaseGetTableVersions();
    }

    @Override
    public long DatabaseAddEmptyTable(Scheme scheme, String name) throws RemoteException {
        return controller.DatabaseAddEmptyTable(scheme, name);
    }

    @Override
    public boolean DatabaseRemoveTable(long version) throws RemoteException {
        return controller.DatabaseRemoveTable(version);
    }

    @Override
    public long[] DatabaseGetTableRowVersions(long version) throws RemoteException {
        return controller.DatabaseGetTableRowVersions(version);
    }

    @Override
    public int DatabaseSize() throws RemoteException {
        return controller.DatabaseSize();
    }

    @Override
    public int DatabaseTableSize(long version) throws RemoteException {
        return controller.DatabaseTableSize(version);
    }

    @Override
    public String DatabaseTableName(long version) throws RemoteException {
        return controller.DatabaseTableName(version);
    }

    @Override
    public int DatabaseTableRowLength(long version) throws RemoteException {
        return controller.DatabaseTableRowLength(version);
    }

    @Override
    public long DatabaseTableAddEmptyRow(long version) throws RemoteException {
        return controller.DatabaseTableAddEmptyRow(version);
    }

    @Override
    public boolean DatabaseTableRemoveRow(long tableVersion, long rowVersion) throws RemoteException {
        return controller.DatabaseTableRemoveRow(tableVersion, rowVersion);
    }

    @Override
    public byte[] DatabaseTableGetField(long tableVersion, long rowVersion, int column) throws RemoteException {
        return controller.DatabaseTableGetField(tableVersion, rowVersion, column);
    }

    @Override
    public String DatabaseTableGetFieldString(long tableVersion, long rowVersion, int column) throws RemoteException {
        return CorbaServer.EscapeNull(controller.DatabaseTableGetFieldString(tableVersion, rowVersion, column));
    }

    @Override
    public void DatabaseTableSetField(long tableVersion, long rowVersion, int column, byte[] value) throws RemoteException {
        controller.DatabaseTableSetField(tableVersion, rowVersion, column, value);
    }

    @Override
    public void DatabaseTableSetFieldStr(long tableVersion, long rowVersion, int column, String strValue) throws RemoteException {
        controller.DatabaseTableSetFieldStr(tableVersion, rowVersion, column, CorbaServer.UnescapeNull(strValue));
    }

    @Override
    public long DatabaseTableFind(long tableVersion, String pattern) throws RemoteException {
        return controller.DatabaseTableFind(tableVersion, pattern);
    }

    @Override
    public long DatabaseTableRemoveDuplicates(long tableVersion) throws RemoteException {
        return controller.DatabaseTableRemoveDuplicates(tableVersion);
    }
}
