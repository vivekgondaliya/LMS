<%@ include file="layout.jsp"%>
<div class="container" style="border: groove;">
	<h2 align="left">Enter Borrower Details</h2>
	<hr />
	<form action="addBorrower" method="post">
		<div class="form-group">
			<label for="name">Enter Borrower Name:</label> <input type="text"
				name="borrowerName" class="form-control"
				placeholder="Enter Borrower Name" />
		</div>
		<div class="form-group">
			<label for="address">Enter Borrower Address:</label> <input
				type="text" name="borrowerAddress" class="form-control"
				placeholder="Enter Borrower Address" />
		</div>
		<div class="form-group">
			<label for="phone">Enter Borrower Phone:</label> <input type="text"
				name="borrowerPhone" class="form-control"
				placeholder="Enter Borrower Phone" />
		</div>
		<input type="submit" class="btn btn-sm btn-default"
			style="margin: 5px; width: 100px;" value="Add Borrower" />
		<button type="button" id="cancel" class="btn btn-sm btn-default" style="margin:5px; width:100px;" onClick="cancel();">Cancel</button>
	</form>
</div>

<script>
(function($) {

 // wire up Remove Last Item button click event
    $("#cancel").click(function(event)
    {
      event.preventDefault(); // cancel default behavior
      window.location="borrowerAdmin.jsp";
    });
})(jQuery);
</script>