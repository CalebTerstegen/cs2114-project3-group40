import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

/** JUnit 4 tests for the linked FIFO customer queue and observer updates. */
public class LineTest
{
    private Line line;
    private Customer alex;
    private Customer sam;

    @Before
    public void setUp()
    {
        line = new Line();
        alex = customer("Alex", 30);
        sam = customer("Sam", 60);
    }

    private Customer customer(String name, int timeLimit)
    {
        Order order = new Order("Latte",
            new ArrayList<String>(Arrays.asList("espresso", "milk")), 5);
        return new Customer(name, order, timeLimit);
    }

    @Test
    public void constructorStartsWithEmptyLine()
    {
        assertEquals(0, line.size());
        assertTrue(line.isEmpty());
    }

    @Test
    public void enqueueAddsCustomersInFifoOrder()
    {
        line.enqueue(alex);
        line.enqueue(sam);

        assertEquals(2, line.size());
        assertSame(alex, line.dequeue());
        assertSame(sam, line.dequeue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void enqueueRejectsNull()
    {
        line.enqueue(null);
    }

    @Test
    public void dequeueRemovesFirstCustomerAndUpdatesSize()
    {
        line.enqueue(alex);
        line.enqueue(sam);

        assertSame(alex, line.dequeue());
        assertEquals(1, line.size());
        assertSame(sam, line.peek());
    }

    @Test(expected = NoSuchElementException.class)
    public void dequeueRejectsEmptyLine()
    {
        line.dequeue();
    }

    @Test
    public void dequeueLastCustomerLeavesEmptyLine()
    {
        line.enqueue(alex);
        assertSame(alex, line.dequeue());

        assertTrue(line.isEmpty());
        assertEquals(0, line.size());
    }

    @Test
    public void peekReturnsFrontWithoutRemoving()
    {
        line.enqueue(alex);

        assertSame(alex, line.peek());
        assertSame(alex, line.peek());
        assertEquals(1, line.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void peekRejectsEmptyLine()
    {
        line.peek();
    }

    @Test
    public void isEmptyFalseAfterEnqueue()
    {
        line.enqueue(alex);
        assertFalse(line.isEmpty());
    }

    @Test
    public void isEmptyTrueInitially()
    {
        assertTrue(line.isEmpty());
    }

    @Test
    public void sizeCountsEnqueuedCustomers()
    {
        line.enqueue(alex);
        line.enqueue(sam);
        assertEquals(2, line.size());
    }

    @Test
    public void sizeIsZeroForEmptyLine()
    {
        assertEquals(0, line.size());
    }

    @Test
    public void enqueueWorksAfterLastCustomerWasRemoved()
    {
        line.enqueue(alex);
        line.dequeue();
        line.enqueue(sam);

        assertEquals(1, line.size());
        assertSame(sam, line.peek());
        assertSame(sam, line.dequeue());
        assertTrue(line.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addObserverRejectsNull()
    {
        line.addObserver(null);
    }

    @Test
    public void observerIsNotifiedAfterEnqueue()
    {
        RecordingObserver observer = new RecordingObserver();
        line.addObserver(observer);

        line.enqueue(alex);
        assertEquals(1, observer.updateCount);
    }

    @Test
    public void observerIsNotifiedAfterDequeue()
    {
        line.enqueue(alex);
        RecordingObserver observer = new RecordingObserver();
        line.addObserver(observer);

        line.dequeue();
        assertEquals(1, observer.updateCount);
    }

    @Test
    public void invalidEnqueueDoesNotNotifyObserver()
    {
        RecordingObserver observer = new RecordingObserver();
        line.addObserver(observer);
        try
        {
            line.enqueue(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException expected)
        {
            assertEquals(0, observer.updateCount);
        }
    }

    @Test
    public void queueWorksWithoutRegisteredObservers()
    {
        line.enqueue(alex);
        assertSame(alex, line.dequeue());
        assertTrue(line.isEmpty());
    }

    /** Simple observer stub: no view is needed to test model notifications. */
    private static class RecordingObserver implements Observer
    {
        private int updateCount;

        @Override
        public void onModelChanged()
        {
            updateCount++;
        }

        // This is the callback used by the team's current Line.java.
        // Keep onModelChanged() above for compatibility with the Observer API.
        public void updateLine()
        {
            updateCount++;
        }

        @Override
        public void updatePlayer(int strikes, int money) {
            // TODO Auto-generated method stub
            
        }
    }
}
