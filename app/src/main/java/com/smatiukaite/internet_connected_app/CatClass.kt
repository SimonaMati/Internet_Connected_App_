package com.smatiukaite.internet_connected_app

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

const val NEW_LIMIT_BY_USER3 = "csc244.switchingactivity.CAT_CLASS"

class CatClass : AppCompatActivity() {
    val url = "https://api.thecatapi.com/v1/images/search"
    var catImage: ImageView? = null
    var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat)

        //Initializing variables
        val view = View(this)
        val setButton2 = findViewById<Button>(R.id.button_Cat_Back)

        //On Set button click
        setButton2.setOnClickListener {
            goToMainActivity(view)
        }

        //SHOWING A DOG PICTURE
        catImage = findViewById<ImageView>(R.id.imageView_Cat)

        DownloadImage().execute(url)
    }

    private inner class DownloadImage : AsyncTask<String, Void, Bitmap>() {

        override fun onPreExecute() {
            super.onPreExecute()
            mProgressDialog = ProgressDialog(this@CatClass)
            mProgressDialog!!.setTitle("Download Image Tutorial")
            mProgressDialog!!.setMessage("Loading...")
            mProgressDialog!!.setIndeterminate(false)
            mProgressDialog!!.show()
        }

        override fun doInBackground(vararg URL: String): Bitmap? {
            val imageURL = URL[0]
            var bitmap: Bitmap? = null
            try {
                // Download Image from URL
                val input = URL(imageURL).openStream()
                val jsonString = input.bufferedReader().use { it.readText() }
                // Parse JSON response to get the image URL
                val jsonArray = JSONArray(jsonString)
                val imageUrl = jsonArray.getJSONObject(0).getString("url")
                val imageStream = URL(imageUrl).openStream()
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(imageStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap
        }

        override fun onPostExecute(result: Bitmap?) {
            // Set the bitmap into ImageView
            catImage?.setImageBitmap(result)
            // Close progressdialog
            mProgressDialog!!.dismiss()
        }
    }

    //Take the new limit number to the Main Activity
    fun goToMainActivity(view: View) {
        val pageName = "You last saw a cat"

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(NEW_LIMIT_BY_USER3, pageName)
        }
        startActivity(intent)
    }

}