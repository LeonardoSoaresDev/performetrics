package net.obvj.performetrics.config;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import net.obvj.performetrics.ConversionMode;
import net.obvj.performetrics.config.Configuration;

/**
 * Unit tests for the {@link Configuration}.
 *
 * @author oswaldo.bapvic.jr
 * @since 2.0.0
 */
public class ConfigurationTest
{

    @Test
    public void constructor_default_defaultValues()
    {
        Configuration configuration = new Configuration();
        assertThat(configuration.getTimeUnit(), is(Configuration.INITIAL_TIME_UNIT));
        assertThat(configuration.getConversionMode(), is(Configuration.INITIAL_CONVERSION_MODE));
        assertThat(configuration.getScale(), is(Configuration.INITIAL_SCALE));
    }

    @Test
    public void setTimeUnit_validTimeUnit_suceeds()
    {
        Configuration configuration = new Configuration();
        configuration.setTimeUnit(TimeUnit.SECONDS);
        assertThat(configuration.getTimeUnit(), is(TimeUnit.SECONDS));
    }

    @Test
    public void setConversionMode_validMode_suceeds()
    {
        Configuration configuration = new Configuration();
        configuration.setConversionMode(ConversionMode.FAST);
        assertThat(configuration.getConversionMode(), is(ConversionMode.FAST));
    }

    @Test
    public void setScale_validNumber_suceeds()
    {
        Configuration configuration = new Configuration();
        configuration.setScale(16);
        assertThat(configuration.getScale(), is(16));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setScale_negativeNumber_fails()
    {
        Configuration configuration = new Configuration();
        configuration.setScale(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setScale_higherThanMaximum_fails()
    {
        Configuration configuration = new Configuration();
        configuration.setScale(17);
    }

}