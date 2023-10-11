package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import data_structures.ArrayList;

/**
 * FILEPATH: /home/diegoq/Projects/ciic4020/librarycatalog-fall2023project-diegoq48/src/main/LibraryCatalogGUI.java
 * 
 * This class represents the graphical user interface for the Library Catalog application. It extends the JFrame class and contains several buttons for interacting with the library catalog. The GUI displays the current state of the library catalog and allows the user to add, remove, and search for books. 
 */
public class LibraryCatalogGUI extends JFrame {
    
    /**
     * The LibraryCatalog object used by the GUI.
     */
    private LibraryCatalog libraryCatalog;
    
    /**
     * The JTextArea used to display the library catalog and search results.
     */
    private JTextArea outputTextArea;
    
    /**
     * The JButton used to add a book to the library catalog.
     */
    private JButton addBookButton;
    
    /**
     * The JButton used to switch between the library catalog and search results.
     */
    private JButton switchViewButton;   
    
    /**
     * The JButton used to remove a book from the library catalog.
     */
    private JButton removeBookButton;
    
    /**
     * The JButton used to search for a book in the library catalog.
     */
    private JButton searchForBookButton;

    /**
     * Constructs a new LibraryCatalogGUI object. Initializes the LibraryCatalog object and sets up the GUI components.
     */
    public LibraryCatalogGUI() {
        try {
            libraryCatalog = new LibraryCatalog();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error initializing library catalog.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    
        setTitle("Library Catalog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane, BorderLayout.CENTER);
    
        switchViewButton = new JButton("Switch View"); 
        switchViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchView(); 
            }
        });
    
        addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        
        removeBookButton = new JButton("Remove Book"); 
        removeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBook();
            }
        });

        searchForBookButton = new JButton("Search for Book");
        searchForBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchForBook();
            }
        });

    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(searchForBookButton);
        buttonPanel.add(switchViewButton);
        buttonPanel.add(addBookButton);
        buttonPanel.add(removeBookButton); 
        add(buttonPanel, BorderLayout.SOUTH);
    
        displayLibraryCatalog();
    
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    /**
     * Switches the view between the library catalog and search results.
     */
    private void switchView() {
        if (outputTextArea.getText().equals(libraryCatalog.getReport())) {
            displayLibraryCatalog();
        } else {
            try {
                libraryCatalog.generateReport();
                outputTextArea.setText(libraryCatalog.getReport());
            } catch (IOException e) {
                outputTextArea.setText("Error generating the report.");
            }
        }
    }
    
    /**
     * Displays the current state of the library catalog in the outputTextArea.
     */
    private void displayLibraryCatalog() {
        StringBuilder catalogText = new StringBuilder("Library Catalog:\n");

        for (Book book : libraryCatalog.getBookCatalog()) {
            if(book.getId() == -1) {continue;}
            catalogText.append("ID: ").append(book.getId()).append("\n");
            catalogText.append("Title: ").append(book.getTitle()).append("\n");
            catalogText.append("Author: ").append(book.getAuthor()).append("\n");
            catalogText.append("Genre: ").append(book.getGenre()).append("\n");
            catalogText.append("Last CheckOut: ").append(book.getLastCheckOut()).append("\n");
            catalogText.append("Checked Out: ").append(book.isCheckedOut() ? "Yes" : "No").append("\n");
            catalogText.append("\n");
        }

        outputTextArea.setText(catalogText.toString());
    }

    /**
     * Searches for a book in the library catalog based on a user-provided search term. Displays the search results in the outputTextArea.
     */
    private void searchForBook(){
        String search = JOptionPane.showInputDialog(this, "Enter search term:");
        ArrayList<Book> searchResults = libraryCatalog.searchForBook(search);
        StringBuilder searchResultsText = new StringBuilder("Search Results:\n");
        if(searchResults.isEmpty()){
            searchResultsText.append("No results found.\n");
        }
        for (Book book : searchResults) {
            searchResultsText.append("ID: ").append(book.getId()).append("\n");
            searchResultsText.append("Title: ").append(book.getTitle()).append("\n");
            searchResultsText.append("Author: ").append(book.getAuthor()).append("\n");
            searchResultsText.append("Genre: ").append(book.getGenre()).append("\n");
            searchResultsText.append("Last CheckOut: ").append(book.getLastCheckOut()).append("\n");
            searchResultsText.append("Checked Out: ").append(book.isCheckedOut() ? "Yes" : "No").append("\n");
            searchResultsText.append("\n");
        }

        outputTextArea.setText(searchResultsText.toString());
    }

    /**
     * Removes a book from the library catalog based on a user-provided book ID. Displays the updated library catalog in the outputTextArea.
     */
    private void removeBook() {
        String idString = JOptionPane.showInputDialog(this, "Enter book ID:");
        int id = Integer.parseInt(idString);
        libraryCatalog.removeBook(id);
        displayLibraryCatalog();
    }

    /**
     * Adds a book to the library catalog based on user-provided information (title, author, genre). 
     */
    private void addBook() {
        String title = JOptionPane.showInputDialog(this, "Enter book title:");
        String author = JOptionPane.showInputDialog(this, "Enter book author:");
        String genre = JOptionPane.showInputDialog(this, "Enter book genre:");

        if (title != null && author != null && genre != null) {
            libraryCatalog.addBook(title, author, genre);
                    displayLibraryCatalog();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LibraryCatalogGUI gui = new LibraryCatalogGUI();
                gui.setVisible(true);
            }
        });
    }
}
