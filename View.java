public interface View
{
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................

    /**
     * Shows the customer currently at the front of the line.
     *
     * @param c
     *            the customer to show
     */

    void showOrder(Customer customer);


    /**
     * Shows the player's current status.
     *
     * @param strikes
     *            strikes so far
     * @param money
     *            money earned so far
     * @param msRemaining
     *            milliseconds left for the current customer
     */
    /**
     * shows the game instructions
     */
    void showInstructions();


    /**
     * shows the ingredients the player can use
     */
    void showIngredients();


    void showStatus(int strikes, int money, long msRemaining);


    /**
     * Shows a feedback message, such as a wrong step or an angry customer.
     *
     * @param msg
     *            the message to show
     */

    void showMessage(String msg);


    /**
     * Shows the game over message.
     */

    void showGameOver();


    /**
     * Reads one line of input from the player.
     *
     * @return the raw text the player typed
     */

    String readInput();

}
