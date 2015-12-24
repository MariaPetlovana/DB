package me.az1.dblab.Common.Types;

import org.junit.Test;

import static org.junit.Assert.*;

public class FloatTypeTest {
    private static byte[] desiredBytes = new byte[] { 63, -16, 0, 0, 0, 0, 0, 0 };
    private static String desiredString = "1.0";
    private static Double desiredFloat = 1.0;
    private static FloatType instance = new FloatType();

    @Test
    public void testToFloat() throws Exception {
        assertEquals(desiredFloat, instance.ToDouble(desiredBytes));
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(desiredString, instance.ToString(desiredBytes));
    }

    @Test
    public void testFromString() throws Exception {
        assertArrayEquals(desiredBytes, instance.FromString(desiredString));
    }

    @Test
    public void testSupports() throws Exception {
        assertTrue(instance.Supports(desiredBytes));
    }

    @Test
    public void testCompare() throws Exception {
        assertTrue(instance.Compare(new byte[] { 0, 1, 1, 1, 1, 5, 2, 6 }, new byte[] { 0, 1, 2, 1, 1, 2, 3, 6 }) < 0);
    }
}
