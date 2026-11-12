import static org.junit.Assert.*;

import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Before;
import org.junit.Test;

/** Unit tests for Player's score, strikes, and observers. */
public class PlayerTest {
    private Player player;

    @Before
    public void setUp() {
        player = new Player();
    }

    @Test
    public void constructorStartsWithZeroMoneyAndStrikes() {
        assertEquals(0, player.getStrikes());
        assertEquals(0, player.getMoney());
        assertFalse(player.isGameOver());
    }

    @Test
    public void constructorNeverStartsWithNegativeValues() {
        assertTrue(player.getStrikes() >= 0);
        assertTrue(player.getMoney() >= 0);
    }

    @Test
    public void addStrikeIncreasesStrikesByOne() {
        player.addStrike();
        assertEquals(1, player.getStrikes());
    }

    @Test
    public void addStrikeDoesNotGoAboveThree() {
        player.addStrike();
        player.addStrike();
        player.addStrike();
        player.addStrike(); // Should have no further effect
        assertEquals(3, player.getStrikes());
    }

    @Test
    public void getStrikesReturnsCurrentCount() {
        player.addStrike();
        player.addStrike();
        assertEquals(2, player.getStrikes());
    }

    @Test
    public void getStrikesIsZeroInitially() {
        assertEquals(0, player.getStrikes());
    }

    @Test
    public void isGameOverAfterThirdStrike() {
        player.addStrike();
        player.addStrike();
        player.addStrike();
        assertTrue(player.isGameOver());
    }

    @Test
    public void isGameOverFalseWithTwoStrikes() {
        player.addStrike();
        player.addStrike();
        assertFalse(player.isGameOver());
    }

    @Test
    public void addMoneyIncreasesTotal() {
        player.addMoney(10);
        player.addMoney(5);
        assertEquals(15, player.getMoney());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMoneyRejectsNegativeAmount() {
        player.addMoney(-1);
    }

    @Test
    public void addMoneyAllowsZeroWithoutChangingTotal() {
        player.addMoney(10);
        player.addMoney(0);
        assertEquals(10, player.getMoney());
    }

    @Test
    public void getMoneyReturnsCurrentTotal() {
        player.addMoney(20);
        assertEquals(20, player.getMoney());
    }

    @Test
    public void getMoneyIsZeroInitially() {
        assertEquals(0, player.getMoney());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addObserverRejectsNull() {
        player.addObserver(null);
    }

    @Test
    public void addObserverNotifiedWhenStrikesChange() {
        AtomicInteger count = new AtomicInteger();
        Observer observer = recordingObserver(count);
        player.addObserver(observer);
        player.addStrike();
        assertEquals("A player state change should notify observers", 1, count.get());
    }

    @Test
    public void addObserverNotifiedWhenMoneyChanges() {
        AtomicInteger count = new AtomicInteger();
        player.addObserver(recordingObserver(count));
        player.addMoney(5);
        assertEquals("A player state change should notify observers", 1, count.get());
    }

    /**
     * Observer source wasn't supplied. A dynamic proxy records update calls
     * without guessing which method parameters the Observer interface uses.
     * This assumes your team's Observer is a Java interface.
     */
    private Observer recordingObserver(AtomicInteger count) {
        return (Observer) Proxy.newProxyInstance(
            Observer.class.getClassLoader(),
            new Class<?>[] { Observer.class },
            (proxy, method, args) -> {
                if (method.getName().startsWith("update")
                    || method.getName().equals("onModelChanged")) {
                    count.incrementAndGet();
                }
                return null;
            });
    }
}
