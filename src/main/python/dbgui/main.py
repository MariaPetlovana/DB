NULL_STRING_VALUE = '3thjZJfeiui805aq73HPtmA0n0lunUxD8stuvyUxEScgfzr6NoamVk5KHTZd'

from random import random
from kivy.app import App
from kivy.clock import Clock
from kivy.uix.label import Label
from kivy.uix.popup import Popup
from kivy.uix.widget import Widget
from kivy.uix.button import Button
from kivy.graphics import Color, Ellipse, Line
from kivy.network.urlrequest import UrlRequest
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.floatlayout import FloatLayout
from kivy.uix.gridlayout import GridLayout
from kivy.uix.tabbedpanel import TabbedPanel
from kivy.uix.tabbedpanel import TabbedPanelHeader
from controller import Controller
from kivy.uix.textinput import TextInput
from kivy.clock import Clock, mainthread
import threading

BTN_H = 30

class DBControlWidget(BoxLayout):
    @staticmethod
    def get_def_btn(text):
        return Button(
            text=text,
            size_hint=(0.5, 1.0),
            pos_hint={'center_x': .5, 'center_y': .5})

    def __init__(self, ctrl, **kwargs):
        super(DBControlWidget, self).__init__(size_hint=(1, None), size=(10, BTN_H), **kwargs)

        self.ctrl = ctrl

        refreshBtn = DBControlWidget.get_def_btn("Refresh")

        def on_refresh_press(instance):
            ctrl.call_update()
        refreshBtn.bind(on_press=on_refresh_press)

        self.add_widget(refreshBtn)

class TableControlWidget(BoxLayout):
    @staticmethod
    def get_def_btn(text):
        return Button(
            text=text,
            size_hint=(0.5, 1.0),
            pos_hint={'center_x': .5, 'center_y': .5})

    def __init__(self, ctrl, tableC, **kwargs):
        super(TableControlWidget, self).__init__(size_hint=(1, None), size=(10, BTN_H), **kwargs)

        n_cols = tableC[2]

        find_input = TextInput(text='|'.join(['.*' for i in xrange(n_cols)]))

        def OnDel(instance):
            ctrl.remove_table(tableC[0])
            ctrl.call_update()

        def OnFind(instance):
            ctrl.find(tableC[0], find_input.text)
            ctrl.call_update()

        delTblBtn = DBControlWidget.get_def_btn("Delete table")
        findBtn = DBControlWidget.get_def_btn("Find")

        delTblBtn.bind(on_press=OnDel)
        findBtn.bind(on_press=OnFind)

        self.add_widget(delTblBtn)
        self.add_widget(findBtn)
        self.add_widget(find_input)

class TableDataWidget(GridLayout):
    def __init__(self, ctrl, tableC, **kwargs):
        rows = tableC[3]
        n_rows = len(rows)
        n_cols = tableC[2]

        super(TableDataWidget, self).__init__(cols=n_cols, rows=n_rows, size_hint=(1, 1), **kwargs)

        for rowC in rows:
            row, data = rowC
            for col in xrange(n_cols):
                value = data[col]
                if value == NULL_STRING_VALUE:
                    value = '<NULL>'
                    
                label = Label(text = value)
                self.add_widget(label)

class TableWidget(BoxLayout):
    def __init__(self, ctrl, tableC, **kwargs):
        super(TableWidget, self).__init__(orientation='vertical', size_hint=(1, 1), **kwargs)

        self.add_widget(TableControlWidget(ctrl, tableC))
        self.add_widget(TableDataWidget(ctrl, tableC))

class DBWidget(TabbedPanel):
    def __init__(self, ctrl, **kwargs):
        super(DBWidget, self).__init__(size_hint=(1, 1), do_default_tab=False, **kwargs)

        self.ctrl = ctrl
        self.ctrl.add_call_listener(self)

    @mainthread
    def draw_db(self, db):
        for table in db:
            name = table[1]
            th = TabbedPanelHeader(text=name)
            th.content = TableWidget(self.ctrl, table)
            self.add_widget(th)

    def update(self):
        def get_db():
            print('updating...')
            if self.ctrl.is_opened():
                db = self.ctrl.get_db()
                self.draw_db(db)
        self.clear_tabs()
        self.clear_widgets()
        self.popup = Popup(title='Loading', content=Label(text='Please wait'),
            auto_dismiss=False, size_hint=(0.3, 0.3))
        self.popup.open()
        try:
            th = threading.Thread(target=get_db)
            th.start()
        finally:
            self.popup.dismiss()
            self.popup = None

class AppWidget(BoxLayout):
    def __init__(self, ctrl, **kwargs):
        super(AppWidget, self).__init__(orientation='vertical', **kwargs)

        self.ctrl = ctrl

        self.add_widget(DBControlWidget(ctrl))
        self.add_widget(DBWidget(ctrl))

class DBApp(App):
    def __init__(self, ctrl, **kwargs):
        super(DBApp, self).__init__(**kwargs)

        self.ctrl = ctrl

    def build(self):
        return AppWidget(self.ctrl)

if __name__ == '__main__':
    ctrl = Controller('http://laba-webservice.herokuapp.com/ws_db?wsdl')
    DBApp(ctrl).run()
