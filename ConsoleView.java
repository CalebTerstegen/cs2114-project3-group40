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
}

