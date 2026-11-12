/**
 * // -------------------------------------------------------------------------
 * /** Controller for the Craze Coffee connects the customer line and player
 * 
 * @author sydneyyang
 * @version Nov 11, 2026
 */
public class Controller
{
    private Line line;
    private Player player;
    private boolean gameRunning;

    /**
     * Creates new controller
     * 
     * @param line
     *            the customer line
     * @param player
     *            new player
     */
    public Controller(Line line, Player player)
    {
        if (line == null || player == null)
        {
            throw new IllegalArgumentException(
                "Line and Player can't be null.");

        }
        this.line = line;
        this.player = player;
        gameRunning = false;
    }


    /**
     * starts game
     */
    public void startGame()
    {
        gameRunning = true;
        System.out.println("Welcome to Craze Coffee!");
    }


    /**
     * Player input
     * 
     * @param raw
     *            the ingredient entered
     */
    public void handleInput(String raw)
    {
        if (!gameRunning)
        {
            System.out.print("Game is not running.");
            return;
        }
        if (raw == null || raw.trim().isEmpty())
        {
            System.out.println("Not a valid response. Try again!");
            return;
        }
        if (line.isEmpty())
        {
            System.out.println("No customers in line");
            return;
        }
        String ingredient = raw.trim();
        Customer current = line.peek();

        StepResult result = line.submitStep(ingredient);

        switch (result)
        {
            case STEP_OK:
                System.out.println("Correct ingredient!");
                break;

            case ORDER_COMPLETE:
                player.addMoney(current.getOrder().getCost());
                line.dequeue();
                System.out.println("Order completed!");
                break;

            case WRONG_STEP:
                System.out.println("Wrong step! Try again.");
                break;

            default:
                System.out.println("Unknown result.");
                break;
        }

        onModelChanged();
    }


    /**
     * updates customer timer
     * 
     * @param elapsedMs
     *            elapsed time in milliseconds
     */
    public void onTick(long elapsedMs)
    {
        if (elapsedMs < 0)
        {
            throw new IllegalArgumentException(
                "Elapsed time can't be negative.");
        }
        if (!gameRunning || line.isEmpty())
        {
            return;
        }
        boolean timedOut = line.tick(elapsedMs);

        if (timedOut)
        {
            player.addStrike();
            System.out.println(
                "Customer ran out of time! Strike added.");

            if (player.isGameOver())
            {
                endGame();
                return;
            }
        }

        onModelChanged();
    }


    /**
     * dislays the current game state
     */
    public void onModelChanged()
    {
        if (!gameRunning)
        {
            return;
        }
        System.out.println("Strikes: " + player.getStrikes());
        System.out.println("Money: " + player.getMoney());

        if (line.isEmpty())
        {
            System.out.println("No customers waiting.");
        }
        else
        {
            Customer current = line.peek();
            System.out.println("Current customer: " + current.getName());
        }
    }


    /**
     * Ends game
     */
    public void endGame()
    {
        gameRunning = false;
        System.out.println("Game Over!");
        System.out.println("Final Amount: " + player.getMoney());
    }


    /**
     * Returns whether the game is running
     * 
     * @return true if running
     */
    public boolean isGameRunning()
    {
        return gameRunning;
    }
}
