/**
 * player class
 */
public class Player
{
    //~ Fields ................................................................
    private int strikes;
    private int money;
    private ArrayList<Observer> observers;

    //~ Constructors ..........................................................
    /**
     * constructs a player
     */
    public Player() {
        strikes = 0;
        money = 0;
    }
    //~Public  Methods ........................................................
    /**
     * getter for strikes
     * @return strikes
     */
    public int getStrikes() {
        return strikes;
    }
    
    /**
     * getter for money
     * @return money
     */
    public int getMoney() {
        return money;
    }
    
    /**
     * adds a strike
     */
    public void addStrike() {
        if(!isGameOver()) {
            strikes++;
        }
    }
    
    /**
     * adds money
     * @param add 
     *      money added
     */
    public void addMoney(int add) {
        if(add < 0) {
            throw new IllegalArgumentException("Illegal argument");
        }
        money += add;
    }
    
    /**
     * checks for game over
     * @return whether 3 strikes have been hit or not
     */
    public boolean isGameOver() {
        return strikes >= 3;
    }
    
    /**
     * adds and observer
     */
    public void addObserver(Observer o) {
        observers.add(o);
    }
    
    /**
     * notifies the observers
     */
    private void notifyObservers() {
        for(Observer o : observers) {
            o.updatePlayer(strikes, money);
        }
    }
}
