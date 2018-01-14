package xyz.oneclickstudio.mockup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yandex.metrica.YandexMetrica;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESC = "desc";
    private static final String DATE = "date";
    ListView listView;
    HashMap<String, Object> hm;
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listView); // находим список
        listView.setDivider(getResources().getDrawable(android.R.color.transparent)); // убираем разделители

        final ArrayList<HashMap<String, Object>> gift_List = new ArrayList<>(); //  собираем массив

        try {
            XmlPullParser parser = getResources().getXml(R.xml.cat_1);
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("contact")) { // ищем тег
                    hm = new HashMap<>(); // добавляем данные
                    hm.put(ID, parser.getAttributeValue(2));
                    hm.put(TITLE, parser.getAttributeValue(3));
                    hm.put(DESC, parser.getAttributeValue(1) );
                    hm.put(DATE, parser.getAttributeValue(0) );

                    gift_List.add(hm);
                }
                parser.next(); // повторяем цикл
            }
        }
        catch (Throwable t) { // ловим ошибки, выводим в диалог. паралельно отправляем отчет в метрику
            YandexMetrica.reportEvent("Ошибка при загрузке каталога: " + t.toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
            builder.setCancelable(false);
            builder.setTitle("Упс!");
            builder.setMessage("При загрузке данных произошла ошибка =(" + "\n\n" + t.toString());
            builder.setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            }).create().show();
        }


        adapter = new SimpleAdapter(ListActivity.this, gift_List, // адаптер
                R.layout.list_catalog, new String[]{ID, TITLE, DESC, DATE},
                new int[]{R.id.text_id, R.id.text_name, R.id.text_prise, R.id.text_link});
        listView.setAdapter(adapter);
    }

    public void item_list_click(View view) { // по клику читаем номер айди
        TextView str_for_pressed_id = view.findViewById(R.id.text_id);
        String str = str_for_pressed_id.getText().toString();

        Intent intent = new Intent(ListActivity.this, ItemActivity.class);
        intent.putExtra("Link_XML", str);
        startActivity(intent);
    }

}
