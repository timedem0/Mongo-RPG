package fi.haagahelia.course.morpg.domain;

public interface UserRepositoryCustom {
	
	public long deleteChar(String userName, String charName);
	public long insertChar(String userName, Character newChar);
	public Character findCharByName(String userName, String charName);
	public long updateChar(String userName, Character newChar);
}
