public class Character {
	// this class is the blueprint for creating other characters in Hamilton GO

	// declare all important Character attributes 
	private int [] basePower ; 						// a range of which base power to use 
	private String charName ; 						// the name of the character 
	private int HP ; 								// this is Hit Points = how much damage a character can take
	private int energy ; 							// measure of when a special ability is available 
	private int defense ; 							// how much an opponent's attack damages player 
	private String desc ; 							// a brief description of the character 

	public Character (String charName, int [] basePower, int HP, int energy, int defense, String desc) {
		// the attributes associated to a character 
		this.basePower = basePower ;
		this.charName = charName ; 
		this.HP = HP ; 
		this.energy = energy ; 
		this.defense = defense ; 
		this.desc = desc ; 
	}
	
	public String toString(Character a) {
		return ("Your character is " + this.charName + " with " + this.HP + " HP and " + this.defense + " defensive ability" ) ; 
	}
}