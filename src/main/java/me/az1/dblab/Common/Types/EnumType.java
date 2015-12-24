package me.az1.dblab.Common.Types;

public class EnumType extends IntType {
    private static final long serialVersionUID = -1681206361769236965L;
    public static enum EnumValue {
        Bachelor,
        Master,
        PhD
    };

    @Override
    public String ToString(byte[] data) {
        if (data == null) {
            return null;
        }

        if (!Supports(data)) {
            throw new RuntimeException("Can't parse enum.");
        }

        return new String(data);
    }

    @Override
    public byte[] FromString(String str) {
        if (str == null) {
            return null;
        }

        byte[] bytes = str.getBytes();

        if (!Supports(bytes)) {
            throw new RuntimeException("Can't parse enum.");
        }

        return bytes;
    }

    @Override
    public boolean Supports(byte[] data) {
        if (data == null) {
            return true;
        }

        try {
            EnumValue.valueOf(new String(data));
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public int Compare(byte[] a, byte[] b) {
        return ToString(a).compareTo(ToString(b));
    }
}
