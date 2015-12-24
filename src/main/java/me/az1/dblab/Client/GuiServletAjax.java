package me.az1.dblab.Client;

import me.az1.dblab.Common.DatabaseControllerWeb;
import me.az1.dblab.Common.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "GuiServletAjax",
        urlPatterns = {"/db_gui.ajax"}
)
public class GuiServletAjax extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        DatabaseControllerWeb controller = GuiServlet.GetControllerWeb();
        String activeTableStr = req.getParameter("version");
        Long activeTable = activeTableStr.equals("null") ? null : Long.parseLong(activeTableStr);

        String action = req.getParameter("action");
        if (action.equals("show_database")) {
            String db = controller.GetDatabase();
            WriteDatabase(out, db, activeTable);
        }
        else if (action.equals("table_find")) {
            String pattern = req.getParameter("pattern");
            System.out.println(activeTable + "    " + pattern);
            Long newTable = controller.DatabaseTableFind(activeTable, pattern);
            String db = controller.GetDatabase();
            WriteDatabase(out, db, newTable);
        }
        else if (action.equals("delete_table")) {
            controller.DatabaseRemoveTable(activeTable);
            String db = controller.GetDatabase();
            WriteDatabase(out, db, null);
        }
        else if (action.equals("table_remove_duplicates")) {
            Long newTable = controller.DatabaseTableRemoveDuplicates(activeTable);
            String db = controller.GetDatabase();
            WriteDatabase(out, db, newTable);
        }

        out.flush();
        out.close();
    }

    private void WriteDatabase(ServletOutputStream out, String db, Long activeTable) throws IOException {
        long[] tables = WebUtils.GetTableVersions(db);
        String[] names = WebUtils.GetTableNames(db);

        out.print("    <form action=\"\"> \n");
        out.print("      <select name=\"tables\" onchange=\"showDatabase(this.value)\">\n");
        out.print("        <option value=\"null\">Select a table:</option>\n");
        for (int index = 0; index < tables.length; ++index) {
            String isSelected = "";
            if (activeTable != null && tables[index] == activeTable) {
                isSelected = "selected=\"selected\"";
            }

            out.print("        <option " + isSelected + "value=\"" + tables[index] + "\">" + names[index] + "</option>\n");
        }

        out.print("      </select>\n");
        out.print("    </form>\n");

        if (activeTable == null) {
            return;
        }

        int nCols = WebUtils.GetNumCols(db, activeTable);
        String pattern = "";
        for (int col = 0; col < nCols; ++col) {
            if (col > 0) {
              pattern += "|";
            }

            pattern += ".*";
        }

        out.print("    <form>\n");
        out.print("      <input type=\"button\" value=\"Find\" onclick=\"tableFind('" + activeTable + "')\" />\n");
        out.print("      <input id=\"pattern\" type=\"text\" name=\"pattern\" value=\"" + pattern + "\" />\n");
        out.print("    </form>\n");
        out.print("    <form>\n");
        out.print("      <input type=\"button\" value=\"Delete this table\" onclick=\"deleteTable('" + activeTable + "')\" />\n");
        out.print("    </form>\n");
        out.print("    <form>\n");
        out.print("      <input type=\"button\" value=\"Remove duplicates\" onclick=\"tableRemoveDuplicates('" + activeTable + "')\" />\n");
        out.print("    </form>\n");

        String[][] fields = WebUtils.GetFields(db, activeTable);
        out.print("    <table border=\"1\" style=\"width:100%\">\n");
        for (int row = 0; row < fields.length; ++row) {
            out.print("      <tr>\n");
            for (int col = 0; col < fields[row].length; ++col) {
                out.print("        <td>" + fields[row][col]+ "</td>\n");
            }

            out.print("      </tr>\n");
        }

        out.print("    </table>\n");

        
    }
}
