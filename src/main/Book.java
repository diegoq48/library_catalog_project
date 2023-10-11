/**
 * This class represents a book in the library containing the following information:
 * - int id represents a unique identifier for the book used for iteration in the data structure.
 * - String title represents the title of the book.
 * - String author represents the author of the book.
 * - String genre represents the genre of the book.
 * - LocalDate lastCheckOut represents the last time the book was checked out, using the Java Time library to represent the date.
 *   If a book is added, the lastChecked out date will then become September 15th, 2023, as per the project requirements.
 * - boolean checkedOut represents whether or not the book is checked out.
 */
package main;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private LocalDate lastCheckOut;
    private boolean checkedOut;

    /**
     * Basic constructor for the book. Checks for glaringly obvious error cases.
     *
     * @param id           The unique identifier for the book.
     * @param title        The title of the book.
     * @param author       The author of the book.
     * @param genre        The genre of the book.
     * @param lastCheckOut The last time the book was checked out.
     * @param checkedOut   Whether or not the book is currently checked out.
     * @throws IllegalArgumentException if any of the parameters are invalid.
     */
    public Book(int id, String title, String author, String genre, LocalDate lastCheckOut, boolean checkedOut) {
        if (id < 1) {
            throw new IllegalArgumentException("ID cannot be negative or 0");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        if (genre == null || genre.isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be null or empty");
        }
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.lastCheckOut = lastCheckOut;
        this.checkedOut = checkedOut;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getLastCheckOut() {
        return lastCheckOut;
    }

    public void setLastCheckOut(LocalDate lastCheckOut) {
        this.lastCheckOut = lastCheckOut;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    @Override
    public String toString() {
        return this.getTitle().toUpperCase() + " BY " + this.getAuthor().toUpperCase();
    }

    /**
     * Calculates late fees for the book based on the last checkout date.
     *
     * @return The calculated late fee for the book.
     */
    public float calculateFees() {
        long daysSince = ChronoUnit.DAYS.between(lastCheckOut, LocalDate.of(2023, 9, 15));
        if (daysSince <= 30) {
            return 0;  
        } 
        float baseFee = 10.0f;
        float extraBilling = (daysSince - 31.0f) * 1.5f;
        return baseFee + extraBilling;
        
    }
}
