package models;

import java.util.List;

public class Book {
	private String id;
	private String title;
	private String authorLastName;
	private String authorFirstName;
	private int rating;
	
	public Book() {
		setid("none");
		setTitle("title");
		setAuthorLastName("lastname");
		setAuthorFirstName("firstname");
		setRating(5);
	}
	
	public Book(String id, 
				String title, 
				String authorLastName, 
				String authorFirstName, 
				int rating) {
		this.id = id;
		this.title = title;
		this.authorLastName = authorLastName;
		this.authorFirstName = authorFirstName;
		this.rating = rating;
	}
	
	public Book(List<String> list) {
		this.id = list.get(0);
		this.title = list.get(1);
		this.authorLastName = list.get(2);
		this.authorFirstName = list.get(3);
		try {
			this.rating = Integer.parseInt(list.get(4));
		} catch (NumberFormatException e) {
			this.rating = 5;
		}
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthorLastName() {
		return authorLastName;
	}

	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}

	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public boolean equals(Book other) {
		return this.id == other.id ? true : false;
	}

	@Override
	public String toString() {
		return this.title + " by " + this.authorFirstName + " " + this.authorLastName;
	}
}
