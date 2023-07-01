package com.example.exquegid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class GoOrMakeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_or_make)
        var type:Boolean=intent.getBooleanExtra("Type", true)
        val goButton=findViewById<Button>(R.id.go)
        goButton.setOnClickListener {
            if(type){//go to ex list

                val intent = Intent(this, ListActivity::class.java).putExtra("Type", true)
                startActivity(intent)
            }
            else{// go to que list
                val intent = Intent(this, ListActivity::class.java).putExtra("Type", false)
                startActivity(intent)
            }

        }
        val makeButton=findViewById<Button>(R.id.make)
        makeButton.setOnClickListener {
            if(type){//go to make ex
                val intent = Intent(this, ListOfMake::class.java).putExtra("Type", true)
                startActivity(intent)
            }
            else{//go to make que
                val intent = Intent(this, ListOfMake::class.java).putExtra("Type", false)
                startActivity(intent)
            }

        }

    }
}


