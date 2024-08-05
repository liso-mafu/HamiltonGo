import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PokemonGame extends JFrame implements ActionListener {
    private JLabel player1Label;
    private JLabel player2Label;
    private JButton startButton;
    private JTextArea resultArea;

    private String[] pokemons = {"charizard", "pikachu", "drago", "ivysaur", "green ninja"};

    public PokemonGame() {
        setTitle("Pokemon Battle");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        player1Label = new JLabel("Player 1: ");
        player2Label = new JLabel("Player 2: ");
        startButton = new JButton("Start Battle!");
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        add(player1Label);
        add(player2Label);
        add(startButton);
        add(resultArea);

        startButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            Random rand = new Random();

            int player1Power = rand.nextInt(10);
            int player2Power = rand.nextInt(10);

            String player1Pokemon = pokemons[rand.nextInt(5)];
            String player2Pokemon = pokemons[rand.nextInt(5)];

            player1Label.setText("Player 1: " + player1Pokemon);
            player2Label.setText("Player 2: " + player2Pokemon);

            if (player1Power > player2Power) {
                resultArea.setText("Player 1 wins with a power level of " + player1Power + " using " + player1Pokemon + "!");
            } else if (player2Power > player1Power) {
                resultArea.setText("Player 2 wins with a power level of " + player2Power + " using " + player2Pokemon + "!");
            } else {
                resultArea.setText("It's a tie! Both players used " + player1Pokemon + "!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PokemonGame game = new PokemonGame();
            game.setVisible(true);
        });
    }
}