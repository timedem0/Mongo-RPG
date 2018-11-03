package fi.haagahelia.course.morpg.domain;

import java.util.List;

public interface LocationRepositoryCustom {
	
	public List<Monster> findMonsterByLocation(String locationName);
	public long deleteMonster(String locationName, String monsterName);
	public long insertMonster(String locationName, Monster newMonster);
	public long updateMonster(String locationName, Monster monsterToEdit);

}
