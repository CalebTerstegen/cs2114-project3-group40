/**
 * customer class
 */
public class Customer
{
    //~ Fields ................................................................
    private String name;
    private Order order;
    private int timeLimit;

    //~ Constructors ..........................................................
    /**
     * constructs a customer
     * @param name
     * @param order
     * @param timeLimit
     */
    public Customer(String name, Order order, int timeLimit)
    {
        if (name == null || name.equals("") || order == null
            || timeLimit <= 0)
        {
            throw new IllegalArgumentException("Illegal argument");
        }

        this.name = name;
        this.order = order;
        this.timeLimit = timeLimit;
    }

    //~Public  Methods ........................................................
    /**
     * getter for name
     * @return returns name
     */
    public String getName() {
        return name;
    }
    
    /**
     * getter for order
     * @return returns the order
     */
    public Order getOrder() {
        return order;
    }
    
    /**
     * getter for the time limit
     * @return returns time limit
     */
    public int getTimeLimit() {
        return timeLimit;
    }
    
    
    
}
