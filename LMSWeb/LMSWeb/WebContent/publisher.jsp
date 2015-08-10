<%@include file="layout.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4" align="center"
		style="border: groove; margin: 5px; padding-bottom: 10px;">
			<h2>
				<em>Publisher</em>
			</h2>
			<hr />
			<p>
				<button type="button" class="btn btn-sm btn-default"
					style="margin: 5px; padding: 5px; width: 100px;"
					onClick="location.href = 'addPublisher.jsp';">Add
					Publisher</button>
				<br />
				<button type="button" class="btn btn-sm btn-default"
					style="margin: 5px; padding: 5px; width: 100px;"
					onClick="location.href = 'viewPublishers.jsp';">View
					Publishers</button>
				<br />
			</p>
		</div>
		<div class="col-sm-4"></div>
	</div>

	<div align="center">
		<hr />
		<button type="button" class="btn btn-sm btn-warning"
			style="margin: 5px; width: 100px;"
			onClick="location.href = 'admin.jsp';">Go Back</button>
	</div>
</div>