package com.example.exquegid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        var inputLogin=findViewById<EditText>(R.id.inputLogin)
        var inputPassword=findViewById<EditText>(R.id.inputPassword)
        var DB= Firebase.firestore
        val TAG="database"
        val signInButton=findViewById<Button>(R.id.signIn2)
        signInButton.setOnClickListener {
            var login = inputLogin.getText().toString()
            var password = inputPassword.getText().toString()
            if (login=="" || password==""){
                Toast.makeText(this, "Вы не ввели некоторые данные", Toast.LENGTH_LONG).show()
            }
            else{
                var flag=false
                val TAG2="bool"
                val tag="checking"
                DB.collection("Users")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            Log.d(tag, "${document.id.toString()}&$login")
                            if(document.id.toString()==login){
                                Log.d(tag, "${document.id.toString()}&$login")
                                if(document.data["Password"]==password){
                                    Log.d(tag, "${document.data["Password"]}&$password")
                                    flag=true
                                    Log.d(TAG2, "$flag")

                                    break
                                }
                            }
                        }
                        if (flag==true){
                            val intent = Intent(this, StartActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Log.d(TAG2, "$flag")
                            Toast.makeText(this, "Неправильный логин и/или пароль", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "Error getting documents: ", exception)
                    }
            }



        }
    }
}