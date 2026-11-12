import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Before;
import org.junit.Test;

/** Unit tests for the FIFO customer line, observers, and planned game methods. */
public class LineTest {
    private Line line;
    private Customer alex;
    private Customer sam;

    @Before
    public void setUp() {
        line = new Line();
        alex = customer("Alex", 30);
        sam = customer("Sam", 60);
    }

    private Customer customer(String name, int seconds) {
        Order order = new Order("Latte",
            new ArrayList<String>(Arrays.asList("espresso", "milk")), 5);
        return new Customer(name, order, seconds);
    }

    @Test
    public void constructorStartsWithEmptyLine() {
        assertEquals(0, line.size());
        assertTrue(line.isEmpty());
    }

    @Test
    public void enqueueAddsCustomerToBackInFifoOrder() {
        line.enqueue(alex);
        line.enqueue(sam);
        assertEquals(2, line.size());
        assertSame(alex, line.dequeue());
        assertSame(sam, line.dequeue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void enqueueRejectsNull() {
        line.enqueue(null);
    }

    @Test
    public void dequeueRemovesFirstCustomerAndUpdatesSize() {
        line.enqueue(alex);
        line.enqueue(sam);
        assertSame(alex, line.dequeue());
        assertEquals(1, line.size());
        assertSame(sam, line.peek());
    }

    @Test(expected = NoSuchElementException.class)
    public void dequeueRejectsEmptyLine() {
        line.dequeue();
    }

    @Test
    public void dequeueLastCustomerLeavesEmptyLine() {
        line.enqueue(alex);
        assertSame(alex, line.dequeue());
        assertTrue(line.isEmpty());
        assertEquals(0, line.size());
    }

    @Test
    public void peekReturnsFrontWithoutRemoving() {
        line.enqueue(alex);
        assertSame(alex, line.peek());
        assertSame(alex, line.peek());
        assertEquals(1, line.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void peekRejectsEmptyLine() {
        line.peek();
    }

    @Test
    public void isEmptyFalseAfterEnqueue() {
        line.enqueue(alex);
        assertFalse(line.isEmpty());
    }

    @Test
    public void isEmptyTrueInitially() {
        assertTrue(line.isEmpty());
    }

    @Test
    public void sizeCountsEnqueuedCustomers() {
        line.enqueue(alex);
        line.enqueue(sam);
        assertEquals(2, line.size());
    }

    @Test
    public void sizeZeroForEmptyLine() {
        assertEquals(0, line.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addObserverRejectsNull() {
        line.addObserver(null);
    }

    @Test
    public void addObserverNotifiedAfterEnqueue() {
        AtomicInteger count = new AtomicInteger();
        line.addObserver(recordingObserver(count));
        line.enqueue(alex);
        assertEquals("Adding a customer should notify observers", 1, count.get());
    }

    @Test
    public void addObserverNotifiedAfterDequeue() {
        line.enqueue(alex);
        AtomicInteger count = new AtomicInteger();
        line.addObserver(recordingObserver(count));
        line.dequeue();
        assertEquals("Removing a customer should notify observers", 1, count.get());
    }

    @Test
    public void submitStepReturnsStepOkForCorrectNonFinalStep() {
        line.enqueue(alex);
        assertEquals("STEP_OK", String.valueOf(invokeSubmitStep("espresso")));
    }

    @Test
    public void submitStepReturnsOrderCompleteForFinalStep() {
        line.enqueue(alex);
        invokeSubmitStep("espresso");
        assertEquals("ORDER_COMPLETE", String.valueOf(invokeSubmitStep("milk")));
    }

    @Test
    public void submitStepReturnsWrongStepForIncorrectIngredient() {
        line.enqueue(alex);
        assertEquals("WRONG_STEP", String.valueOf(invokeSubmitStep("milk")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitStepRejectsNull() {
        line.enqueue(alex);
        invokeSubmitStep(null);
    }

    @Test
    public void tickDoesNotExpireCustomerEarly() {
        line.enqueue(alex); // 30-second limit
        assertFalse(invokeTick(1000L)); // One second elapsed
        assertSame(alex, line.peek());
    }

    @Test
    public void tickExpiresFrontCustomerAtDeadline() {
        line.enqueue(alex); // 30-second limit
        line.enqueue(sam);
        assertFalse(invokeTick(1000L));
        assertTrue(invokeTick(29000L));
        assertSame(sam, line.peek());
        assertEquals(1, line.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void tickRejectsNegativeElapsedTime() {
        invokeTick(-1L);
    }

    @Test
    public void tickOnEmptyLineDoesNotExpireCustomer() {
        assertFalse(invokeTick(1000L));
        assertTrue(line.isEmpty());
    }

    /**
     * Planned public methods have not yet been added to the provided Line.java.
     * Reflection allows this test class to compile without calling missing
     * methods directly. Tests will fail clearly until those methods exist.
     */
    private Object invokeSubmitStep(String ingredient) {
        return invokePlannedMethod("submitStep", new Class<?>[] {String.class}, ingredient);
    }

    private boolean invokeTick(long elapsedMs) {
        Object result = invokePlannedMethod("tick", new Class<?>[] {long.class}, elapsedMs);
        assertTrue("tick must return boolean", result instanceof Boolean);
        return (Boolean) result;
    }

    private Object invokePlannedMethod(String name, Class<?>[] parameterTypes,
        Object argument) {
        try {
            Method method = Line.class.getMethod(name, parameterTypes);
            return method.invoke(line, argument);
        }
        catch (NoSuchMethodException e) {
            throw new AssertionError("Line." + name
                + " is in the spec but not yet implemented", e);
        }
        catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new AssertionError("Line." + name + " failed", cause);
        }
        catch (ReflectiveOperationException e) {
            throw new AssertionError("Could not run Line." + name, e);
        }
    }

    /**
     * Observer interface source wasn't provided. This records any update
     * callback without assuming a specific updateLine parameter type.
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
