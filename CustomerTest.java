import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/** Unit tests for Customer, based on the Crazed Coffee specification. */
public class CustomerTest {
    private Order latte;
    private Customer customer;

    @Before
    public void setUp() {
        latte = new Order("Latte",
            new ArrayList<String>(Arrays.asList("espresso", "milk")), 5);
        customer = new Customer("Alex", latte, 30);
    }

    @Test
    public void constructorAcceptsValidValues() {
        assertEquals("Alex", customer.getName());
        assertSame(latte, customer.getOrder());
        assertEquals(30, customer.getTimeLimit());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsEmptyName() {
        new Customer("", latte, 30);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsNullName() {
        new Customer(null, latte, 30);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsNullOrder() {
        new Customer("Alex", null, 30);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsZeroTimeLimit() {
        new Customer("Alex", latte, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsNegativeTimeLimit() {
        new Customer("Alex", latte, -1);
    }

    @Test
    public void getNameReturnsGivenName() {
        assertEquals("Alex", customer.getName());
    }

    @Test
    public void getNameSupportsOneCharacterName() {
        assertEquals("A", new Customer("A", latte, 30).getName());
    }

    @Test
    public void getOrderReturnsSameOrderObject() {
        assertSame(latte, customer.getOrder());
    }

    @Test
    public void getTimeLimitReturnsGivenTime() {
        assertEquals(30, customer.getTimeLimit());
    }

    @Test
    public void getTimeLimitSupportsMinimumPositiveTime() {
        assertEquals(1, new Customer("Alex", latte, 1).getTimeLimit());
    }
}
