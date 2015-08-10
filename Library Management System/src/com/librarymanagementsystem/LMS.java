package com.librarymanagementsystem;

import java.util.Scanner;

import com.librarymanagementsystem.admin.Administrator;
import com.librarymanagementsystem.borrower.Borrower;
import com.librarymanagementsystem.librarian.Librarian;

public class LMS {

	public static void main(String[] args) {	
		Scanner sc = new Scanner(System.in);
		boolean exit = false;
		do{
			System.out.println();
			System.out.println("Welcome to GCIT Library Management System.");
			System.out.println();
			System.out.println("1) Administrator \n2) Librarian \n3) Borrower \n4) Exit");
			System.out.println();
			System.out.print("Enter Choice for Main Menu: ");
			int mainMenuChoice = sc .nextInt();


			switch (mainMenuChoice) {
			case 1:
				Administrator admin = new Administrator();
				admin.displayAdminMenu();
				break;
			case 2:
				Librarian lib = new Librarian();
				lib.libraryMenu();
				break;
			case 3:
				Borrower bor = new Borrower();
				bor.cardNumber();
				break;
			case 4:
				exit = true;
				break;

			default:
				System.out.println("Enter a valid choice.");
				System.out.println();

				break;
			}
		}while(!exit);
		sc.close();
	}

}
