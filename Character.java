public class Character {
	// this class is the blueprint for creating other characters in Hamilton GO

	// declare all important Character attributes 
	private double basePower ; 						// a range of which base power to use 
	private String characterName ; 						// the name of the character 
	private double HP = 100.0 ; 								// this is Hit Points = how much damage a character can take
	private double energy  = 0.0; 							// measure of when a special ability is available 
	private double defense ; 							// how much an opponent's attack damages player 
	private String desc ; 							// a brief description of the character 

	public Character (String charName, double basePower, double defense, String desc) {
		// the attributes associated to a character 
		this.basePower = basePower ;
		this.characterName = charName ; 
		this.defense = defense ; 
		this.desc = desc ; 
	}

    // accessors 

    public String getCharacterName ()  {
        return characterName ;
    }
    public double getBasePower () {
        return basePower ; 
    }
    public double getHP () {
        return HP ; 
    }
    public double getEnergy () {
        return energy ; 
    }
    public String getDescription () {
        return desc ; 
    }
    public double defense () {
        return defense ; 
    }

    @Override
    public String toString () {
		return String.format("%s - %s with base power %.2f", characterName, desc, basePower) ; 
	}

    // modifiers 

    public void updateHealth(double health) {
        this.HP += health ; 
    }
}