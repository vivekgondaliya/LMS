<%@include file="layout.jsp"%>
<body>
	<div class="container">
	<div class="row" style="margin-left:5px;">
		<div class="col-sm-4"></div>
		<div class="col-sm-4" align="center"
		style="border: groove; margin: 5px; padding-bottom: 10px;">
			<h2>
				<em>Borrower</em>
			</h2>
			<hr />
			<p>
				<button type="button" class="btn btn-sm btn-default"
					style="margin: 5px; padding: 5px; width: 100px;"
					onClick="location.href = 'addBorrower.jsp';">Add
					Borrower</button>
				<br />
				<button type="button" class="btn btn-sm btn-default"
					style="margin: 5px; padding: 5px; width: 100px;"
					onClick="location.href = 'viewBorrowers.jsp';">View
					Borrowers</button>
				<br />
			</p>
		</div>
		<div class="col-sm-4"></div>
	</div>
</div>
	<div align="center">
		<hr />
		<button type="button" class="btn btn-sm btn-warning"
			style="margin: 5px; width: 100px;"
			onClick="location.href = 'admin.jsp';">Go Back</button>
	</div>
</body>