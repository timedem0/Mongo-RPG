package fi.haagahelia.course.morpg.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponRepository extends MongoRepository<Weapon, String> {
	
	public List<Weapon> findByWeaponName(String name);
	public void deleteByWeaponName(String name);	
}
