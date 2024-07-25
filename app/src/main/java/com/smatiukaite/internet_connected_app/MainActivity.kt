package com.smatiukaite.internet_connected_app

/**************************
Class: CSC244
Student: Simona Matiukaite
Project: Internet Connected App
 ***************************/

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

const val HISTORY_EXTRA = "csc244.switchingactivity.ARRAY_LIST"
const val STRING_DATA = "com.smatiukaite.saveddata.DATA"
var fis: FileInputStream? = null

class MainActivity : AppCompatActivity() {
    private var FILE_NAME = "example.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val view = View(this)
        var mainTitle = findViewById<TextView>(R.id.text_beginning)

        val jokeTitle = findViewById<Button>(R.id.button_Joke)
        jokeTitle.setOnClickListener {
            goToJokeClass(view)
        }

        val dogTitle = findViewById<Button>(R.id.button_Dog)
        dogTitle.setOnClickListener {
            goToDogClass(view)
        }

        val catTitle = findViewById<Button>(R.id.button_Cat)
        catTitle.setOnClickListener {
            goToCatClass(view)
        }

        var passedMessage1 = intent.getStringExtra(NEW_LIMIT_BY_USER1).toString()
        var passedMessage2 = intent.getStringExtra(NEW_LIMIT_BY_USER2).toString()
        var passedMessage3 = intent.getStringExtra(NEW_LIMIT_BY_USER3).toString()

        if(passedMessage1 != "null" && passedMessage2 == "null" && passedMessage3 == "null"){
            mainTitle.visibility = View.VISIBLE
            passedMessage1 = intent.getStringExtra(NEW_LIMIT_BY_USER1).toString()
            mainTitle.setText(passedMessage1)
        }else if (passedMessage2 != "null" && passedMessage1 == "null" && passedMessage3 == "null"){
            mainTitle.visibility = View.VISIBLE
            passedMessage2 = intent.getStringExtra(NEW_LIMIT_BY_USER2).toString()
            mainTitle.setText(passedMessage2)
        }else if(passedMessage3 != "null" && passedMessage1 == "null" && passedMessage2 == "null"){
            mainTitle.visibility = View.VISIBLE
            passedMessage3 = intent.getStringExtra(NEW_LIMIT_BY_USER3).toString()
            mainTitle.setText(passedMessage3)
        }

//        if(!(passedMessage1 == "null") && passedMessage2.equals("null")
//            && passedMessage3.equals("null")){

            //SAVING INTO THE FILE
            val text: String = mainTitle.text.toString()
            var fos: FileOutputStream? = null

        if(text.isNotBlank()) {
            try {
                Log.d("Entering mode ", "SAVE")
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE)
                fos.write(text.toByteArray())

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                fos?.let {
                    try {
                        it.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
//        }

        Log.d(passedMessage1, " 1")
        Log.d(passedMessage2, " 2")
        Log.d(passedMessage3, " 3")

            //LOAD THE FILE
            mainTitle.visibility = View.VISIBLE
            var fis: FileInputStream? = null

            try {
                Log.d("Entering mode ", "LOAD")
                fis = openFileInput(FILE_NAME)
                val isr = InputStreamReader(fis)
                val br = BufferedReader(isr)
                val sb = StringBuilder()
                var text: String?

                while (br.readLine().also { text = it } != null) {
                    sb.append(text).append("\n")
                    mainTitle.setText(sb.toString())
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                fis?.let {
                    try {
                        it.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
    }


//    fun getMessage(message1: String, message2: String, message3: String){
//        if(message1 != "null" && message2 == "null" && message3 == "null"){
//            mainTitle.visibility = View.VISIBLE
//            message1 = intent.getStringExtra(NEW_LIMIT_BY_USER1).toString()
//            mainTitle.setText(message1)
//    }

    //Go to the Joke Class
    fun goToJokeClass(view: View) {
        val intent = Intent(this, JokeClass::class.java).apply {
        }
        startActivity(intent)
    }

    //Go to the Dog Class
    fun goToDogClass(view: View) {
        val intent = Intent(this, DogClass::class.java).apply {
        }
        startActivity(intent)
    }

    //Go to the Cat Class
    fun goToCatClass(view: View) {
        val intent = Intent(this, CatClass::class.java).apply {
        }
        startActivity(intent)
    }

    override fun onPause() {
        Log.d("PAUSE", "THIS")

        var mainTitle = findViewById<TextView>(R.id.text_beginning)
        var fis: FileInputStream? = null

        try {
            Log.d("Entering mode ", "LOAD ON PAUSE")
            fis = openFileInput(FILE_NAME)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            val sb = StringBuilder()
            var text: String?

            while (br.readLine().also { text = it } != null) {
                sb.append(text).append("\n")
                mainTitle.setText(sb.toString())
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fis?.let {
                try {
                    it.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        super.onPause()
    }

    override fun onDestroy() {
        Log.d("DESTROY", "THIS")
        super.onDestroy()
    }

}