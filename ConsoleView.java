import java.util.Scanner;

public class ConsoleView
    implements View
{
    //~ Fields ................................................................
    private Scanner scanner;
    //~ Constructors ..........................................................

    //~Public  Methods ........................................................

   public ConsoleView() {
       scanner = new Scanner(System.in);
       
   }
   
   public void showOrder(Customer custormer) {
       System.out.println();
       System.out.println("Customer: " + custormer.getName());
       System.out.println("Order: " + custormer.getOrder().getName());
   }
   
   public void showStatus(int strikes, int money, long msRemaining){
       System.out.println("Strikes: " + strikes + "/3   Money: $" + money
           + "   Time left: " + (msRemaining / 1000) + " seconds");
       
   }
   
   public void showMessage(String msg)
   {
       System.out.println(msg);
   }
   
   public void showGameOver()
   {
       System.out.println();
       System.out.println("GAME OVER");
   }
   
   public String readInput()
   {
       System.out.print("> ");
       return scanner.nextLine();
   }
   
   /**
    * shows the game instructions
    */
   public void showInstructions() {
       System.out.println("How to play:");
       System.out.println("- Each customer orders a drink.");
       System.out.println("- Type the steps of the recipe, one at a time.");
       System.out.println("- A wrong step or running out of time gives a strike.");
       System.out.println("- 3 strikes and the game is over.");
       System.out.println("- Serve orders correctly to earn money.");
       System.out.println();
   }
   
   /**
    * shows the ingredients the player can use
    */
   public void showIngredients() {
       System.out.println("Ingredients: espresso, milk, water, foam");
       System.out.println();
   }
}

