package net.obvj.performetrics.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import net.obvj.performetrics.util.Duration.FormatStyle;

/**
 * Unit tests for the {@link Duration}.
 *
 * @author oswaldo.bapvic.jr
 * @since 2.0.0
 */
public class DurationTest
{

    @Test
    public void of_validAmountAndTimeUnitAndNanosecondsPrecision_populatesAccordingly()
    {
        Duration td1 = Duration.of(1999888777, TimeUnit.NANOSECONDS);
        assertThat(td1.getHours(),       is(0L));
        assertThat(td1.getMinutes(),     is(0));
        assertThat(td1.getSeconds(),     is(1));
        assertThat(td1.getNanoseconds(), is(999888777));
    }

    @Test
    public void of_validAmountAndTimeUnitAndSecondsPrecision_populatesAccordingly()
    {
        Duration td1 = Duration.of(5410, TimeUnit.SECONDS);
        assertThat(td1.getHours(),       is(1L));
        assertThat(td1.getMinutes(),     is(30));
        assertThat(td1.getSeconds(),     is(10));
        assertThat(td1.getNanoseconds(), is(0));
    }

    @Test
    public void toString_full_displaysAllUnits()
    {
        assertThat(Duration.of(0,          TimeUnit.NANOSECONDS).toString(FormatStyle.FULL),  is( "00:00:00.000000000"));

        assertThat(Duration.of(1,          TimeUnit.NANOSECONDS).toString(FormatStyle.FULL),  is( "00:00:00.000000001"));
        assertThat(Duration.of(1,          TimeUnit.MILLISECONDS).toString(FormatStyle.FULL), is( "00:00:00.001000000"));
        assertThat(Duration.of(1,          TimeUnit.SECONDS).toString(FormatStyle.FULL),      is( "00:00:01.000000000"));
        assertThat(Duration.of(1,          TimeUnit.MINUTES).toString(FormatStyle.FULL),      is( "00:01:00.000000000"));
        assertThat(Duration.of(1,          TimeUnit.HOURS).toString(FormatStyle.FULL),        is( "01:00:00.000000000"));
        assertThat(Duration.of(1,          TimeUnit.DAYS).toString(FormatStyle.FULL),         is( "24:00:00.000000000"));

        assertThat(Duration.of(789,        TimeUnit.NANOSECONDS).toString(FormatStyle.FULL),  is( "00:00:00.000000789"));
        assertThat(Duration.of(123456789,  TimeUnit.NANOSECONDS).toString(FormatStyle.FULL),  is( "00:00:00.123456789"));
        assertThat(Duration.of(1000000000, TimeUnit.NANOSECONDS).toString(FormatStyle.FULL),  is( "00:00:01.000000000"));
        assertThat(Duration.of(1001,       TimeUnit.MILLISECONDS).toString(FormatStyle.FULL), is( "00:00:01.001000000"));
        assertThat(Duration.of(1601,       TimeUnit.MILLISECONDS).toString(FormatStyle.FULL), is( "00:00:01.601000000"));
        assertThat(Duration.of(3601,       TimeUnit.MILLISECONDS).toString(FormatStyle.FULL), is( "00:00:03.601000000"));
        assertThat(Duration.of(70,         TimeUnit.SECONDS).toString(FormatStyle.FULL),      is( "00:01:10.000000000"));
        assertThat(Duration.of(601,        TimeUnit.SECONDS).toString(FormatStyle.FULL),      is( "00:10:01.000000000"));
        assertThat(Duration.of(959,        TimeUnit.SECONDS).toString(FormatStyle.FULL),      is( "00:15:59.000000000"));
        assertThat(Duration.of(960,        TimeUnit.SECONDS).toString(FormatStyle.FULL),      is( "00:16:00.000000000"));
        assertThat(Duration.of(970,        TimeUnit.SECONDS).toString(FormatStyle.FULL),      is( "00:16:10.000000000"));
        assertThat(Duration.of(3601,       TimeUnit.MINUTES).toString(FormatStyle.FULL),      is( "60:01:00.000000000"));
        assertThat(Duration.of(3601,       TimeUnit.MINUTES).toString(FormatStyle.FULL),      is( "60:01:00.000000000"));
        assertThat(Duration.of(2,          TimeUnit.HOURS).toString(FormatStyle.FULL),        is( "02:00:00.000000000"));
        assertThat(Duration.of(100,        TimeUnit.HOURS).toString(FormatStyle.FULL),        is("100:00:00.000000000"));
    }

    @Test
    public void toString_short_abbreviatesIfPossible()
    {
        assertThat(Duration.of(0,          TimeUnit.NANOSECONDS).toString(FormatStyle.SHORT),  is(       "00.000000000"));

        assertThat(Duration.of(1,          TimeUnit.NANOSECONDS).toString(FormatStyle.SHORT),  is(       "00.000000001"));
        assertThat(Duration.of(1,          TimeUnit.MILLISECONDS).toString(FormatStyle.SHORT), is(       "00.001000000"));
        assertThat(Duration.of(1,          TimeUnit.SECONDS).toString(FormatStyle.SHORT),      is(       "01.000000000"));
        assertThat(Duration.of(1,          TimeUnit.MINUTES).toString(FormatStyle.SHORT),      is(    "01:00.000000000"));
        assertThat(Duration.of(1,          TimeUnit.HOURS).toString(FormatStyle.SHORT),        is( "01:00:00.000000000"));
        assertThat(Duration.of(1,          TimeUnit.DAYS).toString(FormatStyle.SHORT),         is( "24:00:00.000000000"));

        assertThat(Duration.of(789,        TimeUnit.NANOSECONDS).toString(FormatStyle.SHORT),  is(       "00.000000789"));
        assertThat(Duration.of(123456789,  TimeUnit.NANOSECONDS).toString(FormatStyle.SHORT),  is(       "00.123456789"));
        assertThat(Duration.of(1000000000, TimeUnit.NANOSECONDS).toString(FormatStyle.SHORT),  is(       "01.000000000"));
        assertThat(Duration.of(1001,       TimeUnit.MILLISECONDS).toString(FormatStyle.SHORT), is(       "01.001000000"));
        assertThat(Duration.of(1601,       TimeUnit.MILLISECONDS).toString(FormatStyle.SHORT), is(       "01.601000000"));
        assertThat(Duration.of(3601,       TimeUnit.MILLISECONDS).toString(FormatStyle.SHORT), is(       "03.601000000"));
        assertThat(Duration.of(70,         TimeUnit.SECONDS).toString(FormatStyle.SHORT),      is(    "01:10.000000000"));
        assertThat(Duration.of(601,        TimeUnit.SECONDS).toString(FormatStyle.SHORT),      is(    "10:01.000000000"));
        assertThat(Duration.of(959,        TimeUnit.SECONDS).toString(FormatStyle.SHORT),      is(    "15:59.000000000"));
        assertThat(Duration.of(960,        TimeUnit.SECONDS).toString(FormatStyle.SHORT),      is(    "16:00.000000000"));
        assertThat(Duration.of(970,        TimeUnit.SECONDS).toString(FormatStyle.SHORT),      is(    "16:10.000000000"));
        assertThat(Duration.of(3601,       TimeUnit.MINUTES).toString(FormatStyle.SHORT),      is( "60:01:00.000000000"));
        assertThat(Duration.of(3601,       TimeUnit.MINUTES).toString(FormatStyle.SHORT),      is( "60:01:00.000000000"));
        assertThat(Duration.of(2,          TimeUnit.HOURS).toString(FormatStyle.SHORT),        is( "02:00:00.000000000"));
        assertThat(Duration.of(100,        TimeUnit.HOURS).toString(FormatStyle.SHORT),        is("100:00:00.000000000"));
    }

    @Test
    public void toString_noArguments_appliesShortStyle()
    {
        assertThat(Duration.of(3601, TimeUnit.MILLISECONDS).toString(), is("03.601000000"));
    }

    @Test
    public void equals_similarButNotSameObjects_true()
    {
        Duration td60seconds = Duration.of(60, TimeUnit.SECONDS);
        Duration td1Minute = Duration.of(1, TimeUnit.MINUTES);
        assertThat(td60seconds.equals(td1Minute), is(true));
    }

    @Test
    public void equals_differentTimes_false()
    {
        Duration td1001millis = Duration.of(1001, TimeUnit.MILLISECONDS);
        Duration td1second = Duration.of(1, TimeUnit.SECONDS);
        assertThat(td1001millis.equals(td1second), is(false));
    }

    @Test
    public void hashCode_twoSimilarObjectsInAHashSet_setMaintainsOnlyOneObject()
    {
        Set<Duration> set = new HashSet<>();
        set.add(Duration.of(60, TimeUnit.SECONDS));
        set.add(Duration.of(1, TimeUnit.MINUTES));
        assertThat(set.size(), is(1));
    }

}