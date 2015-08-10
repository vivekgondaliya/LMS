<%@ include file="layout.jsp"%>
<div class="container" style="border: groove;">
	<h2 align="left">
		<em>Enter Publisher Details</em>
	</h2>
	<div class="container">
		<form class="form-group" action="addPublisher" method="post">

			<div class="form-group">
				<label for="labelPublisherName">Enter Publisher Name:</label> <input
					type="text" name="publisherName" class="form-control"
					placeholder="Enter Publlisher Name" />
			</div>

			<div class="form-group">
				<label for="labelPublisherAddress">Enter Publisher Address:</label>
				<input type="text" name="publisherAddress" class="form-control"
					placeholder="Enter Publlisher Address" />
			</div>

			<div class="form-group">
				<label for="labelPublisherPhone">Enter Publisher Phone:</label> <input
					type="text" name="publisherPhone" class="form-control"
					placeholder="Enter Publlisher Phone" />
			</div>
			<input class="btn btn-sm btn-default"
				style="margin: 5px; padding: 3px; width: 100px;" type="submit"
				value="Add Publisher" />
			<button type="button" id="cancel" class="btn btn-sm btn-default"
				style="margin: 5px; padding: 3px; width: 100px;" onClick="cancel();">Cancel</button>
		</form>
	</div>
</div>

<script>
	(function($) {

		// wire up Remove Last Item button click event
		$("#cancel").click(function(event) {
			event.preventDefault(); // cancel default behavior
			window.location = "publisher.jsp";
		});
	})(jQuery);
</script>