package fi.haagahelia.course.morpg.domain;

public interface UserRepositoryCustom {
	
	public Character findCharByName(String userName, String charName);
	public long insertChar(String userName, Character newChar);
	public long updateChar(String userName, Character newChar);
	public long deleteChar(String userName, String charName);
}
