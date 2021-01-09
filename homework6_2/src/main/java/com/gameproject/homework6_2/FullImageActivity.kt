package com.gameproject.homework6_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FullImageActivity : AppCompatActivity() {

    private lateinit var imageUri: String
    private lateinit var imageImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)
        imageImageView = findViewById(R.id.imageImageView)

        findViewById<FloatingActionButton>(R.id.backButton).setOnClickListener { finish() }

        imageUri = intent.getStringExtra("image").toString()
        installImage()

    }

    private fun installImage() {
        Glide.with(applicationContext)
                .load(imageUri)
                .error(R.drawable.glide_error)
                .into(imageImageView)
    }

}