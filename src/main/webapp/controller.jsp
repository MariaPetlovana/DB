<%@include file="utils.jsp"%>
<%
  String action = request.getParameter("action");
  if (action.equals("show_table")) {
    session.setAttribute("active_table", Long.parseLong(request.getParameter("table_version")));
  }
  else if (action.equals("table_find")) {
    long table = Long.parseLong(request.getParameter("table_version"));
    String pattern = request.getParameter("pattern");
    Long newTable = controller.DatabaseTableFind(table, pattern);
    session.setAttribute("active_table", newTable);
  }
  else if (action.equals("table_delete")) {
    long table = Long.parseLong(request.getParameter("table_version"));
    controller.DatabaseRemoveTable(table);
    session.setAttribute("active_table", null);
  }
  response.sendRedirect("db_gui.jsp");
%>