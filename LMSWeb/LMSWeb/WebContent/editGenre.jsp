<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Genre"%>
<%
	AdministratorService adminService = new AdministratorService();
	String genreId = request.getParameter("genreId");
	Genre genre = adminService.readOneGenre(Integer
			.parseInt(genreId));
%>


<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">&times;</button>
	<h4 class="modal-title">Edit Genre</h4>
</div>
 
<div class="modal-body">
	<form action="editGenre" method="post">
	<div class="form-group">
		
		<label>Enter Genre Name:</label>
		 <input class="form-control" type="text" name="genreName"
			value='<%=genre.getGenreName()%>'> 
	</div>
		
		<input type="hidden" name="authorId" value=<%=genre.getGenreId()%>>
	
	<div class="form-group">
		 <input class="btn btn-md btn-info" type="submit" />
	</div>	
		
	</form>
</div>
 