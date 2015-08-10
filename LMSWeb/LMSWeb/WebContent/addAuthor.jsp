<%@ include file="layout.jsp"%>
<div class="container" style="border:groove;">
	<h2 align="left">Enter Author Details</h2>
	<hr />
	<div class="col-sm-4"></div>
	<div class="col-sm-4">
		<form action="addAuthor" method="post">
			<div class="form-group">
				<label for="authorName">Enter Author Name: </label> <input
					class="form-control" type="Text" name="authorName" />
			</div>

			<div class="form-group">
				<input type="submit" class="btn btn-sm btn-default"
					style="margin: 5px; width: 100px;" value="Add Author" />
				<button type="button" class="btn btn-sm btn-default"
					style="margin: 5px; width: 100px;"
					onClick="location.href = 'author.jsp';">Cancel</button>
			</div>
		</form>

	</div>
	<div class="col-sm-4"></div>
</div>