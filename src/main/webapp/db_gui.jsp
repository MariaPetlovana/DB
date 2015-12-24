<%@include file="utils.jsp"%>
<html>
  <body>
<%=WebUtils.GetAnimation()%>
    <%
      if (!controller.IsOpened()) {
        %>
          <h2>Database is not opened.</h2>
        <%
      }
      else {
        %>
          <h2>Database is opened.</h2>
        <%
        String db = controller.GetDatabase();
        long[] tables = WebUtils.GetTableVersions(db);
        String[] names = WebUtils.GetTableNames(db);

        for (int index = 0; index < tables.length; ++index) {
          %>
            <button type="button" onclick=location.href="controller.jsp?action=show_table&table_version=<%= tables[index]%>">
              <%=names[index]%>
            </button>
          <%
        }

        Long activeTable = (Long) session.getAttribute("active_table");
        int activeTableIndex = -1;
        for (int index = 0; index < tables.length; ++index) {
          if (activeTable != null && activeTable.equals(tables[index])) {
            activeTableIndex = index;
          }
        }

        if (activeTableIndex == -1) {
          activeTable = null;
        }

        session.setAttribute("active_table", activeTable);

        if (activeTable != null) {
          %>
            <h4>Table: <%=names[activeTableIndex]%></h4>
          <%
          int nCols = WebUtils.GetNumCols(db, activeTable);
          String pattern = "";
          for (int col = 0; col < nCols; ++col) {
            if (col > 0) {
              pattern += "|";
            }

            pattern += ".*";
          }

          %>
            <form action="controller.jsp" method="GET">
              <input type="hidden" name="action" value="table_find" />
              <input type="hidden" name="table_version" value="<%=activeTable%>" />
              <input type="submit" value="Find" />
              <input type="text" name="pattern" value="<%=pattern%>" />
            </form>
            <form action="controller.jsp" method="GET">
              <input type="hidden" name="action" value="table_delete" />
              <input type="hidden" name="table_version" value="<%=activeTable%>" />
              <input type="submit" value="Delete this table" />
            </form>
          <%

          String[][] fields = WebUtils.GetFields(db, activeTable);
          %>
            <table border="1" style="width:100%">
          <%
          for (int row = 0; row < fields.length; ++row) {
            %><tr><%
            for (int col = 0; col < fields[row].length; ++col) {
              %><td><%=fields[row][col]%></td><%
            }

            %></tr><%
          }

          %></table><%
        }
      }
    %>
  </body>
</html>
