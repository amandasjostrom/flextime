package com.lahtinen.apps.flextime;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.concurrent.TimeUnit;


public class MainActivity extends ActionBarActivity {

    private static final String[] NUMBERS = Calculator.buildPickerNumbers();
    private static final PeriodFormatter TIME_FORMATTER = new PeriodFormatterBuilder()
            .appendHours()
            .appendSuffix("h")
            .appendMinutes()
            .appendSuffix("m")
            .toFormatter();

    private ApplicationStorage applicationStorage;
    private NumberPicker numberPicker;
    private TextView tvTime;
    private TextView lastUpdated;
    private LinearLayout timeOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applicationStorage = new ApplicationStorage(this);
        tvTime = (TextView) findViewById(R.id.tvTime);
        lastUpdated = (TextView) findViewById(R.id.lastUpdated);
        timeOverview = (LinearLayout) findViewById(R.id.timeOverview);
        setupAddButton();
        setupSubtractButton();
        setupPickers();
    }

    private void setupAddButton() {
        ((Button) findViewById(R.id.btAdd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newTime = Calculator.stringToInt(
                        NUMBERS[numberPicker.getValue()]
                );
                applicationStorage.updateTime(new Duration(TimeUnit.MINUTES.toMillis(newTime)), false);
                updateTimeOverview();
                resetPicker();
            }
        });
    }

    private void setupSubtractButton() {
        ((Button) findViewById(R.id.btSubtract)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newTime = Calculator.stringToInt(
                        NUMBERS[numberPicker.getValue()]
                );
                applicationStorage.updateTime(new Duration(TimeUnit.MINUTES.toMillis(newTime)), true);
                updateTimeOverview();
                resetPicker();
            }
        });
    }

    private void setupPickers() {
        numberPicker = (NumberPicker) findViewById(R.id.timePicker);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(NUMBERS.length - 1);
        numberPicker.setDisplayedValues(NUMBERS);
        numberPicker.setWrapSelectorWheel(false);
        resetPicker();
    }

    private void resetPicker() {
        numberPicker.setValue(NUMBERS.length - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTimeOverview();
    }

    private void updateTimeOverview() {
        Duration minutes = applicationStorage.loadTime();
        tvTime.setText(TIME_FORMATTER.print(minutes.toPeriod()));
        timeOverview.setBackgroundResource(minutes.getMillis() < 0 ? R.drawable.fade_red : R.drawable.fade_green);
        lastUpdated.setText(getString(R.string.last_updated) + ": " + applicationStorage.loadModified());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            Toast.makeText(this, "Flextime 1.2 by Joakim Lahtinen", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.action_reset) {
            applicationStorage.reset();
            updateTimeOverview();
            resetPicker();
        }
        return super.onOptionsItemSelected(item);
    }
}
