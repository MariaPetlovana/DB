package me.az1.dblab.Common;

import me.az1.dblab.Server.CorbaServer;

import java.io.IOException;
import java.rmi.RemoteException;

public class DatabaseControllerWebNullUnescapeAdapter extends DatabaseControllerNullUnescapeAdapter implements DatabaseControllerWeb {
    private  DatabaseControllerWeb controllerWeb;
    public DatabaseControllerWebNullUnescapeAdapter(DatabaseControllerWeb controllerWeb) {
        super(controllerWeb);

        this.controllerWeb = controllerWeb;
    }

    @Override
    public String GetDatabase() throws RemoteException {
        return this.controllerWeb.GetDatabase();
    }
}
