package net.obvj.performetrics.runnable;

import static net.obvj.performetrics.Counter.Type.CPU_TIME;
import static net.obvj.performetrics.Counter.Type.SYSTEM_TIME;
import static net.obvj.performetrics.Counter.Type.USER_TIME;
import static net.obvj.performetrics.Counter.Type.WALL_CLOCK_TIME;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.obvj.performetrics.Counter;
import net.obvj.performetrics.Counter.Type;
import net.obvj.performetrics.MultiCounterMonitorableOperation;
import net.obvj.performetrics.util.PerformetricsUtils;

/**
 * Test methods for the {@link MultiCounterRunnableOperation}
 *
 * @author oswaldo.bapvic.jr
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PerformetricsUtils.class)
public class MultiCounterRunnableOperationTest
{
    private static final long MOCKED_WALL_CLOCK_TIME = 2000000000l;
    private static final long MOCKED_CPU_TIME = 1200000000l;
    private static final long MOCKED_USER_TIME = 1200000001l;
    private static final long MOCKED_SYSTEM_TIME = 1200000002l;

    @Mock
    private Runnable runnable;

    /**
     * Setup the expects on {@link PerformetricsUtils} mock with constant values
     */
    private void setupExpects()
    {
        given(PerformetricsUtils.getWallClockTimeNanos()).willReturn(MOCKED_WALL_CLOCK_TIME);
        given(PerformetricsUtils.getCpuTimeNanos()).willReturn(MOCKED_CPU_TIME);
        given(PerformetricsUtils.getUserTimeNanos()).willReturn(MOCKED_USER_TIME);
        given(PerformetricsUtils.getSystemTimeNanos()).willReturn(MOCKED_SYSTEM_TIME);
    }

    /**
     * Checks that all units-before are equal to the test constants
     */
    private void assertAllUnitsBefore(MultiCounterMonitorableOperation operation)
    {
        assertThat(operation.getCounter(WALL_CLOCK_TIME).getUnitsBefore(), is(MOCKED_WALL_CLOCK_TIME));
        assertThat(operation.getCounter(CPU_TIME).getUnitsBefore(), is(MOCKED_CPU_TIME));
        assertThat(operation.getCounter(USER_TIME).getUnitsBefore(), is(MOCKED_USER_TIME));
        assertThat(operation.getCounter(SYSTEM_TIME).getUnitsBefore(), is(MOCKED_SYSTEM_TIME));
    }

    /**
     * Checks that all units-after are equal to the test constants
     */
    private void assertAllUnitsAfter(MultiCounterMonitorableOperation operation)
    {
        assertThat(operation.getCounter(WALL_CLOCK_TIME).getUnitsAfter(), is(MOCKED_WALL_CLOCK_TIME));
        assertThat(operation.getCounter(CPU_TIME).getUnitsAfter(), is(MOCKED_CPU_TIME));
        assertThat(operation.getCounter(USER_TIME).getUnitsAfter(), is(MOCKED_USER_TIME));
        assertThat(operation.getCounter(SYSTEM_TIME).getUnitsAfter(), is(MOCKED_SYSTEM_TIME));
    }

    /**
     * Checks that all units-before are equal to zero for the given counters list
     */
    private void assertAllUnitsBeforeEqualZero(Counter... counters)
    {
        for (Counter c : counters)
            assertThat("For the counter of type: " + c.getType(), c.getUnitsBefore(), is(0L));
    }

    /**
     * Checks that all units-after are equal to zero for the given counters list
     */

    private void assertAllUnitsAfterEqualZero(Counter... counters)
    {
        for (Counter c : counters)
            assertThat("For the counter of type: " + c.getType(), c.getUnitsAfter(), is(0L));
    }

    /**
     * Tests, for a given {@link Runnable} and a single counter, that the correct counter is
     * specified for this operation and the initial values are zero
     */
    @Test
    public void constructor_withOneType_assignsCorrectCounteAndInitialValues()
    {
        MultiCounterRunnableOperation op = new MultiCounterRunnableOperation(runnable, CPU_TIME);
        assertThat(op.getCounters().size(), is(1));
        Counter counter = op.getCounter(CPU_TIME);
        assertAllUnitsBeforeEqualZero(counter);
        assertAllUnitsAfterEqualZero(counter);
    }

    /**
     * Tests, for a given {@link Runnable} and more than one counter, that the correct
     * counters are specified for this operation and the initial values are zero
     */
    @Test
    public void constructor_withTwoTypes_assignsCorrectCounteAndInitialValues()
    {
        MultiCounterRunnableOperation op = new MultiCounterRunnableOperation(runnable, CPU_TIME, USER_TIME);
        assertThat(op.getCounters().size(), is(2));
        Counter counter1 = op.getCounter(CPU_TIME);
        Counter counter2 = op.getCounter(USER_TIME);
        assertAllUnitsBeforeEqualZero(counter1, counter2);
        assertAllUnitsAfterEqualZero(counter1, counter2);
    }

    /**
     * Tests, for a given {@link Runnable} and no specific counter, that all available
     * counters are specified for this operation
     */
    @Test
    public void constructor_withoutType_assignsAllAvailableCounterTypes()
    {
        MultiCounterRunnableOperation op = new MultiCounterRunnableOperation(runnable);
        assertThat(op.getCounters().size(), is(Type.values().length));
        assertNotNull("Wall-clock-time counter not set", op.getCounter(WALL_CLOCK_TIME));
        assertNotNull("CPU-time counter not set", op.getCounter(CPU_TIME));
        assertNotNull("User-time counter not set", op.getCounter(USER_TIME));
        assertNotNull("System-time counter not set", op.getCounter(SYSTEM_TIME));
    }

    /**
     * Tests the elapsed time for a dummy {@link Runnable} with no specific counter set (all
     * counters)
     */
    @Test
    public void run_givenAllTypes_updatesAllCounters()
    {
        PowerMockito.mockStatic(PerformetricsUtils.class);
        MultiCounterRunnableOperation operation = new MultiCounterRunnableOperation(runnable);
        setupExpects();
        operation.run();
        assertAllUnitsBefore(operation);
        assertAllUnitsAfter(operation);
    }

}
