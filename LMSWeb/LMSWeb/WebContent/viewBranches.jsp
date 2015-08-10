<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.LibraryBranch"%>
<%
	AdministratorService adminService = new AdministratorService();
	List<LibraryBranch> branchs = null;
	if (request.getAttribute("branchs") != null) {
		branchs = (List<LibraryBranch>) request.getAttribute("branchs");
	} else {
		branchs = adminService.readBranches(0,10);
	}
%>

<%@include file="layout.jsp"%>

<div class="container">
	<div class="row">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="input-group custom-search-form">
				<input type="text" id="searchString" name="serachString"
					placeholder="Enter name or address to search" class="form-control">
				<span class="input-group-btn">
					<button class="btn btn-default" type="button"
						onclick="javascript:searchBranchs();">
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

		<%
			for(int i=1; i<=(((adminService.readAllLibraryBranch().size())-1)/10)+1; i++){
		%>
		<li><a href="pageBranches?pageNo=<%=i%>"><%=i%></a></li>
		<%
			}
		%>
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>
<hr />
<div class="container">
	<h2 align="center">
		<em>Library Branch List</em>
	</h2>
	<div>
		${result}
		<table class="table table-striped" id="branchsTable"
			style="border: groove;">
			<tbody>
				<tr>
					<th>Branch Name</th>
					<th>Branch Address</th>
					<th>Edit Branch</th>
					<th>Delete Branch</th>
				</tr>
				<%
					for (LibraryBranch lb : branchs) {
				%>
				<tr>
					<td><%=lb.getBranchName()%></td>
					<td><%=lb.getBranchAddress()%></td>
					<td><button type="button" class="btn btn-sm btn-success"
							data-toggle="modal" data-target="#editModal"
							onclick="javascript:editBranch();"
							href="editBranch.jsp?branchId=<%=lb.getBranchId()%>">Edit</button></td>
					<td><button type="button" class="btn btn-sm btn-danger"
							onclick="javascript:location.href='deleteLibraryBranch?branchId=<%=lb.getBranchId()%>';">Delete</button></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>

		<div>
			<hr />
			<button type="button" class="btn btn-sm btn-warning"
				style="margin: 5px; width: 100px;"
				onClick="location.href = 'libraryBranch.jsp';">Go Back</button>
		</div>
	</div>

	<div id="editModal" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel" data-backdrop="true">
		<div class="modal-dialog modal-md">
			<div class="modal-content"></div>
		</div>
	</div>
</div>

<!-- Search Branch Script -->
<script>
	function searchBranchs() {
		$.ajax({
			url : "searchBranches",
			data : {
				searchString : $('#searchString').val()
			}
		}).done(function(data) {
			$('#branchsTable').html(data);
		});
	}
</script>