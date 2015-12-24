package me.az1.dblab.Common;

import java.util.ArrayList;
import java.util.Arrays;

public class Row extends VersionedClass {
    private static final long serialVersionUID = 823661899935623L;

    protected ArrayList<byte[]> fields;

    public Row(ArrayList<byte[]> fields) {
        this.fields = fields;
    }

    public int Size() {
        return fields.size();
    }

    public void SetField(int index, byte[] value) {
        fields.set(index, value);
    }

    public byte[] GetField(int index) {
        return fields.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Row record = (Row) o;
        for (int i = 0; i < record.Size(); i++) {
            if (!Arrays.equals(fields.get(i), record.GetField(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return fields.hashCode();
    }
}
