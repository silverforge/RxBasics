package xyz.sevil.rx.rxbasics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Single;
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

//        londonTemp.setText("12 °C");
//        stockholmTemp.setText("15 °C");
//        budapestTemp.setText("26 °C");
//        sydneyTemp.setText("30 °C");

//
//        Observable.range(1, 20)
//                .delay(5, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(integer -> {
//                    stockholmTemp.setText(String.valueOf(integer));
//                });

        WeatherRepository repository = new WeatherRepository();

        Single.fromCallable(() -> repository.getCityWeather("London,uk"))
                .timeout(60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherInfo -> {
                    Double temp = weatherInfo.getMain().getTemp();
                    londonTemp.setText(String.valueOf(temp) + " °C");
                }, throwable -> {
                    Log.e("onCreate", throwable.getMessage());
                });

        Single.fromCallable(() -> repository.getCityWeather("Stockholm,se"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherInfo -> {
                    Double temp = weatherInfo.getMain().getTemp();
                    stockholmTemp.setText(String.valueOf(temp) + " °C");
                }, throwable -> {
                    Log.e("onCreate", throwable.getMessage());
                });

        Single.fromCallable(() -> repository.getCityWeather("Budapest,hu"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherInfo -> {
                    Double temp = weatherInfo.getMain().getTemp();
                    budapestTemp.setText(String.valueOf(temp) + " °C");
                }, throwable -> {
                    Log.e("onCreate", throwable.getMessage());
                });

        Single.fromCallable(() -> repository.getCityWeather("Sydney,au"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherInfo -> {
                    Double temp = weatherInfo.getMain().getTemp();
                    sydneyTemp.setText(String.valueOf(temp) + " °C");
                }, throwable -> {
                    Log.e("onCreate", throwable.getMessage());
                });

        RxTextView.textChanges(citySearch)
                .map(charSequence -> {
                    Log.d("TAG", charSequence.toString());

                    StringBuilder sb = new StringBuilder(charSequence.length());
                    return sb.append(charSequence).toString();
                })
//                .debounce(2, TimeUnit.SECONDS)
//                .distinctUntilChanged()
                .subscribe(enteredText -> {
                    // onNext

                    Log.d("TAG", enteredText);

                    customCityName.setText(enteredText);

                    Observable.fromCallable(() -> repository.getCityWeather(enteredText))
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(weatherInfo -> {
                                // onNext

                                Log.d("TAG", "service call");

                                if (weatherInfo != null && weatherInfo.getMain() != null && weatherInfo.getMain().getTemp() != null) {
                                    Double temp = weatherInfo.getMain().getTemp();
                                    customCityTemp.setText(String.valueOf(temp) + " °C");
                                }
                            }, throwable -> {
                                // onError

                            }, () -> {
                                // onCompleted

                            });
                }, throwable -> {
                    // onError

                }, () -> {
                    // onCompleted

                });
    }
}
