<%@ include file="layout.jsp"%>
<div class="container" style="border:groove;">
	<h2 align="left">Enter Genre Details</h2>
	<hr />
	<div class="col-sm-4"></div>
	<div class="col-sm-4">
		<form action="addGenre" method="post">
			<div class="form-group">
				<label for="genreName">Enter Genre Name: </label> <input
					class="form-control" type="Text" name="genreName" />
			</div>

			<div class="form-group">
				<input type="submit" class="btn btn-sm btn-default"
					style="margin: 5px; width: 100px;" value="Add Genre" />
				<button type="button" class="btn btn-sm btn-default"
					style="margin: 5px; width: 100px;"
					onClick="location.href = 'viewGenres.jsp';">Cancel</button>
			</div>
		</form>

	</div>
	<div class="col-sm-4"></div>
</div>