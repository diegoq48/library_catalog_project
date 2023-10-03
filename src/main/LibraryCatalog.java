package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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
	public List<Book> getBooksFromFiles() throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("./data/catalog.csv"))) {
			String line = reader.readLine();
			line = reader.readLine();
			while (line != null) {
				// Skip the first line (header)
				String[] bookInfo = line.split(",");
				
				// Check if the lastCheckOut field is not null
				if (bookInfo.length >= 5 && bookInfo[4] != null && !bookInfo[4].isEmpty()) {
					try {
						int id = Integer.parseInt(bookInfo[0]);
						String title = bookInfo[1];
						String author = bookInfo[2];
						String genre = bookInfo[3];
						LocalDate lastCheckOut = LocalDate.parse(bookInfo[4]);
						boolean checkedOut = Boolean.parseBoolean(bookInfo[5]);
						Book book = new Book(id, title, author, genre, lastCheckOut, checkedOut);
						bookCatalog.add(book);
					} catch (NumberFormatException e) {
						// Handle the case where the ID is not a valid integer
						System.err.println("Invalid ID format: " + bookInfo[0]);
					} catch (DateTimeParseException e) {
						// Handle the case where the date format is invalid
						System.err.println("Invalid date format: " + bookInfo[4]);
					}
				} else {
					// Handle the case where the lastCheckOut field is null or empty
					System.err.println("Null or empty lastCheckOut field");
				}

				line = reader.readLine();
			}
			return bookCatalog;
		} catch (IOException e) {
			throw new IOException("File not found");
		}
	}

	
	
	private List<User> getUsersFromFiles() throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader("./data/user.csv"))){
			String line = reader.readLine();
			line = reader.readLine();
			while(line != null) {
				// Skip the first line (header)
				String[] userInfo = line.split(",");
				int id = Integer.parseInt(userInfo[0]);
				String name = userInfo[1];
				List<Integer> checkedOutList = new ArrayList<Integer>();
				if(userInfo.length > 2) {
					//keep in mind that the list is enclosed by {}
					String[] checkedOutBooks = userInfo[2].substring(1, userInfo[2].length() - 1).split(" ");
					for(int i = 0; i < checkedOutBooks.length; i++) {
						checkedOutList.add(Integer.parseInt(checkedOutBooks[i]));
					}
				}
				User user = new User(id, name, checkedOutList);
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
	public void writeBooksToFile(Book bookToBeAdded) throws IOException{

		try(BufferedWriter writer = new BufferedWriter(new FileWriter("./data/catalog.csv", true))){
			writer.newLine();
			writer.write(bookToBeAdded.getId() + "," 
			+ bookToBeAdded.getTitle() + ","
			+ bookToBeAdded.getAuthor() + "," 
			+ bookToBeAdded.getGenre() + "," 
			+ bookToBeAdded.getLastCheckOut() + "," 
			+ bookToBeAdded.isCheckedOut());
		}
		catch(IOException e) {
			System.out.println("File not found");
			throw e;
		}
		//close the buffer 


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
		Book book = new Book(bookCatalog.size()+1, title, author, genre, LocalDate.of(2023, 9, 15), false);
		bookCatalog.add(book);
/* 		try{
			writeBooksToFile(book);
		}catch(IOException e){
			System.out.println("File not found");
		}*/
	}  
	public void removeBookFromFile(int id){
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			File file = new File("./data/catalog.csv");
			File tempFile = new File("./data/temp.csv");
			reader = new BufferedReader(new FileReader(file));
			writer = new BufferedWriter(new FileWriter(tempFile));
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
				String[] bookInfo = currentLine.split(",");
				int bookId = Integer.parseInt(bookInfo[0]);
				if(bookId != id) {
					writer.write(currentLine + System.getProperty("line.separator"));
				}
			}
			reader.close();
			writer.close();
			file.delete();
			tempFile.renameTo(file);
		}catch(IOException e) {
			System.out.println("File not found");
		}
	}
/* 	public void shiftBookIdsFromStorage(int removedId){
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			File file = new File("./data/catalog.csv");
			File tempFile = new File("./data/temp.csv");
			reader = new BufferedReader(new FileReader(file));
			writer = new BufferedWriter(new FileWriter(tempFile));
			String currentLine;
			while((currentLine = reader.readLine()) != null) {
				String[] bookInfo = currentLine.split(",");
				int bookId = Integer.parseInt(bookInfo[0]);
				if(bookId > removedId) {
					bookId--;toString
					bookInfo[0] = Integer.toString(bookId);
					String newLine = String.join(",", bookInfo);
					writer.write(newLine + System.getProperty("line.separator"));
				}else {
					writer.write(currentLine + System.getProperty("line.separator"));
				}
			}
			reader.close();
			writer.close();
			file.delete();
			tempFile.renameTo(file);
		}catch(IOException e) {
			System.out.println("File not found");
		}
	} */

/* 	public void shiftIds(int removedId){
		for(int i = removedId; i < bookCatalog.size(); i++) {
			bookCatalog.get(i).setId(bookCatalog.get(i).getId() - 1);
		}
		for(int i = 0; i < users.size(); i++){
			for(int j = 0; j < users.get(i).getCheckedOutList().size(); j++){
				if(users.get(i).getCheckedOutList().get(j) > removedId){
					users.get(i).getCheckedOutList().set(j, users.get(i).getCheckedOutList().get(j) - 1);
				}
			}
		}
		shiftBookIdsFromStorage(removedId);
	} */
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
		//set book catalog id to null
		bookCatalog.get(id-1).setId(-1);
		//removeBookFromFile(id);
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
		if(bookCatalog.get(id-1).isCheckedOut()) {
			return false;
		}
		bookCatalog.get(id-1).setCheckedOut(true);
		bookCatalog.get(id-1).setLastCheckOut(LocalDate.now());
	/* 	try {
			writeBooksToFile(bookCatalog.get(id-1));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("File not found");
		} */
		return true;
	}
	// overide the arrayList get method to return the book at the specified index

	public boolean returnBook(int id) {
		if(id < 1) {
			throw new IllegalArgumentException("Id must be a positive number");
		}
		if(id > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Id is too large for a 32-bit integer");
		}
		if(!bookCatalog.get(id-1).isCheckedOut() || bookCatalog.get(id-1).getId() > bookCatalog.size()) {
			return false;
		}
		bookCatalog.get(id-1).setCheckedOut(false);
		bookCatalog.get(id-1).setLastCheckOut(null);
/* 		try{
			writeBooksToFile(bookCatalog.get(id));
		}catch(IOException e){
			System.out.println("File not found");
		} */
		return true;
	}
	
	public boolean getBookAvailability(int id) {
		if(id < 1) {
			throw new IllegalArgumentException("Id must be a positive number");
		}
		if(id > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Id is too large for a 32-bit integer");
		}
		return bookCatalog.get(id-1).isCheckedOut();
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
	public int bookCount(String title) {
		int count = 0;
		for(int i = 0; i < bookCatalog.size(); i++) {
			if(bookCatalog.get(i).getTitle().equals(title)) {
				count++;
			}
		}
		return count;
	}


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
		output += "Science Fiction\t\t\t\t\t" + (bookCount.get("Science Fiction")) + "\n";
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
		int count = 0;
		for(int i = 0; i < bookCatalog.size(); i++) {
			if(bookCatalog.get(i).isCheckedOut()) {
				output += bookCatalog.get(i).getTitle().toUpperCase() + " by " + bookCatalog.get(i).getAuthor().toUpperCase() + "\n";
				count++;
			}
		}
		
		
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + count + "\n\n";
		// delete the count from memory
		count = 0;


		
		
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
		// make a loop that itterates over users and checks if the books in their checked out list are late if so print the user name and the total fees
		double owedDues = 0;
		double totalFees = 0;
		
		for (User user : users) {
			totalFees = 0;
		
			for (Integer bookId : user.getCheckedOutList()) {
				double bookFees = bookCatalog.get(bookId-1).calculateFees();
				System.out.println(bookFees);
				if (bookFees > 0) {
					totalFees += bookFees;
				}
			}
		
			if (totalFees > 0) {
				owedDues += totalFees;
				output += user.getName() + "\t\t\t\t\t$" + totalFees + "\n";
			}
		}
			
		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + (owedDues) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.
		
		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("./report/report.txt"))){
			writer.write(output);
		}catch(IOException e) {
			System.out.println("File not found");
		}
		
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
