package com.happiestminds.remainderactivity

import MyDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.View
import android.widget.*

class addRemainderActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    lateinit var titleEditText:EditText
    lateinit var descriptEditText: EditText

    lateinit var dateButton: Button
    lateinit var timeButton: Button

  //   var date = "dd-mm-yy"
   // var time ="hh-mm"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_remainder)

        titleEditText = findViewById(R.id.titleT)
        descriptEditText= findViewById(R.id.descriptionT)

        timeButton = findViewById(R.id.timeB)
        dateButton = findViewById(R.id.dateB)
    }

    fun dateClick(view: View) {

            val dlg = DatePickerDialog(this)
            dlg.setOnDateSetListener { dPicker, year, month, day ->
                dateButton.text= "$day-${month+1}-$year"

    }
        dlg.show()

    }
    fun timeClick(view: View) {
        val dlg = TimePickerDialog(this,this,10,
            0,true)
        dlg.show()

    }

    override fun onTimeSet(p0: TimePicker?, hh: Int, mm: Int) {
        timeButton.text = "$hh:$mm"
    }



    fun submitClick(view: View) {
        var title = titleEditText.text.toString()
        var description = descriptEditText.text.toString()
        var date = dateButton.text.toString()
        var time = timeButton.text.toString()
        if (titleEditText.text.isNotEmpty()) {
            val dlg = MyDialog()

            dlg.isCancelable = false
            val dataBundle = Bundle()
            dataBundle.putString(
                "msg", """DO you want to submit this?
       |Title: ${titleEditText.text}
       |Description:${descriptEditText.text}
       |Date: ${dateButton.text}
       |Time:${timeButton.text}
    """.trimMargin()
            )

            dlg.arguments = dataBundle// passing argument to other class
            dlg.show(supportFragmentManager, null)
            val title = titleEditText.text.toString()
//            val reminderObject =
//                Reminder(
//                    title.toString(), descriptEditText.text.toString(), dateButton.text.toString(),
//                    timeButton.text.toString()
//                )
//            reminderList.add(reminderObject)
            val remd = Reminder(title, description, date, time)
            if (DBWrapper(this).addRemainder(remd))
            else {

                Toast.makeText(this, "Please enter the reminder", Toast.LENGTH_LONG).show()
            }

            val dateString = "${dateButton.text} ${timeButton.text.toString()}"

            Log.d("AddReminder", "$dateString")
            val format = SimpleDateFormat("dd-MM-yyyy HH:mm")
            val date = format.parse(dateString)
            val cal = Calendar.getInstance()
            cal.time = date
            Log.d("Add reminder", "milli: ${cal.timeInMillis}")

            var value = ContentValues();



            value.put(CalendarContract.Events.DTSTART, cal.timeInMillis)
            value.put(CalendarContract.Events.DTEND, cal.timeInMillis + 60 * 1000);
            value.put(CalendarContract.Events.TITLE, titleEditText.text.toString());
            value.put(CalendarContract.Events.DESCRIPTION, descriptEditText.text.toString());
            value.put(CalendarContract.Events.CALENDAR_ID, 1);
            value.put(CalendarContract.Events.EVENT_TIMEZONE, "IST")
            value.put(CalendarContract.Events.HAS_ALARM, 1)

            var uri1 = contentResolver.insert(CalendarContract.Events.CONTENT_URI, value);
            Log.d("Add Reminder", "calenderBtnClick:  $uri1")
            val evenID = uri1?.lastPathSegment?.toInt()



            Toast.makeText(this, "Task Scheduled Successfully", Toast.LENGTH_SHORT).show();


            val cr: ContentResolver = contentResolver
            var values = ContentValues();
            values.put(CalendarContract.Reminders.EVENT_ID, evenID);
            values.put(
                CalendarContract.Reminders.METHOD,
                CalendarContract.Reminders.METHOD_DEFAULT
            );
            values.put(
                CalendarContract.Reminders.MINUTES,
                CalendarContract.Reminders.MINUTES_DEFAULT
            );
            val reminderUri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);



            Log.d("Add reminder", "reminder uri: $reminderUri")
        } else {
            Toast.makeText(this, "enter the remainder", Toast.LENGTH_LONG).show()
            //  val newReminder = reminderList(titleEditText.text.toString(), descriptEditText.text.toString(), date,
            // time)
        }
    }

            fun cancelClick(view: View) {
                Log.d("cancel", "cancel clicked")
            }
        }












