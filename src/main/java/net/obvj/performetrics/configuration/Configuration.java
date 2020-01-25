package net.obvj.performetrics.configuration;

import java.util.concurrent.TimeUnit;

import net.obvj.performetrics.strategy.ConversionStrategy;

/**
 * An object that groups common Performetrics configuration.
 *
 * @author oswaldo.bapvic.jr
 * @since 2.0.0
 */
public class Configuration
{
    /**
     * The initial time unit to be maintained if no specific time unit informed
     */
    public static final TimeUnit INITIAL_TIME_UNIT = TimeUnit.NANOSECONDS;

    /**
     * The initial conversion strategy to be applied if no specific strategy is set
     */
    public static final ConversionStrategy INITIAL_CONVERSION_STRATEGY = ConversionStrategy.DOUBLE_PRECISION;

    /**
     * The initial maximum number of decimal places applicable if double-precision conversion
     * strategy is set
     */
    public static final int INITIAL_SCALE = 5;

    private TimeUnit timeUnit = INITIAL_TIME_UNIT;
    private ConversionStrategy conversionStrategy = INITIAL_CONVERSION_STRATEGY;
    private int scale = INITIAL_SCALE;

    /**
     * @return the default time unit
     */
    public TimeUnit getTimeUnit()
    {
        return timeUnit;
    }

    /**
     * Sets a time unit to be maintained by default if no specific time unit is specified.
     *
     * @param timeUnit the {@link TimeUnit} to set
     */
    public void setTimeUnit(TimeUnit timeUnit)
    {
        this.timeUnit = timeUnit;
    }

    /**
     * @return the conversion strategy
     */
    public ConversionStrategy getConversionStrategy()
    {
        return conversionStrategy;
    }

    /**
     * Sets a conversion strategy to be applied in supported operations if no specific
     * strategy is set.
     *
     * @param conversionStrategy the {@link ConversionStrategy} to set
     */
    public void setConversionStrategy(ConversionStrategy conversionStrategy)
    {
        this.conversionStrategy = conversionStrategy;
    }

    /**
     * @return the scale
     */
    public int getScale()
    {
        return scale;
    }

    /**
     * Sets a maximum number of decimal places to be applied if double-precision conversion
     * strategy is set.
     *
     * @param scale a number between 0 and 16 to be set
     * @throws IllegalArgumentException if a number outside the allowed range is received
     */
    public void setScale(int scale)
    {
        if (scale < 0 || scale > 16)
        {
            throw new IllegalArgumentException("The scale must be a number between 0 and 16");
        }
        this.scale = scale;
    }

}
