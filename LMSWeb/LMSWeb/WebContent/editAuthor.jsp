<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Author"%>
<%
	AdministratorService adminService = new AdministratorService();
	String authorId = request.getParameter("authorId");
	Author author = adminService.readOneAuthor(Integer
			.parseInt(authorId));
%>


<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">&times;</button>
	<h4 class="modal-title">Edit Author</h4>
</div>
 
<div class="modal-body">
	<form action="editAuthor" method="post">
	<div class="form-group">
		
		<label>Enter Author Name:</label>
		 <input class="form-control" type="text" name="authorName"
			value='<%=author.getAuthorName()%>'> 
	</div>
		
		<input type="hidden" name="authorId" value=<%=author.getAuthorId()%>>
	
	<div class="form-group">
		 <input class="btn btn-md btn-info" type="submit" />
	</div>	
		
	</form>
</div>
 