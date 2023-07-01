package com.example.exquegid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val goToExListButton=findViewById<Button>(R.id.Excursion)
        goToExListButton.setOnClickListener {
            val intent = Intent(this, GoOrMakeActivity::class.java).putExtra("Type", true)
            startActivity(intent)
        }
        val goToQueListButton=findViewById<Button>(R.id.Quest)
        goToQueListButton.setOnClickListener {
            val intent = Intent(this, GoOrMakeActivity::class.java).putExtra("Type", false)
            startActivity(intent)
        }
    }
}