package me.az1.dblab.Common;

import me.az1.dblab.Common.DatabaseControllerWeb;
import me.az1.dblab.Common.DatabaseControllerWebNullUnescapeAdapter;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class WebUtils {
    public static final String NULL_STRING_VALUE = "3thjZJfeiui805aq73HPtmA0n0lunUxD8stuvyUxEScgfzr6NoamVk5KHTZd";
    public static DatabaseControllerWeb GetWebServiceController() {
        try {
            URL url = new URL("http://localhost:7777/ws_db"); //laba-webservice.herokuapp.com/ws_db?wsdl
            QName qname = new QName("http://Common.dblab.az1.me/", "DatabaseControllerService");
            Service service = Service.create(url, qname);
            return new DatabaseControllerWebNullUnescapeAdapter(service.getPort(DatabaseControllerWeb.class));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String FromBase64(String s) {
        try {
            return new String(Base64.getDecoder().decode(s), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }

    public static long[] GetTableVersions(String db) {
        String[] tables = db.split("\\;");
        long[] result = new long[tables.length];
        for (int index = 0; index < tables.length; ++index) {
            String[] data = tables[index].split("\\:");
            result[index] = Long.parseLong(data[0]);
        }

        return result;
    }

    public static String[] GetTableNames(String db) {
        String[] tables = db.split("\\;");
        String[] result = new String[tables.length];
        for (int index = 0; index < tables.length; ++index) {
            String[] data = tables[index].split("\\:");
            result[index] = FromBase64(data[1]);
        }

        return result;
    }

    public static int GetNumCols(String db, long table) {
        String[] tables = db.split("\\;");
        for (int index = 0; index < tables.length; ++index) {
            String[] data = tables[index].split("\\:");
            if (table == Long.parseLong(data[0])) {
                return Integer.parseInt(data[2]);
            }
        }

        return 0;
    }

    public static String[][] GetFields(String db, long table) {
        String[] tables = db.split("\\;");
        for (int index = 0; index < tables.length; ++index) {
            String[] data = tables[index].split("\\:");
            if (table == Long.parseLong(data[0])) {
                if (data.length < 4) {
                    return new String[][] {};
                }
                
                String[] rows = data[3].split("\\,");
                String[][] result = new String[rows.length][];
                for (int row = 0; row < rows.length; ++row) {
                    String[] fields = rows[row].split("\\|");
                    result[row] = new String[fields.length - 1];
                    for (int col = 0; col < fields.length - 1; ++col) {
                        String value = FromBase64(fields[col + 1]);
                        if (value.equals(NULL_STRING_VALUE)) {
                            value = "<NULL>";
                        }
                        result[row][col] = value;
                    }
                }

                return result;
            }
        }

        return null;
    }

    public static String GetAnimation() {
        return "    <div>" + new java.util.Date() + "    </div>\n";
    }
}
