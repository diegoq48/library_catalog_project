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
	// make a constructor that takes in all the fields
	public Book(int id, String title, String author, String genre, LocalDate lastCheckOut, boolean checkedOut) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.lastCheckOut = lastCheckOut;
		this.checkedOut = checkedOut;
	}
	
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
		/*
		 * This is supposed to follow the format
		 * 
		 * {TITLE} By {AUTHOR}
		 * 
		 * Both the title and author are in uppercase.
		 */
		return this.getTitle().toUpperCase() + " BY " + this.getAuthor().toUpperCase();
	}
	public double calculateFees() {
		double baseFee = 10.00;
		double feePerDay = 1.50;
		//make the comparison date be september 15th, 2023
		LocalDate september15th = LocalDate.of(2023, 9, 15);
		long daysLate = ChronoUnit.DAYS.between(this.getLastCheckOut(), september15th);
		int daysLateInt = (int) daysLate;

		if (daysLateInt > 31) {
			return (double) (baseFee + ((daysLate-31) * feePerDay));
		}
		return 0.0;
	}
}
