<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Borrower"%>
<%
	AdministratorService adminService = new AdministratorService();
List<Borrower> borrowers = null;
if (request.getAttribute("borrowers") != null) {
	borrowers = (List<Borrower>) request.getAttribute("borrowers");
} else {
	borrowers = adminService.readBorrowers(0,10);
}
%>
<%@include file="layout.jsp"%>

<div class="container">
	<div class="row">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="input-group custom-search-form">
				<input type="text" id="searchString" name="serachString"
					placeholder="Enter name to search" class="form-control"> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button"
						onclick="javascript:searchBorrowers();">
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

		<%for(int i=1; i<=(((adminService.readAllBorrower().size())-1)/10)+1; i++){%>
		<li><a href="pageBorrowers?pageNo=<%=i%>"><%=i%></a></li>
		<%}%>
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>

<h2 align="center">Borrower List</h2>
<hr />
<div style="border: margin:5px; padding-bottom: 10px;">
	${result}
	<table class="table table-striped" id="borrowersTable" style="border: groove;">
		<tr>
			<th>Count</th>
			<th>Name</th>
			<th>Address</th>
			<th>Phone</th>
			<th>Edit Borrower</th>
			<th>Delete Borrower</th>
		</tr>
		<%
			int count = 1; for (Borrower b : borrowers){
		%>
		<tr>
			<td><%=count%></td>
			<td><%=b.getName()%></td>
			<td><%=b.getAddress()%></td>
			<td><%=b.getPhone()%></td>
			<td><button type="button" class="btn btn-sm btn-success"
					data-toggle="modal" data-target="#editModal"
					href="editBorrower.jsp?cardNo=<%=b.getCardNo()%>">Edit</button></td>
			<td><button type="button" class="btn btn-sm btn-danger"
					onclick="javascript:location.href='deleteBorrower?cardNo=<%=b.getCardNo()%>';">Delete</button></td>
		</tr>
		<%
			count++;}
		%>
	</table>
	
<button type="button" class="btn btn-sm btn-warning"
	style="margin: 5px; width: 100px;"
	onClick="location.href = 'admin.jsp';">Go Back</button>

<div id="editModal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-md">
		<div class="modal-content"></div>
	</div>
</div>

<!-- Search Borrower Script -->
<script>
	function searchBorrowers() {
		$.ajax({
			url : "searchBorrowers",
			data : {
				searchString : $('#searchString').val()
			}
		}).done(function(data) {
			$('#borrowersTable').html(data);
		});
	}
</script>
