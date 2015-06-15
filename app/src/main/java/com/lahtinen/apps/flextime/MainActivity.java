package com.lahtinen.apps.flextime;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static final String[] NUMBERS = Calculator.buildPickerNumbers();

    private ApplicationStorage applicationStorage;
    private NumberPicker numberPicker;
    private Button btCommit;
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applicationStorage = new ApplicationStorage(this);
        tvTime = (TextView) findViewById(R.id.tvTime);
        setupCommitButton();
        setupPickers();
    }

    private void setupCommitButton() {
        btCommit = (Button) findViewById(R.id.btCommit);
        btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newTime = Calculator.stringToInt(
                        NUMBERS[numberPicker.getValue()]
                );
                int oldTime = applicationStorage.loadTime();
                if (applicationStorage.saveTime(oldTime + newTime)) {
                    updateTimeText();
                    resetPicker();
                }
            }
        });
    }

    private void setupPickers() {
        numberPicker = (NumberPicker) findViewById(R.id.timePicker);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(38);
        numberPicker.setDisplayedValues(NUMBERS);
        numberPicker.setWrapSelectorWheel(false);
        resetPicker();
    }

    private void resetPicker() {
        numberPicker.setValue(NUMBERS.length / 2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTimeText();
    }

    private void updateTimeText() {
        tvTime.setText(Calculator.intMinutesToString(applicationStorage.loadTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            Toast.makeText(this, "Flextime 1.0-beta by Joakim Lahtinen", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
