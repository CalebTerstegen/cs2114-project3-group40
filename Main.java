import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;

/** Starts the graphical version of Craze Coffee. */
public class Main
{
    private static final int TIME_LIMIT = 30;

    public  static void main(String[] args)
    {
        EventQueue.invokeLater(() -> {
            Line line = new Line();
            Player player = new Player();
            GraphicalUI view = new GraphicalUI();
            Controller controller = new Controller(line, player, view);
            view.setController(controller);

            populateLine(line);
            view.showInstructions();
            view.showIngredients();
            view.setVisible(true);
        });
    }

    /** Supplies the starting customers and recipes for either interface. */
    public static void populateLine(Line line)
    {
        if (line == null)
        {
            throw new IllegalArgumentException("Line cannot be null");
        }

        ArrayList<String> latte = new ArrayList<String>(
            Arrays.asList("espresso", "milk", "foam"));
        ArrayList<String> americano = new ArrayList<String>(
            Arrays.asList("espresso", "water"));
        ArrayList<String> redEye = new ArrayList<String>(
            Arrays.asList("coffee", "espresso"));

        // Four customers make it possible to test a three-strike game over.
        line.enqueue(new Customer("Sally", new Order("Latte", latte, 5), TIME_LIMIT));
        line.enqueue(new Customer("Tom", new Order("Americano", americano, 3), TIME_LIMIT));
        line.enqueue(new Customer("Ferb", new Order("Red Eye", redEye, 4), TIME_LIMIT));
        line.enqueue(new Customer("Aaron", new Order("Latte", latte, 5), TIME_LIMIT));
    }
}
