package main;

import java.io.IOException;

public class TestMain {

	/*
	 * You can use this method for testing. If you run it as is 
	 * you should be able to generate the same report as report/expected_report.txt
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			LibraryCatalog lc = new LibraryCatalog();
			System.out.println(lc.getUsers().size() + " " + lc.getBookCatalog().size());
			lc.addBook("My Personal Biography", "G Bonilla", "Classics");
			lc.addBook("Coder's Guide to Failing", "G Bonilla", "Adventure");
			// print the all the id of the books in the lc 
			lc.removeBook(16);
			System.out.println(lc.getBookCatalog().get(35).getId());
			System.out.println(lc.getBookCatalog().get(35).calculateFees());
			
			// test that the book was actually removed 
			// print the all the id of the books in the lc


			for(Book b : lc.getBookCatalog()) {
				System.out.println(b.getId());
			}
			//lc.generateReport();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
