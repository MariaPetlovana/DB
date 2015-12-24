package me.az1.dblab.Common.Types;

import java.nio.ByteBuffer;

public class CharType extends AbstractType {
    private static final long serialVersionUID = -54354314645234L;

    @Override
    public String ToString(byte[] data) {
        if (data == null) {
            return null;
        }

        char value = ToCharacter(data);
        return "" + value;
    }

    public Character ToCharacter(byte[] data) {
        return ByteBuffer.wrap(data).getChar();
    }

    @Override
    public byte[] FromString(String str) {
        if (str == null) {
            return null;
        }

        if (str.length() != 1) {
            throw new RuntimeException("Can't parse char");
        }

        char value = new Character(str.charAt(0));
        return ByteBuffer.allocate(2).putChar(value).array();
    }

    @Override
    public boolean Supports(byte[] data) {
        return data == null || data.length == 2;
    }

    @Override
    public int Compare(byte[] a, byte[] b) {
        return ToCharacter(a).compareTo(ToCharacter(b));
    }
}
