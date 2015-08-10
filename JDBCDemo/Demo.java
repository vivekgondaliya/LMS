package com.gcit.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.jdbc.PreparedStatement;

public class Demo {

	public static void main(String[] args) {
		
		try {
			
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "1234");
			Statement stmt = conn.createStatement();
			
			String selectQuery = "SELECT * FROM tbl_author where authorName= ?";
			PreparedStatement pstmt = conn.prepareStatement(selectQuery);
			pstmt.setShort(1, "Bill Burr");
			
			ResultSet rs = pstmt.executeQuery(selectQuery);
			
			while(rs.next()){
				System.out.println("Author ID: " + rs.getInt("authorId"));
				System.out.println("Author Name: " + rs.getString("authorName"));
				System.out.println();
			}
			
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter a new Author Name: ");
			String authorName = scan.nextLine();
			
			String createQuery = "INSERT INTO tbl_author (authorName) values ('" +authorName+"')";
			stmt.executeUpdate(createQuery);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
