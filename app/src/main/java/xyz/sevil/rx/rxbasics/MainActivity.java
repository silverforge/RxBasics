package xyz.sevil.rx.rxbasics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView londonTemp;
    private TextView stockholmTemp;
    private TextView budapestTemp;
    private TextView sydneyTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        londonTemp = (TextView) findViewById(R.id.london_temp);
        stockholmTemp = (TextView) findViewById(R.id.stockholm_temp);
        budapestTemp = (TextView) findViewById(R.id.budapest_temp);
        sydneyTemp = (TextView) findViewById(R.id.sydney_temp);

        londonTemp.setText("12 °C");
        stockholmTemp.setText("15 °C");
        budapestTemp.setText("26 °C");
        sydneyTemp.setText("30 °C");
    }
}
