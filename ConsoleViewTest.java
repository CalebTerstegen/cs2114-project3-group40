import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
 
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
public class ConsoleViewTest
{
    //~ Fields ................................................................
    private PrintStream ogOut;
    private InputStream ogIn;
    private ByteArrayOutputStream captured;
    
    //~ Constructors ..........................................................

    @Before
    public void setUp() {
        ogOut = System.out;
        ogIn = System.in;
        captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));  
    }
    
    @After
    public void tearDown() {
        System.setOut(ogOut);
        System.setIn(ogIn);
    }
    
    private ConsoleView viewWithInput(String typed)
    {
        System.setIn(new ByteArrayInputStream(typed.getBytes()));
        return new ConsoleView();
    }
 
    private String output()
    {
        return captured.toString();
    }
    //~Public  Methods ........................................................
    
    @Test
    public void testShowMessage()
    {
        ConsoleView view = viewWithInput("");
        view.showMessage("You messed up the order.");
        assertTrue(output().contains("You messed up the order."));
    }
    
    /**
     * showMessage works with an empty message and does not crash.
     */
    @Test
    public void testShowMessageEmpty()
    {
        ConsoleView view = viewWithInput("");
        view.showMessage("");
        assertEquals(System.lineSeparator(), output());
    }
 
    /**
     * showGameOver prints the game over banner.
     */
    @Test
    public void testShowGameOver()
    {
        ConsoleView view = viewWithInput("");
        view.showGameOver();
        assertTrue(output().contains("GAME OVER"));
    }
 
    /**
     * showStatus prints strikes, money and the time converted to seconds.
     */
    @Test
    public void testShowStatus()
    {
        ConsoleView view = viewWithInput("");
        view.showStatus(2, 15, 12000);
        String out = output();
        assertTrue(out.contains("Strikes: 2/3"));
        assertTrue(out.contains("Money: $15"));
        assertTrue(out.contains("Time left: 12 seconds"));
    }
 
    /**
     * Boundary: a brand new game with zero everything.
     */
    @Test
    public void testShowStatusZeros()
    {
        ConsoleView view = viewWithInput("");
        view.showStatus(0, 0, 0);
        String out = output();
        assertTrue(out.contains("Strikes: 0/3"));
        assertTrue(out.contains("Money: $0"));
        assertTrue(out.contains("Time left: 0 seconds"));
    }
 
    /**
     * Boundary: less than one second left rounds down to 0 seconds.
     */
    @Test
    public void testShowStatusRoundsDown()
    {
        ConsoleView view = viewWithInput("");
        view.showStatus(1, 5, 999);
        assertTrue(output().contains("Time left: 0 seconds\""));
    }
 
    /**
     * showOrder prints the customer's name and the drink name.
     */
    @Test
    public void testShowOrder()
    {
        ConsoleView view = viewWithInput("");
        ArrayList<String> steps = new ArrayList<String>(
            Arrays.asList("espresso", "milk", "foam"));
        Customer sally = new Customer("Sally", new Order("Latte", steps, 5),
            30);
        view.showOrder(sally);
        String out = output();
        assertTrue(out.contains("Sally"));
        assertTrue(out.contains("Latte"));
    }
 
    /**
     * showOrder does not reveal the recipe steps to the player.
     */
    @Test
    public void testShowOrderHidesIngredients()
    {
        ConsoleView view = viewWithInput("");
        ArrayList<String> steps = new ArrayList<String>(
            Arrays.asList("espresso", "milk", "foam"));
        Customer sally = new Customer("Sally", new Order("Latte", steps, 5),
            30);
        view.showOrder(sally);
        assertTrue(!output().contains("espresso"));
    }
 
    /**
     * readInput returns what the player typed, without the line ending.
     */
    @Test
    public void testReadInput()
    {
        ConsoleView view = viewWithInput("espresso\n");
        assertEquals("espresso", view.readInput());
    }
 
    /**
     * readInput prints a prompt before reading.
     */
    @Test
    public void testReadInputPrintsPrompt()
    {
        ConsoleView view = viewWithInput("milk\n");
        view.readInput();
        assertTrue(output().contains(">"));
    }
 
    /**
     * Each call to readInput returns the next line.
     */
    @Test
    public void testReadInputMultipleLines()
    {
        ConsoleView view = viewWithInput("espresso\nmilk\n");
        assertEquals("espresso", view.readInput());
        assertEquals("milk", view.readInput());
    }
 
    /**
     * Boundary: a blank line comes back as an empty string. Deciding that it
     * is invalid input is the Controller's job, not the view's.
     */
    @Test
    public void testReadInputBlankLine()
    {
        ConsoleView view = viewWithInput("\n");
        assertEquals("", view.readInput());
    }
}


