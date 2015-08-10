<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Publisher"%>
<%
	AdministratorService adminService = new AdministratorService();
	List<Publisher> publishers = null;
	if (request.getAttribute("publishers") != null) {
		publishers = (List<Publisher>) request
				.getAttribute("publishers");
	} else {
		publishers = adminService.readPublishers(0, 10);
	}
%>
<%@include file="layout.jsp"%>

<div class="container">
	<div class="row">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="input-group custom-search-form">
				<input type="text" id="searchString" name="serachString"
					placeholder="Enter name, address or phone to search" class="form-control"> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button"
						onclick="javascript:searchPublishers();">
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

		<%for(int i=1; i<=(((adminService.readAllPublisher().size())-1)/10)+1; i++){%>
		<li><a href="pagePublishers?pageNo=<%=i%>"><%=i%></a></li>
		<%}%>
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>
<hr />

<div class="container">
	${result}

	<div style="border: margin:5px; padding-bottom: 10px;">
		<h2 align="center">
			<em>Publisher List</em>
		</h2>
		<table class="table table-striped" id="publishersTable" style="border: groove;">
			<tr>
				<th>Publisher Name</th>
				<th>Publisher Address</th>
				<th>Publisher Phone</th>
				<th>Edit Publisher</th>
				<th>Delete Publisher</th>
			</tr>
			<%
				for (Publisher p : publishers) {
			%>
			<tr>
				<td><%=p.getPublisherName()%></td>
				<td><%=p.getPublisherAddress()%></td>
				<td><%=p.getPublisherPhone()%></td>
				<td><button type="button" class="btn btn-sm btn-success"
						data-toggle="modal" data-target="#myModal1"
						href="editPublisher.jsp?publisherId=<%=p.getPublisherId()%>">Edit</button></td>
				<td><button type="button" class="btn btn-sm btn-danger"
						onclick="javascript:location.href='deletePublisher?publisherId=<%=p.getPublisherId()%>';">Delete</button></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>

	<div>
		<hr />
		<button type="button" class="btn btn-sm btn-warning"
			style="margin: 5px; width: 100px;"
			onClick="location.href = 'publisher.jsp';">Go Back</button>
	</div>


	<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg">
			<div class="modal-content"></div>
		</div>
	</div>

</div>

<!-- Search Author Script -->
<script>
	function searchPublishers() {
		$.ajax({
			url : "searchPublishers",
			data : {
				searchString : $('#searchString').val()
			}
		}).done(function(data) {
			$('#publishersTable').html(data);
		});
	}
</script>