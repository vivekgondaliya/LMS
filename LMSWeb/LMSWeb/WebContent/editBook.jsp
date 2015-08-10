<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Book"%>
<%@page import="com.gcit.lms.domain.Publisher"%>
<%@page import="com.gcit.lms.domain.Author"%>
<%@page import="com.gcit.lms.domain.Genre"%>

<%
	AdministratorService adminService = new AdministratorService();
	String bookId = request.getParameter("bookId");
	Book book = adminService.readOneBook(Integer.parseInt(bookId));
	List<Publisher> publishers = adminService.readAllPublisher();
	List<Author> authors = adminService.readAllAuthors();
	List<Genre> genres = adminService.readAllGenres();
%>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">&times;</button>
	<h4 class="modal-title">Edit Book</h4>
</div>

<div class="modal-body">
	<form action="editBook" method="post">
		<div class="form-group">
			<label>Enter Book Title:</label> <input class="form-control"
				type="text" name="title" value='<%=book.getTitle()%>' />
		</div>

		<div class="form-group">
			<label>Select Publisher: </label>
			<select class="form-control" name="selectPublisher">
				<%for (Publisher p : publishers) {%>
				<option value=<%=p.getPublisherId()%>><%=p.getPublisherName()%></option>
				<%}%></select>
		</div> 
		
		<div class="form-group">
			<label>Select Author(s):</label>
			<select multiple class="form-control" name="selectAuthor">
				<%for (Author a : authors) {%>
				<option value=<%=a.getAuthorId()%>><%=a.getAuthorName()%></option>
				<%}%></select>
		</div>
		
		<div class="form-group">
			<label>Select Genre(s):</label>
			<select multiple class="form-control" name="selectGenre">
				<%for (Genre g : genres) {%>
				<option value=<%=g.getGenreId()%>><%=g.getGenreName()%></option>
				<%}%></select>
		</div>
				
			<input type="hidden" name="bookId" value=<%=book.getBookId()%>>
		<input type="submit" />
	</form>

</div>

