package fi.haagahelia.course.morpg.domain;

import java.util.List;

public interface UserRepositoryCustom {
	
	public List<User> testQuery(String name);
	public long deleteChar(String userName, String charName);
	
}
