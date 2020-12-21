package com.example.homework4_2

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), CircleCustomView.OnTouchCustomClickListener {

    private var checkBox: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val circleCustomView = findViewById<CircleCustomView>(R.id.circleView)
        checkBox = findViewById(R.id.checkBoxSnackBar)
        circleCustomView.customListenerData(this)

    }

    override fun onClickCoords(view: View, x: Int, y: Int, color: Int) {
        when (checkBox?.isChecked) {
            true -> Snackbar.make(view, getString(R.string.koords, x, y), Snackbar.LENGTH_LONG)
                    .setTextColor(color)
                    .show()
            false -> Toast.makeText(applicationContext,
                    getString(R.string.koords, x, y),
                    Toast.LENGTH_SHORT)
                    .show()
        }
    }
}

