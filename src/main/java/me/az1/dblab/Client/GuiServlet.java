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
        name = "GuiServlet",
        urlPatterns = {"/db_gui.servlet"}
)
public class GuiServlet extends HttpServlet {
    private static DatabaseControllerWeb controllerWeb = null;
    static DatabaseControllerWeb GetControllerWeb() {
        if (controllerWeb == null) {
            controllerWeb = WebUtils.GetWebServiceController();
        }

        return controllerWeb;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        //DatabaseControllerWeb controller = GetControllerWeb();
        out.print("<html>\n  <body>\n");
        out.print(WebUtils.GetAnimation());
        //if (controller.IsOpened()) {
            WriteDatabase(req, resp, out);
        //}
        //else {
        //    out.print("    <h2>Database is not opened.</h2>\n");
        //}
        out.print("  </body>\n</html>\n");
        out.flush();
        out.close();
    }

    private void WriteDatabase(HttpServletRequest req, HttpServletResponse resp, ServletOutputStream out) throws IOException {
        out.write("    <h2>Database is opened.</h2>\n".getBytes());
        // DatabaseControllerWeb controller = GetControllerWeb();
        // String db = controller.GetDatabase();
        // long[] tables = WebUtils.GetTableVersions(db);
        // String[] names = WebUtils.GetTableNames(db);

        out.print("    <script>\n" +
                "      function showDatabase(version)\n" +
                "      {\n" +
                "        var xmlhttp;    \n" +
                "        xmlhttp = new XMLHttpRequest();\n" +
                "        xmlhttp.onreadystatechange = function()\n" +
                "        {\n" +
                "          if (xmlhttp.readyState == 4 && xmlhttp.status == 200)\n" +
                "          {\n" +
                "            document.getElementById(\"database\").innerHTML = xmlhttp.responseText;\n" +
                "          }\n" +
                "        }\n" +
                "        xmlhttp.open(\"GET\", \"db_gui.ajax?action=show_database&version=\" + version, true);\n" +
                "        xmlhttp.send();\n" +
                "      }\n" +
                "      function deleteTable(version)\n" +
                "      {\n" +
                "        var xmlhttp;    \n" +
                "        xmlhttp = new XMLHttpRequest();\n" +
                "        xmlhttp.onreadystatechange = function()\n" +
                "        {\n" +
                "          if (xmlhttp.readyState == 4 && xmlhttp.status == 200)\n" +
                "          {\n" +
                "            document.getElementById(\"database\").innerHTML = xmlhttp.responseText;\n" +
                "          }\n" +
                "        }\n" +
                "        xmlhttp.open(\"GET\", \"db_gui.ajax?action=delete_table&version=\" + version, true);\n" +
                "        xmlhttp.send();\n" +
                "      }\n" +
                "      function tableRemoveDuplicates(version)\n" +
                "      {\n" +
                "        var xmlhttp;    \n" +
                "        xmlhttp = new XMLHttpRequest();\n" +
                "        xmlhttp.onreadystatechange = function()\n" +
                "        {\n" +
                "          if (xmlhttp.readyState == 4 && xmlhttp.status == 200)\n" +
                "          {\n" +
                "            document.getElementById(\"database\").innerHTML = xmlhttp.responseText;\n" +
                "          }\n" +
                "        }\n" +
                "        xmlhttp.open(\"GET\", \"db_gui.ajax?action=table_remove_duplicates&version=\" + version, true);\n" +
                "        xmlhttp.send();\n" +
                "      }\n" +
                "      function tableFind(version)\n" +
                "      {\n" +
                "        var pattern = encodeURIComponent(document.getElementById('pattern').value);\n" +
                "        var xmlhttp;    \n" +
                "        xmlhttp = new XMLHttpRequest();\n" +
                "        xmlhttp.onreadystatechange = function()\n" +
                "        {\n" +
                "          if (xmlhttp.readyState == 4 && xmlhttp.status == 200)\n" +
                "          {\n" +
                "            document.getElementById(\"database\").innerHTML = xmlhttp.responseText;\n" +
                "          }\n" +
                "        }\n" +
                "        xmlhttp.open(\"GET\", \"db_gui.ajax?action=table_find&version=\" + version + \n" +
                "            \"&pattern=\" + pattern, true);\n" +
                "        xmlhttp.send();\n" +
                "      }\n" +
                "      var div = document.createElement('div');\n" +
                "      div.id = 'database';\n" +
                "      document.getElementsByTagName('body')[0].appendChild(div);\n" + 
                "      showDatabase('null');\n" +
                "    </script>\n");

        out.print("    <div id=\"table\">\n");
        out.print("    </div>\n");
    }
}
