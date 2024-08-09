import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PokemonGame1 extends JFrame implements ActionListener {
    private JComboBox<String> player1ComboBox;
    private JComboBox<String> player2ComboBox;
    private JButton startButton;
    private JTextArea resultArea;

    private Pokemon[] pokemons = {
        new Pokemon("Charizard", 50, "Flamethrower"),
        new Pokemon("Pikachu", 30, "Thunderbolt"),
        new Pokemon("Dragonite", 60, "Dragon Claw"),
        new Pokemon("Ivysaur", 40, "Vine Whip"),
        new Pokemon("Greninja", 45, "Water Shuriken")
    };

    public PokemonGame1() {
        setTitle("Pokemon Battle");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create ComboBoxes for Pokémon selection
        player1ComboBox = new JComboBox<>(getPokemonNames());
        player2ComboBox = new JComboBox<>(getPokemonNames());
        startButton = new JButton("Start Battle!");
        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);

        // Layout configuration
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Player 1 Pokémon:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(player1ComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Player 2 Pokémon:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(player2ComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(startButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(new JScrollPane(resultArea), gbc);

        startButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            String player1Selection = (String) player1ComboBox.getSelectedItem();
            String player2Selection = (String) player2ComboBox.getSelectedItem();

            Pokemon player1Pokemon = getPokemonByName(player1Selection);
            Pokemon player2Pokemon = getPokemonByName(player2Selection);

            if (player1Pokemon == null || player2Pokemon == null) {
                resultArea.setText("Error: Pokémon not found.");
                return;
            }

            int player1Power = (int) (Math.random() * player1Pokemon.getBasePower()) + 1;
            int player2Power = (int) (Math.random() * player2Pokemon.getBasePower()) + 1;

            if (player1Power > player2Power) {
                resultArea.setText("Player 1 wins with a power level of " + player1Power + " using " + player1Pokemon.getName() + "!");
            } else if (player2Power > player1Power) {
                resultArea.setText("Player 2 wins with a power level of " + player2Power + " using " + player2Pokemon.getName() + "!");
            } else {
                resultArea.setText("It's a tie! Both players used " + player1Pokemon.getName() + "!");
            }
        }
    }

    private String[] getPokemonNames() {
        String[] names = new String[pokemons.length];
        for (int i = 0; i < pokemons.length; i++) {
            names[i] = pokemons[i].getName();
        }
        return names;
    }

    private Pokemon getPokemonByName(String name) {
        for (Pokemon p : pokemons) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PokemonGame game = new PokemonGame();
            game.setVisible(true);
        });
    }
}

class Pokemon {
    private String name;
    private int basePower;
    private String move;

    public Pokemon(String name, int basePower, String move) {
        this.name = name;
        this.basePower = basePower;
        this.move = move;
    }

    public String getName() {
        return name;
    }

    public int getBasePower() {
        return basePower;
    }

    public String getMove() {
        return move;
    }
}