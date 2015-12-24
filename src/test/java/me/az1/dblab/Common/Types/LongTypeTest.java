package me.az1.dblab.Common.Types;

import org.junit.Test;

import static org.junit.Assert.*;

public class LongTypeTest {
    private static byte[] desiredBytes = new byte[] { 0, 0, 0, 0, 0, 0, 3, 10 };
    private static String desiredString = "778";
    private static Long desiredLong = 778L;
    private static LongType instance = new LongType();

    @Test
    public void testToInt() throws Exception {
        assertEquals(desiredLong, instance.ToLong(desiredBytes));
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
        assertTrue(instance.Compare(new byte[] { 0, 1, 1, 1, 0, 0, 0, 0 }, new byte[] { 0, 1, 2, 1, 1, 1, 1, 1 }) < 0);
    }
}
