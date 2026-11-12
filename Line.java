import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * line class
 */
public class Line
{
    private static class Node<E>
    {
        private Node<E> next;
        private E data;

        /**
         * Creates a new node with the given data
         *
         * @param d
         *            the data to put inside the node
         */
        public Node(E d)
        {
            data = d;
        }

        /**
         * Sets the node after this node
         *
         * @param n
         *            the node after this one
         */
        public void setNext(Node<E> n)
        {
            next = n;
        }

        /**
         * Gets the next node
         *
         * @return the next node
         */
        public Node<E> next()
        {
            return next;
        }

        /**
         * Gets the data in the node
         *
         * @return the data in the node
         */
        public E getData()
        {
            return data;
        }
    }
    //~ Fields ................................................................
    private Node<Customer> front;
    private Node<Customer> back;
    private int size;
    private ArrayList<Observer> observers;

    //~ Constructors ..........................................................
    /**
     * constructs a new line
     */
    public Line() {
        size = 0;
        observers = new ArrayList<Observer>();
    }

    //~Public  Methods ........................................................
    /**
     * getter for size
     * @return size
     */
    public int size() {
        return size;
    }
    
    /**
     * checks if empty
     * @return empty or not
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * adds a customer to the back
     */
    public void enqueue(Customer c) {
        if(c == null) {
            throw new IllegalArgumentException("Illegal argument");
        }
        Node<Customer> n = new Node<Customer>(c);
        if(isEmpty()) {
            
            front = n;
        }
        else {
            back.setNext(n);
        }
        
        back = n;
        size++;
        notifyObservers();
    }

    
    /**
     * removes a customer from the front
     * @return returns the front customer
     */
    public Customer dequeue() {
        if(isEmpty()) {
            throw new NoSuchElementException("Empty line");
        }
        Customer out;
        out = front.getData();
        front = front.next;
        if(front == null) {
            back = null;
        }
        size --;
        notifyObservers();
        return out;
    }
    
    /**
     * checks the front of the line without removal
     * @return returns the front customer
     */
    public Customer peek() {
        if(isEmpty()) {
            throw new NoSuchElementException("Empty line");
        }
        return front.getData();
    }
    
    /**
     * adds and observer
     */
    public void addObserver(Observer o) {
        if (o == null) {
            throw new IllegalArgumentException("Illegal argument");
        }
        observers.add(o);
    }
    
    /**
     * notifies the observers
     */
    private void notifyObservers() {
        for (Observer o : observers) {
            o.updateLine();
        }
    }

}
