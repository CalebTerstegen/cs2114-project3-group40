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
    private long remainingMs;

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
     * resets the timer for the current customer
     */
    private void resetTimer()
    {
        if (line.isEmpty())
        {
            remainingMs = 0;
        }
        else
        {
            remainingMs = line.peek().getTimeLimit() * 1000L;
        }
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
        Order order = current.getOrder();

        boolean correct = order.attemptStep(ingredient);

        if (!correct)
        {
            System.out.println("Wrong ingredient! Progress reset.");
        }
        else if (order.isComplete())
        {
            player.addMoney(order.getCost());
            line.dequeue();

            System.out.println("Order completed!");

            resetTimer();
        }
        else
        {
            System.out.println("Correct ingredient!");
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
                "Elapsed time cannot be negative.");
        }

        if (!gameRunning || line.isEmpty())
        {
            return;
        }

        long elapsed = elapsedMs;

        while (gameRunning && !line.isEmpty() && elapsed >= remainingMs)
        {
            elapsed -= remainingMs;

            line.dequeue();
            player.addStrike();

            System.out.println("Customer ran out of time! Strike added.");

            if (player.isGameOver())
            {
                endGame();
                return;
            }

            resetTimer();
        }

        if (!line.isEmpty())
        {
            remainingMs -= elapsed;
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
            System.out.println("Order: " + current.getOrder().getName());
            System.out.println(
                "Time remaining: " + ((remainingMs + 999) / 1000) + " seconds");
        }
    }


    /**
     * ends game 
     */
    public void endGame()
    {
        if (!gameRunning)
        {
            return;
        }

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


    /**
     * Returns the remaining time
     * 
     * @return remaining milliseconds
     */
    public long getRemainingMs()
    {
        return remainingMs;
    }
}
