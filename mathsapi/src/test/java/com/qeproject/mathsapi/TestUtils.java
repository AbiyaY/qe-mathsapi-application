package com.qeproject.mathsapi;

/**
 * Provision of static utility methods. Class cannot be instantiated or specialised.
 */
public final class TestUtils
{
    /**
     * Private constructor prevents instantiation of this class
     */
    private TestUtils()
    {
        // CNR
    }

    /**
     * Invokes Thread.sleep with exception management
     */
    public static void sleep(long period)
    {
        try
        {
            Thread.sleep(period);
        }
        catch (InterruptedException iex)
        {
            /* CNR */
        }
    }
}
