package fi.haagahelia.course.morpg.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "Users")
public class User {
	@Id
	@Field
	private String id;
	@Field
	private String name;
	@JsonIgnore
	@Field
	private String password;
	@Field
	private int role;
	@Field
	private List<Character> characters;
	
	protected User() {
		this.characters = new ArrayList<>();
	}

	public User(String name, String password, int role, List<Character> characters) {
		this.name = name;
		this.password = password;
		this.role = role;
		this.characters = characters;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public List<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}	
}
