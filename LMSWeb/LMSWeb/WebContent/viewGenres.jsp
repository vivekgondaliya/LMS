<%@include file="layout.jsp"%>

<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Genre"%>
<%
	AdministratorService adminService = new AdministratorService();
List<Genre> genres = null;
if (request.getAttribute("genres") != null) {
	genres = (List<Genre>) request.getAttribute("genres");
} else {
	genres = adminService.readGenres(0,10);
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
						onclick="javascript:searchGenres();">
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

		<%for(int i=1; i<=(((adminService.readAllGenres().size())-1)/10)+1; i++){%>
		<li><a href="pageGenres?pageNo=<%=i%>"><%=i%></a></li>
		<%}%>
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>
<hr />

<h2 align="center">Genre List</h2>
<div>
	${result}
	<table class="table table-striped" id="genresTable"
		style="border: groove;">
		<tr>
			<th>Genre Name</th>
			<th>Edit Genre</th>
			<th>Delete Genre</th>
		</tr>
		<%
				for (Genre g : genres){
			%>
		<tr>
			<td><%=g.getGenreName()%></td>
			<td><button type="button" class="btn btn-sm btn-success"
					data-toggle="modal" data-target="#editModal"
					href="editGenre.jsp?genreId=<%=g.getGenreId()%>">Edit</button></td>
			<td><button type="button" class="btn btn-sm btn-danger"
					onclick="javascript:location.href='deleteGenre?genreId=<%=g.getGenreId()%>';">Delete</button></td>
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

<!-- Search Genre Script -->
<script>
	function searchGenres() {
		$.ajax({
			url : "searchGenre",
			data : {
				searchString : $('#searchString').val()
			}
		}).done(function(data) {
			$('#genresTable').html(data);
		});
	}
</script>
