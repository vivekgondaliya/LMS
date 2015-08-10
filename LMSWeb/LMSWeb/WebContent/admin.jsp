<%@ include file="layout.jsp"%>
<div class="container">
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4" style="border: groove;" align="center">
			<h2 align="center">Administrator Menu</h2>
			<hr />
			<button type="button" class="btn btn-sm btn-info"
				style="margin: 5px; width: 225px;"
				onClick="location.href='author.jsp';">Add/Update/Delete
				Book and Author</button>
			<br />
			<button type="button" class="btn btn-sm btn-info"
				style="margin: 5px; width: 225px;"
				onClick="location.href='publisher.jsp';">Add/Update/Delete
				Publisher</button>
			<br />
			<button type="button" class="btn btn-sm btn-info"
				style="margin: 5px; width: 225px;"
				onClick="location.href='libraryBranch.jsp';">Add/Update/Delete
				Library Branch</button>
			<br />
			<button type="button" class="btn btn-sm btn-info"
				style="margin: 5px; width: 225px;"
				onClick="location.href='borrowerAdmin.jsp';">Add/Update/Delete
				Borrower</button>
			<br />
			<button type="button" class="btn btn-sm btn-info"
				style="margin: 5px; width: 225px;" onClick="location.href='';">Override
				Due Date for Book Loan</button>
			<br />
			<button type="button" class="btn btn-sm btn-warning"
				style="margin: 5px; width: 225px;"
				onClick="location.href='index.jsp';">Cancel</button>
			<br />

		</div>
		<div class="col-sm-4"></div>
	</div>
</div>
