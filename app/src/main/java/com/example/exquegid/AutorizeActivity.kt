package com.example.exquegid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class AutorizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autorize)
        val autorizeButton=findViewById<Button>(R.id.autorize2)
        var DB= Firebase.firestore
        var inputLogin=findViewById<EditText>(R.id.inputLogin2)
        var inputEmail=findViewById<EditText>(R.id.inputEmail)
        var inputPassword=findViewById<EditText>(R.id.inputPassword2)
        val TAG="database"
        autorizeButton.setOnClickListener {
            var login = inputLogin.getText().toString()
            var email = inputEmail.getText().toString()
            var password = inputPassword.getText().toString()

            if (login=="" || password==""|| email==""){
                Toast.makeText(this, "Вы не ввели некоторые данные", Toast.LENGTH_LONG).show()
            }

            else{
                val user = hashMapOf(
                    "E-mail" to email,
                    "Password" to password
                )
                var flag=true
                DB.collection("Users").get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            Log.d(TAG, "${document.id}&$login")
                            if(document.id==login){
                                Log.d(TAG, "${document.id}&$login, proverka prosla")
                                Toast.makeText(this, "Пользователь с таким логином уже существует", Toast.LENGTH_LONG).show()
                                flag=false
                                break
                            }
                        }
                        if (flag){
                            DB.collection("Users").document("$login")
                                .set(user)
                                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                            val intent = Intent(this, StartActivity::class.java)
                            startActivity(intent)
                        }
                    }
            }

        }
    }
}