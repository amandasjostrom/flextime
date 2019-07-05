package com.lahtinen.apps.flextime

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast

import org.joda.time.Duration

import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object{
        internal val NUMBER_PICKER_VALUES = Calculator.buildPickerTimes()
        internal val TAG: String = MainActivity::class.java.simpleName
        internal val TIME_FORMATTER = TimeFormatter()
    }
    private var applicationStorage: ApplicationStorage? = null
    private var numberPicker: NumberPicker? = null
    private var tvTime: TextView? = null
    private var lastUpdated: TextView? = null
    private var timeOverview: LinearLayout? = null
    private val timeformatter: TimeFormatter? = TimeFormatter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applicationStorage = ApplicationStorage(this)
        tvTime = findViewById(R.id.tvTime)
        lastUpdated = findViewById(R.id.lastUpdated)
        timeOverview = findViewById(R.id.timeOverview)
        setupAddButton()
        setupSubtractButton()
        setupPickers()
    }

    private fun setupAddButton() {
        val addTime = UpdateTimeOnClickListener(false)
        findViewById<View>(R.id.btAdd).setOnClickListener(addTime)
    }

    private fun setupSubtractButton() {
        val subtractTime = UpdateTimeOnClickListener(true)
        findViewById<View>(R.id.btSubtract).setOnClickListener(subtractTime)
    }

    private fun setupPickers() {
        numberPicker = findViewById(R.id.timePicker)
        numberPicker!!.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        numberPicker!!.minValue = 0
        numberPicker!!.maxValue = NUMBER_PICKER_VALUES.size - 1
        numberPicker!!.displayedValues = NUMBER_PICKER_VALUES
        numberPicker!!.wrapSelectorWheel = false
        resetPicker()
    }

    private fun resetPicker() {
        numberPicker!!.value = NUMBER_PICKER_VALUES.size - 1
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        updateTimeOverview()
    }

    private fun updateTimeOverview() {
        val minutes = applicationStorage!!.loadTime()
        val newFlextime = TIME_FORMATTER.format(minutes)
        Log.d(TAG, "Setting text to $newFlextime")
        tvTime!!.text = newFlextime
        timeOverview!!.setBackgroundResource(if (minutes.millis < 0) R.drawable.fade_red else R.drawable.fade_green)
        lastUpdated!!.text = getString(R.string.last_updated) + ": " + applicationStorage!!.loadModified()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_about) {
            Toast.makeText(this, "Flextime 1.2 by Joakim Lahtinen", Toast.LENGTH_LONG).show()
        } else if (item.itemId == R.id.action_reset) {
            reset()
            updateTimeOverview()
            resetPicker()
        }
        return super.onOptionsItemSelected(item)
    }

    internal fun reset() {
        applicationStorage!!.reset()
    }

    internal inner class UpdateTimeOnClickListener(var isNegative: Boolean) : View.OnClickListener {

        override fun onClick(v: View) {
            val selectedTime = NUMBER_PICKER_VALUES[numberPicker!!.value]
            val timeInMinutes = Calculator.timeStringToMinutesInt(selectedTime)
            applicationStorage!!.updateTime(Duration(TimeUnit.MINUTES.toMillis(timeInMinutes.toLong())), isNegative)
            updateTimeOverview()
            resetPicker()
        }
    }

}
