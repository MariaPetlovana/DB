package me.az1.dblab.Server;

import me.az1.dblab.Common.DatabaseControllerNullEscapeAdapter;
import me.az1.dblab.Common.DatabaseControllerWeb;
import me.az1.dblab.Common.DatabaseControllerWebAdapter;

import javax.xml.ws.Endpoint;
import java.io.IOException;

public class DBWebService {
    public static void main(String[] args) {
    	int port = 7777;
    	if (args.length > 0) {
    		port = Integer.parseInt(args[0]);
    	}

        DatabaseControllerWeb controllerWeb = new DatabaseControllerWebAdapter(new ServerController());
        try {
            controllerWeb.LoadDatabase("sample.db");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String address = "http://0.0.0.0:" + port + "/ws_db";
        Endpoint.publish(address, controllerWeb);
        System.out.println("Web server running at " + address);
    }
}
