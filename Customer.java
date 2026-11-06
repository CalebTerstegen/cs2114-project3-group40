public class Customer
{
    //~ Fields ................................................................
    private String name;
    private Order order;
    private int timeLimit;

    //~ Constructors ..........................................................
    /**
     * constructs a customer
     * @param
     */
    public Customer(String name, Order order, int timeLimit) {
        this.name = name;
        this.order = order;
        this.timeLimit = timeLimit;
    }

    //~Public  Methods ........................................................

}
