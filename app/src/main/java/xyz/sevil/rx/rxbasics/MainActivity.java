package xyz.sevil.rx.rxbasics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

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
        AndroidThreeTen.init(this);

        citySearch = (EditText) findViewById(R.id.city_search);

        londonTemp = (TextView) findViewById(R.id.london_temp);
        stockholmTemp = (TextView) findViewById(R.id.stockholm_temp);
        budapestTemp = (TextView) findViewById(R.id.budapest_temp);
        sydneyTemp = (TextView) findViewById(R.id.sydney_temp);

        customCityName = (TextView) findViewById(R.id.custom_city_name);
        customCityTemp = (TextView) findViewById(R.id.custom_city_temp);


//        Observable.range(1, 10)
//            .subscribe(item -> {
//                // onNext
//                Log.d("MY_SEQUENCE", String.format("Next item is %s", item));
//            },
//            throwable -> {
//                // onError
//                Log.d("MY_SEQUENCE", throwable.getMessage());
//            },
//            () -> {
//                // onCompleted
//                Log.d("MY_SEQUENCE", "Completed");
//            });


//        londonTemp.setText("12 °C");
//        stockholmTemp.setText("15 °C");
//        budapestTemp.setText("26 °C");
//        sydneyTemp.setText("30 °C");


//        Observable.range(17, 5)
//            .zipWith(Observable.interval(5, TimeUnit.SECONDS), (integer, aLong) -> {return integer;})
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(integer -> {
//                logToConsole(integer);
//                stockholmTemp.setText(String.valueOf(integer) + " °C");
//            },
//            throwable -> {},
//            () -> Log.d("TEMPERATURE", "Completed"));

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
            .sample(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map(charSequence -> {
                Log.d("TAG", charSequence.toString());

                StringBuilder sb = new StringBuilder(charSequence.length());
                return sb.append(charSequence).toString();
            })
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

    private void logToConsole(Integer integer) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = now.format(dateTimeFormatter);

        Log.d("TEMPERATURE", String.format("Next item is %s at %s", integer, dateTime));
    }
}
