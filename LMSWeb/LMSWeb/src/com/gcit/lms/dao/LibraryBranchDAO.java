package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO<LibraryBranch>{
	
	public LibraryBranchDAO(Connection conn) throws Exception {
		super(conn);
	}

		//INSERT
		public void create(LibraryBranch lb) throws Exception {
			save("insert into tbl_library_branch (branchName, branchAddress) values(?,?)",
					new Object[] { lb.getBranchName(), lb.getBranchAddress()});
		}

		//UPDATE
		public void update(LibraryBranch lb) throws Exception {
			save("update tbl_library_branch set branchName=?, branchAddress=? where branchId=?",
					new Object[] { lb.getBranchName(), lb.getBranchAddress(), lb.getBranchId()});

		}

		//DELETE
		public void delete(LibraryBranch lb) throws Exception {
			save("delete from tbl_library_branch where branchId=?",
					new Object[] { lb.getBranchId()});
		}

		//SELECT ALL
		public List<LibraryBranch> readAll() throws Exception {
			return read("select * from tbl_library_branch", null);
		}

		//SELECT ONE
		public LibraryBranch readOne(int branchId) throws Exception {
			
			List<LibraryBranch> branch = read("select * from tbl_library_branch where branchId=?", new Object[] {branchId});
			if(branch!=null && branch.size()>0){
				return branch.get(0);
			}
			return null;
		}

		//RETURN List of Publishers
		@Override
		public List<LibraryBranch> extractData(ResultSet rs) throws Exception {
			
			//create a list
			List<LibraryBranch> branch = new ArrayList<LibraryBranch>();
			BookDAO bDAO = new BookDAO(getConnection());
			BookCopiesDAO bcDAO = new BookCopiesDAO(getConnection());
			BookLoansDAO blDAO = new BookLoansDAO(getConnection());
			
			
			//populate the list
			while(rs.next()){
				
				LibraryBranch lb  = new LibraryBranch();
				lb.setBranchId(rs.getInt("branchId"));
				lb.setBranchName(rs.getString("branchName"));
				lb.setBranchAddress(rs.getString("branchAddress"));
				lb.setLoans(blDAO.readFirstLevel("select * from tbl_book_loans where branchId=?", new Object[] {rs.getInt("branchId")}));
			
//				HashMap<Book, Integer> bookCopies = new HashMap<Book, Integer>();
//				List<BookCopies> noOfCopies = (List<BookCopies>) bcDAO.readFirstLevel("select * from tbl_book_copies where branchId=?"
//						, new Object[] {rs.getInt("branchId")});
//				for(BookCopies bc: noOfCopies){
//					bookCopies.put(bDAO.readOne(bc.getBookId()), bc.getNoOfCopies());
//				}
//				lb.setBookOfCopies(bookCopies);
				
				branch.add(lb);
			}
			return branch;
		}

		@Override
		public List<LibraryBranch> extractDataFirstLevel(ResultSet rs)
				throws Exception {
		
			List<LibraryBranch> branch = new ArrayList<LibraryBranch>();
			while(rs.next()){
				LibraryBranch lb = new LibraryBranch();
				lb.setBranchId(rs.getInt("branchId"));
				lb.setBranchAddress(rs.getString("branchAddress"));
				lb.setBranchName(rs.getString("branchName"));
				branch.add(lb);
			}
			return branch;
		}

		
		//branch by Name
		public List<LibraryBranch> readByBranchName(String searchString) throws Exception{
			searchString = "%"+searchString+"%";
			return (List<LibraryBranch>) read("select * from tbl_library_branch where branchName like ? or branchAddress like ?", new Object[] {searchString, searchString});
		}

		//read for PAGINATION
		public List<LibraryBranch> readAll(int pageNo, int pageSize) throws Exception{
			setPageNo(pageNo);
			setPageSize(pageSize);
			return (List<LibraryBranch>) read("select * from tbl_library_branch", null);
			
		}
}
