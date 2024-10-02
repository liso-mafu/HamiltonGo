public class Character {
	// this class is the blueprint for creating other characters in Hamilton GO

	// declare all important Character attributes 
	private double basePower ; 						// a range of which base power to use 
	private String charName ; 						// the name of the character 
	private int HP ; 								// this is Hit Points = how much damage a character can take
	private int energy ; 							// measure of when a special ability is available 
	private int defense ; 							// how much an opponent's attack damages player 
	private String desc ; 							// a brief description of the character 

	public Character (String charName, double basePower, int HP, int energy, int defense, String desc) {
		// the attributes associated to a character 
		this.basePower = basePower ;
		this.charName = charName ; 
		this.HP = HP ; 
		this.energy = energy ; 
		this.defense = defense ; 
		this.desc = desc ; 
	}
	public String getname(){
		return this.charName;

	}
	public double getpower(){
		return this.basePower;

	}
	public int gethp(){
		return this.HP;

	}
	public int getenergy(){
		return this.energy;

	}
	public int getdefense(){
		return this.defense;
	}
	public String getdesc(){
		return this.desc;

	}
	public void setname(String name){
		this.charName=name;

	}
	public void setpower(int base){
		this.basePower=base;

	}
	public void sethp(int hp){
		this.HP=hp;
	}
	public void setenergy(int energy){
		this.energy=energy;

	}
	public void setdefense(int defense ){
		this.defense=defense;

	}
	public void setdesc(String desc){
		this.desc=desc;

	}


	public String toString() {
		return (this.charName + "\n"+"\n" + "Health: "+ this.HP +"\n" + "Base Attack Power: "+ this.basePower+"\n" + "Starting Energy: "+ this.energy+"\n" + "Defense: "+ this.defense) ; 
	}
}