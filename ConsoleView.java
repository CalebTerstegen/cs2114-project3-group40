import java.util.Scanner;

/** Console implementation of the same presentation interface as GraphicalUI. */
public class ConsoleView implements View
{
    private final Scanner scanner;

    public ConsoleView()
    {
        scanner = new Scanner(System.in);
    }

    @Override
    public void showOrder(Customer customer)
    {
        System.out.println();
        System.out.println("Customer: " + customer.getName());
        System.out.println("Order: " + customer.getOrder().getName());
    }

    @Override
    public void showStatus(int strikes, int money, long msRemaining)
    {
        System.out.println("Strikes: " + strikes + "/3   Money: $" + money
            + "   Time left: " + ((Math.max(0, msRemaining) + 999) / 1000) + " seconds");
    }

    @Override
    public void showInstructions()
    {
        System.out.println("Welcome Barista! Serve drinks and beat the clock.");
        System.out.println("Three strikes and your shift is over!");
    }

    @Override
    public void showIngredients()
    {
        System.out.println("Available ingredients: espresso, milk, foam, water, coffee");
    }

    @Override
    public void showMessage(String message)
    {
        System.out.println(message);
    }

    @Override
    public void showGameOver()
    {
        System.out.println("GAME OVER");
    }

    @Override
    public String readInput()
    {
        System.out.print("> ");
        return scanner.nextLine();
    }
}
