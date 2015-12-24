package me.az1.dblab.Common.Types;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.IntType;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntTypeTest {
    private static byte[] desiredBytes = new byte[] { 0, 0, 3, 10 };
    private static String desiredString = "778";
    private static Integer desiredInt = 778;
    private static IntType instance = new IntType();

    @Test
    public void testToInt() throws Exception {
        assertEquals(desiredInt, instance.ToInt(desiredBytes));
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
        assertTrue(instance.Compare(new byte[] { 0, 1, 1, 1 }, new byte[] { 0, 1, 2, 1 }) < 0);
    }
}