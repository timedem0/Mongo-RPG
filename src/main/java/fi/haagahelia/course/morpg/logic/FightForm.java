package fi.haagahelia.course.morpg.logic;

public class FightForm {
	
	private String userName;
	private String charName;
	private String locationName;
	
	public FightForm() {
		
	}
	
	public FightForm(String userName, String charName, String locationName) {
		this.userName = userName;
		this.charName = charName;
		this.locationName = locationName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}
	
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}
