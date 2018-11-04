package fi.haagahelia.course.morpg.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fi.haagahelia.course.morpg.domain.Character;
import fi.haagahelia.course.morpg.domain.Monster;
import fi.haagahelia.course.morpg.domain.Type;
import fi.haagahelia.course.morpg.domain.Weapon;

public class FightResult {
	
	private String userName;
	private String locationName;
	private Character character;
	private Monster monster;
	private Type type;
	private Weapon weapon;
	
	private int dice;
	private int diceValue;
	
	private int playerResult;
	private int monsterResult;
	private int bonusFromAttack;
	private int bonusFromLocation;
	private int penaltyFromMonsterAttack;
	
	private int battleResult;
	private int victoriesUpdate;
	private int defeatsUpdate;
	
	public FightResult() {
		
	}

    public FightResult(String userName, String locationName, Character character, Monster monster,
			Type type, Weapon weapon, int dice, int diceValue, int playerResult, int monsterResult, int bonusFromAttack,
			int bonusFromLocation, int penaltyFromMonsterAttack, int battleResult, int victoriesUpdate,
			int defeatsUpdate) {

		this.userName = userName;
		this.locationName = locationName;
		this.character = character;
		this.monster = monster;
		this.type = type;
		this.weapon = weapon;
		this.dice = dice;
		this.diceValue = diceValue;
		this.playerResult = playerResult;
		this.monsterResult = monsterResult;
		this.bonusFromAttack = bonusFromAttack;
		this.bonusFromLocation = bonusFromLocation;
		this.penaltyFromMonsterAttack = penaltyFromMonsterAttack;
		this.battleResult = battleResult;
		this.victoriesUpdate = victoriesUpdate;
		this.defeatsUpdate = defeatsUpdate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public int getDice() {
		return dice;
	}

	public void setDice(int dice) {
		this.dice = dice;
	}
	
	public int getDiceValue() {
		return diceValue;
	}

	public void setDiceValue(int diceValue) {
		this.diceValue = diceValue;
	}

	public int getPlayerResult() {
		return playerResult;
	}

	public void setPlayerResult(int playerResult) {
		this.playerResult = playerResult;
	}

	public int getMonsterResult() {
		return monsterResult;
	}

	public void setMonsterResult(int monsterResult) {
		this.monsterResult = monsterResult;
	}

	public int getBonusFromAttack() {
		return bonusFromAttack;
	}

	public void setBonusFromAttack(int bonusFromAttack) {
		this.bonusFromAttack = bonusFromAttack;
	}

	public int getBonusFromLocation() {
		return bonusFromLocation;
	}

	public void setBonusFromLocation(int bonusFromLocation) {
		this.bonusFromLocation = bonusFromLocation;
	}

	public int getPenaltyFromMonsterAttack() {
		return penaltyFromMonsterAttack;
	}

	public void setPenaltyFromMonsterAttack(int penaltyFromMonsterAttack) {
		this.penaltyFromMonsterAttack = penaltyFromMonsterAttack;
	}

	public int getBattleResult() {
		return battleResult;
	}

	public void setBattleResult(int battleResult) {
		this.battleResult = battleResult;
	}

	public int getVictoriesUpdate() {
		return victoriesUpdate;
	}

	public void setVictoriesUpdate(int victoriesUpdate) {
		this.victoriesUpdate = victoriesUpdate;
	}

	public int getDefeatsUpdate() {
		return defeatsUpdate;
	}

	public void setDefeatsUpdate(int defeatsUpdate) {
		this.defeatsUpdate = defeatsUpdate;
	}

	@Override
    public String toString() {
    	ObjectMapper mapper = new ObjectMapper();
    	
    	String jsonString = "";
		try {
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			jsonString = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return jsonString;
    }
}
