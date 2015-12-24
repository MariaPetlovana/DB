package me.az1.dblab.Common;

import javax.jws.WebService;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.Base64;
//import java.util.Base64;

@WebService(serviceName = "DatabaseControllerService",
        endpointInterface = "me.az1.dblab.Common.DatabaseControllerWeb")
public class DatabaseControllerWebAdapter extends DatabaseControllerNullEscapeAdapter implements DatabaseControllerWeb {
    public DatabaseControllerWebAdapter(DatabaseController controller) {
        super(controller);
    }

    @Override
    public String GetDatabase() throws RemoteException {
        StringBuilder result = new StringBuilder();
        for (long table : DatabaseGetTableVersions()) {
            if (result.length() > 0) {
                result.append(';');
            }

            int n_cols = DatabaseTableRowLength(table);

            result.append(table);
            result.append(':');
            result.append(ToBase64(DatabaseTableName(table)));
            result.append(':');
            result.append(n_cols);
            result.append(':');
            boolean first_row = true;
            for (long row : DatabaseGetTableRowVersions(table)) {
                if (first_row) {
                    first_row = false;
                }
                else {
                    result.append(',');
                }

                result.append(row);
                for (int col = 0; col < n_cols; ++col) {
                    result.append('|');
                    result.append(ToBase64(DatabaseTableGetFieldString(table, row, col)));
                }
            }
        }

        return result.toString();
    }

    public static String ToBase64(String s) {
        try {
            return Base64.getEncoder().encodeToString(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }
}
