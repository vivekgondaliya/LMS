<%@include file="layout.jsp"%>

<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Book"%>
<%
	AdministratorService adminService = new AdministratorService();
	List<Book> books = null;
	if (request.getAttribute("books") != null) {
		books = (List<Book>) request.getAttribute("books");
	} else {
		books = adminService.readBooks(0,10);
	}
%>

<div class="container">
	<div class="row">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="input-group custom-search-form">
				<input type="text" id="searchString" name="serachString"
					placeholder="Enter title to search" class="form-control"> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button"
						onclick="javascript:searchBooks();">
						<span class="glyphicon glyphicon-search"></span>
					</button>
				</span>

			</div>
			<!-- /input-group -->
		</div>
		<div class="col-lg-4"></div>
	</div>
</div>

<div class="container">
	<div class="row">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			Search In: <select class="form-control" name="serachIn">
				<option value="">Title</option>
				<option value="">Authors</option>
				<option value="">Genre</option>
			</select>
		</div>
		<div class="col-lg-4"></div>
	</div>

	<!-- Populate Dynamically -->
	<nav align="right" style="margin: 0px;">
		<ul class="pagination" id="page">

			<%
				for(int i=1; i<=(((adminService.readAllBooks().size())-1)/10)+1; i++){
			%>
			<li><a href="pageBooks?pageNo=<%=i%>"><%=i%></a></li>
			<%
				}
			%>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</nav>
	${result}
	<hr />

	<div style="border: margin:5px; padding-bottom: 10px;">
		<h2 align="center">Book List</h2>
		<table class="table table-striped" id="booksTable"
			style="border: groove;">
			<tr>
				<th>Count</th>
				<th>Book Title</th>
				<th>Publisher Name</th>
				<th>Author(s)(Coming Soon)</th>
				<th>Genre(s)(Coming Soon)</th>
				<th>Edit Book</th>
				<th>Delete Book</th>
			</tr>
			<%
				int count= 1; for(Book b: books){
			%>
			<tr>
				<td><%=count%></td>
				<td><%=b.getTitle()%></td>
				<td><%=b.getPublisher().getPublisherName()%></td>
				<td><%="n/a"%></td>
				<td><%="n/a"%></td>
				<td><button type="button" class="btn btn-sm btn-success"
						data-toggle="modal" data-target="#myModal1"
						href="editBook.jsp?bookId=<%=b.getBookId()%>">Edit</button></td>
				<td><button type="button" class="btn btn-sm btn-danger"
						onclick="javascript:location.href='deleteBook?bookId=<%=b.getBookId()%>';">Delete</button></td>
			</tr>
			<%
				count++;}
			%>
		</table>
	</div>

	<button type="button" class="btn btn-sm btn-warning"
		style="margin: 5px; width: 100px;"
		onClick="location.href = 'author.jsp';">Go Back</button>

	<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel" data-backdrop="true">
		<div class="modal-dialog modal-md">
			<div class="modal-content"></div>
		</div>
	</div>
</div>

<!-- Search Books Script -->
<script>
	function searchBooks() {
		$.ajax({
			url : "searchBooks",
			data : {
				searchString : $('#searchString').val()
			}
		}).done(function(data) {
			$('#booksTable').html(data);
		});
	}
</script>