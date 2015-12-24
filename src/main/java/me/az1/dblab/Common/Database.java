package me.az1.dblab.Common;

import java.util.ArrayList;

public class Database extends VersionedClass {
    private static final long serialVersionUID = 19773186723L;

    protected ArrayList<Table> tables;

    public Database() {
        tables = new ArrayList<Table>();
    }

    public long[] GetTableVersions() {
        long[] versions = new long[tables.size()];
        for (int index = 0; index < versions.length; ++index) {
            versions[index] = tables.get(index).GetVersion();
        }

        return versions;
    }

    public long AddEmptyTable(Scheme scheme, String name) {
        Table table = new Table(scheme, name);
        tables.add(table);
        return table.GetVersion();
    }

    public boolean RemoveTable(long version) {
        for (int index = 0; index < tables.size(); ++index) {
            if (tables.get(index).GetVersion() == version) {
                tables.remove(index);
                return true;
            }
        }

        return false;
    }

    public long[] GetTableRowVersions(long version) {
        return GetTable(version).GetRowVersions();
    }

    public int Size() {
        return tables.size();
    }

    public int TableSize(long version) {
        return GetTable(version).Size();
    }

    public String TableName(long version) {
        return GetTable(version).Name();
    }

    public int TableRowLength(long version) {
        return GetTable(version).RowLength();
    }

    public long TableAddEmptyRow(long version) {
        return GetTable(version).AddEmptyRow();
    }

    public boolean TableRemoveRow(long tableVersion, long rowVersion) {
        return GetTable(tableVersion).RemoveRow(rowVersion);
    }

    public byte[] TableGetField(long tableVersion, long rowVersion, int column) {
        return GetTable(tableVersion).GetField(rowVersion, column);
    }

    public String TableGetFieldString(long tableVersion, long rowVersion, int column) {
        return GetTable(tableVersion).GetFieldString(rowVersion, column);
    }

    public void TableSetField(long tableVersion, long rowVersion, int column, byte[] value) {
        GetTable(tableVersion).SetField(rowVersion, column, value);
    }

    public void TableSetField(long tableVersion, long rowVersion, int column, String strValue) {
        GetTable(tableVersion).SetField(rowVersion, column, strValue);
    }

    protected Table GetTable(long version) {
        for (Table table : tables) {
            if (table.GetVersion() == version) {
                return table;
            }
        }

        return null;
    }

    public void TableSortByKey(long tableVersion, int column) {
        GetTable(tableVersion).SortByKey(column);
    }

    public long TableFind(long tableVersion, String pattern) {
        Table source = GetTable(tableVersion);
        Table result = new Table(source.scheme, "Search in " + source.name);
        source.rows.stream().filter(row -> source.RowMatch(row, pattern)).forEach(result::AddRow);
        tables.add(result);
        return result.GetVersion();
    }

    public long TableRemoveDuplicates(long tableVersion) {
        Table source = GetTable(tableVersion);
        Table result = new Table(source.scheme, source.name + " without duplicates");
        source.rows.stream().forEach(result::AddRow);
        result.RemoveDuplicates();
        tables.add(result);
        return result.GetVersion();
    }
}
