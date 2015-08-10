<%@include file="../layout.jsp"%>
<div class="container">
	<div class="row" align="center"
		style="border: groove; margin: 10px; padding-bottom: 10px;">
		<div class="col-sm-6" style="border-right: inset;">
			<h2>
				<em>Author</em>
			</h2>
			<hr />
			<p>
				<button type="button" class="btn btn-sm btn-default"
					style="margin: 5px; width: 100px;"
					onClick="location.href = 'addAuthor.jsp';">Add Author</button>
				<br>
				<button type="button" class="btn btn-sm btn-default"
					style="margin: 5px; width: 100px;"
					onClick="location.href = 'viewAuthors.jsp';">View Authors</button>
				<br />
			</p>
		</div>
		<div class="col-sm-6">
			<h2>
				<em>Book</em>
			</h2>
			<hr />
			<p>
				<button type="button" class="btn btn-sm btn-default"
					style="margin: 5px; width: 100px;"
					onClick="location.href = 'addBook.jsp';">Add Book</button>
				<br>
				<button type="button" class="btn btn-sm btn-default"
					style="margin: 5px; width: 100px;"
					onClick="location.href = 'viewBooks.jsp';">View Books</button>
				<br />
			</p>
		</div>
	</div>
	<div align="center" style="margin: 5px;">
		<button type="button" class="btn btn-sm btn-warning"
			style="margin: 5px; width: 100px;"
			onClick="location.href = 'admin.jsp';">Go Back</button>
		<hr />
	</div>
</div>
