import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.sound.sampled.FloatControl;


// PS this code does not run in VS code, it only works when compiled and run from terminal
// we dont know why lol :(
public class PokemonGame {

    private static Clip clip;                   //for playing music
    private static Clip flip;                //for attack sounds
    
    static String lst[];
    
    static ArrayList<String> playerNames=new ArrayList<>();              //store player names
    static ArrayList<String[]>playerSelectedPokemon=new ArrayList<>();      //store each player's selected Pokemon
    
    static int currentPlayerIndex=0;      //track which player is selecting their character
    static int numberOfPlayers;            //global variable to store the selected number of players
    
    

    //creating objects with Character class
    static Character pikachu=new Character("Pikachu", 90, 100, 50, 0,
            "This electric pokemon is really energetic, and extremely powerful." + "\n"
                    + "Becareful though it's a bit of a glass cannon!");
    static Character Lucario=new Character("Lucario", 60, 225, 25, 8,
            "Lucario is a well rounded fighter but" + "\n" + " although its lack of defense can be its downfall");
    static Character garchomp=new Character("Garchomp", 50, 200, 30, 22, "Careful you dont get bitten!" + "\n"
            + " Garchomp is difficult to damage" + "\n" + "due to its strong defensive skin");
    static Character greninja=new Character("Greninja", 70, 150, 20, 12,
            "Greninja has trained his whole life for battle" + "\n" + "dispite his low health stat");
    static Character zoroark=new Character("Zoroark", 40, 250, 40, 15, "its lurking in the woodland shadows" + "\n"
            + "This is a strong tank fighter" + "\n" + " that can take a lot of damage");
    static Character Absol=new Character("Absol", 20, 300, 55, 0, "It uses the moons power to crush enemies," + "\n"
            + "and dispite its immense health" + "\n" + "has no defensive capabilities");
    static Character mewtwo=new Character("Mewtwo", 50, 275, 20, 7,
            "The experiment gone wrong this man-made Pokemon" + "\n" + "was made to be perfect but"+"\n"
                    + "the experiment was never complete," + "\n" + "its attack power is lower than expected");
    static Character charizard=new Character("Charizard", 60, 250, 0, 5, "They say its roar causes volcanos to erupt,"
            + "\n" + "this durable pokemon is at the top of the foodchain" + "\n" + "and as a result has gotten lazy");
    
    
    
    static String[] characterNames = { "Ash", "Cynthia", "Brown", "Connan", "Motara", "Wells", "Z", "Jessie" };
    static String[] characterImages = { "Ash.JPEG", "Cynthia.JPEG", "Brown.JPEG", "Connan.JPEG",
            "Motara.JPEG", "Wells.JPEG", "Z.JPEG", "Jessie.JPEG" };

    //pokemon names and image file names
    static String[] pokemonNames = { "Pikachu", "Lucario", "Garchomp", "Greninja", "Zoroark", "Absol", "Mewtwo",
            "Charizard" };
    static String[] pokemonImages = { "Pikachu.JPEG", "Lucario.JPEG", "Garchomp.JPEG", "Greninja.JPEG",
            "Zoroark.JPEG", "Absol.JPEG", "Mewtwo.JPEG", "Charizard.JPEG" };

    // global variables to store player names, characters, and Pokemon selections
    static Character pokistat;

    static String p1name, p2name;
    static String p1character, p2character;
    static Character player1poki1, player1poki2, player2poki1, player2poki2;
    
    
    

    static JFrame frame=new JFrame("Hamilton Go!");
    static JButton nextButton;                      // the Next button to  proceed to gui2

    public static void main(String[] args) {
                                               //play background music
        playBackgroundMusic("pokisong.WAV");
        showGui1();
    }

    //create the frame
    public static void showGui1() {

        frame.getContentPane().removeAll();
        defeatedp1=0;
        defeatedp2=0;

        //get screen size and set the frame to fullscreen
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screenSize=toolkit.getScreenSize();

        frame.setSize(screenSize.width,screenSize.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set the frame icon (ensure the image is in the same directory or provide afull path)
        try {
            Image icon=ImageIO.read(new File("Pokeball2.JPEG"));
            Image resizedIcon=icon.getScaledInstance(64,64,Image.SCALE_SMOOTH);
            frame.setIconImage(resizedIcon);
        } 
        catch (IOException e) {
            System.out.println("Icon image not found.");
            e.printStackTrace();
        }

        //load the background image and create a custom jpanel to display it
        try {
            final Image backgroundImage=ImageIO.read(new File("backgrounddoForMenu.JPEG"));
            JPanel background_panel=new JPanel() {
                @Override
                
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage,0,0,getWidth(),getHeight(), this);
                }
            };
            background_panel.setLayout(null);

            // add the welcome message at the top left
            JLabel welcomeLabel=new JLabel("WELCOME TO HAMILTON GO!");
            welcomeLabel.setFont(new Font("Serif",Font.BOLD,70));
            
            welcomeLabel.setForeground(Color.YELLOW);
            
            welcomeLabel.setBounds(400,20,screenSize.width,50);
            background_panel.add(welcomeLabel);

            //add the  message just below
            JLabel labelselectplayers=new JLabel("");
            labelselectplayers.setFont(new Font("Serif",Font.BOLD,12));
           
            labelselectplayers.setForeground(Color.YELLOW);
            
            labelselectplayers.setBounds(300,400,screenSize.width,50);
            background_panel.add(labelselectplayers);

            // button sizes and spacing.
            double buttonSize=screenSize.height*0.10;                            // 10 percent of the screen height for square buttons
            double buttonSpacing=screenSize.height*0.10;                          // 10 percent of the screen height for spacing

            ActionListener buttonListener=new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton=(JButton) e.getSource();
                    numberOfPlayers=2; //store the selected number of players globally
                                         
                    getPlayerNames(numberOfPlayers);//get player names before proceeding
                }
            };

             //layout for 7 buttons 1st row (1,2,3) 2nd row (4,5,6), 3rd row (7)
            int xStart=20;                           //starting X position for the buttons
            int yStart=150;              // starting Y position for the first row

            //Array to store button numbers in proper order
            int[] buttonNumbers = {1,2,3, 4,5, 6,7};

            // loop through and place buttons
            for (int i=0;i<buttonNumbers.length;i++) {
                JButton playerButton=new JButton("FIRST CLICK HERE TO ENTER PLAYER NAMES: ");     // string.valueOf(buttonNumbers[i])
                playerButton.setBackground(Color.BLUE);
                playerButton.setForeground(Color.WHITE);
                
                playerButton.setFont(new Font("Serif",Font.BOLD,20));
                playerButton.setBounds(700,550,(int)500,(int)100);          // square buttons

                //hide buttons and disable their functionality
                if (buttonNumbers[i]!=2) {

                    playerButton.setVisible(false);
                    playerButton.setEnabled(false);

                } 
                else 
                {


                    playerButton.addActionListener(buttonListener);
                }

                background_panel.add(playerButton);

                if ((i+1)%3==0) {

                    yStart+=buttonSize+buttonSpacing;
                    xStart=20;                                       //seset xStart for the next row
                } 
                else
                {
                    xStart+=buttonSize+ buttonSpacing;
                }
            }

            // add next" button
            nextButton=new JButton("START");
            nextButton.setEnabled(false);                           //disabled until players are selected
            nextButton.setBackground(Color.RED);
            nextButton.setForeground(Color.WHITE);

            nextButton.setFont(new Font("Serif",Font.BOLD,60));
            nextButton.setBounds(800,750, (int)(screenSize.height*0.30),(int) buttonSize);

            nextButton.addActionListener(e -> showGui2());              // proceed to gui2 when clicked
            background_panel.add(nextButton);

            //add the background panel to the frame
            frame.setContentPane(background_panel);
            frame.setVisible(true);

        } catch (IOException e) {

            System.out.println("Background image not found.");
            e.printStackTrace();
        }

    }

    //method to play background music
    private static void playBackgroundMusic(String filePath) {
        try {
            if (clip==null||!clip.isRunning()) {
                AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(new File(filePath));
                
                clip=AudioSystem.getClip();
                clip.open(audioInputStream);
                
                FloatControl gainControl=(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-15.0f);
                
                clip.loop(Clip.LOOP_CONTINUOUSLY);                               // loop the music continuously
                clip.start();
            }
        } catch (Exception e) {
            System.out.println("Error playing background music.");
            e.printStackTrace();
        }
    }

    // method to get player names based on the selected number of players
    private static void getPlayerNames(int numberOfPlayers) {
        for (int i=1;i<=numberOfPlayers;i++) {
            String playerName;
            while (true) {
                playerName=JOptionPane.showInputDialog(null,"Player "+i+" Enter Your Name:");
                if (playerName !=null&&!playerName.trim().isEmpty()) {
                    //store each player's name in the corresponding variable
                    switch (i) {
                        case 1:
                            p1name=playerName;
                            break;
                        case 2:
                            p2name=playerName;
                            break;
                    }
                    break;                                                              //exit the loop if a valid name is entered
                } else {
                    JOptionPane.showMessageDialog(null,"Please enter a valid name.");
                }
            }
        }
        nextButton.setEnabled(true);                                   //enable next button after names are entered
    }

    //method to display gui2 (character selection)
    private static void showGui2() {
        if (currentPlayerIndex>=numberOfPlayers) {
            currentPlayerIndex=0;                                     //reset index for Pokemon selection
        }

        frame.getContentPane().removeAll();                                                       //Clear the first GUI

        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screenSize=toolkit.getScreenSize();

        JPanel panelGui2=new JPanel();
        panelGui2.setLayout(null);

        //Panel at the top with player name
        JLabel headingLabel=new JLabel(
                getPlayerNameByIndex(currentPlayerIndex).toUpperCase() + ", SELECT THE CHARACTER TO REPRESENT YOU");
        headingLabel.setFont(new Font("Serif", Font.BOLD, 40));
        headingLabel.setForeground(Color.YELLOW);
        
        headingLabel.setBackground(Color.BLACK);
        headingLabel.setOpaque(true);                                                // ensure the background color is applied
       
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setBounds(0,0,screenSize.width,(int)(screenSize.height *0.10)); //10 percent of the screen height
       
        panelGui2.add(headingLabel);

        //2x4 grid of panels for characters
        double panelw=screenSize.width *0.25;                              //25 percent of screen width for each panel
        double panelh=screenSize.height *0.45;                        //45 percent of screen height for each panel
        
        double buttonw=screenSize.width *0.061;                       //button width 6.1 percent of screen width
        double buttonh=screenSize.height* 0.05;                       //button height 5 percent of screen height

        for (int i=0; i<8; i++) {
            final int index = i;                                                  //This is the key fix-make the index final
            JPanel characterPanel = new JPanel();
            characterPanel.setLayout(null);
            characterPanel.setBounds((int) ((i % 4)* panelw),
                    
            (int) ((i / 4)* panelh+ screenSize.height *0.10), (int)panelw, (int)panelh);
            
                    characterPanel.setBackground(Color.LIGHT_GRAY);

            // load and display character image as a background label
            try {
                Image characterImage=ImageIO.read(new File(characterImages[i]));
                JLabel imageLabel=new JLabel(new ImageIcon(
                        characterImage.getScaledInstance((int) panelw,(int)panelh, Image.SCALE_SMOOTH)));
                imageLabel.setBounds(0, 0, (int) panelw,(int) panelh);
                characterPanel.add(imageLabel);
            } catch (IOException e) {
                System.out.println("Image "+characterImages[i]+" not found.");
                e.printStackTrace();
            }

            //character selection button
            JButton selectButton=new JButton("Select");
            
            selectButton.setBounds(10,10,(int)buttonw,(int) buttonh);
            
            selectButton.setBackground(Color.BLACK);
            selectButton.setForeground(Color.ORANGE);
            
            selectButton.setFont(new Font("Serif",Font.BOLD,12));
            
            characterPanel.add(selectButton);
            //set button on top of the image
            characterPanel.setComponentZOrder(selectButton,0);

            //character name box (Top right corner)
            JLabel characterNameLabel=new JLabel(characterNames[i]);
            characterNameLabel.setBounds((int) (panelw -buttonw-10),10, (int)buttonw,(int)buttonh);
           
            characterNameLabel.setBackground(Color.BLACK);
            characterNameLabel.setForeground(Color.ORANGE);
           
            characterNameLabel.setFont(new Font("Serif",Font.BOLD,12));
            characterNameLabel.setOpaque(true);
           
            characterNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            characterPanel.add(characterNameLabel);
            // ensure name label is on top
            characterPanel.setComponentZOrder(characterNameLabel, 0);

            // add the character panel to Gui2
            panelGui2.add(characterPanel);

            // action for selecting a character
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // store the selected character in the corresponding global variable
                    switch (currentPlayerIndex) {
                        case 0:
                            p1character=characterNames[index];
                            break;
                        case 1:
                            p2character=characterNames[index];
                            break;
                    }
                    selectCharacter(selectButton);
                }
            });
        }

        frame.setContentPane(panelGui2);
        frame.revalidate();
        frame.repaint();
    }

    // method to handle character selection
    private static void selectCharacter(JButton clickedButton) {
        clickedButton.setEnabled(false); // disable the selected button

        currentPlayerIndex++;
        
        if (currentPlayerIndex<numberOfPlayers) {

            
            showGui2(); // move to the next player
        
        } else {


            // all players have selected a character
            // custom dialog with no OK button only next button
            JDialog dialog=new JDialog(frame,"Character Selection Complete",true);
            
            dialog.setLayout(new FlowLayout());
            dialog.setSize(400,200);
            
            JLabel label =new JLabel("press Next for Pokemon selection.");
            dialog.add(label);

            JButton nextButton=new JButton("Next");
            dialog.add(nextButton);

            // action for the next button to proceed to pokemon selection
            nextButton.addActionListener(e -> {
                
                dialog.dispose();
                showGui3();                    // show pokemon selection GUI (Gui3)
            });

            dialog.setLocationRelativeTo(frame);                  //center the dialog
            dialog.setVisible(true);
        }
    }

    //method to display Gui3 (pokemon Selection)
    private static void showGui3() {
        
        if (currentPlayerIndex>=numberOfPlayers) {
            currentPlayerIndex=0;                                     //reset index for pokemon selection
        }

        frame.getContentPane().removeAll();                            // clear the second GUI (character selection)

        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screenSize=toolkit.getScreenSize();

        JPanel panelGui3=new JPanel();
        panelGui3.setLayout(null);

        //panel at the top with player name
        JLabel headingLabel=new JLabel(
                getPlayerNameByIndex(currentPlayerIndex).toUpperCase()+", SELECT YOUR TWO POKEMON");
        headingLabel.setFont(new Font("Serif",Font.BOLD,40));
        
        headingLabel.setForeground(Color.YELLOW);
        
        headingLabel.setBackground(Color.BLACK);
        
        headingLabel.setOpaque(true);                                   // ensure the background color is applied
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headingLabel.setBounds(0,0,screenSize.width, (int)(screenSize.height*0.10));                  // 10 percent of the screen height
        panelGui3.add(headingLabel);

        //2x4 grid of panels for pokemon

        double panelw=screenSize.width *0.25;                                        //25 percent of screen width for each panel
        
        double panelh= screenSize.height* 0.45;                        //45 percent of screen height for each panel
        
        double buttonw= screenSize.width *0.061;                                // button width 6.1 percent of screen width
        double buttonh =screenSize.height* 0.05;                  // button height 5 percent of screen height

        //to store two selected [okemon for the current player

        final ArrayList<String> selectedPokemon=new ArrayList<>();
        final ArrayList<String> infoPokemon=new ArrayList<>();

        for (int i=0;i< 8;i++) {

            final int index =i; // make the index final
            JPanel pokipanel= new JPanel();
            pokipanel.setLayout(null);
            pokipanel.setBounds((int)((i % 4) *panelw),
                    (int)((i/4)*panelh +screenSize.height* 0.10),(int)panelw,(int) panelh);
            pokipanel.setBackground(Color.LIGHT_GRAY);

            // load and display Pokemon image as a background label
            try {

                Image pokemonImage =ImageIO.read(new File(pokemonImages[i]));
                JLabel imageLabel = new JLabel(new ImageIcon(
                        pokemonImage.getScaledInstance((int)panelw, (int)panelh,Image.SCALE_SMOOTH)));
                imageLabel.setBounds(0,0, (int)panelw, (int)panelh);
                pokipanel.add(imageLabel);
            } catch (IOException e) {

                System.out.println("Image " + pokemonImages[i] + " not found.");
                e.printStackTrace();
            }
            // info button. 
            JButton pokinfo=new JButton("Get info");
            pokinfo.setBounds(180,10,(int)buttonw, (int)buttonh);
            pokinfo.setBackground(Color.BLACK);
            
            pokinfo.setForeground(Color.ORANGE);
            pokinfo.setFont(new Font("Serif",Font.BOLD,12));
            pokipanel.add(pokinfo);
            pokipanel.setComponentZOrder(pokinfo, 0);

            // pokemon selection button
            JButton selectButton=new JButton("Select");
            selectButton.setBounds(10,10,(int) buttonw,(int) buttonh);
            selectButton.setBackground(Color.BLACK);
            
            selectButton.setForeground(Color.ORANGE);
            selectButton.setFont(new Font("Serif", Font.BOLD, 12));
            pokipanel.add(selectButton);
            //set button on topof the image
            pokipanel.setComponentZOrder(selectButton, 0);

            //pokemon name box (Top right corner)
            JLabel pokemonNameLabel=new JLabel(pokemonNames[i]);

            pokemonNameLabel.setBounds((int)(panelw-buttonw-10),10, (int)buttonw,
                    (int) buttonh);
            
            
                    pokemonNameLabel.setBackground(Color.BLACK);
            pokemonNameLabel.setForeground(Color.ORANGE);
            
            pokemonNameLabel.setFont(new Font("Serif",Font.BOLD, 12));
            pokemonNameLabel.setOpaque(true);
            
            pokemonNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            pokipanel.add(pokemonNameLabel);//ensure name label is on top

            pokipanel.setComponentZOrder(pokemonNameLabel,0);

            //add the Pokemon panel  to Gui3
            panelGui3.add(pokipanel);

            pokinfo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    infoPokemon.add(pokemonNames[index]);

                    if (infoPokemon.size()>0) {


                        if (infoPokemon.get(infoPokemon.size()-1).equals("Pikachu")) {

                            JOptionPane.showMessageDialog(frame, pikachu.toString());
                        };

                        if (infoPokemon.get(infoPokemon.size()-1).equals("Lucario")) {

                            JOptionPane.showMessageDialog(frame, Lucario.toString());
                        };

                        if (infoPokemon.get(infoPokemon.size()-1).equals("Garchomp")) {

                            JOptionPane.showMessageDialog(frame, garchomp.toString());
                        };

                        if (infoPokemon.get(infoPokemon.size()-1).equals("Greninja")) {

                            JOptionPane.showMessageDialog(frame, greninja.toString());
                        };

                        if (infoPokemon.get(infoPokemon.size()-1).equals("Zoroark")) {

                            JOptionPane.showMessageDialog(frame, zoroark.toString());
                        };

                        if (infoPokemon.get(infoPokemon.size()-1).equals("Absol")) {

                            JOptionPane.showMessageDialog(frame, Absol.toString());
                        };

                        if (infoPokemon.get(infoPokemon.size()-1).equals("Mewtwo")) {

                            JOptionPane.showMessageDialog(frame, mewtwo.toString());
                        };
                        if (infoPokemon.get(infoPokemon.size()-1).equals("Charizard")) {

                            JOptionPane.showMessageDialog(frame, charizard.toString());
                        };

                    }
                }

            });

            //action for selecting a pokemonn

            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedPokemon.size() <2) {

                        selectedPokemon.add(pokemonNames[index]);
                        selectButton.setEnabled(false);                          //disable the selected button

                    }

                    if (selectedPokemon.size() == 2) {

                        // assigning the correct pokemon bases on the selectionn
                        switch (currentPlayerIndex) {


                            case 0:
                                if (selectedPokemon.get(0).equals("Pikachu")) {   // checking player 1 first pokemon

                                    player1poki1=pikachu;
                                }
                                if (selectedPokemon.get(0).equals("Lucario")) {
                                    player1poki1=Lucario;
                                }
                                if (selectedPokemon.get(0).equals("Garchomp")) {
                                    player1poki1=garchomp;
                                }
                                if (selectedPokemon.get(0).equals("Greninja")) {
                                    player1poki1=greninja;
                                }
                                if (selectedPokemon.get(0).equals("Zoroark")) {
                                    player1poki1=zoroark;
                                }
                                if (selectedPokemon.get(0).equals("Absol")) {
                                    player1poki1=Absol;
                                }
                                if (selectedPokemon.get(0).equals("Mewtwo")) {
                                    player1poki1=mewtwo;
                                }
                                if (selectedPokemon.get(0).equals("Charizard")) {
                                    player1poki1=charizard;
                                }
                                if (selectedPokemon.get(1).equals("Pikachu")) {          // checking player 1 second pokemon
                                    player1poki2=pikachu;
                                }
                                if (selectedPokemon.get(1).equals("Lucario")) {
                                    player1poki2=Lucario;
                                }
                                if (selectedPokemon.get(1).equals("Garchomp")) {
                                    player1poki2=garchomp;
                                }
                                if (selectedPokemon.get(1).equals("Greninja")) {
                                    player1poki2=greninja;
                                }
                                if (selectedPokemon.get(1).equals("Zoroark")) {
                                    player1poki2=zoroark;
                                }
                                if (selectedPokemon.get(1).equals("Absol")) {
                                    player1poki2=Absol;
                                }
                                if (selectedPokemon.get(1).equals("Mewtwo")) {
                                    player1poki2=mewtwo;
                                }
                                if (selectedPokemon.get(1).equals("Charizard")) {
                                    player1poki2=charizard;
                                }
                                break;
                            case 1:
                                if (selectedPokemon.get(0).equals("Pikachu")) {      // checking player 2 first pokemon
                                    player2poki1=pikachu;
                                }
                                if (selectedPokemon.get(0).equals("Lucario")) {
                                    player2poki1=Lucario;
                                }
                                if (selectedPokemon.get(0).equals("Garchomp")) {
                                    player2poki1=garchomp;
                                }
                                if (selectedPokemon.get(0).equals("Greninja")) {
                                    player2poki1=greninja;
                                }
                                if (selectedPokemon.get(0).equals("Zoroark")) {
                                    player2poki1=zoroark;
                                }
                                if (selectedPokemon.get(0).equals("Absol")) {
                                    player2poki1=Absol;
                                }
                                if (selectedPokemon.get(0).equals("Mewtwo")) {
                                    player2poki1=mewtwo;
                                }
                                if (selectedPokemon.get(0).equals("Charizard")) {  
                                    player2poki1=charizard;
                                }
                                if (selectedPokemon.get(1).equals("Pikachu")) {         // checking player 2 second pokemon
                                    player2poki2=pikachu;
                                }
                                if (selectedPokemon.get(1).equals("Lucario")) {
                                    player2poki2= Lucario;
                                }
                                if (selectedPokemon.get(1).equals("Garchomp")) {
                                    player2poki2= garchomp;
                                }
                                if (selectedPokemon.get(1).equals("Greninja")) {
                                    player2poki2= greninja;
                                }
                                if (selectedPokemon.get(1).equals("Zoroark")) {

                                    player2poki2 =zoroark;
                                }
                                if (selectedPokemon.get(1).equals("Absol")) {
                                    player2poki2= Absol;

                                }
                                if (selectedPokemon.get(1).equals("Mewtwo")) {

                                    player2poki2 =mewtwo;
                                }
                                if (selectedPokemon.get(1).equals("Charizard")) {
                                    player2poki2 =charizard;
                                }
                                break;
                        }
                        selectNextPlayerOrFinish();                                    // move to the next player or finish
                    }
                }
            });
        }

        frame.setContentPane(panelGui3);
        frame.revalidate();
        frame.repaint();
    }

    // move to the next player for pokemon selection or finish
    private static void selectNextPlayerOrFinish() {
        currentPlayerIndex++;
        if (currentPlayerIndex< numberOfPlayers) {
            showGui3(); // move to the next player for pokemon selection
        } else {
            // all players have selected their Pokemon
            //  fight button
            
            JDialog dialog =new JDialog(frame, "Pokemon Selection Complete",true);
            dialog.setLayout(new FlowLayout());
            dialog.setSize(400,200);
            
            JLabel label=new JLabel("Click Fight!");     
            dialog.add(label);

            JButton fightButton =new JButton("Fight!");
            dialog.add(fightButton);

            // action for the Fight button to proceed to Gui4
            fightButton.addActionListener(e -> {
                dialog.dispose();
                showGui4();                             // call the method to display Gui4
                handleCoinToss();                                             //coin toss that determines the first attacker
                p1energy = player1poki1.getenergy();
                p2energy = player2poki1.getenergy();
                updateButtonStates();
                addFightingMechanics();                             //for calling the fight logic methods

            });

            dialog.setLocationRelativeTo(frame);// Center the dialog
            dialog.setVisible(true);
        }
    }

    // helper method to get player name by index
    private static String getPlayerNameByIndex(int index) {
        switch (index) {
            case 0:
                return p1name;
            case 1:
                return p2name;
            default:
                return "";
        }
    }

    //declare the variables 
    private static JPanel bottompanelleft;
    private static JPanel bottompanelright;
    
    private static Dimension screenSize;
    
    private static JLayeredPane layeredPane;
    
    private static JLabel righthp;
    private static JLabel lefthp;
    
    private static JLabel rightenergy;
    private static JLabel Leftenergy;
    
    private static JPanel rightpanel;
    private static JPanel leftpanel;
    
    private static JButton leftpunchbut;
    private static JButton leftkickbut;
    private static JButton special_left_button;
    
    private static JButton rightpunchbut;
    private static JButton rightkickbut;
    private static JButton special_right_button;
   
   
   
    private static JButton back;
    private static JButton gameinfo;
    
    // Declare health variables as instance variables
    private static int p1hp=100;
    private static int p2hp=100;

   
   
    private static int p1energy=0;
    private static int p2energy=0;

    
    private static int defeatedp1=0;
    private static int defeatedp2=0;

    private static double p2atk=0;
    private static double p1atk=0;

    private static int p1def=0;
    private static int p2def=0;

    private static Character p1attacker=new Character("Pikachu", 1.7,100, 50, 8, "Somthing about pikachu");
    private static Character p2attacker=new Character("Pikachu", 1.7, 100, 50, 8,"Somthing about pikachu");
    
    private static Character attacker=new Character("Pikachu", 1.7, 100, 50, 8, "Somthing about pikachu");

    //declare damage as a class variable
    private static int damage=0;
    private static int costenergy=0;
    // other class variables for coin toss
    private static String playerOneCoinChoice, playerTwoCoinChoice; // coin toss variables
    private static boolean playerOneTurn;

    private static void showGui4() {
        frame.getContentPane().removeAll(); // clear the previous GUI

        Toolkit toolkit= Toolkit.getDefaultToolkit();
        screenSize= toolkit.getScreenSize(); // initialize screenSize

        layeredPane= new JLayeredPane(); // initialize layeredPane
        layeredPane.setPreferredSize(screenSize);

        JPanel panelGui4 =new JPanel();
        panelGui4.setLayout(null);
        panelGui4.setBounds(0,0, screenSize.width,screenSize.height);
        layeredPane.add(panelGui4, JLayeredPane.DEFAULT_LAYER);

        // top left panel (Panel A)

        JPanel topLeftPanel =new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image characterImage =ImageIO.read(new File(p1character+ ".JPEG")); 
                    g.drawImage(characterImage, 0,0, getWidth(),getHeight(),this);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        };
        topLeftPanel.setBounds(0, 0,(int)(screenSize.width*0.25),(int)(screenSize.height*0.50));

        panelGui4.add(topLeftPanel);

        //bottom left panel (Panel B)
        bottompanelleft =new JPanel() { 
            // initialize bottompanelleft
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {

                    Image pokemonImage=ImageIO.read(new File(player1poki2.getname() +".JPEG"));
                    g.drawImage(pokemonImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) 
                {

                    e.printStackTrace();
                }
            }
        };
        bottompanelleft.setBounds(0,(int)(screenSize.height *0.50), (int)(screenSize.width *0.25),
                (int)(screenSize.height*0.50));
        panelGui4.add(bottompanelleft);

        // top right panel (Panel F)
        JPanel topRightPanel=new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image characterImage=ImageIO.read(new File(p2character+".JPEG")); 
                    g.drawImage(characterImage,0,0,getWidth(),getHeight(),this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        topRightPanel.setBounds((int)(screenSize.width* 0.75),0,(int)(screenSize.width*0.25),
                (int)(screenSize.height*0.50));
        panelGui4.add(topRightPanel);

        // bottom right panel (Panel G)
        bottompanelright=new JPanel() { // initialize bottompanelright
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image pokemonImage=ImageIO.read(new File(player2poki2.getname()+".JPEG")); 
                    g.drawImage(pokemonImage,0,0,getWidth(),getHeight(),this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        bottompanelright.setBounds((int)(screenSize.width*0.75),(int)(screenSize.height*0.50),
                (int)(screenSize.width*0.25), (int) (screenSize.height* 0.50));
        panelGui4.add(bottompanelright);

        // center panel (Panel C)
        JPanel centerPanel=new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image centerImage =ImageIO.read(new File("Arena.JPEG")); 
                    g.drawImage(centerImage,0, 0,getWidth(),getHeight(),this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        centerPanel.setBounds((int)(screenSize.width * 0.25), 0,(int)(screenSize.width *0.50), screenSize.height);
        centerPanel.setLayout(null);
        panelGui4.add(centerPanel);

        // left square panel within center panel (Panel D)
        leftpanel =new JPanel() { // initialize leftpanel
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image leftSquareImage=ImageIO.read(new File(player1poki1.getname() + ".JPEG")); 
                    g.drawImage(leftSquareImage,0, 0, getWidth(),getHeight(),this);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        };
        leftpanel.setBounds(0,(int)(screenSize.height*0.35), (int)(screenSize.height*0.308),
                (int) (screenSize.height * 0.308));

        centerPanel.add(leftpanel);

        // right square panel within center panel (Panel E)
        rightpanel =new JPanel() {                  //initialize rightpanel
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image rightSquareImage=ImageIO.read(new File(player2poki1.getname()+".JPEG")); 
                    g.drawImage(rightSquareImage, 0,0,getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        rightpanel.setBounds((int)(screenSize.width*0.50-screenSize.height*0.308),
                (int)(screenSize.height*0.35), (int)(screenSize.height*0.308), (int)(screenSize.height*0.308));
        centerPanel.add(rightpanel);

        //Initialize the buttons

        // back butoon to return to main menu
        back =new JButton("BACK TO MAIN MENU");
        back.setBounds(180, 10, (int) 500, (int) 500);
        back.setBackground(Color.RED);
        
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Serif", Font.BOLD, 12));
       
        back.addActionListener(e -> showGui1());
        
        topRightPanel.add(back);
        topRightPanel.setComponentZOrder(back, 0);

        // game info button to tell the player how to play
        gameinfo=new JButton("GAME TUTORIAL");
        gameinfo.setBounds(180, 10, (int) 500, (int) 500);
        gameinfo.setBackground(Color.YELLOW);
        gameinfo.setForeground(Color.BLACK);
        gameinfo.setFont(new Font("Serif", Font.BOLD, 12));
        
        gameinfo.addActionListener(e -> JOptionPane.showMessageDialog(frame, "HOW TO PLAY" +"\n"+"\n" +
                "- This is a turn based game."+"\n" +
                "- The player who makes the first move is determind by a coin flip."+"\n" +
                "- Each pokemon has a different stats that affect it in battle."+"\n"+
                "- The player can select from three different moves."+"\n"+
                "- The punch, kick and special ability all deal different damages"+"\n"+
                "and they increase in strength in that order." +"\n"+
                "- Each time the pokemon attacks it gains 20 energy"+"\n"+
                "- As energy increases different moves become abvailable,"+"\n"+
                "but they too come at an energy cost..."+"\n"+
                "- When the first of each players pokemon reaches 0 health, their second"+"\n"+
                "Pokemon will be switched in." +"\n"+
                "- The winner of the game is the first to defeat both of the opponents pokemon")); // dialog explaining the gameplay and mechanics
        
        topLeftPanel.add(gameinfo);
        topLeftPanel.setComponentZOrder(gameinfo, 0);

        leftpunchbut=new JButton("Punch");
        leftpunchbut.setBounds(0,(int)(screenSize.height*0.65), (int)(screenSize.height*0.10),
                (int)(screenSize.height*0.05));
        leftpunchbut.setBackground(Color.BLACK);
        leftpunchbut.setForeground(Color.ORANGE);
        leftpunchbut.setFont(new Font("Serif",Font.BOLD,12));
        bottompanelleft.add(leftpunchbut);

        leftkickbut=new JButton("Kick");
        leftkickbut.setBounds((int) (screenSize.height*0.15),(int)(screenSize.height*0.65),
                (int) (screenSize.height * 0.10),(int)(screenSize.height*0.05));

        leftkickbut.setBackground(Color.BLACK);
        leftkickbut.setForeground(Color.ORANGE);

        leftkickbut.setFont(new Font("Serif",Font.BOLD,12));
        bottompanelleft.add(leftkickbut);

        special_left_button=new JButton("Special Ability");
        special_left_button.setBounds(0, (int)(screenSize.height*0.75), (int)(screenSize.height*0.255),
                (int)(screenSize.height*0.05));
        special_left_button.setBackground(Color.BLACK);

        special_left_button.setForeground(Color.ORANGE);

        special_left_button.setFont(new Font("Serif", Font.BOLD,12));
        bottompanelleft.add(special_left_button);

        rightpunchbut=new JButton("Punch");
        rightpunchbut.setBounds((int)(screenSize.width *0.75), (int)(screenSize.height *0.65),
                (int)(screenSize.height* 0.10),(int)(screenSize.height* 0.05));
        rightpunchbut.setBackground(Color.BLACK);

        rightpunchbut.setForeground(Color.ORANGE);

        rightpunchbut.setFont(new Font("Serif",Font.BOLD,12));
        bottompanelright.add(rightpunchbut);


        rightkickbut=new JButton("Kick");
        rightkickbut.setBounds((int)(screenSize.width *0.90), (int) (screenSize.height* 0.65),
                (int)(screenSize.height * 0.10), (int)(screenSize.height *0.05));
        rightkickbut.setBackground(Color.BLACK);
        rightkickbut.setForeground(Color.ORANGE);

        rightkickbut.setFont(new Font("Serif",Font.BOLD,12));
        bottompanelright.add(rightkickbut);


        special_right_button=new JButton( "Special Ability");
        special_right_button.setBounds((int) (screenSize.width* 0.75), (int) (screenSize.height* 0.75),
                (int) (screenSize.height*0.255), (int) (screenSize.height *0.05));
        special_right_button.setBackground(Color.BLACK);
        special_right_button.setForeground(Color.ORANGE);

        special_right_button.setFont(new Font( "Serif",Font.BOLD,12));
        bottompanelright.add(special_right_button);

        // energy and healt boxes on the left
        JLabel healthLabelLeft=new JLabel("Health",SwingConstants.CENTER);
        healthLabelLeft.setBounds((int)(screenSize.width* 0.25+screenSize.height* 0.05),
                (int) (screenSize.height*0.05),(int)(screenSize.height*0.102),(int)(screenSize.height*0.055));

        healthLabelLeft.setBackground(Color.BLACK);
        healthLabelLeft.setForeground(Color.GREEN);
        healthLabelLeft.setFont(new Font("Serif",Font.BOLD, 12));
        healthLabelLeft.setOpaque(true);
        layeredPane.add(healthLabelLeft, JLayeredPane.PALETTE_LAYER);

        JLabel energyLabelLeft=new JLabel("Energy", SwingConstants.CENTER);

        energyLabelLeft.setBounds((int)(screenSize.width *0.25+screenSize.height*0.05),
                (int)(screenSize.height *0.12), (int) (screenSize.height*0.102), (int)(screenSize.height *0.055));
        energyLabelLeft.setBackground(Color.BLACK);
        energyLabelLeft.setForeground(Color.YELLOW);
        energyLabelLeft.setFont(new Font("Serif",Font.BOLD,12));
        energyLabelLeft.setOpaque(true);

        layeredPane.add(energyLabelLeft, JLayeredPane.PALETTE_LAYER);

        // boxes to dsplay numbers for energy and health on the left
        lefthp=new JLabel(""+player1poki1.gethp(), SwingConstants.CENTER);
        lefthp.setBounds((int)(screenSize.width*0.25 + screenSize.height*0.157),
                (int)(screenSize.height*0.05), (int)(screenSize.height*0.055), (int)(screenSize.height*0.055));
        lefthp.setBackground(Color.GREEN);

        lefthp.setForeground(Color.BLACK);
        lefthp.setFont(new Font("Serif",Font.BOLD,12));

        lefthp.setOpaque(true);
        layeredPane.add(lefthp,JLayeredPane.PALETTE_LAYER);

        Leftenergy=new JLabel(""+player1poki1.getenergy(), SwingConstants.CENTER);
        Leftenergy.setBounds((int) (screenSize.width *0.25+screenSize.height* 0.157),
                (int)(screenSize.height *0.12), (int)(screenSize.height *0.055),(int)(screenSize.height* 0.055));
        Leftenergy.setBackground(Color.YELLOW);

        Leftenergy.setForeground(Color.BLACK);

        Leftenergy.setFont(new Font("Serif",Font.BOLD,12));
        Leftenergy.setOpaque(true);
        layeredPane.add(Leftenergy, JLayeredPane.PALETTE_LAYER);

                                    // energy and health boxes on the right
        JLabel healthLabelRight=new JLabel("Health", SwingConstants.CENTER);
        healthLabelRight.setBounds((int) (screenSize.width*0.70),(int)(screenSize.height*0.05),
                (int)(screenSize.height *0.102),(int)(screenSize.height*0.055));
        healthLabelRight.setBackground(Color.BLACK);


        healthLabelRight.setForeground(Color.GREEN);

        healthLabelRight.setFont(new Font("Serif", Font.BOLD,12));
        healthLabelRight.setOpaque(true);
        layeredPane.add(healthLabelRight,JLayeredPane.PALETTE_LAYER);

        JLabel energyLabelRight =new JLabel("Energy",SwingConstants.CENTER);
        energyLabelRight.setBounds((int)(screenSize.width *0.70), (int) (screenSize.height *0.12),
                (int) (screenSize.height *0.102), (int)(screenSize.height * 0.055));
        energyLabelRight.setBackground(Color.BLACK);


        energyLabelRight.setForeground(Color.YELLOW);
        energyLabelRight.setFont(new Font("Serif",Font.BOLD, 12));

        energyLabelRight.setOpaque(true);
        layeredPane.add(energyLabelRight, JLayeredPane.PALETTE_LAYER);

                  // boxes to display numers for energy and health on the right
        righthp=new JLabel("" + player2poki1.gethp(),SwingConstants.CENTER);
        righthp.setBounds((int)(screenSize.width* 0.65), (int)(screenSize.height* 0.05),
                (int)(screenSize.height* 0.055),(int)(screenSize.height *0.055));

        righthp.setBackground(Color.GREEN);
        righthp.setForeground(Color.BLACK);
        righthp.setFont(new Font("Serif", Font.BOLD, 12));

        righthp.setOpaque(true);
        layeredPane.add(righthp, JLayeredPane.PALETTE_LAYER);

        rightenergy=new JLabel(""+player2poki1.getenergy(),SwingConstants.CENTER);
        rightenergy.setBounds((int)(screenSize.width*0.65), 
                (int)(screenSize.height*0.12),
                (int)(screenSize.height* 0.055),(int)(screenSize.height *0.055));
        rightenergy.setBackground(Color.YELLOW);
        rightenergy.setForeground(Color.BLACK);

        rightenergy.setFont(new Font("Serif",Font.BOLD,12));
        rightenergy.setOpaque(true);


        layeredPane.add(rightenergy,JLayeredPane.PALETTE_LAYER);

        frame.setContentPane(layeredPane);
        frame.revalidate();
        frame.repaint();
    }

    private static void addFightingMechanics() {
        
        p1energy =player1poki1.getenergy();
        p2energy =player2poki1.getenergy();

        
        p1hp= player1poki1.gethp();
        p2hp= player2poki1.gethp();
        
        p1atk = player1poki1.getpower();
        p2atk = player2poki1.getpower();
        
        p1def=player1poki1.getdefense();
        p2def=player2poki1.getdefense();

        
        p1attacker=player1poki1;
        p2attacker=player2poki1;
        // method to handle the atttack animation and health reduction
        ActionListener attackAction=new ActionListener() {
            @Override
            
            public void actionPerformed(ActionEvent e) {
                JButton source =(JButton) e.getSource();
                String actionCommand=source.getActionCommand();
                String attackImage="";

                // damage caused by attacks
                costenergy =0;
                Random randomDamage=new Random();
                /*
                 * if (actionCommand.equals("game tutorial")){
                 * frame.getContentPane().removeAll();
                 * System.exit(0);
                 * }
                 */
                if (actionCommand.equals("Punch")) {
                    damage =randomDamage.nextInt(30,40);
                    costenergy =0;

                    leftpunchbut.setEnabled(false);
                    leftkickbut.setEnabled(false);
                    special_left_button.setEnabled(false);

                    rightpunchbut.setEnabled(false);
                    rightkickbut.setEnabled(false);
                    special_right_button.setEnabled(false);

                    try {
                        if (flip==null|| !flip.isRunning()) {
                            AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(new File("punch.WAV"));  // plays punch sound
                            flip=AudioSystem.getClip();
                            flip.open(audioInputStream);

                            FloatControl gainControl=(FloatControl) flip.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(6.0f);

                            flip.start();
                        }
                    } catch (Exception ex) {

                        System.out.println("Error playing sound.");
                        ex.printStackTrace();
                    }

                    attackImage= "punch2_0_V1.PNG"; 

                } else if (actionCommand.equals("Kick")) {
                    damage=randomDamage.nextInt(40, 50);
                    costenergy=20;
                    leftpunchbut.setEnabled(false);
                    leftkickbut.setEnabled(false);
                    special_left_button.setEnabled(false);

                    rightpunchbut.setEnabled(false);
                    rightkickbut.setEnabled(false);
                    special_right_button.setEnabled(false);
                    try {
                        if (flip==null||!flip.isRunning()) {
                            AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(new File("kick.WAV")); // plays kick sound
                            flip=AudioSystem.getClip();
                            flip.open(audioInputStream);
                            FloatControl gainControl =(FloatControl) flip.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(6.0f);

                            flip.start();
                        }
                    } catch (Exception ex) {
                        System.out.println("Error playing sound..");
                        ex.printStackTrace();
                    }

                    attackImage ="kickImage_V1.PNG"; 
                } else if (actionCommand.equals("Special Ability")) {
                    damage= randomDamage.nextInt(50,60);
                    costenergy =80;
                    leftpunchbut.setEnabled(false);
                    leftkickbut.setEnabled(false);
                    special_left_button.setEnabled(false);

                    rightpunchbut.setEnabled(false);
                    rightkickbut.setEnabled(false);
                    special_right_button.setEnabled(false);

                    attackImage =attacker.getname() +"specialAbilityImage.PNG";
                    try {
                        if (flip== null ||!flip.isRunning()) {
                            AudioInputStream audioInputStream =AudioSystem
                                    .getAudioInputStream(new File("special.WAV"));      // plays special ability sound
                            flip= AudioSystem.getClip();
                            flip.open(audioInputStream);
                            FloatControl gainControl =(FloatControl) flip.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(6.0f);

                            flip.start();
                        }
                    } catch (Exception ex) {
                        System.out.println("Error playing sound.");
                        ex.printStackTrace();
                    }

                }

                 // determin]e the direction of the attack
                boolean isLeftAttack =source.getParent()==bottompanelleft;

                            // create the attack image label
                ImageIcon attackIcon= new ImageIcon(attackImage);
                Image attackImg =attackIcon.getImage().getScaledInstance((int)(150),(int)(150), Image.SCALE_SMOOTH);
                JLabel attackLabel= new JLabel(new ImageIcon(attackImg));
                attackLabel.setBounds(

                        isLeftAttack ? (int)(screenSize.width*0.25): (int)(screenSize.width*0.75)-(int)(screenSize.height*0.11),(int)(screenSize.height*0.45), (int)(screenSize.height *0.11),(int) (screenSize.height*0.11));

                layeredPane.add(attackLabel, JLayeredPane.DRAG_LAYER);

                             // timer to move the attack image
                Timer timer = new Timer(10, new ActionListener() {
                    int x = attackLabel.getX();
                    int stepSize = 12;             // Adjust the step size for consistent speed

                    @Override
                    public void actionPerformed(ActionEvent e) {
                                       // calculate the remaining distance to the target
                        int remainingDistance=isLeftAttack
                                ? screenSize.width-(int)(screenSize.width*0.25)-(int)(screenSize.height *0.11)- x:x-(int) (screenSize.width*0.25);

                            // adjust the step size based o the remaining distance to ensure consistent speed
                        if (remainingDistance < stepSize) {
                            stepSize = remainingDistance;
                        }

                        x +=isLeftAttack?stepSize:-stepSize;
                        attackLabel.setLocation(x, attackLabel.getY());

                        //check if the attack image has reached the edge of the opposite panel
                        if ((isLeftAttack&& x>= screenSize.width -(int)(screenSize.width*0.25)
                                - (int) (screenSize.height*0.11))
                                || (!isLeftAttack&& x<=(int)(screenSize.width *0.25))) {

                            ((Timer) e.getSource()).stop();
                            layeredPane.remove(attackLabel);
                            layeredPane.repaint();

                            // reduce health and update the health label
                            if (isLeftAttack) {
                                attacker =p2attacker;

                                p1energy +=(20- costenergy);
                                p2hp-= (damage *(p1atk /100)) -(damage*(p2def/100)); //formula to determine damage after applying all the stats

                                righthp.setText(String.valueOf(p2hp));
                                Leftenergy.setText(String.valueOf(p1energy));
                                if (p2hp<= 0) {
                                    defeatedp2++;
                                    if (defeatedp2==1) {
                                        // replace the picturin the right square panel 
                                        rightpanel.removeAll();
                                        rightpanel.repaint();
                                        try {
                                            Image pokemonImage =ImageIO
                                                    .read(new File(player2poki2.getname() +".JPEG")); 
                                            Image resizedImage=pokemonImage.getScaledInstance(
                                                    rightpanel.getWidth(), rightpanel.getHeight(),
                                                    Image.SCALE_SMOOTH);
                                            JLabel pokemonLabel =new JLabel(new ImageIcon(resizedImage));
                                            rightpanel.add(pokemonLabel);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                        rightpanel.revalidate();
                                        rightpanel.repaint();

                                        // remove all components from botom right panel except attack buttons
                                        bottompanelright.removeAll();
                                        bottompanelright.add(rightpunchbut);
                                        bottompanelright.add(rightkickbut);
                                        bottompanelright.add(special_right_button);
                                        bottompanelright.revalidate();
                                        bottompanelright.repaint();

                                        p2hp =player2poki2.gethp();
                                        p2energy=player2poki2.getenergy();
                                        p2atk= player2poki2.getpower();
                                        p2def =player2poki2.getdefense();

                                        p2attacker=player2poki2;
                                        righthp.setText(String.valueOf(p2hp));
                                        rightenergy.setText(String.valueOf(p2energy));
                                    } else if (defeatedp2 == 2) {
                                        JOptionPane.showMessageDialog(frame, p1name + " WINS!!");
                                        // reset health and restart the program
                                        p1hp=100;
                                        p2hp=100;

                                        lefthp.setText(String.valueOf(p1hp));
                                        righthp.setText(String.valueOf(p2hp));
                                        restartProgram();
                                    }
                                }
                            } else {
                                attacker=p1attacker;
                                p2energy+=(20-costenergy);     // changing energy based on move played
                                p1hp -=(damage*(p2atk /100)) -(damage *(p1def/100));
                                lefthp.setText(String.valueOf(p1hp));
                                rightenergy.setText(String.valueOf(p2energy));
                                if (p1hp<=0) {
                                    defeatedp1++;
                                    if (defeatedp1==1) {
                                        // replace the picture in the left square  panel
                                        leftpanel.removeAll();
                                        leftpanel.repaint();
                                        try {
                                            Image pokemonImage = ImageIO
                                                    .read(new File(player1poki2.getname() +".JPEG")); 
                                            Image resizedImage=pokemonImage.getScaledInstance(
                                                    leftpanel.getWidth(), leftpanel.getHeight(),
                                                    Image.SCALE_SMOOTH);
                                            JLabel pokemonLabel =new JLabel(new ImageIcon(resizedImage));
                                            leftpanel.add(pokemonLabel);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                        leftpanel.revalidate();
                                        leftpanel.repaint();

                                        // remove all components  from bottom left panel except attack buttons
                                        bottompanelleft.removeAll();

                                        bottompanelleft.add(leftpunchbut);
                                        bottompanelleft.add(leftkickbut);
                                        bottompanelleft.add(special_left_button);

                                        bottompanelleft.revalidate();
                                        bottompanelleft.repaint();

                                        p1hp =player1poki2.gethp();
                                        p1energy= player1poki2.getenergy();
                                        p1atk= player1poki2.getpower();
                                        p1def= player1poki2.getdefense();

                                        p1attacker= player1poki2;

                                        lefthp.setText(String.valueOf(p1hp));
                                        Leftenergy.setText(String.valueOf(p1energy));

                                    } else if (defeatedp1==2) {
                                        JOptionPane.showMessageDialog(frame, p2name+" WINS!!");
                                        // reset health and restart the  program
                                        p1hp =100;
                                        p2hp =100;
                                        lefthp.setText(String.valueOf(p1hp));
                                        righthp.setText(String.valueOf(p2hp));
                                        restartProgram();
                                    }
                                }
                            }

                                                     //switch  turns
                            playerOneTurn=!playerOneTurn;
                            updateButtonStates();
                        }
                    }
                });
                timer.start();
            }
        };

        //add action listeners to the buttons
        back.addActionListener(attackAction);
        
        
        leftpunchbut.addActionListener(attackAction);
        leftkickbut.addActionListener(attackAction);
        special_left_button.addActionListener(attackAction);
        
        rightpunchbut.addActionListener(attackAction);
        rightkickbut.addActionListener(attackAction);
        special_right_button.addActionListener(attackAction);
    }

    // method to restart the program
    private static void restartProgram() {
        frame.dispose();
        
        
        defeatedp1=0;                     // reset player one health count
        defeatedp2=0;            //reset player two health count
        playerOneTurn=true;                  //reset turn to player one
        
        main(new String[0]);
    }

    // method to update button states based on the current turn
    private static void updateButtonStates() {
        int energy=0;

        leftpunchbut.setEnabled(playerOneTurn);
        leftkickbut.setEnabled(playerOneTurn);
        special_left_button.setEnabled(playerOneTurn);

        rightpunchbut.setEnabled(!playerOneTurn);
        rightkickbut.setEnabled(!playerOneTurn);
        special_right_button.setEnabled(!playerOneTurn);

        if (playerOneTurn== true) {       // enabling different buttons based on amount of energy
            energy=p1energy;

            if (energy<60) {
                special_left_button.setEnabled(false);    
            }
            if (energy<20) {
                leftkickbut.setEnabled(false);
                special_left_button.setEnabled(false);
            }
        }
        if (playerOneTurn ==false) {
            energy =p2energy;

            if (energy<60) {
                special_right_button.setEnabled(false);
            }
            if (energy<20) {
                rightkickbut.setEnabled(false);
                special_right_button.setEnabled(false);
            }
        }
    }

    // method to handle the coin toss and determine the first player
    private static void handleCoinToss() {
        //ask player one to choose heads or tails
        Object[] options={ "Heads", "Tails" };
        playerOneCoinChoice=(String) JOptionPane.showInputDialog(frame,p1name+" heads or tails?",
                "Coin Toss",JOptionPane.PLAIN_MESSAGE,null,options,options[0]);

               //ask player two to choose heads or tails
        playerTwoCoinChoice=playerOneCoinChoice.equals("Heads") ?"Tails":"Heads";

        // randomly choose between heads and tails
        Random random =new Random();
        int coin= random.nextInt(0,2);
        
        String coinResult="";
        if (coin==0){
            coinResult="Heads";
        }else{
            coinResult="Tails";
        }

                    //determine the first player based on the coin toss result
        String first= coinResult.equals(playerOneCoinChoice) ? p1name:p2name;
        playerOneTurn =first.equals(p1name);

        //display the coin toss result
        
        JOptionPane.showMessageDialog(frame, first+" won the coin toss and will attack first.",
                "Coin Toss Result",JOptionPane.INFORMATION_MESSAGE);

        // update button states based on the current turn
        updateButtonStates();
    }

}
