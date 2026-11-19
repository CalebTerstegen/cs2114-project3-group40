import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JTabbedPane;

/** Swing view for Craze Coffee; gameplay decisions belong to Controller. */
public class GraphicalUI extends JFrame implements View
{
    private static final long serialVersionUID = 1L;

    private static final Color CREAM = new Color(248, 238, 220);
    private static final Color BROWN = new Color(93, 60, 37);
    private static final Color COFFEE = new Color(170, 121, 65);
    private static final Color WHITE = new Color(255, 251, 245);

    private final CardLayout cards;
    private final JPanel screens;
    private final Timer timer;

    private Controller controller;
    private long lastTickNanos;
    private int lastStrikes;
    private int lastMoney;

    private JLabel customerLabel;
    private JLabel orderLabel;
    private JLabel strikesLabel;
    private JLabel moneyLabel;
    private JLabel timeLabel;
    private JLabel messageLabel;
    private JLabel endHeading;
    private JLabel finalMoneyLabel;
    private JLabel instructionLabel;
    private JLabel ingredientsLabel;
    private JTextField ingredientField;
    private JTabbedPane gameTabs;

    /** Builds the welcome, gameplay, and results screens. */
    public GraphicalUI()
    {
        super("Crazed Coffee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 550);
        setMinimumSize(new Dimension(590, 500));
        setLocationRelativeTo(null);

        cards = new CardLayout();
        screens = new JPanel(cards);
        screens.add(buildWelcomeScreen(), "welcome");
        screens.add(buildGameScreen(), "game");
        screens.add(buildGameOverScreen(), "over");
        setContentPane(screens);
        cards.show(screens, "welcome");

        // Swing Timer fires on the event-dispatch thread, just like button clicks.
        timer = new Timer(100, event -> advanceTimer());
    }

    /** The controller is created after the window, resolving the MVC cycle. */
    public void setController(Controller controller)
    {
        if (controller == null)
        {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        this.controller = controller;
    }

    private JPanel buildWelcomeScreen()
    {
        JPanel panel = verticalPanel(CREAM);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 35, 30, 35));
        panel.add(Box.createVerticalGlue());
        panel.add(centered("CRAZED COFFEE", 33, BROWN));
        panel.add(Box.createVerticalStrut(35));
        panel.add(centered("Welcome, Barista!", 23, BROWN));
        panel.add(Box.createVerticalStrut(18));

        instructionLabel = centered("Serve drinks and beat the clock.", 18, BROWN);
        panel.add(instructionLabel);
        panel.add(Box.createVerticalStrut(12));
        panel.add(centered("Three strikes and your shift is over!", 16, BROWN));
        panel.add(Box.createVerticalStrut(12));
        panel.add(centered("Need a hint? Check the Ingredients tab during your shift.", 13, BROWN));
        panel.add(Box.createVerticalGlue());

        JButton start = actionButton("Start Game");
        start.setAlignmentX(CENTER_ALIGNMENT);
        start.setMaximumSize(new Dimension(220, 58));
        start.addActionListener(event -> startGameFromButton());
        panel.add(start);
        panel.add(Box.createVerticalStrut(25));
        return panel;
    }

    private JPanel buildGameScreen()
    {
        JPanel panel = new JPanel(new BorderLayout(14, 14));
        panel.setBackground(CREAM);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel north = new JPanel(new BorderLayout(8, 10));
        north.setOpaque(false);
        north.add(centered("CRAZED COFFEE", 28, BROWN), BorderLayout.NORTH);

        JPanel stats = new JPanel(new GridLayout(1, 3, 10, 0));
        stats.setOpaque(false);
        timeLabel = statusBox(stats, "Time: 30s");
        strikesLabel = statusBox(stats, "Strikes: 0/3");
        moneyLabel = statusBox(stats, "Money: $0");
        north.add(stats, BorderLayout.CENTER);
        panel.add(north, BorderLayout.NORTH);

        JPanel center = verticalPanel(WHITE);
        center.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(221, 196, 163)),
            BorderFactory.createEmptyBorder(22, 12, 18, 12)));
        center.add(Box.createVerticalStrut(4));
        center.add(centered("CURRENT CUSTOMER", 15, COFFEE));
        center.add(Box.createVerticalStrut(8));
        customerLabel = centered("Waiting to start", 25, BROWN);
        center.add(customerLabel);
        center.add(Box.createVerticalStrut(8));
        orderLabel = centered("Order: --", 19, BROWN);
        center.add(orderLabel);
        center.add(Box.createVerticalStrut(12));
        center.add(centered("Type each ingredient in the correct order", 13, BROWN));
        panel.add(center, BorderLayout.CENTER);

        JPanel south = verticalPanel(CREAM);
        gameTabs = new JTabbedPane();
        gameTabs.setFont(new Font("SansSerif", Font.BOLD, 14));

        JPanel makeDrinkPanel = new JPanel(new BorderLayout(8, 8));
        makeDrinkPanel.setBackground(CREAM);
        makeDrinkPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        makeDrinkPanel.add(centered("TYPE YOUR NEXT INGREDIENT", 15, BROWN), BorderLayout.NORTH);
        JPanel inputRow = new JPanel(new BorderLayout(10, 0));
        inputRow.setOpaque(false);
        ingredientField = new JTextField();
        ingredientField.setFont(new Font("SansSerif", Font.PLAIN, 19));
        ingredientField.setToolTipText("Type an ingredient, such as espresso, then press Enter");
        ingredientField.setPreferredSize(new Dimension(300, 48));
        ingredientField.addActionListener(event -> submitIngredient());
        inputRow.add(ingredientField, BorderLayout.CENTER);

        JButton submitButton = actionButton("Submit");
        submitButton.addActionListener(event -> submitIngredient());
        inputRow.add(submitButton, BorderLayout.EAST);
        makeDrinkPanel.add(inputRow, BorderLayout.CENTER);
        gameTabs.addTab("Make Drink", makeDrinkPanel);

        // This panel is only visible after the player selects the Ingredients tab.
        JPanel ingredientOptions = new JPanel(new BorderLayout(8, 8));
        ingredientOptions.setBackground(WHITE);
        ingredientOptions.setBorder(BorderFactory.createEmptyBorder(13, 10, 10, 10));
        ingredientsLabel = centered("Espresso  |  Milk  |  Foam  |  Water  |  Coffee", 16, BROWN);
        ingredientOptions.add(ingredientsLabel, BorderLayout.CENTER);
        ingredientOptions.add(
            centered("The countdown keeps running while this tab is open!", 12, COFFEE),
            BorderLayout.SOUTH);
        gameTabs.addTab("Ingredients", ingredientOptions);
        gameTabs.addChangeListener(event -> {
            if (gameTabs.getSelectedIndex() == 0 && controller != null
                && controller.isGameRunning())
            {
                SwingUtilities.invokeLater(() -> ingredientField.requestFocusInWindow());
            }
        });
        south.add(gameTabs);
        south.add(Box.createVerticalStrut(12));
        messageLabel = centered("Good luck!", 15, BROWN);
        south.add(messageLabel);
        panel.add(south, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildGameOverScreen()
    {
        JPanel panel = verticalPanel(CREAM);
        panel.setBorder(BorderFactory.createEmptyBorder(45, 25, 45, 25));
        panel.add(Box.createVerticalGlue());
        endHeading = centered("SHIFT COMPLETE", 31, BROWN);
        panel.add(endHeading);
        panel.add(Box.createVerticalStrut(25));
        finalMoneyLabel = centered("Final earnings: $0", 22, BROWN);
        panel.add(finalMoneyLabel);
        panel.add(Box.createVerticalStrut(16));
        panel.add(centered("Thanks for playing Crazed Coffee!", 17, BROWN));
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel verticalPanel(Color background)
    {
        JPanel panel = new JPanel();
        panel.setBackground(background);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    private JLabel centered(String text, int fontSize, Color color)
    {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Georgia", Font.PLAIN, fontSize));
        label.setForeground(color);
        label.setAlignmentX(CENTER_ALIGNMENT);
        return label;
    }

    private JLabel statusBox(JPanel container, String text)
    {
        JLabel label = centered(text, 16, BROWN);
        label.setOpaque(true);
        label.setBackground(WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        container.add(label);
        return label;
    }

    private JButton actionButton(String text)
    {
        JButton button = new JButton(text);
        button.setBackground(COFFEE);
        button.setForeground(Color.BLACK);
        button.setOpaque(true);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setFocusPainted(false);
        return button;
    }

    private void startGameFromButton()
    {
        if (controller == null)
        {
            JOptionPane.showMessageDialog(this, "The game is not initialized yet.");
            return;
        }
        cards.show(screens, "game");
        gameTabs.setSelectedIndex(0);
        controller.startGame();
        if (controller.isGameRunning())
        {
            lastTickNanos = System.nanoTime();
            timer.start();
            ingredientField.setEnabled(true);
            SwingUtilities.invokeLater(() -> ingredientField.requestFocusInWindow());
        }
    }

    private void submitIngredient()
    {
        if (controller != null && controller.isGameRunning())
        {
            // The player types the ingredient; the Controller still validates it.
            String ingredient = ingredientField.getText();
            advanceTimer();
            if (controller.isGameRunning())
            {
                controller.handleInput(ingredient);
            }
            ingredientField.setText("");
            ingredientField.requestFocusInWindow();
        }
    }

    private void advanceTimer()
    {
        if (controller == null || !controller.isGameRunning())
        {
            timer.stop();
            return;
        }
        long now = System.nanoTime();
        long elapsedMs = (now - lastTickNanos) / 1_000_000L;
        if (elapsedMs > 0)
        {
            lastTickNanos += elapsedMs * 1_000_000L;
            controller.onTick(elapsedMs);
        }
    }

    @Override
    public void showOrder(Customer customer)
    {
        customerLabel.setText(customer.getName());
        orderLabel.setText("Order: " + customer.getOrder().getName());
    }

    @Override
    public void showStatus(int strikes, int money, long msRemaining)
    {
        lastStrikes = strikes;
        lastMoney = money;
        strikesLabel.setText("Strikes: " + strikes + "/3");
        moneyLabel.setText("Money: $" + money);
        timeLabel.setText("Time: " + ((Math.max(0, msRemaining) + 999) / 1000) + "s");
    }

    @Override
    public void showInstructions()
    {
        instructionLabel.setText("Serve drinks and beat the clock.");
    }

    @Override
    public void showIngredients()
    {
        ingredientsLabel.setText("Espresso  |  Milk  |  Foam  |  Water  |  Coffee");
    }

    @Override
    public void showMessage(String message)
    {
        messageLabel.setText(message);
    }

    @Override
    public void showGameOver()
    {
        timer.stop();
        ingredientField.setEnabled(false);
        endHeading.setText(lastStrikes >= 3 ? "GAME OVER" : "SHIFT COMPLETE!");
        finalMoneyLabel.setText("Final earnings: $" + lastMoney);
        cards.show(screens, "over");
    }

    /** Eclipse can also launch this class directly. */
    public static void main(String[] args)
    {
        Main.main(args);
    }
}
