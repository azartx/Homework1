package com.gameproject.homework6_2

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var onImageClick: RecyclerViewAdapter.OnImageClickListener
    private lateinit var recycler: RecyclerView
    private lateinit var addImage: FloatingActionButton
    private lateinit var localAdapter: RecyclerViewAdapter
    private lateinit var alertView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.recycler)
        addImage = findViewById(R.id.addNewImage)

        onImageClick = object : RecyclerViewAdapter.OnImageClickListener {
            override fun onImageClick(imageObject: ImageObject, position: Int) {
                Intent(applicationContext, FullImageActivity::class.java).apply {
                    putExtra("image", imageObject.imageUri)
                    startActivityForResult(this, 2)
                }
            }
        }

        val localLayoutManager = GridLayoutManager(this, 2)
        localAdapter = RecyclerViewAdapter(this, ArrayList(), onImageClick)
        recycler.apply {
            layoutManager = localLayoutManager
            adapter = localAdapter
        }

        // добавление картинки
        addImage.setOnClickListener {
            choiceImageUploadOption()
        }

    }

    private fun getPictureFromGallery() {
        Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(this, "select picture"), 1)
        }
    }

    @SuppressLint("InflateParams")
    private fun choiceImageUploadOption() {
        val li = this.layoutInflater
        val dialog = AlertDialog.Builder(this)
        alertView = li.inflate(R.layout.item_get_url_dialog_alert, null)
        val editText = alertView.findViewById<EditText>(R.id.input_text)

        dialog.apply {
            setView(alertView)
            setTitle(getString(R.string.upload_picture_alert_title))
            setMessage(getString(R.string.alertMessage))
            // choice 1
            setNegativeButton(getString(R.string.alert_get_iname_from_gallery)) { _, _ ->
                getPictureFromGallery()
            }
            // choice 2
            setPositiveButton(getText(R.string.ok_button)) { dialog, _ ->
                if (editText.text.isEmpty()) {
                    dialog.cancel()
                    Log.i("FFFF", "is empty")
                } else {
                    val getUri = Uri.parse(editText.text.toString())
                    if (Patterns.WEB_URL.matcher(getUri.toString()).matches()) {
                        localAdapter.imageList.add(ImageObject(getUri.toString()))
                    } else {
                        Log.i("FFFF", "incorrect uri")
                        dialog.cancel()
                    }
                }
            }
            create()
            show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // интент из галареи
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            localAdapter.apply {
                imageList.add(ImageObject(selectedImageUri.toString()))
                notifyDataSetChanged()
            }
        }

    }

}