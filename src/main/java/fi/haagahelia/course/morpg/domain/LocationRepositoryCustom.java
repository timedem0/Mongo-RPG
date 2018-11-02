package fi.haagahelia.course.morpg.domain;

import java.util.List;

public interface LocationRepositoryCustom {
	
	public List<Monster> findMonsterByLocation(String locationName);
	public long deleteMonster(String locationName, String monsterName);

}
