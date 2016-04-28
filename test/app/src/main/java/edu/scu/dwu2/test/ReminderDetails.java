package edu.scu.dwu2.test;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by Danni on 3/4/16.
 */
public class ReminderDetails extends Activity {
    private ReminderModel reminderDetails;
    private TimePicker timePicker;
    private EditText edtName;
    private ReminderCustomSwitch chkWeekly;
    private ReminderCustomSwitch chkSunday;
    private TextView txtToneSelection;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_desc);
        timePicker = (TimePicker) findViewById(R.id.reminder_time_picker);
        edtName = (EditText) findViewById(R.id.reminder_name);
        chkWeekly = (ReminderCustomSwitch) findViewById(R.id.reminder_repeat_weekly);
        chkSunday = (ReminderCustomSwitch) findViewById(R.id.reminder_repeat_sunday);
        txtToneSelection = (TextView) findViewById(R.id.reminder_label_tone_selection);
        chkWeekly.setChecked(reminderDetails.repeatWeekly);
        chkSunday.setChecked(reminderDetails.getRepeatingDay(ReminderModel.SUNDAY));
        txtToneSelection.setText(RingtoneManager.getRingtone(this, reminderDetails.reminderTone).getTitle(this));
    }
    final LinearLayout ringToneContainer = (LinearLayout)findViewById(R.id.reminder_ringtone_container);
    ringToneContainer.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            startActivityForResult(intent , 1); }
    });	}
    private void updateModelFromLayout() {
        reminderDetails.timeMinute = timePicker.getCurrentMinute().intValue();
        reminderDetails.timeHour = timePicker.getCurrentHour().intValue();
        reminderDetails.name = edtName.getText().toString();
        reminderDetails.repeatWeekly = chkWeekly.isChecked();
        reminderDetails.setRepeatingDay(ReminderModel.SUNDAY, chkSunday.isChecked());
        reminderDetails.isEnabled = true;
    }

