package com.happiestminds.remainderactivity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.happiestminds.remainderactivity.ShowReminder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun remainderClick(view: View) {
        val remainderIntent = Intent(this,addRemainderActivity().javaClass)
        startActivity(remainderIntent)
    }

    fun displayClick(view: View) {
        val showIntent=Intent(this,ShowReminder().javaClass)
        startActivity(showIntent)
    }

    override fun onResume() {
        if (checkSelfPermission(Manifest.permission.READ_CALENDAR)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR),1)
        }
        super.onResume()
    }
}