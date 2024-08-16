import javax.swing.*; // Importing Swing components for GUI
import java.awt.*; // Importing AWT components for layout and dimension handling
import java.awt.image.BufferedImage; // Importing BufferedImage for image handling
import java.io.IOException; // Importing IOException for handling input/output exceptions
import javax.imageio.ImageIO; // Importing ImageIO for reading images

public class CustomGUI extends JFrame {
    // Declaring all the panels used in the GUI
    private JPanel mainPanel;
    private ImagePanel middlePanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel leftTopPanel;
    private JPanel leftBottomPanel;
    private JPanel rightTopPanel;
    private JPanel rightBottomPanel;
    private JPanel leftSquarePanel;
    private JPanel rightSquarePanel;

    public CustomGUI() {
        setTitle("Pokemon"); // Setting the title of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Setting the default close operation
        setLayout(new BorderLayout()); // Setting the layout manager for the frame

        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width; // Getting the screen width
        int screenHeight = screenSize.height; // Getting the screen height

        // Set the size of the frame to the screen size
        setSize(screenWidth, screenHeight);

        // Calculate proportional dimensions
        int middlePanelWidth = screenWidth / 2; // Middle panel width is half of the screen width
        int middlePanelHeight = screenHeight; // Middle panel height is the full screen height
        int sidePanelWidth = screenWidth / 4; // Side panel width is a quarter of the screen width
        int sidePanelHeight = screenHeight; // Side panel height is the full screen height
        
        // Calculate the size of the square panels to ensure a 5cm gap between them
        int dpi = Toolkit.getDefaultToolkit().getScreenResolution(); // Get screen resolution in DPI
        int gapInPixels = (int) (5 * dpi / 2.54); // Convert 5cm to pixels
        int squarePanelSize = (middlePanelHeight - gapInPixels) / 2; // Calculate the size of the square panels

        // Initializing all the panels
        mainPanel = new JPanel(new BorderLayout());
        middlePanel = new ImagePanel("/pictures/Arena.jpg"); // Custom panel to display background image
        middlePanel.setLayout(null); // Use null layout to manually position components
        leftPanel = new JPanel(new GridLayout(2, 1));
        rightPanel = new JPanel(new GridLayout(2, 1));
        leftTopPanel = new JPanel();
        leftBottomPanel = new JPanel();
        rightTopPanel = new JPanel();
        rightBottomPanel = new JPanel();
        leftSquarePanel = new JPanel();
        rightSquarePanel = new JPanel();

        // Setting the preferred size for the middle panel
        middlePanel.setPreferredSize(new Dimension(middlePanelWidth, middlePanelHeight));
        // Setting the bounds for the left square panel
        leftSquarePanel.setBounds(0, middlePanelHeight / 2 - squarePanelSize / 2, squarePanelSize, squarePanelSize);
        // Setting the bounds for the right square panel
        rightSquarePanel.setBounds(middlePanelWidth - squarePanelSize, middlePanelHeight / 2 - squarePanelSize / 2, squarePanelSize, squarePanelSize);

        // Setting the preferred size for the left and right panels
        leftPanel.setPreferredSize(new Dimension(sidePanelWidth, sidePanelHeight));
        rightPanel.setPreferredSize(new Dimension(sidePanelWidth, sidePanelHeight));

        // Adding the top and bottom panels to the left panel
        leftPanel.add(leftTopPanel);
        leftPanel.add(leftBottomPanel);
        // Adding the top and bottom panels to the right panel
        rightPanel.add(rightTopPanel);
        rightPanel.add(rightBottomPanel);

        // Adding the square panels to the middle panel
        middlePanel.add(leftSquarePanel);
        middlePanel.add(rightSquarePanel);

        // Adding the left, middle, and right panels to the main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Adding the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
    }

    // Custom JPanel class to display an image as background
    class ImagePanel extends JPanel {
        private BufferedImage image;

        public ImagePanel(String imagePath) {
            try {
                image = ImageIO.read(getClass().getResource(imagePath));
                if (image == null) {
                    throw new IOException("Image not found: " + imagePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // Method to add and resize an image to fit a panel
    public void addImageToPanel(JPanel panel, String resourcePath) {
        try {
            // Load the image as a BufferedImage
            BufferedImage originalImage = ImageIO.read(getClass().getResource(resourcePath));
            if (originalImage == null) {
                throw new IOException("Image not found: " + resourcePath);
            }
            
            // Get the dimensions of the panel
            int panelWidth = panel.getWidth();
            int panelHeight = panel.getHeight();
            
            // Resize the image to fit the panel
            Image resizedImage = originalImage.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
            
            // Create an ImageIcon from the resized image
            ImageIcon imageIcon = new ImageIcon(resizedImage);
            
            // Create a JLabel with the ImageIcon
            JLabel label = new JLabel(imageIcon);
            
            // Add the JLabel to the panel
            panel.add(label);
            
            // Refresh the panel to display the image
            panel.revalidate();
            panel.repaint();
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions
        }
    }

    public static void main(String[] args) {
        CustomGUI gui = new CustomGUI(); // Create an instance of the CustomGUI class
        gui.setVisible(true); // Make the GUI visible

        // Example of adding images to panels
        gui.addImageToPanel(gui.leftTopPanel, "/pictures/Ash.jpg");
        gui.addImageToPanel(gui.leftBottomPanel, "/pictures/Pokemon_Pikachu_Ash.jpg");
        gui.addImageToPanel(gui.rightTopPanel, "/pictures/Cynthia.jpg");
        gui.addImageToPanel(gui.rightBottomPanel, "/pictures/Pokemon_Garchomp_Cynthia.jpg");
        gui.addImageToPanel(gui.leftSquarePanel, "/pictures/Pokemon_Greninja_Ash.jpg");
        gui.addImageToPanel(gui.rightSquarePanel, "/pictures/Pokemon_ Lucario_Cynthia.jpg");
		
    }
}


