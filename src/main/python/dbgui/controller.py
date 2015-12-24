from suds.client import Client
import base64

class Controller:
    def __init__(self, url):
        self.client = Client(url)

        self.client.options.cache.clear()
        cache = self.client.options.cache
        cache.setduration(seconds=90)
        self.update_listeners = []

    def call_update(self):
        for obj in self.update_listeners:
            obj.update()

    def add_call_listener(self, obj):
        self.update_listeners.append(obj)

    def from_base64(self, str):
        return base64.b64decode(str)

    def parse_row(self, str):
        data = str.split('|')
        return long(data[0]), [self.from_base64(field) for field in data[1:]]

    def parse_table(self, str):
        ver, name, n_cols, data = str.split(':')
        fields = [self.parse_row(row_str) for row_str in data.split(',')]
        return long(ver), self.from_base64(name), int(n_cols), fields

    def get_db(self):
        str = self.client.service.GetDatabase()
        return [self.parse_table(table_str) for table_str in str.split(';')]
    
    def is_opened(self):
        return self.client.service.IsOpened()

    def new_database(self):
        return self.client.service.NewDatabase()

    def load_database(self, path):
        return self.client.service.LoadDatabase(path)

    def save_database(self, path):
        return self.client.service.SaveDatabase(path)

    def close_database(self):
        return self.client.service.CloseDatabase()

    def get_table_versions(self):
        return self.client.service.DatabaseGetTableVersions()[0]

    def add_table(self):
        return self.not_implemented()
        # return self.client.service.DatabaseAddEmptyTable(scheme, name)

    def remove_table(self, version):
        self.client.service.DatabaseRemoveTable(version)

    def get_row_versions(self, tableV):
        return self.client.service.DatabaseGetTableRowVersions(tableV)[0]

    def get_n_tables(self):
        return self.client.service.DatabaseSize()

    def get_n_rows(self, tableV):
        return self.client.service.DatabaseTableSize(tableV)

    def get_table_name(self, tableV):
        return self.client.service.DatabaseTableName(tableV)

    def get_n_cols(self, tableV):
        return self.client.service.DatabaseTableRowLength(tableV)

    def add_row(self, tableV):
        return self.client.service.DatabaseTableAddEmptyRow(tableV)

    def remove_row(self, tableV, rowV):
        self.client.service.DatabaseTableRemoveRow(tableV, rowV)

    def get_field(self, tableV, rowV, col):
        return self.client.service.DatabaseTableGetField(tableV, rowV, col)

    def get_field_str(self, tableV, rowV, col):
        return self.client.service.DatabaseTableGetFieldString(tableV, rowV, col)

    def set_field(self, tableV, rowV, col, value):
        self.client.service.DatabaseTableSetField(tableV, rowV, col, value)

    def set_field(self, tableV, rowV, col, valueStr):
        self.client.service.DatabaseTableSetFieldStr(tableV, rowV, col, valueStr)

    def find(self, tableV, pattern):
        return self.client.service.DatabaseTableFind(tableV, pattern)