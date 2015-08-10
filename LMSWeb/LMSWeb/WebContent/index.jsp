<%@include file="layout.jsp"%>
<div class="container">
	<!-- MENU -->
	<ul id="menu">
		<li><a href="#"><span class="ui-icon ui-icon-folder-open"></span>User
				Category</a>
			<ul>
				<li><a href="#"><span class="ui-icon ui-icon-folder-open"></span>Administrator</a>
					<ul>
						<li><a href="#"><span class="ui-icon ui-icon-folder-open"></span>Add</a>
							<ul>
								<li><a href="addAuthor.jsp">Author</a></li>
								<li><a href="addBook.jsp">Book</a></li>
								<li><a href="addBorrower.jsp">Borrower</a></li>
								<li><a href="addGenre.jsp">Genre</a></li>
								<li><a href="addBranch.jsp">Library Branch</a></li>
								<li><a href="addPublisher.jsp">Publisher</a></li>
							</ul></li>
						<li><a href="#"><span class="ui-icon ui-icon-folder-open"></span>View</a>
							<ul>
								<li><a href="viewAuthors.jsp">Author</a></li>
								<li><a href="viewBooks.jsp">Book</a></li>
								<li><a href="extendDueDate.jsp">Extend Due Date</a></li>
								<li><a href="viewBorrowers.jsp">Borrower</a></li>
								<li><a href="viewGenres.jsp">Genre</a></li>
								<li><a href="viewBranches.jsp">Library Branch</a></li>
								<li><a href="viewPublisher.jsp">Publisher</a></li>
							</ul></li>
					</ul></li>
				<li><a href="#"><span class="ui-icon ui-icon-folder-open"></span>Librarian</a></li>
				<li><a href="#"><span class="ui-icon ui-icon-folder-open"></span>Borrower</a></li>
			</ul></li>
	</ul>


	<div align="center" style="border: groove;">
		<h2>Welcome to Library Management System</h2>
		<hr />
		<div class="container" align="center">
			<img class="img-responsive, img-thumbnail" src="libraryBookShelf.jpg"
				alt="homeBackground" width="700" height="700" />
		</div>
	</div>

	<br><br><hr/><br>
	<!-- ACCORDION -->
	<div id="accordion">
		<h3>Administrator</h3>
		<div>
			<button type="button" class="btn btn-sm btn-default"
				style="margin: 5px; width: 300px;"
				onClick="location.href='author.jsp';">Add/Update/Delete
				Book and Author</button>
			<br />
			<button type="button" class="btn btn-sm btn-default"
				style="margin: 5px; width: 300px;"
				onClick="location.href='publisher.jsp';">Add/Update/Delete
				Publisher</button>
			<br />
			<button type="button" class="btn btn-sm btn-default"
				style="margin: 5px; width: 300px;"
				onClick="location.href='libraryBranch.jsp';">Add/Update/Delete
				Library Branch</button>
			<br />
			<button type="button" class="btn btn-sm btn-default"
				style="margin: 5px; width: 300px;" onClick="location.href='';">Add/Update/Delete
				Borrower</button>
			<br />
			<button type="button" class="btn btn-sm btn-default"
				style="margin: 5px; width: 300px;" onClick="location.href='';">Override
				Due Date for Book Loan</button>
			<br />
		</div>
		<h3>Librarian</h3>
		<div>
			<button type="button" class="btn btn-sm btn-default"
				style="margin: 5px; width: 125px;" onClick="location.href='#';">
				Manage Branch</button>
			<button type="button" class="btn btn-sm btn-default"
				style="margin: 5px; width: 125px;"
				onClick="location.href='index.jsp';">Cancel</button>
			<br />
		</div>
		<h3>Borrower</h3>
		<div>
			Enter Card No: <input type="text" name="cardNo" />
			<button type="button" class="btn btn-sm btn-default"
				style="margin: 5px; width: 100px;" onClick="location.href='#';">Enter</button>
			<button type="button" class="btn btn-sm btn-default"
				style="margin: 5px; width: 100px;"
				onClick="location.href='index.jsp';">Cancel</button>
			<br />
		</div>
	</div>

</div>
<script>
	var icons = {
		header : "ui-icon-circle-arrow-e",
		activeHeader : "ui-icon-circle-arrow-s"
	};

	$(function() {
		$('#accordion').accordion({
			collapsible : true,
			icons : icons,
			heightStyle : "content", //"auto"
			//active: 0,
			//event: "mouseover",
			//EVENTs to occur on activate
			//activate: function(event, ui){
			//	alert(ui.newHeader.context.draggable);
			//}
			animate : "easeInOutBack"
		});
	});

	//menu initialization
	$(function() {
		$('#menu').menu({

		});
	});
</script>
