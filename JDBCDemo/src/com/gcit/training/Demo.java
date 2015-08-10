package com.gcit.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Demo {

	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		
		try {
			
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "1234");
			Statement stmt = conn.createStatement();
			
			
			
			System.out.println("Please enter a new Branch Name or N/A for no change.");
			String newBranchName = scan.nextLine();
			System.out.println("Please enter a new Branch Address or N/A for no change.");
			String newBranchAddress = scan.nextLine();
			
			String updateQuery = "UPDATE tbl_library_branch SET branchName = '" +newBranchName+ "', branchAddress = '" +newBranchAddress + "' WHERE branchId =1";
			stmt.executeUpdate(updateQuery);
			
			String selectQuery = "SELECT branchName FROM tbl_library_branch";
			ResultSet rs = stmt.executeQuery(selectQuery);
			
			while(rs.next()){
				System.out.println("Branch Name: " + rs.getString("branchName"));
				System.out.println();
			}
			/*System.out.println("Enter a new Author Name: ");
			String authorName = scan.nextLine();
			
			String createQuery = "INSERT INTO tbl_author (authorName) values ('" +authorName+"')";
			stmt.executeUpdate(createQuery);
			*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
