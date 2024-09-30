import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;

public class CharacterList { 
    private LinkedList <Character> characters ; 

    /* 
    public CharacterList () {
        characters = new LinkedList <>() ; 
    } // constructor 
    */

    public CharacterList (int n) {
        // this is the base character options 
        characters = new LinkedList<>() ; 
        characters.add(new Character("James Connan", 18.0,  10.0, "A worthy opponent for all lecturers and students")) ;
        characters.add(new Character("Dane Brown", 16.0,  10.0, "A well-versed fighter")) ;
        characters.add(new Character("Zelalem Shibeshi", 15.0,  10.0, "A developer of extraordinary pain")) ;
        characters.add(new Character("Jordan Prince", 19.0,  10.0, "A younger fighter who hasn't reached the peak of his powers")) ;
        characters.add(new Character("Liso Mafu", 18.0, 10.0, "A leader amongst fighters")) ; 
    } // constructor 
    
    // accessors 
    public Character getCharacter(int n) {
        return characters.get(n) ;
    }
    public void add (Character c) {
        characters.add(c) ;
    } // add 

    public void remove (Character c) {
        characters.remove(c); 
    } // remove 

    public String toString() {
        String data = "" ; 
        for (Character c : characters) {
            data += c ; 
        }

        return data ; 
    } // toString 

    
}