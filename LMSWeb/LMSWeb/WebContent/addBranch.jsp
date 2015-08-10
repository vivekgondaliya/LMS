<%@ include file="layout.jsp"%>
<div class="container" style="border: groove;">
	<h2 align="left">
		<em>Enter Branch Details</em>
	</h2><hr/>
	<div class="col-sm-4"></div>
	<div class="col-sm-4">
		<form action="addBranch" method="post">
			<div class="form-group">
			<label for="name">Enter Branch Name:</label> <input type="text"
				name="branchName" class="form-control"
				placeholder="Enter Branch Name" />
			</div>
			<div class="form-group">
			<label for="name">Enter Branch Address:</label> <input type="text"
				name="branchAddress" class="form-control"
				placeholder="Enter Branch Address" />
			</div>
			<input class="btn btn-sm btn-default"
						style="margin: 5px; width: 100px;" type="submit"
						value="Add Branch" />
			<button type="button" id="cancel" class="btn btn-sm btn-default" style="margin:5px; width:100px;" onClick="cancel();">Cancel</button>			
		</form>

	</div>
	<div class="col-sm-4"></div>
</div>

<script>
	(function($) {

		// wire up Remove Last Item button click event
		$("#cancel").click(function(event) {
			event.preventDefault(); // cancel default behavior
			window.location = "libraryBranch.jsp";
		});
	})(jQuery);
</script>