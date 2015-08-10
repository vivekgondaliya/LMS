<%@include file="layout.jsp"%>

<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Author"%>
<%
	AdministratorService adminService = new AdministratorService();
List<Author> authors = null;
if (request.getAttribute("authors") != null) {
	authors = (List<Author>) request.getAttribute("authors");
} else {
	authors = adminService.readAuthors(0,10);
}
%>


<div class="container">
	<div class="row">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="input-group custom-search-form">
				<input type="text" id="searchString" name="serachString"
					placeholder="Enter name to search" class="form-control"> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button"
						onclick="javascript:searchAuthors();">
						<span class="glyphicon glyphicon-search"></span>
					</button>
				</span>
			</div>
			<!-- /input-group -->
		</div>
		<div class="col-lg-4"></div>
	</div>
</div>


<!-- Populate Dynamically -->
<nav align="right" style="margin: 0px;">
	<ul class="pagination" id="page">

		<%for(int i=1; i<=(((adminService.readAllAuthors().size())-1)/10)+1; i++){%>
		<li><a href="pageAuthors?pageNo=<%=i%>"><%=i%></a></li>
		<%}%>
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>
<hr />

<h2 align="center">Author List</h2>
<div>
	${result}
	<table class="table table-striped" id="authorsTable"
		style="border: groove;">
		<tr>
			<th>Author Name</th>
			<th>Edit Author</th>
			<th>Delete Author</th>
		</tr>
		<%
				for (Author a : authors){
			%>
		<tr>
			<td><%=a.getAuthorName()%></td>
			<td><button type="button" class="btn btn-sm btn-success"
					data-toggle="modal" data-target="#editModal"
					href="editAuthor.jsp?authorId=<%=a.getAuthorId()%>">Edit</button></td>
			<td><button type="button" class="btn btn-sm btn-danger"
					onclick="javascript:location.href='deleteAuthor?authorId=<%=a.getAuthorId()%>';">Delete</button></td>
		</tr>
		<%
			}
			%>
	</table>

	<button type="button" class="btn btn-sm btn-warning"
		style="margin: 5px; width: 100px;"
		onClick="location.href = 'author.jsp';">Go Back</button>

	<div id="editModal" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel" data-backdrop="true">
		<div class="modal-dialog modal-md">
			<div class="modal-content"></div>
		</div>
	</div>
</div>

<!-- Search Author Script -->
<script>
	function searchAuthors() {
		$.ajax({
			url : "searchAuthors",
			data : {
				searchString : $('#searchString').val()
			}
		}).done(function(data) {
			$('#authorsTable').html(data);
		});
	}
</script>
