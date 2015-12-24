package me.az1.dblab.Common.Types;

import org.junit.Test;

import static org.junit.Assert.*;

public class CharTypeTest {
    private static byte[] desiredBytes = new byte[] { 0, 'a' };
    private static String desiredString = "a";
    private static Character desiredCharacter = 'a';
    private static CharType instance = new CharType();

    @Test
    public void testToChar() throws Exception {
        assertEquals(desiredCharacter, instance.ToCharacter(desiredBytes));
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
        assertTrue(instance.Compare(new byte[] { 0, 5 }, new byte[] { 0, 20 }) < 0);
    }
}