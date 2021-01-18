package com.cyphercubecorporation.calculator

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        button2.setOnClickListener {
            var emailIntent = Intent(Intent.ACTION_SEND);

            /* Fill it with Data */
            emailIntent.data= Uri.parse("mailto:")
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("cyphercubecorporation@gmail.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Calculator App Review");

            /* Send it off to the Activity-Chooser */
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        }

        button3.setOnClickListener {
            try{
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)))
            }catch ( e : ActivityNotFoundException){
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)))
            }
        }
    }
}
