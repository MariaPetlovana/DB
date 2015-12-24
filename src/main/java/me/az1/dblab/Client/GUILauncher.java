package me.az1.dblab.Client;

import me.az1.dblab.Client.Gui.MainPanel;
import me.az1.dblab.Common.*;
import me.az1.dblab.Server.ServerController;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.swing.*;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

public class GUILauncher {
    protected static DatabaseController GetLocalController() {
        return new ServerController();
    }

    protected static DatabaseController GetRmiJrmpController() {
        try {
            return (DatabaseController) Naming.lookup("//localhost/RmiJrmpServer");
        } catch (NotBoundException e) {
            System.out.println(e);
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println(e);
            e.printStackTrace();
        } catch (RemoteException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        return null;
    }

    protected static DatabaseController GetRmiIiopController() {
        Properties p = System.getProperties();
        p.put("java.naming.factory.initial", "com.sun.jndi.cosnaming.CNCtxFactory");
        p.put("java.naming.provider.url", "iiop://localhost:1050");

        try {
            Context context = new InitialContext();
            Object reference = context.lookup("RmiIiopServer");
            return (DatabaseController) PortableRemoteObject.narrow(reference, DatabaseController.class);
        } catch (NamingException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        return null;
    }

    protected static DatabaseController GetRmiIiopToCorbaController() {
        Properties p = System.getProperties();
        p.put("java.naming.factory.initial", "com.sun.jndi.cosnaming.CNCtxFactory");
        p.put("java.naming.provider.url", "iiop://localhost:1050");

        try {
            Context context = new InitialContext();
            Object reference = context.lookup("CorbaServer");
            return new DatabaseControllerCorbaAdapter((DatabaseControllerCorba) PortableRemoteObject.narrow(reference, DatabaseControllerCorba.class));
        } catch (NamingException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        return null;
    }

    protected static DatabaseController GetCorbaController(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            DatabaseControllerCorba corbaController = DatabaseControllerCorbaHelper.narrow(ncRef.resolve_str("CorbaServer"));
            return new DatabaseControllerCorbaAdapter(corbaController);
        } catch(Exception e) {
            System.out.println("CORBA error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    protected static DatabaseController GetWebServiceController() {
        try {
            URL url = new URL("http://localhost:7777/ws_db");
            // URL url = new URL("http://dblab-server.herokuapp.com/ws_db?wsdl");
            QName qname = new QName("http://Common.dblab.az1.me/", "DatabaseControllerService");
            Service service = Service.create(url, qname);
            return new DatabaseControllerNullUnescapeAdapter(service.getPort(DatabaseControllerWeb.class));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println("Starting gui");
        DatabaseController controller = null;
        for (String arg : args) {
            if (arg.equals("-UseLocalController")) {
                System.out.println("Local controller");
                controller = GetLocalController();
            }
            else if (arg.equals("-UseJrmpController")) {
                System.out.println("JRMP controller");
                controller = GetRmiJrmpController();
            }
            else if (arg.equals("-UseIiopController")) {
                System.out.println("IIOP controller");
                controller = GetRmiIiopController();
            }
            else if (arg.equals("-UseIiopToCorbaController")) {
                System.out.println("Iiop To Corba controller");
                controller = GetRmiIiopToCorbaController();
            }
            else if (arg.equals("-UseCorbaController")) {
                System.out.println("Corba controller");
                controller = GetCorbaController(args);
            }
            else if (arg.equals("-UseWebServiceController")) {
                System.out.println("Web service controller");
                controller = GetWebServiceController();
            }
        }

        if (controller == null) {
            System.out.println("Controller not found.");
            return;
        }

        final DatabaseController finalController = controller;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Gui");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new MainPanel(new ClientController(finalController)), BorderLayout.CENTER);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
