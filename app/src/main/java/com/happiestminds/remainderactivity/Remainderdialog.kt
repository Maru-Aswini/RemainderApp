package com.happiestminds.remainderactivity

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class Remainderdialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dlg: Dialog? = null

//        val remd = Remind(title!!,description!!,time!!,date!!)
        //retrieve bundle
        val message = arguments?.getString("msg")
        val idx = arguments?.getInt("idx") ?: 0
        val title=arguments?.getString("id")
        val descript=arguments?.getString("descript")
        val date=arguments?.getString("date")
        val time=arguments?.getString("time")
        val reminder=Reminder(title!!,descript!!,date!!,time!!)

        //create dialog here
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("")
            builder.setMessage(message)
            builder.setPositiveButton("ok") { dialog, i ->
                //executed when the button is clicked

//                DBWrapper(it).deleteRemainder(remd)
                activity?.finish()

            }
            builder.setNegativeButton("Delete") { dialog, i ->
                DBWrapper(it).deleteReminder(reminder)
                reminderList.remove(reminderList[idx])
                activity?.finish()

            }
            dlg = builder.create()
        }
        return dlg!!
    }
}


