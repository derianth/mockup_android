package xyz.oneclickstudio.mockup;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yandex.metrica.YandexMetrica;

import org.xmlpull.v1.XmlPullParser;

public class ItemActivity extends AppCompatActivity {

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView text_item = findViewById(R.id.text_item);
        TextView title_item = findViewById(R.id.title_item);
        ImageView image_item = findViewById(R.id.image_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //noinspection ConstantConditions
        final String link_rate = getIntent().getExtras().getString("Link_XML");

        try {
            XmlPullParser parser = getResources().getXml(R.xml.news);
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals(link_rate)) { // ищем тег

                    title_item.setText(parser.getAttributeValue(2));
                    Picasso.with(context).load(parser.getAttributeValue(1)).into(image_item);
                    text_item.setText(parser.getAttributeValue(0));

                }
                parser.next(); // повторяем цикл
            }
        }
        catch (Throwable t) { // ловим ошибки, выводим в диалог. паралельно отправляем отчет в метрику
            YandexMetrica.reportEvent("Ошибка при загрузке каталога: " + t.toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);
            builder.setCancelable(false);
            builder.setTitle("Упс!");
            builder.setMessage("При загрузке данных произошла ошибка =(" + "\n\n" + t.toString());
            builder.setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            }).create().show();
        }
    }
}
