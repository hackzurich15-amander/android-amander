package ch.christofbuechi.android_amander;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class FilterActivity extends AppCompatActivity {

    private int progressPrice = 0;
    private int progressPS = 0;
    private Spinner spinner;
    private TextView minPSValue;
    private TextView maxPriceValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        minPSValue = (TextView) findViewById(R.id.minpsvalue);
        maxPriceValue = (TextView) findViewById(R.id.maxpricevalue);
        initSeekBars();


        spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);



        TextView sendButton = (TextView) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, LoadingScreenActivity.class);
                addValuesToIntent(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void initSeekBars() {
        SeekBar seekPrice = (SeekBar) findViewById(R.id.seekBarprice);
        initPriceSeekBar(seekPrice);
        final int yourStepPrice = 1000;
        seekPrice.incrementProgressBy(1000);
        seekPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = ((int) Math.round(progress / yourStepPrice)) * yourStepPrice;
                seekBar.setProgress(progress);
                progressPrice = progress;
                maxPriceValue.setText("" + progressPrice);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBar seekPS = (SeekBar) findViewById(R.id.seekBarps);
        initPSSeekBar(seekPS);
        final int psStep = 10;
        seekPS.incrementProgressBy(10);
        seekPS.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = ((int) Math.round(progress / psStep)) * psStep;
                seekBar.setProgress(progress);
                progressPS = progress;
                minPSValue.setText("" + progressPS);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void initPSSeekBar(SeekBar seekPS) {
        seekPS.setProgress(100);
        minPSValue.setText("" + 100);
        progressPS = 100;
    }

    private void initPriceSeekBar(SeekBar seekPrice) {
        seekPrice.setProgress(30000);
        maxPriceValue.setText("" + 30000);
        progressPrice = 30000;
    }

    private void addValuesToIntent(Intent intent) {
        intent.putExtra("price", progressPrice);
        intent.putExtra("ps", progressPS);
        intent.putExtra("brand", (String) spinner.getSelectedItem());

    }
}
