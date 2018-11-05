package fi.haagahelia.course.morpg.domain;

import java.util.List;

public interface UserRepositoryCustom {
	
	public Character findCharByName(String userName, String charName);
	public List<Character> findCharsByUser(String userName);
	public long insertChar(String userName, Character newChar);
	public long updateChar(String userName, Character charToEdit);
	public long updateCharStats(String userName, Character charToEdit, int victoriesUpdate, int defeatsUpdate);
	public long deleteChar(String userName, String charName);
	public long insertDefaultType(String userName, String charName);
	public long insertDefaultWeapon(String userName, String charName);
}
