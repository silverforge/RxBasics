package xyz.sevil.rx.rxbasics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText citySearch;

    private TextView londonTemp;
    private TextView stockholmTemp;
    private TextView budapestTemp;
    private TextView sydneyTemp;

    private TextView customCityName;
    private TextView customCityTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        citySearch = (EditText) findViewById(R.id.city_search);

        londonTemp = (TextView) findViewById(R.id.london_temp);
        stockholmTemp = (TextView) findViewById(R.id.stockholm_temp);
        budapestTemp = (TextView) findViewById(R.id.budapest_temp);
        sydneyTemp = (TextView) findViewById(R.id.sydney_temp);

        customCityName = (TextView) findViewById(R.id.custom_city_name);
        customCityTemp = (TextView) findViewById(R.id.custom_city_temp);

        londonTemp.setText("12 °C");
        stockholmTemp.setText("15 °C");
        budapestTemp.setText("26 °C");
        sydneyTemp.setText("30 °C");
    }
}
