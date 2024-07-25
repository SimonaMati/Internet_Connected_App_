package com.smatiukaite.internet_connected_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.util.Objects
import kotlin.math.log
import kotlin.random.Random

const val NEW_LIMIT_BY_USER1 = "csc244.switchingactivity.JOKE_CLASS"

class JokeClass : AppCompatActivity() {
    private lateinit var textJoke: TextView
    private lateinit var mQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joke)

        // Initializing TextView
        textJoke = findViewById(R.id.textView_Joke)

        // Initializing variables
        val view = View(this)
        val setButton = findViewById<Button>(R.id.button_Joke_Back)

        //On Set button click
        setButton.setOnClickListener {
            goToMainActivity(view)
        }

        jsonParse()

    }

    private fun jsonParse() {
        val url = "https://www.myjsons.com/v/9079936"
        mQueue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val jsonArray = response.getJSONArray("dogJokes")
                val prefs = getSharedPreferences("joke_prefs", Context.MODE_PRIVATE)
                var currentIndex = prefs.getInt("current_index", 0)

                if (currentIndex >= jsonArray.length()) {
                    currentIndex = 0
                }

                var aJoke = jsonArray.getJSONObject(currentIndex).toString()

                //Remove suffix and prefix
                aJoke = aJoke.removeSuffix("\"}")
                aJoke = aJoke.substring(6)

                textJoke.text = aJoke

                val editor = prefs.edit()
                editor.putInt("current_index", currentIndex + 1)
                editor.apply()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error ->
            error.printStackTrace()
        })

        mQueue.add(request)
    }



    fun doInBackground(vararg URL: String): String? {
        val imageURL = URL[0]
        var joke: String? = null
        try {
            // Download JSON from URL
            val input = URL(imageURL).openStream()
            val jsonString = input.bufferedReader().use { it.readText() }
            // Parse JSON response to get a random joke
            val jsonArray = JSONObject(jsonString).getJSONArray("dogJokes")
            val randomIndex = (0 until jsonArray.length()).random()
            joke = jsonArray.getJSONObject(randomIndex).getString(randomIndex.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return joke
    }

    //Take the new limit number to the Main Activity
    fun goToMainActivity(view: View) {
        val pageName = "You last saw a joke"

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(NEW_LIMIT_BY_USER1, pageName)
        }
        startActivity(intent)
    }
}
