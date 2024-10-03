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





public class PokemonGame {
	
	
	private static Clip clip; // for playing music
	static String lst[];
    static ArrayList<String> playerNames = new ArrayList<>(); // Store player names
    static ArrayList<String[]> playerSelectedPokemon = new ArrayList<>(); // Store each player's selected Pokémon
    static int currentPlayerIndex = 0; // Track which player is selecting their character
    static int numberOfPlayers; // Global variable to store the selected number of players
    static JFrame frame= new JFrame("Pokémon Game");
    static JButton nextButton; // The Next button to proceed to Gui2

    // Character names and image file names 
    static Character pikachu= new Character("Pikachu", 90,100,50,0,"This electric pokemon is really energetic, and extremely powerful."+"\n"+"Becareful though it's a bit of a glass cannon!");
    static Character Lucario= new Character("Lucario", 60,225,25,8,"Lucario is a well rounded fighter but"+"\n"+" although its lack of defense can be its downfall");
    static Character garchomp= new Character("Garchomp",50,200,30,22,"Careful you dont get bitten!"+"\n"+" Garchomp is difficult to damage"+"\n"+"due to its strong defensive skin");
    static Character greninja= new Character("Greninja", 70,150,20,12,"Greninja has trained his whole life for battle"+"\n"+"dispite his low health stat");
    static Character zoroark= new Character("Zoroark", 40,250,40,15,"its lurking in the woodland shadows"+"\n"+"This is a strong tank fighter"+"\n"+" that can take a lot of damage");
    static Character Absol= new Character("Absol", 20,300,60,0,"It uses the moons power to crush enemies,"+"\n"+"and dispite its immense health"+"\n"+"has no defensive capabilities");
    static Character mewtwo= new Character("Mewtwo", 50,275,20,7,"The experiment gone wrong this man-made Pokemon"+"\n"+"was made to be perfect but"+"\n"+"the experiment was never complete,"+"\n"+"its attack power is lower than expected");
    static Character charizard= new Character("Charizard", 60,250,0,5,"They say its roar causes volcanos to erupt,"+"\n"+"this durable pokemon is at the top of the foodchain"+"\n"+"and as a result has gotten lazy");
    static String[] characterNames = {"Ash", "Cynthia", "Brown", "Connan", "Motara", "Wells", "Z", "Jessie"};
    static String[] characterImages = {"Ash.JPEG", "Cynthia.JPEG", "Brown.JPEG", "Connan.JPEG",
                                       "Motara.JPEG", "Wells.JPEG", "Z.JPEG", "Jessie.JPEG"};

    // Pokémon names and image file names 
    static String[] pokemonNames = {"Pikachu", "Lucario", "Garchomp", "Greninja", "Zoroark", "Absol", "Mewtwo", "Charizard"};
    static String[] pokemonImages = {"Pikachu.JPEG", "Lucario.JPEG", "Garchomp.JPEG", "Greninja.JPEG",
                                     "Zoroark.JPEG", "Absol.JPEG", "Mewtwo.JPEG", "Charizard.JPEG"};

    // Global variables to store player names, characters, and Pokémon selections
    static Character pokistat;
    static String playerOneName, playerTwoName, playerThreeName, playerFourName, playerFiveName, playerSixName, playerSevenName;
    static String playerOneCharacter, playerTwoCharacter, playerThreeCharacter, playerFourCharacter, playerFiveCharacter, playerSixCharacter, playerSevenCharacter;
    static Character playerOnePokemon1, playerOnePokemon2, playerTwoPokemon1, playerTwoPokemon2;
    static String playerThreePokemon1, playerThreePokemon2, playerFourPokemon1, playerFourPokemon2;
    static String playerFivePokemon1, playerFivePokemon2, playerSixPokemon1, playerSixPokemon2;
    static String playerSevenPokemon1, playerSevenPokemon2;


		

  public static void main(String[] args) {
     // Play background music
    playBackgroundMusic("pokisong.WAV");
	showGui1();
  }
	
	// Create the frame
  public static void showGui1(){
    
    frame.getContentPane().removeAll();
    playerOneHealthCount=0;
    playerTwoHealthCount=0;
 
    // Get screen size and set the frame to fullscreen
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = toolkit.getScreenSize();
    frame.setSize(screenSize.width, screenSize.height);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Set the frame icon (ensure the image is in the same directory or provide a full path)
    try {
        Image icon = ImageIO.read(new File("Pokeball2.JPEG"));
        Image resizedIcon = icon.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        frame.setIconImage(resizedIcon);
    } catch (IOException e) {
        System.out.println("Icon image not found.");
        e.printStackTrace();
    }

    // Load the background image and create a custom JPanel to display it
    try {
        final Image backgroundImage = ImageIO.read(new File("backgrounddoForMenu.JPEG"));
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);

        // Add the welcome message at the top left
        JLabel welcomeLabel = new JLabel("WELCOME TO HAMILTON GO!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 70));
        welcomeLabel.setForeground(Color.YELLOW);
        welcomeLabel.setBounds(400, 20, screenSize.width, 50);
        backgroundPanel.add(welcomeLabel);

        // Add the "Select the number of players" message just below
        JLabel selectPlayersLabel = new JLabel("");
        selectPlayersLabel.setFont(new Font("Serif", Font.BOLD,12 ));
        selectPlayersLabel.setForeground(Color.YELLOW);
        selectPlayersLabel.setBounds(300, 400, screenSize.width, 50);
        backgroundPanel.add(selectPlayersLabel);

        // Button sizes and spacing
        double buttonSize = screenSize.height * 0.10; // 10% of the screen height for square buttons
        double buttonSpacing = screenSize.height * 0.10; // 10% of the screen height for spacing

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clickedButton = (JButton) e.getSource();
                numberOfPlayers = 2; // Store the selected number of players globally Integer.parseInt(clickedButton.getText())
                getPlayerNames(numberOfPlayers); // Get player names before proceeding
            }
        };

        // Layout for 7 buttons: 1st row (1,2,3), 2nd row (4,5,6), 3rd row (7)
        int xStart = 20; // Starting X position for the buttons
        int yStart = 150; // Starting Y position for the first row

        // Array to store button numbers in proper order
        int[] buttonNumbers = {1, 2, 3, 4, 5, 6, 7};

        // Loop through and place buttons
        for (int i = 0; i < buttonNumbers.length; i++) {
            JButton playerButton = new JButton("CLICK HERE"+"\n"+" TO ENTER PLAYER NAMES: ");  //String.valueOf(buttonNumbers[i])
            playerButton.setBackground(Color.BLUE);
            playerButton.setForeground(Color.WHITE);
            playerButton.setFont(new Font("Serif", Font.BOLD, 20));
            playerButton.setBounds(700, 550, (int) 500, (int) 100); // Square buttons

            // Hide buttons and disable their functionality except the button with number 2
            if (buttonNumbers[i] != 2) {
                playerButton.setVisible(false);
                playerButton.setEnabled(false);
            } else {
                playerButton.addActionListener(buttonListener);
            }

            backgroundPanel.add(playerButton);

            if ((i + 1) % 3 == 0) {
                yStart += buttonSize + buttonSpacing;
                xStart = 20; // Reset xStart for the next row
            } else {
                xStart += buttonSize + buttonSpacing;
            }
        }

        // Add "Next" button
        nextButton = new JButton("START");
        nextButton.setEnabled(false); // Disabled until players are selected
        nextButton.setBackground(Color.RED);
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("Serif", Font.BOLD, 60));
        nextButton.setBounds((int) 800, 750, (int) (screenSize.height * 0.30), (int) buttonSize);
        nextButton.addActionListener(e -> showGui2()); // Proceed to Gui2 when clicked
        backgroundPanel.add(nextButton);

        // Add the background panel to the frame
        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);

    } catch (IOException e) {
        System.out.println("Background image not found.");
        e.printStackTrace();
    }
 
}

// Method to play background music
    private static void playBackgroundMusic(String filePath) {
        try {
            if (clip == null || !clip.isRunning()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
                clip.start();
            }
        } catch (Exception e) {
            System.out.println("Error playing background music.");
            e.printStackTrace();
        }
    }

// Method to get player names based on the selected number of players
private static void getPlayerNames(int numberOfPlayers) {
    for (int i = 1; i <= numberOfPlayers; i++) {
        String playerName;
        while (true) {
            playerName = JOptionPane.showInputDialog(null, "Player " + i + " Enter Your Name:");
            if (playerName != null && !playerName.trim().isEmpty()) {
                // Store each player's name in the corresponding variable
                switch (i) {
                    case 1:
                        playerOneName = playerName;
                        break;
                    case 2:
                        playerTwoName = playerName;
                        break;
                }
                break; // Exit the loop if a valid name is entered
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid name.");
            }
        }
    }
    nextButton.setEnabled(true); // Enable "Next" button after names are entered
}
                                                                                                                                                                                       


    // Method to display Gui2 (Character Selection)
    private static void showGui2() {
        if (currentPlayerIndex >= numberOfPlayers) {
            currentPlayerIndex = 0; // Reset index for Pokémon selection
        }

        frame.getContentPane().removeAll(); // Clear the first GUI

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        JPanel panelGui2 = new JPanel();
        panelGui2.setLayout(null);

        // Panel at the top with player name
        JLabel headingLabel = new JLabel(getPlayerNameByIndex(currentPlayerIndex).toUpperCase() + ", SELECT THE CHARACTER TO REPRESENT YOU");
        headingLabel.setFont(new Font("Serif", Font.BOLD, 40));
        headingLabel.setForeground(Color.YELLOW);
        headingLabel.setBackground(Color.BLACK);
        headingLabel.setOpaque(true); // Ensure the background color is applied
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setBounds(0, 0, screenSize.width, (int) (screenSize.height * 0.10)); // 10% of the screen height
        panelGui2.add(headingLabel);

        // 2x4 Grid of Panels for Characters
        double panelWidth = screenSize.width * 0.25;  // 25% of screen width for each panel
        double panelHeight = screenSize.height * 0.45; // 45% of screen height for each panel
        double buttonWidth = screenSize.width * 0.061; // Button width 6.1% of screen width
        double buttonHeight = screenSize.height * 0.05; // Button height 5% of screen height

        for (int i = 0; i < 8; i++) {
            final int index = i; // This is the key fix - make the index final
            JPanel characterPanel = new JPanel();
            characterPanel.setLayout(null);
            characterPanel.setBounds((int) ((i % 4) * panelWidth), (int) ((i / 4) * panelHeight + screenSize.height * 0.10), (int) panelWidth, (int) panelHeight);
            characterPanel.setBackground(Color.LIGHT_GRAY);

            // Load and display character image as a background label
            try {
                Image characterImage = ImageIO.read(new File(characterImages[i]));
                JLabel imageLabel = new JLabel(new ImageIcon(characterImage.getScaledInstance((int) panelWidth, (int) panelHeight, Image.SCALE_SMOOTH)));
                imageLabel.setBounds(0, 0, (int) panelWidth, (int) panelHeight);
                characterPanel.add(imageLabel);
            } catch (IOException e) {
                System.out.println("Image " + characterImages[i] + " not found.");
                e.printStackTrace();
            }

            // Character selection button
            JButton selectButton = new JButton("Select");
            selectButton.setBounds(10, 10, (int) buttonWidth, (int) buttonHeight);
            selectButton.setBackground(Color.BLACK);
            selectButton.setForeground(Color.ORANGE);
            selectButton.setFont(new Font("Serif", Font.BOLD, 12));
            characterPanel.add(selectButton);
            // Set button on top of the image
            characterPanel.setComponentZOrder(selectButton, 0);

            // Character name box (Top right corner)
            JLabel characterNameLabel = new JLabel(characterNames[i]);
            characterNameLabel.setBounds((int) (panelWidth - buttonWidth - 10), 10, (int) buttonWidth, (int) buttonHeight);
            characterNameLabel.setBackground(Color.BLACK);
            characterNameLabel.setForeground(Color.ORANGE);
            characterNameLabel.setFont(new Font("Serif", Font.BOLD, 12));
            characterNameLabel.setOpaque(true);
            characterNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            characterPanel.add(characterNameLabel);
            // Ensure name label is on top
            characterPanel.setComponentZOrder(characterNameLabel, 0);

            // Add the character panel to Gui2
            panelGui2.add(characterPanel);

            // Action for selecting a character
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Store the selected character in the corresponding global variable
                    switch (currentPlayerIndex) {
                        case 0:
                            playerOneCharacter = characterNames[index];
                            break;
                        case 1:
                            playerTwoCharacter = characterNames[index];
                            break;
                        case 2:
                            playerThreeCharacter = characterNames[index];
                            break;
                        case 3:
                            playerFourCharacter = characterNames[index];
                            break;
                        case 4:
                            playerFiveCharacter = characterNames[index];
                            break;
                        case 5:
                            playerSixCharacter = characterNames[index];
                            break;
                        case 6:
                            playerSevenCharacter = characterNames[index];
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

    // Method to handle character selection
    private static void selectCharacter(JButton clickedButton) {
        clickedButton.setEnabled(false); // Disable the selected button

        currentPlayerIndex++;
        if (currentPlayerIndex < numberOfPlayers) {
            showGui2(); // Move to the next player
        } else {
            // All players have selected a character
            // Custom dialog with no "OK" button, only "Next" button
            JDialog dialog = new JDialog(frame, "Character Selection Complete", true);
            dialog.setLayout(new FlowLayout());
            dialog.setSize(400, 200);
            JLabel label = new JLabel("press Next for Pokémon selection.");
            dialog.add(label);

            JButton nextButton = new JButton("Next");
            dialog.add(nextButton);

            // Action for the next button to proceed to Pokémon selection
            nextButton.addActionListener(e -> {
                dialog.dispose();
                showGui3(); // Show Pokémon selection GUI (Gui3)
            });

            dialog.setLocationRelativeTo(frame); // Center the dialog
            dialog.setVisible(true);
        }
    }

    // Method to display Gui3 (Pokémon Selection)
    private static void showGui3() {
        if (currentPlayerIndex >= numberOfPlayers) {
            currentPlayerIndex = 0; // Reset index for Pokémon selection
        }

        frame.getContentPane().removeAll(); // Clear the second GUI (character selection)

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        JPanel panelGui3 = new JPanel();
        panelGui3.setLayout(null);

        // Panel at the top with player name
        JLabel headingLabel = new JLabel(getPlayerNameByIndex(currentPlayerIndex).toUpperCase() + ", SELECT YOUR TWO POKEMON");
        headingLabel.setFont(new Font("Serif", Font.BOLD, 40));
        headingLabel.setForeground(Color.YELLOW);
        headingLabel.setBackground(Color.BLACK);
        headingLabel.setOpaque(true); // Ensure the background color is applied
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setBounds(0, 0, screenSize.width, (int) (screenSize.height * 0.10)); // 10% of the screen height
        panelGui3.add(headingLabel);

        // 2x4 Grid of Panels for Pokémon
        double panelWidth = screenSize.width * 0.25;  // 25% of screen width for each panel
        double panelHeight = screenSize.height * 0.45; // 45% of screen height for each panel
        double buttonWidth = screenSize.width * 0.061; // Button width 6.1% of screen width
        double buttonHeight = screenSize.height * 0.05; // Button height 5% of screen height

        // To store two selected Pokémon for the current player
        final ArrayList<String> selectedPokemon = new ArrayList<>();
        final ArrayList<String> infoPokemon= new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            final int index = i; // Make the index final
            JPanel pokemonPanel = new JPanel();
            pokemonPanel.setLayout(null);
            pokemonPanel.setBounds((int) ((i % 4) * panelWidth), (int) ((i / 4) * panelHeight + screenSize.height * 0.10), (int) panelWidth, (int) panelHeight);
            pokemonPanel.setBackground(Color.LIGHT_GRAY);

            // Load and display Pokémon image as a background label
            try {
                Image pokemonImage = ImageIO.read(new File(pokemonImages[i]));
                JLabel imageLabel = new JLabel(new ImageIcon(pokemonImage.getScaledInstance((int) panelWidth, (int) panelHeight, Image.SCALE_SMOOTH)));
                imageLabel.setBounds(0, 0, (int) panelWidth, (int) panelHeight);
                pokemonPanel.add(imageLabel);
            } catch (IOException e) {
                System.out.println("Image " + pokemonImages[i] + " not found.");
                e.printStackTrace();
            }
            // info button
            JButton pokinfo = new JButton("Get info");
            pokinfo.setBounds(180, 10, (int) buttonWidth, (int) buttonHeight);
            pokinfo.setBackground(Color.BLACK);
            pokinfo.setForeground(Color.ORANGE);
            pokinfo.setFont(new Font("Serif", Font.BOLD, 12));
            pokemonPanel.add(pokinfo);
            pokemonPanel.setComponentZOrder(pokinfo, 0);

            // Pokémon selection button
            JButton selectButton = new JButton("Select");
            selectButton.setBounds(10, 10, (int) buttonWidth, (int) buttonHeight);
            selectButton.setBackground(Color.BLACK);
            selectButton.setForeground(Color.ORANGE);
            selectButton.setFont(new Font("Serif", Font.BOLD, 12));
            pokemonPanel.add(selectButton);
            // Set button on top of the image
            pokemonPanel.setComponentZOrder(selectButton, 0);

            // Pokémon name box (Top right corner)
            JLabel pokemonNameLabel = new JLabel(pokemonNames[i]);
            pokemonNameLabel.setBounds((int) (panelWidth - buttonWidth - 10), 10, (int) buttonWidth, (int) buttonHeight);
            pokemonNameLabel.setBackground(Color.BLACK);
            pokemonNameLabel.setForeground(Color.ORANGE);
            pokemonNameLabel.setFont(new Font("Serif", Font.BOLD, 12));
            pokemonNameLabel.setOpaque(true);
            pokemonNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            pokemonPanel.add(pokemonNameLabel);
            // Ensure name label is on top
            pokemonPanel.setComponentZOrder(pokemonNameLabel, 0);

            // Add the Pokémon panel to Gui3
            panelGui3.add(pokemonPanel);
            
            pokinfo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    infoPokemon.add(pokemonNames[index]);
                    
                    if (infoPokemon.size()>0) {
                        if (infoPokemon.get(infoPokemon.size()-1).equals("Pikachu")){


                            JOptionPane.showMessageDialog(frame,pikachu.toString() );
                        };

                        if (infoPokemon.get(infoPokemon.size()-1).equals("Lucario")){


                            JOptionPane.showMessageDialog(frame,Lucario.toString() );
                        };

                        if (infoPokemon.get(infoPokemon.size()-1).equals("Garchomp")){


                            JOptionPane.showMessageDialog(frame,garchomp.toString() );
                        };

                        if (infoPokemon.get(infoPokemon.size()-1).equals("Greninja")){


                            JOptionPane.showMessageDialog(frame,greninja.toString() );
                        };

                        if (infoPokemon.get(infoPokemon.size()-1).equals("Zoroark")){


                            JOptionPane.showMessageDialog(frame,zoroark.toString() );
                        };

                        if (infoPokemon.get(infoPokemon.size()-1).equals("Absol")){


                            JOptionPane.showMessageDialog(frame,Absol.toString() );
                        };

                        if (infoPokemon.get(infoPokemon.size()-1).equals("Mewtwo")){


                            JOptionPane.showMessageDialog(frame,mewtwo.toString() );
                        };
                        if (infoPokemon.get(infoPokemon.size()-1).equals("Charizard")){


                            JOptionPane.showMessageDialog(frame,charizard.toString() );
                        };

                    }
                }
                
            });

            // Action for selecting a Pokémon
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedPokemon.size() < 2) {
                        selectedPokemon.add(pokemonNames[index]);
                        selectButton.setEnabled(false); // Disable the selected button
                        
                    }

                    if (selectedPokemon.size() == 2) {
                        // Store selected Pokémon in corresponding global variables
                        switch (currentPlayerIndex) {
                            case 0:
                                if (selectedPokemon.get(0).equals("Pikachu")){
                                    playerOnePokemon1=pikachu;
                                }
                                if (selectedPokemon.get(0).equals("Lucario")){
                                    playerOnePokemon1=Lucario;
                                }
                                if (selectedPokemon.get(0).equals("Garchomp")){
                                    playerOnePokemon1=garchomp;
                                }
                                if (selectedPokemon.get(0).equals("Greninja")){
                                    playerOnePokemon1=greninja;
                                }
                                if (selectedPokemon.get(0).equals("Zoroark")){
                                    playerOnePokemon1=zoroark;
                                }
                                if (selectedPokemon.get(0).equals("Absol")){
                                    playerOnePokemon1=Absol;
                                }
                                if (selectedPokemon.get(0).equals("Mewtwo")){
                                    playerOnePokemon1=mewtwo;
                                }
                                if (selectedPokemon.get(0).equals("Charizard")){
                                    playerOnePokemon1=charizard;
                                }
                                if (selectedPokemon.get(1).equals("Pikachu")){
                                    playerOnePokemon2=pikachu;
                                }
                                if (selectedPokemon.get(1).equals("Lucario")){
                                    playerOnePokemon2=Lucario;
                                }
                                if (selectedPokemon.get(1).equals("Garchomp")){
                                    playerOnePokemon2=garchomp;
                                }
                                if (selectedPokemon.get(1).equals("Greninja")){
                                    playerOnePokemon2=greninja;
                                }
                                if (selectedPokemon.get(1).equals("Zoroark")){
                                    playerOnePokemon2=zoroark;
                                }
                                if (selectedPokemon.get(1).equals("Absol")){
                                    playerOnePokemon2=Absol;
                                }
                                if (selectedPokemon.get(1).equals("Mewtwo")){
                                    playerOnePokemon2=mewtwo;
                                }
                                if (selectedPokemon.get(1).equals("Charizard")){
                                    playerOnePokemon2=charizard;
                                }
                                break;
                            case 1:
                                if (selectedPokemon.get(0).equals("Pikachu")){
                                    playerTwoPokemon1=pikachu;
                                }
                                if (selectedPokemon.get(0).equals("Lucario")){
                                    playerTwoPokemon1=Lucario;
                                }
                                if (selectedPokemon.get(0).equals("Garchomp")){
                                    playerTwoPokemon1=garchomp;
                                }
                                if (selectedPokemon.get(0).equals("Greninja")){
                                    playerTwoPokemon1=greninja;
                                }
                                if (selectedPokemon.get(0).equals("Zoroark")){
                                    playerTwoPokemon1=zoroark;
                                }
                                if (selectedPokemon.get(0).equals("Absol")){
                                    playerTwoPokemon1=Absol;
                                }
                                if (selectedPokemon.get(0).equals("Mewtwo")){
                                    playerTwoPokemon1=mewtwo;
                                }
                                if (selectedPokemon.get(0).equals("Charizard")){
                                    playerTwoPokemon1=charizard;
                                }
                                if (selectedPokemon.get(1).equals("Pikachu")){
                                    playerTwoPokemon2=pikachu;
                                }
                                if (selectedPokemon.get(1).equals("Lucario")){
                                    playerTwoPokemon2=Lucario;
                                }
                                if (selectedPokemon.get(1).equals("Garchomp")){
                                    playerTwoPokemon2=garchomp;
                                }
                                if (selectedPokemon.get(1).equals("Greninja")){
                                    playerTwoPokemon2=greninja;
                                }
                                if (selectedPokemon.get(1).equals("Zoroark")){
                                    playerTwoPokemon2=zoroark;
                                }
                                if (selectedPokemon.get(1).equals("Absol")){
                                    playerTwoPokemon2=Absol;
                                }
                                if (selectedPokemon.get(1).equals("Mewtwo")){
                                    playerTwoPokemon2=mewtwo;
                                }
                                if (selectedPokemon.get(1).equals("Charizard")){
                                    playerTwoPokemon2=charizard;
                                }
                                break;
                            case 2:
                                playerThreePokemon1 = selectedPokemon.get(0);
                                playerThreePokemon2 = selectedPokemon.get(1);
                                break;
                            case 3:
                                playerFourPokemon1 = selectedPokemon.get(0);
                                playerFourPokemon2 = selectedPokemon.get(1);
                                break;
                            case 4:
                                playerFivePokemon1 = selectedPokemon.get(0);
                                playerFivePokemon2 = selectedPokemon.get(1);
                                break;
                            case 5:
                                playerSixPokemon1 = selectedPokemon.get(0);
                                playerSixPokemon2 = selectedPokemon.get(1);
                                break;
                            case 6:
                                playerSevenPokemon1 = selectedPokemon.get(0);
                                playerSevenPokemon2 = selectedPokemon.get(1);
                                break;
                        }
                        selectNextPlayerOrFinish(); // Move to the next player or finish
                    }
                }
            });
        }

        frame.setContentPane(panelGui3);
        frame.revalidate();
        frame.repaint();
    }

    // Move to the next player for Pokémon selection or finish
    private static void selectNextPlayerOrFinish() {
    currentPlayerIndex++;
    if (currentPlayerIndex < numberOfPlayers) {
        showGui3(); // Move to the next player for Pokémon selection
    } else {
        // All players have selected their Pokémon
        // Custom dialog with no "OK" button, only "Fight!!" button
        JDialog dialog = new JDialog(frame, "Pokémon Selection Complete", true);
        dialog.setLayout(new FlowLayout());
        dialog.setSize(400, 200);
        JLabel label = new JLabel("Click Fight!!");
        dialog.add(label);

        JButton fightButton = new JButton("Fight!!");
        dialog.add(fightButton);

        // Action for the Fight!! button to proceed to Gui4
        fightButton.addActionListener(e -> {
            dialog.dispose();
            showGui4(); // Call the method to display Gui4
            handleCoinToss();// coin toss that determines the first attacker
            playerOneEnergy=playerOnePokemon1.getenergy();
            playerTwoEnergy=playerTwoPokemon1.getenergy();
            updateButtonStates();
			addFightingMechanics(); // for calling the fight logic methods
			
        });

        dialog.setLocationRelativeTo(frame); // Center the dialog
        dialog.setVisible(true);
    }
}


    // Helper method to get player name by index
    private static String getPlayerNameByIndex(int index) {
        switch (index) {
            case 0:
                return playerOneName;
            case 1:
                return playerTwoName;
            case 2:
                return playerThreeName;
            case 3:
                return playerFourName;
            case 4:
                return playerFiveName;
            case 5:
                return playerSixName;
            case 6:
                return playerSevenName;
            default:
                return "";
        }
    }




// Declare the variables as class variables
private static JPanel bottomLeftPanel;
private static JPanel bottomRightPanel;
private static Dimension screenSize;
private static JLayeredPane layeredPane;
private static JLabel healthValueRight;
private static JLabel healthValueLeft;
private static JLabel energyValueRight;
private static JLabel energyValueLeft;
private static JPanel rightSquarePanel;
private static JPanel leftSquarePanel;
private static JButton punchButtonLeft;
private static JButton kickButtonLeft;
private static JButton specialAbilityButtonLeft;
private static JButton punchButtonRight;
private static JButton kickButtonRight;
private static JButton specialAbilityButtonRight;
private static JButton back;
// Declare health variables as instance variables
private static int playerOneHealth = 100;
private static int playerTwoHealth = 100;

private static int playerOneEnergy = 0;
private static int playerTwoEnergy = 0;

private static int playerOneHealthCount = 0;
private static int playerTwoHealthCount = 0;

private static double playerTwobase = 0;
private static double playerOnebase = 0;

private static int playerOnedef = 0;
private static int playerTwodef = 0;
private static Character playerOneAttacker=new Character("Pikachu", 1.7,100,50,8,"Somthing about pikachu");
private static Character playerTwoAttacker=new Character("Pikachu", 1.7,100,50,8,"Somthing about pikachu");
private static Character attacker=new Character("Pikachu", 1.7,100,50,8,"Somthing about pikachu");

// Declare damage as a class variable
private static int damage = 0;
private static int costenergy=0;
//other class variables for coin toss
private static String playerOneCoinChoice, playerTwoCoinChoice; // coin toss variables
private static boolean playerOneTurn;



private static void showGui4() {
    frame.getContentPane().removeAll(); // Clear the previous GUI

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    screenSize = toolkit.getScreenSize(); // Initialize screenSize

    layeredPane = new JLayeredPane(); // Initialize layeredPane
    layeredPane.setPreferredSize(screenSize);

    JPanel panelGui4 = new JPanel();
    panelGui4.setLayout(null);
    panelGui4.setBounds(0, 0, screenSize.width, screenSize.height);
    layeredPane.add(panelGui4, JLayeredPane.DEFAULT_LAYER);

    // Top left panel (Panel A)
    
  
    JPanel topLeftPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                Image characterImage = ImageIO.read(new File(playerOneCharacter + ".JPEG")); // Replace with your image file
                g.drawImage(characterImage, 0, 0, getWidth(), getHeight(), this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    topLeftPanel.setBounds(0, 0, (int) (screenSize.width * 0.25), (int) (screenSize.height * 0.50));

    panelGui4.add(topLeftPanel);

    // Bottom left panel (Panel B)
    bottomLeftPanel = new JPanel() { // Initialize bottomLeftPanel
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                Image pokemonImage = ImageIO.read(new File(playerOnePokemon2.getname() + ".JPEG")); // Replace with your image file
                g.drawImage(pokemonImage, 0, 0, getWidth(), getHeight(), this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    bottomLeftPanel.setBounds(0, (int) (screenSize.height * 0.50), (int) (screenSize.width * 0.25), (int) (screenSize.height * 0.50));
    panelGui4.add(bottomLeftPanel);

    // Top right panel (Panel F)
    JPanel topRightPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                Image characterImage = ImageIO.read(new File(playerTwoCharacter + ".JPEG")); // Replace with your image file
                g.drawImage(characterImage, 0, 0, getWidth(), getHeight(), this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    topRightPanel.setBounds((int) (screenSize.width * 0.75), 0, (int) (screenSize.width * 0.25), (int) (screenSize.height * 0.50));
    panelGui4.add(topRightPanel);

    // Bottom right panel (Panel G)
    bottomRightPanel = new JPanel() { // Initialize bottomRightPanel
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                Image pokemonImage = ImageIO.read(new File(playerTwoPokemon2.getname() + ".JPEG")); // Replace with your image file
                g.drawImage(pokemonImage, 0, 0, getWidth(), getHeight(), this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    bottomRightPanel.setBounds((int) (screenSize.width * 0.75), (int) (screenSize.height * 0.50), (int) (screenSize.width * 0.25), (int) (screenSize.height * 0.50));
    panelGui4.add(bottomRightPanel);

    // Center panel (Panel C)
    JPanel centerPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                Image centerImage = ImageIO.read(new File("Arena.JPEG")); // Replace with your image file
                g.drawImage(centerImage, 0, 0, getWidth(), getHeight(), this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    centerPanel.setBounds((int) (screenSize.width * 0.25), 0, (int) (screenSize.width * 0.50), screenSize.height);
    centerPanel.setLayout(null);
    panelGui4.add(centerPanel);

    // Left square panel within center panel (Panel D)
    leftSquarePanel = new JPanel() { // Initialize leftSquarePanel
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                Image leftSquareImage = ImageIO.read(new File(playerOnePokemon1.getname() + ".JPEG")); // Replace with your image file
                g.drawImage(leftSquareImage, 0, 0, getWidth(), getHeight(), this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    leftSquarePanel.setBounds(0, (int) (screenSize.height * 0.35), (int) (screenSize.height * 0.308), (int) (screenSize.height * 0.308));
    centerPanel.add(leftSquarePanel);

    // Right square panel within center panel (Panel E)
    rightSquarePanel = new JPanel() { // Initialize rightSquarePanel
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                Image rightSquareImage = ImageIO.read(new File(playerTwoPokemon1.getname() + ".JPEG")); // Replace with your image file
                g.drawImage(rightSquareImage, 0, 0, getWidth(), getHeight(), this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    rightSquarePanel.setBounds((int) (screenSize.width * 0.50 - screenSize.height * 0.308), (int) (screenSize.height * 0.35), (int) (screenSize.height * 0.308), (int) (screenSize.height * 0.308));
    centerPanel.add(rightSquarePanel);

    // Initialize the buttons
    back = new JButton("back to main menue");
    back.setBounds(180, 10, (int)500, (int)500);
    back.setBackground(Color.BLACK);
    back.setForeground(Color.ORANGE);
    back.setFont(new Font("Serif", Font.BOLD, 12));
    back.addActionListener(e -> showGui1());
    topRightPanel.add(back);
    topRightPanel.setComponentZOrder(back, 0);

    punchButtonLeft = new JButton("Punch");
    punchButtonLeft.setBounds(0, (int) (screenSize.height * 0.65), (int) (screenSize.height * 0.10), (int) (screenSize.height * 0.05));
    punchButtonLeft.setBackground(Color.BLACK);
    punchButtonLeft.setForeground(Color.ORANGE);
    punchButtonLeft.setFont(new Font("Serif", Font.BOLD, 12));
    bottomLeftPanel.add(punchButtonLeft);

    kickButtonLeft = new JButton("Kick");
    kickButtonLeft.setBounds((int) (screenSize.height * 0.15), (int) (screenSize.height * 0.65), (int) (screenSize.height * 0.10), (int) (screenSize.height * 0.05));
    kickButtonLeft.setBackground(Color.BLACK);
    kickButtonLeft.setForeground(Color.ORANGE);
    kickButtonLeft.setFont(new Font("Serif", Font.BOLD, 12));
    bottomLeftPanel.add(kickButtonLeft);

    specialAbilityButtonLeft = new JButton("Special Ability");
    specialAbilityButtonLeft.setBounds(0, (int) (screenSize.height * 0.75), (int) (screenSize.height * 0.255), (int) (screenSize.height * 0.05));
    specialAbilityButtonLeft.setBackground(Color.BLACK);
    specialAbilityButtonLeft.setForeground(Color.ORANGE);
    specialAbilityButtonLeft.setFont(new Font("Serif", Font.BOLD, 12));
    bottomLeftPanel.add(specialAbilityButtonLeft);

    punchButtonRight = new JButton("Punch");
    punchButtonRight.setBounds((int) (screenSize.width * 0.75), (int) (screenSize.height * 0.65), (int) (screenSize.height * 0.10), (int) (screenSize.height * 0.05));
    punchButtonRight.setBackground(Color.BLACK);
    punchButtonRight.setForeground(Color.ORANGE);
    punchButtonRight.setFont(new Font("Serif", Font.BOLD, 12));
    bottomRightPanel.add(punchButtonRight);

    
	
	
	kickButtonRight = new JButton("Kick");
    kickButtonRight.setBounds((int) (screenSize.width * 0.90), (int) (screenSize.height * 0.65), (int) (screenSize.height * 0.10), (int) (screenSize.height * 0.05));
    kickButtonRight.setBackground(Color.BLACK);
    kickButtonRight.setForeground(Color.ORANGE);
    kickButtonRight.setFont(new Font("Serif", Font.BOLD, 12));
    bottomRightPanel.add(kickButtonRight);

    specialAbilityButtonRight = new JButton("Special Ability");
    specialAbilityButtonRight.setBounds((int) (screenSize.width * 0.75), (int) (screenSize.height * 0.75), (int) (screenSize.height * 0.255), (int) (screenSize.height * 0.05));
    specialAbilityButtonRight.setBackground(Color.BLACK);
    specialAbilityButtonRight.setForeground(Color.ORANGE);
    specialAbilityButtonRight.setFont(new Font("Serif", Font.BOLD, 12));
    bottomRightPanel.add(specialAbilityButtonRight);

    // Energy and Health boxes on the left
    JLabel healthLabelLeft = new JLabel("Health", SwingConstants.CENTER);
    healthLabelLeft.setBounds((int) (screenSize.width * 0.25 + screenSize.height * 0.05), (int) (screenSize.height * 0.05), (int) (screenSize.height * 0.102), (int) (screenSize.height * 0.055));
    healthLabelLeft.setBackground(Color.BLACK);
    healthLabelLeft.setForeground(Color.GREEN);
    healthLabelLeft.setFont(new Font("Serif", Font.BOLD, 12));
    healthLabelLeft.setOpaque(true);
    layeredPane.add(healthLabelLeft, JLayeredPane.PALETTE_LAYER);

    JLabel energyLabelLeft = new JLabel("Energy", SwingConstants.CENTER);
    energyLabelLeft.setBounds((int) (screenSize.width * 0.25 + screenSize.height * 0.05), (int) (screenSize.height * 0.12), (int) (screenSize.height * 0.102), (int) (screenSize.height * 0.055));
    energyLabelLeft.setBackground(Color.BLACK);
    energyLabelLeft.setForeground(Color.YELLOW);
    energyLabelLeft.setFont(new Font("Serif", Font.BOLD, 12));
    energyLabelLeft.setOpaque(true);
    layeredPane.add(energyLabelLeft, JLayeredPane.PALETTE_LAYER);

    // Boxes to display numbers for energy and health on the left
    healthValueLeft = new JLabel(""+playerOnePokemon1.gethp(), SwingConstants.CENTER);
    healthValueLeft.setBounds((int) (screenSize.width * 0.25 + screenSize.height * 0.157), (int) (screenSize.height * 0.05), (int) (screenSize.height * 0.055), (int) (screenSize.height * 0.055));
    healthValueLeft.setBackground(Color.GREEN);
    healthValueLeft.setForeground(Color.BLACK);
    healthValueLeft.setFont(new Font("Serif", Font.BOLD, 12));
    healthValueLeft.setOpaque(true);
    layeredPane.add(healthValueLeft, JLayeredPane.PALETTE_LAYER);

    energyValueLeft = new JLabel(""+playerOnePokemon1.getenergy(), SwingConstants.CENTER);
    energyValueLeft.setBounds((int) (screenSize.width * 0.25 + screenSize.height * 0.157), (int) (screenSize.height * 0.12), (int) (screenSize.height * 0.055), (int) (screenSize.height * 0.055));
    energyValueLeft.setBackground(Color.YELLOW);
    energyValueLeft.setForeground(Color.BLACK);
    energyValueLeft.setFont(new Font("Serif", Font.BOLD, 12));
    energyValueLeft.setOpaque(true);
    layeredPane.add(energyValueLeft, JLayeredPane.PALETTE_LAYER);

    // Energy and Health boxes on the right
    JLabel healthLabelRight = new JLabel("Health", SwingConstants.CENTER);
    healthLabelRight.setBounds((int) (screenSize.width * 0.70), (int) (screenSize.height * 0.05), (int) (screenSize.height * 0.102), (int) (screenSize.height * 0.055));
    healthLabelRight.setBackground(Color.BLACK);
    healthLabelRight.setForeground(Color.GREEN);
    healthLabelRight.setFont(new Font("Serif", Font.BOLD, 12));
    healthLabelRight.setOpaque(true);
    layeredPane.add(healthLabelRight, JLayeredPane.PALETTE_LAYER);

    JLabel energyLabelRight = new JLabel("Energy", SwingConstants.CENTER);
    energyLabelRight.setBounds((int) (screenSize.width * 0.70), (int) (screenSize.height * 0.12), (int) (screenSize.height * 0.102), (int) (screenSize.height * 0.055));
    energyLabelRight.setBackground(Color.BLACK);
    energyLabelRight.setForeground(Color.YELLOW);
    energyLabelRight.setFont(new Font("Serif", Font.BOLD, 12));
    energyLabelRight.setOpaque(true);
    layeredPane.add(energyLabelRight, JLayeredPane.PALETTE_LAYER);

    // Boxes to display numbers for energy and health on the right
    healthValueRight = new JLabel(""+playerTwoPokemon1.gethp(), SwingConstants.CENTER);
    healthValueRight.setBounds((int) (screenSize.width * 0.65), (int) (screenSize.height * 0.05), (int) (screenSize.height * 0.055), (int) (screenSize.height * 0.055));
    healthValueRight.setBackground(Color.GREEN);
    healthValueRight.setForeground(Color.BLACK);
    healthValueRight.setFont(new Font("Serif", Font.BOLD, 12));
    healthValueRight.setOpaque(true);
    layeredPane.add(healthValueRight, JLayeredPane.PALETTE_LAYER);
    
    energyValueRight = new JLabel(""+playerTwoPokemon1.getenergy(), SwingConstants.CENTER);
    energyValueRight.setBounds((int) (screenSize.width * 0.65), (int) (screenSize.height * 0.12), (int) (screenSize.height * 0.055), (int) (screenSize.height * 0.055));
    energyValueRight.setBackground(Color.YELLOW);
    energyValueRight.setForeground(Color.BLACK);
    energyValueRight.setFont(new Font("Serif", Font.BOLD, 12));
    energyValueRight.setOpaque(true);
    layeredPane.add(energyValueRight, JLayeredPane.PALETTE_LAYER);

    frame.setContentPane(layeredPane);
    frame.revalidate();
    frame.repaint();
}

	
	

    private static void addFightingMechanics() {
        // Method to handle the attack animation and health reduction
        playerOneEnergy=playerOnePokemon1.getenergy();
        playerTwoEnergy=playerTwoPokemon1.getenergy();
        playerOneHealth=playerOnePokemon1.gethp();
        playerTwoHealth=playerTwoPokemon1.gethp();
        playerOnebase=playerOnePokemon1.getpower();
        playerTwobase=playerTwoPokemon1.getpower();
        playerOnedef=playerOnePokemon1.getdefense();
        playerTwodef=playerTwoPokemon1.getdefense();
        playerOneAttacker=playerOnePokemon1;
        playerTwoAttacker=playerTwoPokemon1;

        ActionListener attackAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                String actionCommand = source.getActionCommand();
                String attackImage = "";
            
                 
				// damage caused by attacks
                costenergy=0;
				Random randomDamage = new Random();
                //if (actionCommand.equals("back to main menue")){
                    //frame.getContentPane().removeAll();
                    //main(lst);
                    //System.exit(0);
                //}
                if (actionCommand.equals("Punch")) {
                    damage = randomDamage.nextInt(30,40);
                    costenergy=0;
                    
                    punchButtonLeft.setEnabled(false);
                    kickButtonLeft.setEnabled(false);
                    specialAbilityButtonLeft.setEnabled(false);

                    punchButtonRight.setEnabled(false);
                    kickButtonRight.setEnabled(false);
                    specialAbilityButtonRight.setEnabled(false);
                    
                    attackImage = "punch2_0_V1.PNG"; //  image file
                } else if (actionCommand.equals("Kick")) {
                    damage = randomDamage.nextInt(40,50);
                    costenergy=40;
                    punchButtonLeft.setEnabled(false);
                    kickButtonLeft.setEnabled(false);
                    specialAbilityButtonLeft.setEnabled(false);

                    punchButtonRight.setEnabled(false);
                    kickButtonRight.setEnabled(false);
                    specialAbilityButtonRight.setEnabled(false);

                    attackImage = "kickImage_V1.PNG"; //  image file
                } else if (actionCommand.equals("Special Ability")) {
                    damage = randomDamage.nextInt(50,60);
                    costenergy=80;
                    punchButtonLeft.setEnabled(false);
                    kickButtonLeft.setEnabled(false);
                    specialAbilityButtonLeft.setEnabled(false);

                    punchButtonRight.setEnabled(false);
                    kickButtonRight.setEnabled(false);
                    specialAbilityButtonRight.setEnabled(false);
                    
                    attackImage = attacker.getname()+"specialAbilityImage.PNG";
                    
                    
                }

                // Determine the direction of the attack
                boolean isLeftAttack = source.getParent() == bottomLeftPanel;

                // Create the attack image label
                ImageIcon attackIcon = new ImageIcon(attackImage);
                Image attackImg = attackIcon.getImage().getScaledInstance((int) (150), (int) (150), Image.SCALE_SMOOTH);
                JLabel attackLabel = new JLabel(new ImageIcon(attackImg));
                attackLabel.setBounds(isLeftAttack ? (int) (screenSize.width * 0.25) : (int) (screenSize.width * 0.75) - (int) (screenSize.height * 0.11), (int) (screenSize.height * 0.45), (int) (screenSize.height * 0.11), (int) (screenSize.height * 0.11));
                layeredPane.add(attackLabel, JLayeredPane.DRAG_LAYER);

                
				
				
				
				
				// Timer to move the attack image
                Timer timer = new Timer(10, new ActionListener() {
                    int x = attackLabel.getX();
                    int stepSize = 12; // Adjust the step size for consistent speed

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Calculate the remaining distance to the target
                        int remainingDistance = isLeftAttack ? screenSize.width - (int) (screenSize.width * 0.25) - (int) (screenSize.height * 0.11) - x : x - (int) (screenSize.width * 0.25);

                        // Adjust the step size based on the remaining distance to ensure consistent speed
                        if (remainingDistance < stepSize) {
                            stepSize = remainingDistance;
                        }

                        x += isLeftAttack ? stepSize : -stepSize;
                        attackLabel.setLocation(x, attackLabel.getY());

                        // Check if the attack image has reached the edge of the opposite panel
                        if ((isLeftAttack && x >= screenSize.width - (int) (screenSize.width * 0.25) - (int) (screenSize.height * 0.11)) || (!isLeftAttack && x <= (int) (screenSize.width * 0.25))) {
                            ((Timer) e.getSource()).stop();
                            layeredPane.remove(attackLabel);
                            layeredPane.repaint();


                            // Reduce health and update the health label
                            if (isLeftAttack) {
                                attacker=playerTwoAttacker;

                                playerOneEnergy+=(20-costenergy);
                                playerTwoHealth -= (damage*(playerOnebase/100))-(damage*(playerTwodef/100)); // (damage*playerbase/100)-(damage*defense/100)
                                
                                healthValueRight.setText(String.valueOf(playerTwoHealth));
                                energyValueLeft.setText(String.valueOf(playerOneEnergy));
                                if (playerTwoHealth <= 0) {
                                    playerTwoHealthCount++;
                                    if (playerTwoHealthCount == 1) {
                                        // Replace the picture in the right square panel
                                        rightSquarePanel.removeAll();
                                        rightSquarePanel.repaint();
                                        try {
                                            Image pokemonImage = ImageIO.read(new File(playerTwoPokemon2.getname() + ".JPEG")); // Replace with your image file
                                            Image resizedImage = pokemonImage.getScaledInstance(rightSquarePanel.getWidth(), rightSquarePanel.getHeight(), Image.SCALE_SMOOTH);
                                            JLabel pokemonLabel = new JLabel(new ImageIcon(resizedImage));
                                            rightSquarePanel.add(pokemonLabel);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                        rightSquarePanel.revalidate();
                                        rightSquarePanel.repaint();

                                        // Remove all components from bottom right panel except attack buttons
                                        bottomRightPanel.removeAll();
                                        bottomRightPanel.add(punchButtonRight);
                                        bottomRightPanel.add(kickButtonRight);
                                        bottomRightPanel.add(specialAbilityButtonRight);
                                        bottomRightPanel.revalidate();
                                        bottomRightPanel.repaint();

                                        playerTwoHealth = playerTwoPokemon2.gethp();
                                        playerTwoEnergy=playerTwoPokemon2.getenergy();
                                        playerTwobase=playerTwoPokemon2.getpower();
                                        playerTwodef=playerTwoPokemon2.getdefense();
                                        
                                        playerTwoAttacker=playerTwoPokemon2;
                                        healthValueRight.setText(String.valueOf(playerTwoHealth));
                                        energyValueRight.setText(String.valueOf(playerTwoEnergy));
                                    } else if (playerTwoHealthCount == 2) {
                                        JOptionPane.showMessageDialog(frame, playerOneName + " WINS!!");
                                        // Reset health and restart the program
                                        playerOneHealth = 100;
                                        playerTwoHealth = 100;
                                        
                                        healthValueLeft.setText(String.valueOf(playerOneHealth));
                                        healthValueRight.setText(String.valueOf(playerTwoHealth));
                                        restartProgram();
                                    }
                                }
                            } else {
                                attacker=playerOneAttacker;
                                playerTwoEnergy+=(20-costenergy);
                                playerOneHealth -= (damage*(playerTwobase/100))-(damage*(playerOnedef/100));
                                healthValueLeft.setText(String.valueOf(playerOneHealth));
                                energyValueRight.setText(String.valueOf(playerTwoEnergy));
                                if (playerOneHealth <= 0) {
                                    playerOneHealthCount++;
                                    if (playerOneHealthCount == 1) {
                                        // Replace the picture in the left square panel
                                        leftSquarePanel.removeAll();
                                        leftSquarePanel.repaint();
                                        try {
                                            Image pokemonImage = ImageIO.read(new File(playerOnePokemon2.getname() + ".JPEG")); // Replace with your image file
                                            Image resizedImage = pokemonImage.getScaledInstance(leftSquarePanel.getWidth(), leftSquarePanel.getHeight(), Image.SCALE_SMOOTH);
                                            JLabel pokemonLabel = new JLabel(new ImageIcon(resizedImage));
                                            leftSquarePanel.add(pokemonLabel);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                        leftSquarePanel.revalidate();
                                        leftSquarePanel.repaint();

                                        // Remove all components from bottom left panel except attack buttons
                                        bottomLeftPanel.removeAll();
                                        bottomLeftPanel.add(punchButtonLeft);
                                        bottomLeftPanel.add(kickButtonLeft);
                                        bottomLeftPanel.add(specialAbilityButtonLeft);
                                        bottomLeftPanel.revalidate();
                                        bottomLeftPanel.repaint();

                                        playerOneHealth = playerOnePokemon2.gethp();
                                        playerOneEnergy=playerOnePokemon2.getenergy();
                                        playerOnebase=playerOnePokemon2.getpower();
                                        playerOnedef=playerOnePokemon2.getdefense();
                                        playerOneAttacker=playerOnePokemon2;
                                        healthValueLeft.setText(String.valueOf(playerOneHealth));
                                        energyValueLeft.setText(String.valueOf(playerOneEnergy));

                                    } else if (playerOneHealthCount == 2) {
                                        JOptionPane.showMessageDialog(frame, playerTwoName + " WINS!!");
                                        // Reset health and restart the program
                                        playerOneHealth = 100;
                                        playerTwoHealth = 100;
                                        healthValueLeft.setText(String.valueOf(playerOneHealth));
                                        healthValueRight.setText(String.valueOf(playerTwoHealth));
                                        restartProgram();
                                    }
                                }
                            }

                            // Switch turns
                            playerOneTurn = !playerOneTurn;
                            updateButtonStates();
                        }
                    }
                });
                timer.start();
            }
        };

        // Add action listeners to the buttons
        back.addActionListener(attackAction);
        punchButtonLeft.addActionListener(attackAction);
        kickButtonLeft.addActionListener(attackAction);
        specialAbilityButtonLeft.addActionListener(attackAction);
        punchButtonRight.addActionListener(attackAction);
        kickButtonRight.addActionListener(attackAction);
        specialAbilityButtonRight.addActionListener(attackAction);
    }

    // Method to restart the program
    private static void restartProgram() {
        frame.dispose();
        playerOneHealthCount = 0; // Reset player one health count
        playerTwoHealthCount = 0; // Reset player two health count
        playerOneTurn = true; // Reset turn to player one
        main(new String[0]);
    }

   
    // Method to update button states based on the current turn
    private static void updateButtonStates() {
        int energy=0;

        punchButtonLeft.setEnabled(playerOneTurn);
        kickButtonLeft.setEnabled(playerOneTurn);
        specialAbilityButtonLeft.setEnabled(playerOneTurn);

        punchButtonRight.setEnabled(!playerOneTurn);
        kickButtonRight.setEnabled(!playerOneTurn);
        specialAbilityButtonRight.setEnabled(!playerOneTurn);

        if (playerOneTurn==true){
            energy=playerOneEnergy;
        
            if (energy<60){
                specialAbilityButtonLeft.setEnabled(false);
            }
            if (energy<20){
                kickButtonLeft.setEnabled(false);
                specialAbilityButtonLeft.setEnabled(false);
            }
        }
        if (playerOneTurn==false){
            energy=playerTwoEnergy;
        
            if (energy<60){
                specialAbilityButtonRight.setEnabled(false);
            }
            if (energy<20){
                kickButtonRight.setEnabled(false);
                specialAbilityButtonRight.setEnabled(false);
            }
        }
    }

    // Method to handle the coin toss and determine the first player
    private static void handleCoinToss() {
        // Ask player one to choose heads or tails
        Object[] options = {"Heads", "Tails"};
        playerOneCoinChoice = (String) JOptionPane.showInputDialog(frame, playerOneName + " heads or tails?", "Coin Toss", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        // Ask player two to choose heads or tails
        playerTwoCoinChoice = playerOneCoinChoice.equals("Heads") ? "Tails" : "Heads";

        // Randomly choose between heads and tails
        Random random = new Random();
        String coinResult = random.nextBoolean() ? "Heads" : "Tails";

        // Determine the first player based on the coin toss result
        String firstPlayer = coinResult.equals(playerOneCoinChoice) ? playerOneName : playerTwoName;
        playerOneTurn = firstPlayer.equals(playerOneName);

        // Display the coin toss result
        JOptionPane.showMessageDialog(frame, firstPlayer + " won the coin toss and will attack first.", "Coin Toss Result", JOptionPane.INFORMATION_MESSAGE);

        // Update button states based on the current turn
        updateButtonStates();
    }
	
	


	
	
	
	




}






		 
		 
		 

    
    
    
  
  
  


    



    
   

  


 

    

    








    


  






  
  







  
  

 
 
 







