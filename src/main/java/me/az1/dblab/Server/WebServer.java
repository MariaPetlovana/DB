package me.az1.dblab.Server;

import java.io.File;
import org.apache.catalina.startup.Tomcat;

public class WebServer {
    public static void main(String[] args) throws Exception {
        String webAppDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();

        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));

        tomcat.addWebapp("/", new File(webAppDirLocation).getAbsolutePath());
        System.out.println("Configuring app with basedir: " + new File("./" + webAppDirLocation).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}
