import java.util.ArrayList;
import java.util.Arrays;
public class Main
{
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    private static final int TIME_LIMIT = 30;
    // ~Public Methods ........................................................

    public static void main(String[] args)
    {

        Line line = new Line();
        Player player = new Player();
        View view = new ConsoleView();
        view.showInstructions();
        view.showIngredients();
        
        Controller controller = new Controller(line, player);
        
        ArrayList<String> latte = new ArrayList<String>(
            Arrays.asList("espresso", "milk", "foam"));
        ArrayList<String> americano = new ArrayList<String>(
            Arrays.asList("espresso", "water"));
        ArrayList<String> redEye = new ArrayList<String>(
            Arrays.asList("coffee", "espresso"));
        
        line.enqueue(
            new Customer("Sally", new Order("Latte", latte, 5), TIME_LIMIT));
        line.enqueue(
            new Customer(
                "Tom",
                new Order("Americano", americano, 3),
                TIME_LIMIT));
        line.enqueue(
            new Customer("Ferb", new Order("Latte", latte, 5), TIME_LIMIT));
        line.enqueue(
            new Customer(
                "Aaron",
                new Order("Americano", americano, 3),
                TIME_LIMIT));

        runGame(controller, line, player, view);
    }


    public static void runGame(
        Controller controller,
        Line line,
        Player player,
        View view)
    {
        controller.startGame();
        long newCustomerTime = System.currentTimeMillis();
        long last = System.currentTimeMillis();

        if (!line.isEmpty())
        {
            view.showOrder(line.peek());
        }

        while (!player.isGameOver() && !line.isEmpty())
        {
            if(newCustomerTime-System.currentTimeMillis() >= 10000*(Math.random()+1)) {
                line.newCustomer();
                System.out.println("New customer!");
                newCustomerTime = System.currentTimeMillis();
            }
            String input = view.readInput();
            long now = System.currentTimeMillis();
                
            controller.onTick(now - last);
            last = now;

            if (player.isGameOver() || line.isEmpty())
            {
                break;
            }
            controller.handleInput(input);
        }

        if (controller.isGameRunning())
        {
            controller.endGame();
        }
    }
}
