<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Borrower"%>
<%
	AdministratorService adminService = new AdministratorService();
	String cardNo = request.getParameter("cardNo");
	Borrower borrower = adminService.readOneBorrower(Integer
			.parseInt(cardNo));
%>


<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">&times;</button>
	<h4 class="modal-title">Edit Borrower</h4>
</div>
<div class="modal-body">
	<form action="editBorrower" method="post">
		<div class="form-group">
			Enter Borrower Name: <input class="form-control" type="text" name="borrowerName"
			value='<%=borrower.getName()%>'>
		</div>
		<div class="form-group">
			Enter Borrower Address: <input class="form-control" type="text" name="borrowerAddress"
			value='<%=borrower.getAddress()%>'>
		</div>
		<div class="form-group">
			Enter Borrower Phone: <input class="form-control" type="text" name="borrowerPhone"
			value='<%=borrower.getPhone()%>'>	
		</div>
		<input type="hidden" name="cardNo" value=<%=borrower.getCardNo()%>>
		 <div class="form-group">
		 <input class="btn btn-sm btn-info" type="submit" />
		 </div>
	</form>
</div>