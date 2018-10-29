package fi.haagahelia.course.morpg.domain;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;

public class SignForm {
	
    @NotEmpty
    @Size(min=4, max=30)
    private String name = "";

    @NotEmpty
    @Size(min=5, max=30)
    private String password = "";

    @NotEmpty
    @Size(min=5, max=30)
    private String passwordCheck = "";

    @NotEmpty
    private String role = "user";

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

	public String getPasswordCheck() {
		return passwordCheck;
	}

	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
