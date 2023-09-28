package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

import data_structures.ArrayList;
import data_structures.DoublyLinkedList;
import data_structures.SinglyLinkedList;
import interfaces.FilterFunction;
import interfaces.List;

public class LibraryCatalog {
	
	private List<Book> bookCatalog;	
	private List<User> users;
		
	public LibraryCatalog() throws IOException {
		//open the catalog csv file and read the books path:../data/catalog.csv
		//open the users csv file and read the users path:../data/users.csv
		//initialize the book catalog and users list
		//call the getBooksFromFiles() method and store the books in the book catalog
		//call the getUsersFromFiles() method and store the users in the users list
		users = new ArrayList<User>();
		bookCatalog = new ArrayList<Book>();
		getBooksFromFiles();
		getUsersFromFiles();

		
	}
	private List<Book> getBooksFromFiles() throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader("../data/catalog.csv"))){
			String line = reader.readLine();
			while(line != null) {
				String[] bookInfo = line.split(",");
				int id = Integer.parseInt(bookInfo[0]);
				String title = bookInfo[1];
				String author = bookInfo[2];
				String genre = bookInfo[3];
				LocalDate lastCheckOut = LocalDate.parse(bookInfo[4]);
				boolean checkedOut = Boolean.parseBoolean(bookInfo[5]);
				Book book = new Book(id, title, author, genre, lastCheckOut, checkedOut);
				bookCatalog.add(book);
				line = reader.readLine();
			}
			return bookCatalog;

		}catch(IOException e) {
			throw new IOException("File not found");
		}

	}
	
	private List<User> getUsersFromFiles() throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader("../data/users.csv"))){
			String line = reader.readLine();
			while(line != null) {
				String[] userInfo = line.split(",");
				int id = Integer.parseInt(userInfo[0]);
				String name = userInfo[1];
				User user = new User();
				user.setId(id);
				user.setName(name);
				users.add(user);
				line = reader.readLine();
			}
			return users;

		}catch(IOException e) {
			throw new IOException("File not found");
		}
	}
	public List<Book> getBookCatalog() {
		return bookCatalog;
	}
	public List<User> getUsers() {
		return users;
	}
	public void writeBooksToFile(Book bookToBeAdded){
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("../data/catalog.csv", true))){
			writer.write(bookToBeAdded.getId() + "," + bookToBeAdded.getTitle() + "," + bookToBeAdded.getAuthor() + "," + bookToBeAdded.getGenre() + "," + bookToBeAdded.getLastCheckOut() + "," + bookToBeAdded.isCheckedOut());
			writer.newLine();
		}catch(IOException e) {
			System.out.println("File not found");
		}

	}
	public void addBook(String title, String author, String genre) {
		//create a new book object
		//add the book to the book catalog
		//call the writeBooksToFile() method to write the book to the catalog file
		//call the getBooksFromFiles() method to update the book catalog
		// set last checkout to today's date
		if(title == null || title.isEmpty() || author == null || author.isEmpty() || genre == null || genre.isEmpty()) {
			throw new IllegalArgumentException("None of the following fields can be empty {title, author, genre}");
		}
		Book book = new Book(bookCatalog.size(), title, author, genre, null, false);
		bookCatalog.add(book);
		writeBooksToFile(book);
		
	} 
	public void removeBook(int id) {
		//remove the book from the book catalog
		//call the writeBooksToFile() method to write the book to the catalog file
		//call the getBooksFromFiles() method to update the book catalog
		//set the last checkout to null
		if(id < 1) {
			throw new IllegalArgumentException("Id must be a positive number");
		}
		if(id > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Id is too large for a 32-bit integer");
		}
		bookCatalog.remove(id);
		writeBooksToFile(bookCatalog.get(id));
	}	
	
	public boolean checkOutBook(int id) {
		//set the book's checked out status to true
		//set the last checkout date to today's date
		//call the writeBooksToFile() method to write the book to the catalog file
		//call the getBooksFromFiles() method to update the book catalog
		if(id < 1) {
			throw new IllegalArgumentException("Id must be a positive number");
		}
		if(id > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Id is too large for a 32-bit integer");
		}
		if(bookCatalog.get(id).isCheckedOut()) {
			return false;
		}
		bookCatalog.get(id).setCheckedOut(true);
		bookCatalog.get(id).setLastCheckOut(LocalDate.now());
		writeBooksToFile(bookCatalog.get(id));
		return true;
	}
	public boolean returnBook(int id) {
		if(id < 1) {
			throw new IllegalArgumentException("Id must be a positive number");
		}
		if(id > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Id is too large for a 32-bit integer");
		}
		if(!bookCatalog.get(id).isCheckedOut() || bookCatalog.get(id).getId() > bookCatalog.size()) {
			return false;
		}
		bookCatalog.get(id).setCheckedOut(false);
		bookCatalog.get(id).setLastCheckOut(null);
		writeBooksToFile(bookCatalog.get(id));
		return true;
	}
	
	public boolean getBookAvailability(int id) {
		if(id < 1) {
			throw new IllegalArgumentException("Id must be a positive number");
		}
		if(id > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Id is too large for a 32-bit integer");
		}
		return bookCatalog.get(id).isCheckedOut();
	}
	public HashMap<String, Integer> bookCount() {
		HashMap<String, Integer> bookCount = new HashMap<String, Integer>();
		for(int i = 0; i < bookCatalog.size(); i++) {
			if(bookCount.containsKey(bookCatalog.get(i).getGenre())) {
				bookCount.put(bookCatalog.get(i).getGenre(), bookCount.get(bookCatalog.get(i).getGenre()) + 1);
			}else {
				bookCount.put(bookCatalog.get(i).getGenre(), 1);
			}
		}
		return bookCount;
		
	}
	//make a method that returns the amount of books in the book catalog per genre using a hashmap



	public void generateReport() throws IOException {
		
		String output = "\t\t\t\tREPORT\n\n";
		output += "\t\tSUMMARY OF BOOKS\n";
		output += "GENRE\t\t\t\t\t\tAMOUNT\n";
		/*
		 * In this section you will print the amount of books per category.
		 * 
		 * Place in each parenthesis the specified count. 
		 * 
		 * Note this is NOT a fixed number, you have to calculate it because depending on the 
		 * input data we use the numbers will differ.
		 * 
		 * How you do the count is up to you. You can make a method, use the searchForBooks()
		 * function or just do the count right here.
		 */
		HashMap<String, Integer> bookCount = bookCount();
		output += "Adventure\t\t\t\t\t" + (bookCount.get("Adventure")) + "\n";
		output += "Fiction\t\t\t\t\t\t" + (bookCount.get("Fiction")) + "\n";
		output += "Classics\t\t\t\t\t" + (bookCount.get("Classics")) + "\n";
		output += "Mystery\t\t\t\t\t\t" + (bookCount.get("Mystery")) + "\n";
		output += "Science Fiction\t\t\t\t\t" + (bookCount.get("ScienceFiction")) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (bookCatalog.size()) + "\n\n";
		//delete the hasmap from memeory 
		bookCount = null;
	
		/*
		 * This part prints the books that are currently checked out
		 */
		output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		/*
		 * Here you will print each individual book that is checked out.
		 * 
		 * Remember that the book has a toString() method. 
		 * Notice if it was implemented correctly it should print the books in the 
		 * expected format.
		 * 
		 * PLACE CODE HERE
		 */
		
		
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" (bookCatalog.size()) + "\n\n";
		
		
		/*
		 * Here we will print the users the owe money.
		 */
		output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		/*
		 * Here you will print all the users that owe money.
		 * The amount will be calculating taking into account 
		 * all the books that have late fees.
		 * 
		 * For example if user Jane Doe has 3 books and 2 of them have late fees.
		 * Say book 1 has $10 in fees and book 2 has $78 in fees.
		 * 
		 * You would print: Jane Doe\t\t\t\t\t$88.00
		 * 
		 * Notice that we place 5 tabs between the name and fee and 
		 * the fee should have 2 decimal places.
		 * 
		 * PLACE CODE HERE!
		 */
		// scan user catalog to find the late books in the array 
/* 		for(int i = 0; i < users.size(); i++) {
			double totalFees = 0;
			for(int j = 0; j < bookCatalog.size(); j++) {
				if(bookCatalog.get(j).getId() in users.get(i).getCheckedOutList()) {
					totalFees += bookCatalog.get(j).calculateFees();
				}
			}
			if(totalFees > 0) {
				output += users.get(i).getName() + "\t\t\t\t\t$" + totalFees + "\n";
			} */
		//}

			
		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + (/*Place here the total amount of money owed to the library.*/) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.
		
		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		
	}
	
	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for
	 * other parts of the project.
	 */
	public List<Book> searchForBook(FilterFunction<Book> func) {
		return null;
	}
	
	public List<User> searchForUsers(FilterFunction<User> func) {
		return null;
	}
	
}
