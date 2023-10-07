/* This file shows the library catalog which implements methods necesary in order to modify convert and in general mess 
 * around with the catalog of books at our disposal 
 */
package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import data_structures.ArrayList;
import interfaces.FilterFunction;
import interfaces.List;

public class LibraryCatalog {
	
	private List<Book> bookCatalog;	
	private List<User> users;
	/**
	 * Initializes a new LibraryCatalog object by reading data from external CSV files.
	 *
	 * This constructor creates a new LibraryCatalog object and initializes its internal lists of users and books
	 * by reading data from external CSV files. It performs the following actions:
	 *
	 * 1. Initializes an empty list of users.
	 * 2. Initializes an empty list of books.
	 * 3. Calls the {@link #getBooksFromFiles()} method to populate the book catalog from a CSV file.
	 * 4. Calls the {@link #getUsersFromFiles()} method to populate the list of users from a CSV file.
	 *
	 * The CSV files used for data population are expected to be located at the default paths "./data/catalog.csv" for books
	 * and "./data/users.csv" for users. These files should follow specific formats as documented in the respective methods.
	 *
	 * If there are any errors while reading the CSV files or if the files are not found, this constructor will throw
	 * an IOException.
	 *
	 * @throws IOException if there is an error reading the CSV files or if the files are not found.
	 */
	public LibraryCatalog() throws IOException {
		users = new ArrayList<User>();
		bookCatalog = new ArrayList<Book>();
		getBooksFromFiles();
		getUsersFromFiles();
	}
	/**
	 * Retrieves a list of Book objects by parsing data from a CSV file located at "./data/catalog.csv".
	 *
	 * This method reads the contents of the CSV file line by line and creates Book objects from the data found in each line.
	 * The CSV file is expected to have the following format for each book entry:
	 *
	 * Format: "id, title, author, genre, lastCheckOut, checkedOut"
	 * - id: An integer representing the book's unique identifier.
	 * - title: The title of the book.
	 * - author: The author of the book.
	 * - genre: The genre or category of the book.
	 * - lastCheckOut: A date in ISO 8601 format (yyyy-MM-dd) representing the last checkout date.
	 * - checkedOut: A boolean indicating whether the book is currently checked out (true) or not (false).
	 *
	 * If any line in the CSV file does not match the expected format or contains missing or empty fields,
	 * it will be skipped, and an error message will be printed to the standard error stream.
	 *
	 * @return A List of Book objects containing the information parsed from the CSV file.
	 * @throws IOException if there is an error reading the file or if the file is not found.
	 */
	public List<Book> getBooksFromFiles() throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("./data/catalog.csv"))) {
			String line = reader.readLine();
			line = reader.readLine();
			while (line != null) {
				String[] bookInfo = line.split(",");
				
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
						System.err.println("Invalid ID format: " + bookInfo[0]);
					} catch (DateTimeParseException e) {
						System.err.println("Invalid date format: " + bookInfo[4]);
					}
				} else {
					System.err.println("Null or empty lastCheckOut field");
				}

				line = reader.readLine();
			}
			return bookCatalog;
		} catch (IOException e) {
			throw new IOException("File not found");
		}
	}

	
	/**
	 * Retrieves a list of User objects by parsing data from a CSV file located at "./data/users.csv".
	 *
	 * This method reads the contents of the CSV file line by line and creates User objects from the data found in each line.
	 * The CSV file is expected to have the following format for each user entry:
	 *
	 * Format: "id, name, checkedOutBooks"
	 * - id: An integer representing the user's unique identifier.
	 * - name: The name of the user.
	 * - checkedOutBooks: A list of book IDs (space-separated) that the user has checked out, enclosed in square brackets.
	 *
	 * If a user has not checked out any books, the "checkedOutBooks" field will be empty.
	 *
	 * If any line in the CSV file does not match the expected format or contains missing or empty fields,
	 * it will be skipped, and an error message will be printed to the standard error stream.
	 *
	 * @return A List of User objects containing the information parsed from the CSV file.
	 * @throws IOException if there is an error reading the file or if the file is not found.
	 */
	private List<User> getUsersFromFiles() throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader("./data/user.csv"))){
			String line = reader.readLine();
			line = reader.readLine();
			while(line != null) {
				String[] userInfo = line.split(",");
				int id = Integer.parseInt(userInfo[0]);
				String name = userInfo[1];
				List<Integer> checkedOutList = new ArrayList<Integer>();
				if(userInfo.length > 2) {
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
	/* Getters for the created data structure  */
	public List<Book> getBookCatalog() {
		return bookCatalog;
	}
	/* Getters for the created data structure  */
	public List<User> getUsers() {
		return users;
	}

	//Todo implement method to maintain the book catalog after tas have corrected the code 
	/* public void writeBooksToFile(Book bookToBeAdded) throws IOException{

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
	} */
	/**
	 * Adds a new book to the library catalog with the specified title, author, and genre.
	 *
	 * This method creates a new Book object with the provided title, author, and genre, and assigns it a unique ID
	 * based on the current size of the book catalog list. The book is initially marked as not checked out and given
	 * a default lastCheckOut date of September 15, 2023.
	 *
	 * If any of the input parameters (title, author, genre) are null or empty, this method throws an
	 * IllegalArgumentException, indicating that none of these fields can be empty.
	 *
	 * The newly created Book object is then added to the library catalog.
	 *
	 * @param title The title of the book to be added.
	 * @param author The author of the book to be added.
	 * @param genre The genre of the book to be added.
	 * @throws IllegalArgumentException if any of the input fields (title, author, genre) are null or empty.
	 */
	public void addBook(String title, String author, String genre) {
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

	/* Todo method to implement after ta's grade the project  */
	/* public void removeBookFromFile(int id){
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
 */
	/**
	 * Removes a book from the library catalog with the specified ID.
	 *
	 * This method removes the book with the specified ID from the library catalog. 
	 * it is removed from the library catalog by setting its ID to -1.
	 *
	 * @param id The ID of the book to be removed.
	 * @throws IllegalArgumentException if the provided ID is not a positive number.
	 */
	public void removeBook(int id) {
		if(id < 1) {
			throw new IllegalArgumentException("Id must be a positive number");
		}
		if(id > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Id is too large for a 32-bit integer");
		}
		//Set the id to -1 as to not mess up the id's of the other books in the arraylist
		bookCatalog.get(id-1).setId(-1);
		
	}	
	
	/**
	 * Adds a new user to the library with the specified name.
	 *
	 * This method creates a new User object with the provided name and assigns it a unique ID
	 * based on the current size of the users list. The user is initially not assigned any books.
	 *
	 * If the provided name is null or empty, this method throws an IllegalArgumentException,
	 * indicating that the name cannot be empty.
	 *
	 * The newly created User object is then added to the list of users.
	 *
	 * @param name The name of the user to be added.
	 * @throws IllegalArgumentException if the provided name is null or empty.
	 */
	public boolean checkOutBook(int id) {
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
		return true;
	}

	 /**
	 * Returns a book to the library catalog based on the provided book ID.
	 *
	 * This method attempts to return a book to the library catalog with the given book ID. It performs the following steps:
	 *
	 * 1. Validates that the provided book ID is a positive number. If the ID is not positive, an IllegalArgumentException is thrown.
	 * 2. Validates that the provided book ID is within the valid range for a 32-bit integer. If the ID is too large, an IllegalArgumentException is thrown.
	 * 3. Checks if the book with the specified ID is currently checked out and if the ID is within the valid range of book IDs in the catalog.
	 *    - If the book is not checked out or the provided ID is out of range, the method returns false, indicating that the return operation failed.
	 * 4. If the book is checked out and the ID is valid, the book's status is updated to "not checked out," and the lastCheckOut date is set to null.
	 * 5. Optionally, this method can be used to write the updated book catalog to a file (uncommented code block).
	 *
	 * @param id The unique identifier (ID) of the book to be returned.
	 * @return true if the book was successfully returned; false if the book was not checked out or the ID is invalid.
	 * @throws IllegalArgumentException if the provided book ID is not a positive number or is too large for a 32-bit integer.
	 */
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
		return true;
	}
	
	/**
	 * Checks the availability of a book in the library catalog based on the provided book ID.
	 *
	 * This method determines whether a book with the given book ID is available for checkout in the library catalog.
	 * It performs the following steps:
	 *
	 * 1. Validates that the provided book ID is a positive number. If the ID is not positive, an IllegalArgumentException is thrown.
	 * 2. Validates that the provided book ID is within the valid range for a 32-bit integer. If the ID is too large, an IllegalArgumentException is thrown.
	 * 3. Retrieves the book with the specified ID from the catalog and checks if it is currently checked out.
	 *    - If the book is checked out, the method returns false, indicating that the book is not available.
	 *    - If the book is not checked out, the method returns true, indicating that the book is available for checkout.
	 *
	 * @param id The unique identifier (ID) of the book to check for availability.
	 * @return true if the book is available (not checked out); false if the book is currently checked out or the ID is invalid.
	 * @throws IllegalArgumentException if the provided book ID is not a positive number or is too large for a 32-bit integer.
	 */

	public boolean getBookAvailability(int id) {
		if(id < 1) {
			throw new IllegalArgumentException("Id must be a positive number");
		}
		if(id > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Id is too large for a 32-bit integer");
		}
		return !bookCatalog.get(id-1).isCheckedOut();
	}

	/**
	 * Counts the number of books in each genre and returns the result as a HashMap.
	 *
	 * This method iterates through the book catalog and counts the number of books in each genre category.
	 * It creates a HashMap where the keys represent the genres, and the values represent the count of books in each genre.
	 *
	 * @return A HashMap where each key is a genre and the corresponding value is the count of books in that genre.
	 */

	public HashMap<String, Integer> genreCount() {
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
	/**
	 * Counts the number of books with a specific title in the library catalog.
	 *
	 * This method iterates through the book catalog and counts the number of books that have a title
	 * matching the provided title parameter.
	 *
	 * @param title The title of the book to count.
	 * @return The number of books in the library catalog that have the specified title.
	 */
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

		HashMap<String, Integer> bookCount = genreCount();
		output += "Adventure\t\t\t\t\t" + (bookCount.get("Adventure")) + "\n";
		output += "Fiction\t\t\t\t\t\t" + (bookCount.get("Fiction")) + "\n";
		output += "Classics\t\t\t\t\t" + (bookCount.get("Classics")) + "\n";
		output += "Mystery\t\t\t\t\t\t" + (bookCount.get("Mystery")) + "\n";
		output += "Science Fiction\t\t\t\t\t" + (bookCount.get("Science Fiction")) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + (bookCatalog.size()) + "\n\n";
		bookCount = null;

		output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		int count = 0;
		for(int i = 0; i < bookCatalog.size(); i++) {
			if(bookCatalog.get(i).isCheckedOut()) {
				output += bookCatalog.get(i).getTitle().toUpperCase() + " by " + bookCatalog.get(i).getAuthor().toUpperCase() + "\n";
				count++;
			}
		}
		
		
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + count + "\n\n";
		count = 0;


		
		

		output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";

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
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("./report/report.txt"))){
			writer.write(output);
		}catch(IOException e) {
			System.out.println("File not found");
		}
		
	}
	/**
	 * Filters and retrieves a list of books from the library catalog based on a provided filter function.
	 *
	 * This method applies a user-defined filter function (func) to each book in the library catalog.
	 * If a book satisfies the filter condition specified by the function, it is added to the resulting list.
	 *
	 * @param func The filter function that determines which books to include in the result.
	 * @return A List of books that meet the filter criteria specified by the provided filter function.
	 */
	public List<Book> searchForBook(FilterFunction<Book> func) {
		// Initialize a list to store the filtered books.
		ArrayList<Book> filteredBooks = new ArrayList<Book>();

		// Iterate through the book catalog.
		for (int i = 0; i < bookCatalog.size(); i++) {
			// Check if the current book satisfies the filter condition specified by the function.
			if (func.filter(bookCatalog.get(i))) {
				// If the book meets the criteria, add it to the filtered list.
				filteredBooks.add(bookCatalog.get(i));
			}
		}

		return filteredBooks;
	}

	/**
	 * Filters and retrieves a list of users from the library's user list based on a provided filter function.
	 *
	 * This method applies a user-defined filter function (func) to each user in the user list.
	 * If a user satisfies the filter condition specified by the function, they are added to the resulting list.
	 *
	 * @param func The filter function that determines which users to include in the result.
	 * @return A List of users that meet the filter criteria specified by the provided filter function.
	 */
	public List<User> searchForUsers(FilterFunction<User> func) {
		// Initialize a list to store the filtered users.
		ArrayList<User> filteredUsers = new ArrayList<User>();

		// Iterate through the user list.
		for (int i = 0; i < users.size(); i++) {
			// Check if the current user satisfies the filter condition specified by the function.
			if (func.filter(users.get(i))) {
				// If the user meets the criteria, add them to the filtered list.
				filteredUsers.add(users.get(i));
			}
		}

		return filteredUsers;
	}

	
}
