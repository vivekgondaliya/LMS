<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.LibraryBranch"%>
<%
	AdministratorService adminService = new AdministratorService();
	String branchId = request.getParameter("branchId");
	LibraryBranch  lb= adminService.readOneLibraryBranch(Integer
			.parseInt(branchId));
%>


<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">&times;</button>
	<h4 class="modal-title">Edit Branch</h4>
</div>

<div class="modal-body" data-backdrop="true">
	<form action="editBranch" method="post">
		<div class="form-group">
			<label>Enter Branch Name:</label> <input class="form-control"
				type="text" name="branchName" value='<%=lb.getBranchName()%>'>
		</div>

		<div class="form-group">
			<label>Enter Branch Address:</label> <input class="form-control"
				type="text" name="branchAddress" value='<%=lb.getBranchAddress()%>'>
		</div>

		<input type="hidden" name="branchId" value=<%=lb.getBranchId()%>>
		<div class="form-group">
			<input type="submit" class="btn btn-info btn-md " />
		</div>
		
	</form>
</div>
