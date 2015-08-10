package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO<LibraryBranch> implements ResultSetExtractor<List<LibraryBranch>>{
	
	@Autowired
	BookDAO bookDAO;
	@Autowired
	BookCopiesDAO bookCopiesDAO;
	@Autowired
	BookLoansDAO bookLoanDAO;
	
	//INSERT
	public void create(LibraryBranch lb) throws Exception {
		template.update("insert into tbl_library_branch (branchName, branchAddress) values(?,?)",
				new Object[] { lb.getBranchName(), lb.getBranchAddress()});
	}

	//UPDATE
	public void update(LibraryBranch lb) throws Exception {
		template.update("update tbl_library_branch set branchName=?, branchAddress=? where branchId=?",
				new Object[] { lb.getBranchName(), lb.getBranchAddress(), lb.getBranchId()});

	}

	//DELETE
	public void delete(LibraryBranch lb) throws Exception {
		template.update("delete from tbl_library_branch where branchId=?",
				new Object[] { lb.getBranchId()});
	}

	//SELECT ALL
	public List<LibraryBranch> readAll() throws Exception {
		return template.query("select * from tbl_library_branch", this);
	}

	//SELECT ONE
	public LibraryBranch readOne(int branchId) throws Exception {

		List<LibraryBranch> branch = template.query("select * from tbl_library_branch where branchId=?", new Object[] {branchId}, this);
		if(branch!=null && branch.size()>0){
			return branch.get(0);
		}
		return null;
	}

	//RETURN List of Publishers
	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {

		//create a list
		List<LibraryBranch> branch = new ArrayList<LibraryBranch>();

		//populate the list
		while(rs.next()){
			LibraryBranch lb  = new LibraryBranch();
			lb.setBranchId(rs.getInt("branchId"));
			lb.setBranchName(rs.getString("branchName"));
			lb.setBranchAddress(rs.getString("branchAddress"));
//			lb.setLoans(template.query("select * from tbl_book_loans where branchId=?", new Object[] {rs.getInt("branchId")}, bookLoanDAO));
			branch.add(lb);
		}
		return branch;
	}

	//branch by Name
	public List<LibraryBranch> readByBranchName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<LibraryBranch>) template.query("select * from tbl_library_branch where branchName like ? or branchAddress like ?", new Object[] {searchString, searchString}, this);
	}

	//read for PAGINATION
	public List<LibraryBranch> readAll(int pageNo, int pageSize) throws Exception{
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<LibraryBranch>) template.query("select * from tbl_library_branch", this);

	}
}
