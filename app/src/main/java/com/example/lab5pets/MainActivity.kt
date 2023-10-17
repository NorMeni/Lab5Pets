package com.example.lab5pets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    var petImageURL = ""
    lateinit var button: Button
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.petButton)
        imageView = findViewById(R.id.imageView3)

        getDogImageURL()
        getNextImage()
        Log.d("petImageURL", "pet image URL set")
    }

    private fun getDogImageURL() {
        val client = AsyncHttpClient()
        client["https://dog.ceo/api/breeds/image/random", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                petImageURL = json.jsonObject.getString("message")
                Log.d("Dog", "response successful$json")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }
        }]
    }

    private fun getNextImage() {
        val localPetImageURL = petImageURL  // Create a local reference

        button.setOnClickListener {
            Glide.with(this)
                .load(localPetImageURL)
                .fitCenter()
                .into(imageView)
        }
    }
}