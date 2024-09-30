import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Pokemon {
    private String name;
    private int basePower;

    public Pokemon(String name, int basePower) {
        this.name = name;
        this.basePower = basePower;
    }

    public String getName() {
        return name;
    }

    public int getBasePower() {
        return basePower;
    }
}

public class TextBasedPokemonGameGUI {
    private static Pokemon[] pokemons = {
        new Pokemon("Charizard", 50),
        new Pokemon("Pikachu", 30),
        new Pokemon("Dragonite", 60),
        new Pokemon("Ivysaur", 40),
        new Pokemon("Greninja", 45)
    };

    private JComboBox<String> player1ComboBox;
    private JComboBox<String> player2ComboBox;
    private JTextArea resultArea;

    public TextBasedPokemonGameGUI() {
        JFrame frame = new JFrame("Pokémon Battle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        // Player 1 selection
        player1ComboBox = new JComboBox<>(getPokemonNames());
        player2ComboBox = new JComboBox<>(getPokemonNames());
        JButton battleButton = new JButton("Battle!");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        battleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulateBattle();
            }
        });

        frame.add(new JLabel("Player 1 Pokémon:"));
        frame.add(player1ComboBox);
        frame.add(new JLabel("Player 2 Pokémon:"));
        frame.add(player2ComboBox);
        frame.add(battleButton);
        frame.add(new JScrollPane(resultArea));

        frame.setVisible(true);
    }

    private String[] getPokemonNames() {
        String[] names = new String[pokemons.length];
        for (int i = 0; i < pokemons.length; i++) {
            names[i] = pokemons[i].getName();
        }
        return names;
    }

    private void simulateBattle() {
        int player1Choice = player1ComboBox.getSelectedIndex();
        int player2Choice = player2ComboBox.getSelectedIndex();

        Pokemon player1Pokemon = pokemons[player1Choice];
        Pokemon player2Pokemon = pokemons[player2Choice];

        int player1Power = (int) (Math.random() * player1Pokemon.getBasePower()) + 1;
        int player2Power = (int) (Math.random() * player2Pokemon.getBasePower()) + 1;

        StringBuilder result = new StringBuilder("Battle Results:\n");
        result.append(player1Pokemon.getName()).append(" power: ").append(player1Power).append("\n");
        result.append(player2Pokemon.getName()).append(" power: ").append(player2Power).append("\n");

        if (player1Power > player2Power) {
            result.append(player1Pokemon.getName()).append(" wins!");
        } else if (player2Power > player1Power) {
            result.append(player2Pokemon.getName()).append(" wins!");
        } else {
            result.append("It's a tie!");
        }

        resultArea.setText(result.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextBasedPokemonGameGUI::new);
    }
}