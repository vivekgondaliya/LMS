<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Author"%>
<%@page import="com.gcit.lms.domain.Publisher"%>
<%@page import="com.gcit.lms.domain.Genre"%>
<%
	AdministratorService adminService = new AdministratorService();
	List<Author> authors = adminService.readAllAuthors();
	List<Publisher> publishers = adminService.readAllPublisher();
	List<Genre> genres = adminService.readAllGenres();
%>

<%@include file="layout.jsp"%>
<div class="container" style="border: groove;">
	<h2 align="left">Enter Book Details</h2>
	<hr />
	<div class="col-sm-4"></div>
	<div class="col-sm-4">
		<form class="form-group" action="addBook" method="post">

			<div class="form-group">
				<label for="title">Enter Book Title:</label> <input type="text"
					name="bookTitle" class="form-control" placeholder="Enter Title" />
			</div>
			<div class="form-group">
				<label for="selectAuthor">Select Author:</label> <select multiple
					name="selectAuthor" class="form-control">
					<%
						for (Author a : authors) {
					%>
					<option value=<%=a.getAuthorId()%>><%=a.getAuthorName()%></option>
					<%
						}
					%>
				</select>
			</div>
			<div class="form-group">
				<label for="selectGenre">Select Genre:</label> <select multiple
					class="form-control" name="selectGenre">
					<%
						for (Genre g : genres) {
					%>
					<option value=<%=g.getGenreId()%>>
						<%=g.getGenreName()%></option>
					<%
						}
					%>
				</select>
			</div>
			<div class="form-group">
				<label for="selectGenre">Select Publisher:</label> <select
					class="form-control" name="selectPublisher">
					<%
						for (Publisher p : publishers) {
					%>
					<option value=<%=p.getPublisherId()%>><%=p.getPublisherName()%></option>
					<%
						}
					%>
				</select>
			</div>
			<input class="btn btn-sm btn-default"
				style="margin: 5px; width: 100px;" type="submit" value="Add Book" />
			<button type="button" id="cancel" class="btn btn-sm btn-default"
				style="margin: 5px; padding: 3px; width: 100px;" onClick="cancel();">Cancel</button>

		</form>
	</div>
	<div class="col-sm-4"></div>

</div>

<script>
	(function($) {

		// wire up Remove Last Item button click event
		$("#cancel").click(function(event) {
			event.preventDefault(); // cancel default behavior
			window.location = "author.jsp";
		});
	})(jQuery);
</script>