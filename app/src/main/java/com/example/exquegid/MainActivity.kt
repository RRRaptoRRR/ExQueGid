package com.example.exquegid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val autorizeButton=findViewById<Button>(R.id.autorize)
        autorizeButton.setOnClickListener {
            val intent = Intent(this, AutorizeActivity::class.java)
            startActivity(intent)
        }
        val signInButton=findViewById<Button>(R.id.signIn2)

        signInButton.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
    }
}