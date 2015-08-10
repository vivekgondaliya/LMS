<%@page import="com.gcit.lms.service.AdministratorService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.domain.Publisher"%>
<%
	AdministratorService adminService = new AdministratorService();
	String publisherId = request.getParameter("publisherId");
	Publisher publisher = adminService.readOnePublisher(Integer
			.parseInt(publisherId));
%>


<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">&times;</button>
	<h4 class="modal-title">Edit Publisher</h4>
</div>
<div class="modal-body">
	<form class="form-group" style="padding:5px;" action="editPublisher" method="post">
		Enter Publisher Name: <input class="form-control" type="text" name="publisherName" value='<%=publisher.getPublisherName()%>'> 
		Enter Publisher Address: <input class="form-control" type="text" name="publisherAddress" value='<%=publisher.getPublisherAddress()%>'> 
		Enter Publisher Phone: <input class="form-control" type="text" name="publisherPhone"value='<%=publisher.getPublisherPhone()%>'>
		 <input type="hidden" name="publisherId" value=<%=publisher.getPublisherId()%>> 
		 <input type="submit" class="btn btn-default"/>
		 <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	</form>
</div>