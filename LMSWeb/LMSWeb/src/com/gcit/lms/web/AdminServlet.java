package com.gcit.lms.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;
import com.gcit.lms.service.AdministratorService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({"/addAuthor", "/viewAuthors","/editAuthor", "/searchAuthors","/deleteAuthor", "/pageAuthors",
	"/addBook", "/viewBooks", "/editBook", "/searchBooks","/deleteBook", "/pageBooks",
	"/addBorrower", "/viewBorrowers","/editBorrower", "/searchBorrowers","/deleteBorrower", "/pageBorrowers", 
	"/addPublisher", "/viewPublishers", "/editPublisher", "/searchPublishers","/deletePublisher", "/pagePublishers",
	"/addBranch", "/editBranch", "/viewBranch","/pageBranches", "/searchBranches", "/deleteLibraryBranch",
	"/addGenre", "/viewGenre", "/editGenre", "/deleteGenre", "/searchGenre", "/pageGenre"})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqURL = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		System.out.println("The requested URI-> " + reqURL);

		switch (reqURL) {
		case "/searchAuthors":
			searchAuthors(request, response);
			break;
		case "/pageAuthors":
			pageAuthors(request, response);
			break;
		case "/deleteAuthor":
			deleteAuthor(request, response);
			break;
		case"/searchBooks":
			searchBooks(request, response);
			break;
		case "/pageBooks":
			pageBooks(request, response);
			break;
		case "/deleteBook":
			deleteBook(request, response);
			break;
		case "/searchBorrowers":
			searchBorrowers(request, response);
			break;
		case "/pageBorrowers":
			pageBorrower(request, response);
			break;
		case "/deleteBorrower":
			deleteBorrower(request, response);
			break;
		case "/deletePublisher":
			deletePublisher(request, response);
			break;
		case "/pagePublishers":
			pagePublishers(request, response);
			break;
		case "/searchPublishers":
			searchPublishers(request, response);
			break;
		case "/deleteLibraryBranch":
			deleteLibraryBranch(request, response);
			break;
		case "/pageBranches":
			pageBranches(request, response);
			break;
		case "/searchBranches":
			searchBranches(request, response);
			break;
		case "/deleteGenre":
			deleteGenre(request, response);
			break;
		case "/pageGenre":
			pageGenre(request, response);
			break;
		case "/searchGenre":
			searchGenre(request, response);
			break;
		default:
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get servlet name
		String reqURL = request.getRequestURI().substring(request.getContextPath().length(), request.getRequestURI().length());
		System.out.println("The requested URI-> " + reqURL);

		switch (reqURL) {
		case "/addAuthor":
			getCreateAuthor(request, response);
			break;
		case "/editAuthor":
			editAuthor(request, response);
			break;
		case "/viewAuthors":
			viewAuthors(request, response);
			break;
		case "/searchAuthors":
			searchAuthors(request, response);
			break;
		case "/addBook":
			createBook(request, response);
			break;
		case "/editBook":
			editBook(request, response);
			break;
		case "/viewBooks":
			viewBooks(request, response);
			break;
		case "/searchBooks":
			searchBooks(request, response);
			break;
		case "/addBorrower":
			createBorrower(request, response);
			break;
		case "/editBorrower":
			editBorrower(request, response);
			break;
		case "/viewBorrower":
			viewBorrowers(request, response);
			break;
		case "/searchBorrowers":
			searchBorrowers(request, response);
			break;
		case "/addPublisher":
			getCreatePublisher(request, response);
			break;
		case "/viewPublishers":
			viewPublishers(request, response);
			break;
		case "/editPublisher":
			editPublisher(request, response);
			break;
		case "/addBranch":
			createBranch(request, response);
			break;
		case "/editBranch":
			editBranch(request, response);
			break;
		case "/viewBranch":
			viewBranches(request, response);
			break;
		case "/addGenre":
			createGenre(request, response);
			break;
		case "/editGenre":
			editGenre(request, response);
			break;
		case "/viewGenre":
			viewGenre(request, response);
			break;
		default:
			break;
		}
	}

	/*
	AUTHOR

	 */	
	// create Author
	private void getCreateAuthor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String authorName = request.getParameter("authorName");
		Author a = new Author();
		a.setAuthorName(authorName);

		AdministratorService adminService = new AdministratorService();
		try {
			adminService.createAuthor(a);
			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Author Added Successfully.");

		} catch (Exception e) {
			//LAST point to THROW EXCEPTION
			e.printStackTrace();

			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Author Add Failed." + e.getMessage());
		}

		//Use of FORWARD
		//			RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin.jsp");
		//			rd.forward(request, response);

		//Use of REDIRECT
		response.sendRedirect("author.jsp");
	}

	//edit Author
	private void editAuthor(HttpServletRequest request,
			HttpServletResponse response) {

		AdministratorService adminService = new AdministratorService();
		String authorName = request.getParameter("authorName");
		int authorId = Integer.parseInt(request.getParameter("authorId"));

		Author a = new Author();
		a.setAuthorName(authorName);
		a.setAuthorId(authorId);

		try {
			adminService.updateAuthor(a);
			request.setAttribute("result", "Author updated Successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Author update failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewAuthors.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//delete Author
	private void deleteAuthor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String authorId = request.getParameter("authorId");
		Author a = new Author();
		a.setAuthorId(Integer.parseInt(authorId));

		AdministratorService adminService = new AdministratorService();
		try {
			adminService.deleteAuthor(a);
			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Author Deleted Successfully.");

		} catch (Exception e) {
			//LAST point to THROW EXCEPTION
			e.printStackTrace();

			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Author Delete Failed." + e.getMessage());
		}

		//Use of FORWARD
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/viewAuthors.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}

		//Use of REDIRECT
		//response.sendRedirect("admin.jsp");
	}


	//search Authors
	private void searchAuthors(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String searchString = request.getParameter("searchString");
		try {
			List<Author> authors =  new AdministratorService().searchAuthors(searchString);
			request.setAttribute("authors", authors);
			StringBuilder str = new StringBuilder();
			str.append("<tr><th>Author Name</th><th>Edit Author</th><th>Delete Author</th></tr>");
			for(Author a: authors){
				str.append("<tr><td>"+a.getAuthorName()+"</td><td><button type='button' "
						+ "class='btn btn-md btn-success' data-toggle='modal' data-target='#editModal' href='editAuthor.jsp?authorId="+a.getAuthorId()+"'>"
						+ "Edit</button></td><td><button type='button' class='btn btn-md btn-danger' onclick='javascript:location.href="
						+ "'deleteAuthor?authorId="+a.getAuthorId()+"'>Delete</button></td></tr>");
			}
			response.getWriter().write(str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//view Authors
	private List<Author> viewAuthors(HttpServletRequest request,
			HttpServletResponse response) {
		//int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		try {
			return new AdministratorService().readAuthors(0, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//page Author
	private void pageAuthors(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Author> authors = new AdministratorService().readAuthors(pageNo, 10);
			request.setAttribute("authors", authors);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewAuthors.jsp");
		rd.forward(request, response);
	}


	/*
	PUBLISHER

	 */
	//create Publisher
	private void getCreatePublisher(HttpServletRequest request, HttpServletResponse response) throws IOException{

		String publisherName = request.getParameter("publisherName");
		String publisherAddress= request.getParameter("publisherAddress");
		String publisherPhone = request.getParameter("publisherPhone");

		Publisher pub = new Publisher();
		pub.setPublisherName(publisherName);
		pub.setPublisherAddress(publisherAddress);
		pub.setPublisherPhone(publisherPhone);

		AdministratorService adminService = new AdministratorService();
		try {
			adminService.createPublisher(pub);
			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Publisher Added Successfully.");

		} catch (Exception e) {
			//LAST point to THROW EXCEPTION
			e.printStackTrace();

			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Publisher Add Failed." + e.getMessage());
		}
		response.sendRedirect("publisher.jsp");
	}

	//view Publishers
	private List<Publisher> viewPublishers(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			return new AdministratorService().readAllPublisher();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	//edit Publisher
	private void editPublisher(HttpServletRequest request,
			HttpServletResponse response) {

		AdministratorService adminService = new AdministratorService();
		String publisherName = request.getParameter("publisherName");
		String publisherAddress = request.getParameter("publisherAddress");
		String publisherPhone = request.getParameter("publisherPhone");
		int publisherId = Integer.parseInt(request.getParameter("publisherId"));

		Publisher pub = new Publisher();
		pub.setPublisherId(publisherId);
		pub.setPublisherName(publisherName);
		pub.setPublisherAddress(publisherAddress);
		pub.setPublisherPhone(publisherPhone);

		try {
			adminService.updatePublisher(pub);
			request.setAttribute("result", "Publisher updated Successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Publisher update failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewPublishers.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//delete Publisher
	private void deletePublisher(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String publisherId = request.getParameter("publisherId");
		Publisher p = new Publisher();
		p.setPublisherId(Integer.parseInt(publisherId));

		AdministratorService adminService = new AdministratorService();
		try {
			adminService.deletePublisher(p);
			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Publisher Deleted Successfully.");

		} catch (Exception e) {
			//LAST point to THROW EXCEPTION
			e.printStackTrace();

			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Publisher Delete Failed." + e.getMessage());
		}

		//Use of FORWARD
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/viewPublishers.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}


	//page publisher
	private void pagePublishers(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Publisher> publishers = new AdministratorService().readPublishers(pageNo, 10);
			request.setAttribute("publishers", publishers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewPublishers.jsp");
		rd.forward(request, response);
	}



	//search Authors
	private void searchPublishers(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String searchString = request.getParameter("searchString");
		try {
			List<Publisher> publishers =  new AdministratorService().searchPublishers(searchString);
			request.setAttribute("publishers", publishers);
			StringBuilder str = new StringBuilder();
			str.append("<tr><th>Publisher Name</th><th>Publisher Address</th><th>Publisher Phone</th><th>Edit Publisher</th><th>Delete Publisher</th></tr>");
			for(Publisher p: publishers){
				str.append("<tr><td>"+p.getPublisherName()+"</td><td>"+p.getPublisherAddress()+"</td><td>"+p.getPublisherPhone()+"</td><td><button type='button' "
						+ "class='btn btn-md btn-success' data-toggle='modal' data-target='#editModal' href='editPublisher.jsp?publisherId="+p.getPublisherId()+"'>"
						+ "Edit</button></td><td><button type='button' class='btn btn-md btn-danger' onclick='javascript:location.href="
						+ "'deletePublisher?publisherId="+p.getPublisherId()+"'>Delete</button></td></tr>");
			}
			response.getWriter().write(str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*	
	BOOK
	 */	
	//create Book
	private void createBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter("bookTitle");
		//String authorId = request.getParameter("selectAuthor");
		String publisherId = request.getParameter("selectPublisher");
		String[] authorIds = request.getParameterValues("selectAuthor");
		String[] genreIds = request.getParameterValues("selectGenre");

		//set publisherId
		Publisher pub = new Publisher();
		pub.setPublisherId(Integer.parseInt(publisherId));

		//set new Book
		Book b = new Book();
		b.setTitle(title);
		b.setPublisher(pub);

		//update list of genres and authors
		List<Author> authors = new ArrayList<Author>();
		List<Genre> genres = new ArrayList<Genre>();

		AdministratorService adminService = new AdministratorService();
		try {
			for(String authorId: authorIds){
				authors.add(adminService.readOneAuthor(Integer.parseInt(authorId)));
			}
			b.setAuthors(authors);
			for(String genreId : genreIds) {
				genres.add(adminService.readOneGenre(Integer.parseInt(genreId)));
			}
			b.setGenres(genres);
			adminService.createBook(b);
			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Book Added Successfully.");

		} catch (Exception e) {
			//LAST point to THROW EXCEPTION
			e.printStackTrace();

			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Book Add Failed." + e.getMessage());
		}

		//Use of FORWARD
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/author.jsp");
		rd.forward(request, response);

		//Use of REDIRECT
		//response.sendRedirect("admin.jsp");
	}


	//edit Book
	private void editBook(HttpServletRequest request,
			HttpServletResponse response) {

		String title = request.getParameter("title");
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String publisherId = request.getParameter("selectPublisher");
		String[] authorIds = request.getParameterValues("selectAuthor");
		String[] genreIds = request.getParameterValues("selectGenre");

		Book b = new Book();
		b.setTitle(title);
		b.setBookId(bookId);

		//update list of genres and authors
		List<Author> authors = new ArrayList<Author>();
		List<Genre> genres = new ArrayList<Genre>();


		Publisher pub = new Publisher();
		pub.setPublisherId(Integer.parseInt(publisherId));
		String publisherName = request.getParameter("publisherName");
		pub.setPublisherName(publisherName);
		b.setPublisher(pub);

		AdministratorService adminService = new AdministratorService();
		try {
			for(String authorId: authorIds){
				authors.add(adminService.readOneAuthor(Integer.parseInt(authorId)));
			}
			b.setAuthors(authors);
			for(String genreId : genreIds) {
				genres.add(adminService.readOneGenre(Integer.parseInt(genreId)));
			}
			b.setGenres(genres);
			adminService.updateBook(b);
			request.setAttribute("result", "Book updated Successfully.");


		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Book Update Failed. " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBooks.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	//search Books
	private void searchBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String searchString = request.getParameter("searchString");
		try {
			List<Book> books =  new AdministratorService().searchBooks(searchString);
			request.setAttribute("books", books);
			StringBuilder str = new StringBuilder();
			str.append("<tr><th>Book Title</th><th>Publisher Name</th><th>Edit Book</th><th>Delete Book</th></tr>");
			for(Book b: books){
				str.append("<tr><td>"+b.getTitle()+"</td><td>"+b.getPublisher().getPublisherName()+"</td><td><button type='button' "
						+ "class='btn btn-md btn-success' data-toggle='modal' data-target='#editModal' href='editBook.jsp?bookId="+b.getBookId()+"'>"
						+ "Edit</button></td><td><button type='button' class='btn btn-md btn-danger' onclick='javascript:location.href="
						+ "'deleteBook?bookId="+b.getBookId()+"'>Delete</button></td></tr>");
			}
			response.getWriter().write(str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//view Books
	private List<Book> viewBooks(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			return new AdministratorService().readAllBooks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//delete Book
	private void deleteBook(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String bookId = request.getParameter("bookId");
		Book b = new Book();
		b.setBookId(Integer.parseInt(bookId));

		AdministratorService adminService = new AdministratorService();
		try {
			adminService.deletebook(b);
			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Book Deleted Successfully.");

		} catch (Exception e) {
			//LAST point to THROW EXCEPTION
			e.printStackTrace();

			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Book Delete Failed." + e.getMessage());
		}

		//Use of FORWARD
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/viewBooks.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}

		//Use of REDIRECT
		//response.sendRedirect("admin.jsp");
	}

	//page Book
	private void pageBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Book> books = new AdministratorService().readBooks(pageNo, 10);
			request.setAttribute("books", books);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBooks.jsp");
		rd.forward(request, response);
	}


	/*
	LIBRARY BRANCH	
	 */	

	// create library branch
	private void createBranch(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String branchName = request.getParameter("branchName");
		String branchAddress= request.getParameter("branchAddress");

		LibraryBranch lb = new LibraryBranch();
		lb.setBranchName(branchName);
		lb.setBranchAddress(branchAddress);

		AdministratorService adminService = new AdministratorService();
		try {
			adminService.createLibraryBranch(lb);
			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Library Branch Added Successfully.");

		} catch (Exception e) {
			//LAST point to THROW EXCEPTION
			e.printStackTrace();

			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Library Branch Add Failed." + e.getMessage());
		}

		//Use of FORWARD
		//			RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin.jsp");
		//			rd.forward(request, response);

		//Use of REDIRECT
		response.sendRedirect("viewBranches.jsp");
	}


	//edit Branch
	private void editBranch(HttpServletRequest request,
			HttpServletResponse response) {

		AdministratorService adminService = new AdministratorService();
		String branchName = request.getParameter("branchName");
		String branchAddress = request.getParameter("branchAddress");
		int branchId = Integer.parseInt(request.getParameter("branchId"));

		LibraryBranch lb = new LibraryBranch();
		lb.setBranchName(branchName);
		lb.setBranchAddress(branchAddress);
		lb.setBranchId(branchId);

		try {
			adminService.updateLibraryBranch(lb);
			request.setAttribute("result", "Branch updated Successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Branch update failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBranches.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//view Branches
	private List<LibraryBranch> viewBranches(HttpServletRequest request,
			HttpServletResponse response) {
		//int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		try {
			return new AdministratorService().readAllLibraryBranch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//delete library branch
	private void deleteLibraryBranch(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String branchId = request.getParameter("branchId");
		LibraryBranch lb = new LibraryBranch();
		lb.setBranchId(Integer.parseInt(branchId));

		AdministratorService adminService = new AdministratorService();
		try {
			adminService.deleteLibraryBranch(lb);
			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Branch Deleted Successfully.");

		} catch (Exception e) {
			//LAST point to THROW EXCEPTION
			e.printStackTrace();

			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Branch Delete Failed." + e.getMessage());
		}

		//Use of FORWARD
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/viewBranches.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}

		//Use of REDIRECT
		//response.sendRedirect("admin.jsp");
	}

	//page branches
	private void pageBranches(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<LibraryBranch> branches = new AdministratorService().readBranches(pageNo, 10);
			request.setAttribute("branches", branches);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBranches.jsp");
		rd.forward(request, response);
	}



	//search Branches
	private void searchBranches(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String searchString = request.getParameter("searchString");
		try {
			List<LibraryBranch> branches =  new AdministratorService().searchBranches(searchString);
			request.setAttribute("branchs", branches);
			StringBuilder str = new StringBuilder();
			str.append("<tr><th>Branch Name</th><th>Branch Address</th><th>Edit Branch</th><th>Delete Branch</th></tr>");
			for(LibraryBranch lb: branches){
				str.append("<tr><td>"+lb.getBranchName()+"</td><td>"+lb.getBranchAddress()+"</td><td><button type='button' "
						+ "class='btn btn-md btn-success' data-toggle='modal' data-target='#editModal' href='editBranch.jsp?branchId="+lb.getBranchId()+"'>"
						+ "Edit</button></td><td><button type='button' class='btn btn-md btn-danger' onclick='javascript:location.href="
						+ "'deleteBranch?branchId="+lb.getBranchId()+"'>Delete</button></td></tr>");
			}
			response.getWriter().write(str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	BORROWER
	 */

	// create Borrower
	private void createBorrower(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String borrowerName = request.getParameter("borrowerName");
		String borrowerAddress = request.getParameter("borrowerAddress");
		String borrowerPhone = request.getParameter("borrowerPhone");

		Borrower b = new Borrower();
		b.setName(borrowerName);
		b.setAddress(borrowerAddress);
		b.setPhone(borrowerPhone);

		AdministratorService adminService = new AdministratorService();
		try {
			adminService.createBorrower(b);
			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Borrower Added Successfully.");

		} catch (Exception e) {
			//LAST point to THROW EXCEPTION
			e.printStackTrace();

			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Borrower Add Failed." + e.getMessage());
		}

		//Use of FORWARD
		//			RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin.jsp");
		//			rd.forward(request, response);

		//Use of REDIRECT
		response.sendRedirect("borrowerAdmin.jsp");
	}


	//edit Borrower
	private void editBorrower(HttpServletRequest request,
			HttpServletResponse response) {

		AdministratorService adminService = new AdministratorService();
		String borrowerName = request.getParameter("borrowerName");
		String borrowerAddress = request.getParameter("borrowerAddress");
		String borrowerPhone = request.getParameter("borrowerPhone");

		int cardNo = Integer.parseInt(request.getParameter("cardNo"));

		Borrower b = new Borrower();
		b.setCardNo(cardNo);
		b.setName(borrowerName);
		b.setAddress(borrowerAddress);
		b.setPhone(borrowerPhone);

		try {
			adminService.updateBorrower(b);
			request.setAttribute("result", "Borrower Updated Successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Borrower Update failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBorrowers.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//search Borrowers
	private void searchBorrowers(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String searchString = request.getParameter("searchString");
		try {
			List<Borrower> borrowers =  new AdministratorService().searchBorrowers(searchString);
			request.setAttribute("borrowers", borrowers);
			StringBuilder str = new StringBuilder();
			str.append("<tr><th>Name</th><th>Address</th><th>Phone</th><th>Edit Borrower</th><th>Delete Borrower</th></tr>");
			for(Borrower b: borrowers){
				str.append("<tr><td>"+b.getName()+"</td><td>"+b.getAddress()+"</td><td>"+b.getPhone()+"</td><td><button type='button' "
						+ "class='btn btn-md btn-success' data-toggle='modal' data-target='#editModal' href='editBorrower.jsp?cardNo="+b.getCardNo()+"'>"
						+ "Edit</button></td><td><button type='button' class='btn btn-md btn-danger' onclick='javascript:location.href="
						+ "'deleteBorrower?cardNo="+b.getCardNo()+"'>Delete</button></td></tr>");
			}
			response.getWriter().write(str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//view Borrowers
	private List<Borrower> viewBorrowers(HttpServletRequest request,
			HttpServletResponse response) {
		//int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		try {
			return new AdministratorService().readBorrowers(0, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	//delete Borrower
	private void deleteBorrower(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String cardNo = request.getParameter("cardNo");
		Borrower b = new Borrower();
		b.setCardNo(Integer.parseInt(cardNo));

		AdministratorService adminService = new AdministratorService();
		try {
			adminService.deleteBorrower(b);
			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Borrower Deleted Successfully.");

		} catch (Exception e) {
			//LAST point to THROW EXCEPTION
			e.printStackTrace();

			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Borrower Delete Failed." + e.getMessage());
		}

		//Use of FORWARD
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/viewBorrowers.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}

	//page Borrower
	private void pageBorrower(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Borrower> borrowers = new AdministratorService().readBorrowers(pageNo, 10);
			request.setAttribute("borrowers", borrowers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewBorrowers.jsp");
		rd.forward(request, response);
	}

	/*
	 * ************GENRE***********
	 **/	

	// create Genre
	private void createGenre(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String genreName = request.getParameter("genreName");
		Genre g = new Genre();
		g.setGenreName(genreName);

		AdministratorService adminService = new AdministratorService();
		try {
			adminService.createGenre(g);
			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Genre Added Successfully.");

		} catch (Exception e) {
			//LAST point to THROW EXCEPTION
			e.printStackTrace();

			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Genre Add Failed." + e.getMessage());
		}

		//Use of REDIRECT
		response.sendRedirect("author.jsp");
	}

	//edit Genre
	private void editGenre(HttpServletRequest request,
			HttpServletResponse response) {

		AdministratorService adminService = new AdministratorService();
		String genreName = request.getParameter("genreName");
		int genreId = Integer.parseInt(request.getParameter("genreId"));

		Genre g = new Genre();
		g.setGenreName(genreName);
		g.setGenreId(genreId);

		try {
			adminService.updateGenre(g);
			request.setAttribute("result", "Genre updated Successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("result",
					"Genre update failed " + e.getMessage());
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewGenres.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//delete Genre
	private void deleteGenre(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String genreId = request.getParameter("genreId");
		Genre a = new Genre();
		a.setGenreId(Integer.parseInt(genreId));

		AdministratorService adminService = new AdministratorService();
		try {
			adminService.deleteGenre(a);
			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Genre Deleted Successfully.");

		} catch (Exception e) {
			//LAST point to THROW EXCEPTION
			e.printStackTrace();

			//following line of code can be used only in case of FORWARD
			request.setAttribute("result", "Genre Delete Failed." + e.getMessage());
		}

		//Use of FORWARD
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/viewGenres.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}

		//Use of REDIRECT
		//response.sendRedirect("admin.jsp");
	}


	//search Genres
	private void searchGenre(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String searchString = request.getParameter("searchString");
		try {
			List<Genre> genres =  new AdministratorService().searchGenres(searchString);
			request.setAttribute("genres", genres);
			StringBuilder str = new StringBuilder();
			str.append("<tr><th>Genre Name</th><th>Edit Genre</th><th>Delete Genre</th></tr>");
			for(Genre a: genres){
				str.append("<tr><td>"+a.getGenreName()+"</td><td><button type='button' "
						+ "class='btn btn-md btn-success' data-toggle='modal' data-target='#editModal' href='editGenre.jsp?genreId="+a.getGenreId()+"'>"
						+ "Edit</button></td><td><button type='button' class='btn btn-md btn-danger' onclick='javascript:location.href="
						+ "'deleteGenre?genreId="+a.getGenreId()+"'>Delete</button></td></tr>");
			}
			response.getWriter().write(str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//view Genres
	private List<Genre> viewGenre(HttpServletRequest request,
			HttpServletResponse response) {
		//int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		try {
			return new AdministratorService().readGenres(0, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//page Author
	private void pageGenre(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));

		try {
			List<Genre> genres = new AdministratorService().readGenres(pageNo, 10);
			request.setAttribute("genres", genres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/viewGenres.jsp");
		rd.forward(request, response);
	}

}
