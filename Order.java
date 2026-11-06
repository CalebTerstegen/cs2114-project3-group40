import java.util.ArrayList;

public class Order
{
    //~ Fields ................................................................
    private String name;
    private ArrayList<String> ingredients;
    private int cost;
    private int step;

    //~ Constructors ..........................................................
    /**
     * Constructs a full order
     * 
     * The name of the order
     * @param name
     *      The name of the order
     * @param ingredients
     *      The ingredients in the order
     * @param cost
     *      The cost of the order
     *      
     */
    public Order(String name, ArrayList<String> ingredients, int cost) {
        if(name == null || name.equals("") || ingredients == null || ingredients.isEmpty() || cost < 0) {
            throw new IllegalArgumentException("One or more parameters are invalid");
        }
        this.name = name;
        this.ingredients = ingredients;
        this.cost = cost;
        step = 0;
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
     * getter for ingredients
     * @return returns the ingredients
     */
    public ArrayList<String> getIngredients(){
        return ingredients;
    }
    
    /**
     * getter for cost
     * @return returns the cost
     */
    public int getCost() {
        return cost;
    }
    
    /**
     * attempts an ingredient/step of order
     * @param ingredient
     *      the ingredient being attempted and compared against the order
     * @return returns whether the attempt at the ingredient was successful
     */
    public boolean attemptStep(String ingredient) {
        if(ingredient == null || ingredient.equals("") || !ingredient.equals(ingredients.get(step))) {
            resetProgress();
            return false;
        }
        if(step >= ingredients.size()) {
            return false;
        }
        step++;
        return true;
    }
    
    /**
     * resets the step counter
     */
    public void resetProgress() {
        step = 0;
    }
    
    /**
     * checks if order is complete
     * @return returns true or false depending on if the order is complete
     */
    public boolean isComplete() {
        return step >= ingredients.size();
    }
    
}
