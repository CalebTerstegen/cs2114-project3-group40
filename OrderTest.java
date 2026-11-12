import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/** Unit tests for the Order recipe and step-validation logic. */
public class OrderTest {
    private Order latte;

    private ArrayList<String> recipe(String... items) {
        return new ArrayList<String>(Arrays.asList(items));
    }

    @Before
    public void setUp() {
        latte = new Order("Latte", recipe("espresso", "milk", "foam"), 5);
    }

    @Test
    public void constructorAcceptsValidValues() {
        assertEquals("Latte", latte.getName());
        assertEquals(recipe("espresso", "milk", "foam"), latte.getIngredients());
        assertEquals(5, latte.getCost());
        assertFalse(latte.isComplete());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsNullName() {
        new Order(null, recipe("espresso"), 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsEmptyName() {
        new Order("", recipe("espresso"), 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsNullIngredients() {
        new Order("Latte", null, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsEmptyIngredients() {
        new Order("Latte", recipe(), 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsNegativeCost() {
        new Order("Latte", recipe("espresso"), -1);
    }

    @Test
    public void getNameReturnsName() {
        assertEquals("Latte", latte.getName());
    }

    @Test
    public void getIngredientsReturnsIngredientsInOrder() {
        assertEquals(recipe("espresso", "milk", "foam"), latte.getIngredients());
    }

    @Test
    public void getIngredientsSupportsSingleStepRecipe() {
        Order simple = new Order("Espresso", recipe("espresso"), 3);
        assertEquals(recipe("espresso"), simple.getIngredients());
    }

    @Test
    public void getCostReturnsCost() {
        assertEquals(5, latte.getCost());
    }

    @Test
    public void getCostAllowsZero() {
        assertEquals(0, new Order("Free sample", recipe("water"), 0).getCost());
    }

    @Test
    public void attemptStepAcceptsCorrectNextIngredient() {
        assertTrue(latte.attemptStep("espresso"));
        assertTrue(latte.attemptStep("milk"));
        assertFalse(latte.isComplete());
    }

    @Test
    public void attemptStepRejectsWrongOrderAndResetsProgress() {
        assertTrue(latte.attemptStep("espresso"));
        assertFalse(latte.attemptStep("foam")); // Wrong second step
        assertFalse(latte.isComplete());
        // After a reset, the first ingredient must be required again.
        assertFalse(latte.attemptStep("milk"));
        assertTrue(latte.attemptStep("espresso"));
    }

    @Test
    public void attemptStepRejectsNullAndResetsProgress() {
        assertTrue(latte.attemptStep("espresso"));
        assertFalse(latte.attemptStep(null));
        assertFalse(latte.attemptStep("milk")); // Cannot skip espresso after reset
        assertTrue(latte.attemptStep("espresso"));
    }

    @Test
    public void attemptStepRejectsEmptyIngredient() {
        assertFalse(latte.attemptStep(""));
        assertTrue(latte.attemptStep("espresso"));
    }

    @Test
    public void attemptStepAfterCompletionDoesNotCrash() {
        assertTrue(latte.attemptStep("espresso"));
        assertTrue(latte.attemptStep("milk"));
        assertTrue(latte.attemptStep("foam"));
        // The spec doesn't define the return value for an extra step, but
        // the game should not crash or lose the completed state.
        latte.attemptStep("espresso");
        assertTrue(latte.isComplete());
    }

    @Test
    public void resetProgressRestartsPartiallyCompletedRecipe() {
        assertTrue(latte.attemptStep("espresso"));
        latte.resetProgress();
        assertFalse(latte.isComplete());
        assertFalse(latte.attemptStep("milk"));
        assertTrue(latte.attemptStep("espresso"));
    }

    @Test
    public void resetProgressOnFreshOrderIsSafe() {
        latte.resetProgress();
        assertFalse(latte.isComplete());
        assertTrue(latte.attemptStep("espresso"));
    }

    @Test
    public void isCompleteTrueOnlyAfterFinalStep() {
        assertFalse(latte.isComplete());
        latte.attemptStep("espresso");
        assertFalse(latte.isComplete());
        latte.attemptStep("milk");
        assertFalse(latte.isComplete());
        latte.attemptStep("foam");
        assertTrue(latte.isComplete());
    }

    @Test
    public void isCompleteFalseForNewOrder() {
        assertFalse(latte.isComplete());
    }
}
