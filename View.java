/**
 * Presentation operations shared by the console and graphical interfaces.
 * The model does not depend on this interface.
 */
public interface View
{
    void showOrder(Customer customer);

    void showStatus(int strikes, int money, long msRemaining);

    void showInstructions();

    void showIngredients();

    void showMessage(String message);

    void showGameOver();

    /**
     * Kept for compatibility with the old console game loop. A graphical
     * interface receives input from button clicks instead of blocking here.
     */
    default String readInput()
    {
        throw new UnsupportedOperationException(
            "Graphical input is handled with button actions");
    }
}
