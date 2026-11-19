/**
 * Coordinates the player, customer line, timing, and presentation.
 * Order validates recipe steps; Line remains only a FIFO customer queue.
 */
public class Controller
{
    private final Line line;
    private final Player player;
    private final View view;
    private boolean gameRunning;
    private long remainingMs;

    /**
     * Keeps the older constructor working for the console version and tests.
     */
    public Controller(Line line, Player player)
    {
        this(line, player, new ConsoleView());
    }

    public Controller(Line line, Player player, View view)
    {
        if (line == null || player == null || view == null)
        {
            throw new IllegalArgumentException("Line, Player, and View cannot be null");
        }

        this.line = line;
        this.player = player;
        this.view = view;
        gameRunning = false;
        remainingMs = 0;
    }

    /** Begins a shift and displays the first customer's order. */
    public void startGame()
    {
        if (gameRunning)
        {
            return;
        }
        gameRunning = true;
        resetTimer();
        view.showMessage("Welcome to Crazed Coffee!");
        onModelChanged();
    }

    private void resetTimer()
    {
        remainingMs = line.isEmpty() ? 0 : line.peek().getTimeLimit() * 1000L;
    }

    /** Processes a selected ingredient (or typed console input). */
    public void handleInput(String raw)
    {
        if (!gameRunning)
        {
            view.showMessage("Game is not running.");
            return;
        }
        if (raw == null || raw.trim().isEmpty())
        {
            view.showMessage("Not a valid response. Try again!");
            return;
        }
        if (line.isEmpty())
        {
            endGame();
            return;
        }

        Customer current = line.peek();
        Order order = current.getOrder();
        boolean correct = order.attemptStep(raw.trim().toLowerCase());

        if (!correct)
        {
            // Retains the current team's rule: an incorrect step loses a customer.
            line.dequeue();
            player.addStrike();
            view.showMessage("Wrong ingredient! Customer left. Strike added.");
            if (player.isGameOver())
            {
                endGame();
                return;
            }
            resetTimer();
        }
        else if (order.isComplete())
        {
            int tip = (int)(remainingMs / 2000L);
            player.addMoney(order.getCost() + tip);
            line.dequeue();
            view.showMessage("Order completed! Tip earned: $" + tip);
            resetTimer();
        }
        else
        {
            view.showMessage("Correct ingredient!");
        }

        onModelChanged();
    }

    /** Advances the current customer's countdown using actual elapsed time. */
    public void onTick(long elapsedMs)
    {
        if (elapsedMs < 0)
        {
            throw new IllegalArgumentException("Elapsed time cannot be negative");
        }
        if (!gameRunning || line.isEmpty())
        {
            return;
        }

        long elapsed = elapsedMs;
        boolean customerTimedOut = false;
        while (gameRunning && !line.isEmpty() && elapsed >= remainingMs)
        {
            elapsed -= remainingMs;
            line.dequeue();
            player.addStrike();
            customerTimedOut = true;
            view.showMessage("Customer ran out of time! Strike added.");
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
        if (customerTimedOut)
        {
            onModelChanged();
        }
        else
        {
            // The countdown needs repainting even if no customer timed out.
            view.showStatus(player.getStrikes(), player.getMoney(), remainingMs);
        }
    }

    /** Refreshes all visible model information. */
    public void onModelChanged()
    {
        if (!gameRunning)
        {
            return;
        }
        view.showStatus(player.getStrikes(), player.getMoney(), remainingMs);
        if (line.isEmpty())
        {
            endGame();
        }
        else
        {
            view.showOrder(line.peek());
        }
    }

    /** Ends the game once and tells the view to show the final screen. */
    public void endGame()
    {
        if (!gameRunning)
        {
            return;
        }
        gameRunning = false;
        view.showStatus(player.getStrikes(), player.getMoney(), remainingMs);
        view.showGameOver();
    }

    public boolean isGameRunning()
    {
        return gameRunning;
    }

    public long getRemainingMs()
    {
        return remainingMs;
    }
}
