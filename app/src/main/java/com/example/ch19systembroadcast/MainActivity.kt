package com.example.ch19systembroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.lang.IllegalArgumentException
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    lateinit var intentFilter: IntentFilter
    lateinit var timereceiver: TimeReceiver
    var current_count = 0

    val Log = Logger.getLogger(javaClass.name)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timereceiver = TimeReceiver()
        intentFilter = IntentFilter(Intent.ACTION_TIME_TICK)
    }

    override fun onResume() {
        super.onResume()

        Log.info("App is resuming")
        registerReceiver(timereceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()

        Log.info("App is paused")
        try {
            unregisterReceiver(timereceiver)
        }
        catch (iae:IllegalArgumentException) {
            Log.warning(iae.toString())
        }
    }

    inner class TimeReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            current_count +=1
            var message = "counter:${current_count}"
            Log.info(message)
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
        }
    }
}