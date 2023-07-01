package com.example.exquegid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.LatLng

class ListOfMake : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_make)
        var number =findViewById<TextView>(R.id.number)
        var Prompt=findViewById<TextView>(R.id.prompt)
        var inputPromt=findViewById<EditText>(R.id.inputPromtOfPoint)
        var inputName=findViewById<EditText>(R.id.inputNameOfPoint)
        var inputDiscription=findViewById<EditText>(R.id.inputDiscriptionOfPoint)
        var inputLength=findViewById<EditText>(R.id.InputLengthOfPoint)
        var inputWidth=findViewById<EditText>(R.id.InputWidthOfPoint)
        var type:Boolean=intent.getBooleanExtra("Type", true)
        var ind=0
        var makeExData=ExcursionDataClass("0",  "0", mutableListOf(), mutableListOf(), mutableListOf())
        var makeQueData=QuestDataClass("0",  "0", mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())
        if (type==true){//
            Prompt.visibility=GONE
            inputPromt.visibility=GONE
        }
        var save =findViewById<Button>(R.id.save)
        var next =findViewById<Button>(R.id.nextPoint)
        var previous =findViewById<Button>(R.id.previousPoint)
        previous.visibility= GONE
        var DB= Firebase.firestore
        save.setOnClickListener {
            var flag=true
            var name = inputName.getText().toString()
            var discription = inputDiscription.getText().toString()
            var length= inputLength.getText().toString().toDouble()
            var width= inputWidth.getText().toString().toDouble()
            var prompt= inputPromt.getText().toString()
            if (type!=true) {//
                if (name == "" || discription == "" || length == null || width == null || prompt == "") {//ввели ли все данные, квест
                    Toast.makeText(this, "Вы не ввели некоторые данные", Toast.LENGTH_LONG).show()
                    flag=false
                }
            }
            else {
                    if (name=="" || discription==""|| length==null || width==null){//ввели ли все данные, экс
                        Toast.makeText(this, "Вы не ввели некоторые данные", Toast.LENGTH_LONG).show()
                        flag=false
                    }
            }
            if(flag){
                if (ind==makeQueData.names.size||ind==makeExData.names.size){
                    if(type!=true){//quest
                        makeQueData.discriptions.add(discription)
                        makeQueData.prompts.add(prompt)
                        makeQueData.names.add(name)
                        makeQueData.points.add(com.google.android.gms.maps.model.LatLng(width, length))
                    }
                    else{
                        makeExData.discriptions.add(discription)
                        makeExData.names.add(name)
                        makeExData.points.add(com.google.android.gms.maps.model.LatLng(width, length))

                    }
                }
                if(type!=true){
                    var kol=-1
                    DB.collection("Quest").get().addOnSuccessListener { result ->
                        for (document in result) {
                            kol+=1
                        }
                        val quest= hashMapOf(
                            "discriptions" to makeQueData.discriptions,
                            "prompts" to makeQueData.prompts,
                            "points" to makeQueData.points
                    )
                        DB.collection("Quest").document("$kol").set(quest)
                    }
                }
                else{
                    var kol=0
                    var count:Int
                    DB.collection("Excursion").get().addOnSuccessListener { result ->
                        for (document in result) {
                            kol+=1
                        }
                        count=kol
                        val excursion= hashMapOf(
                            "discriptions" to makeExData.discriptions,
                            "names" to makeExData.names,
                            "points" to makeExData.points
                    )
                        DB.collection("Excursion").document("$kol").set(excursion)
                    }
                }
            }
        }
        next.setOnClickListener {
            var name = inputName.getText().toString()
            var discription = inputDiscription.getText().toString()
            var length= inputLength.getText().toString().toDouble()
            var width= inputWidth.getText().toString().toDouble()
            var prompt= inputPromt.getText().toString()
            var flag=true
            if (type!=true){//квест
                if (name=="" || discription==""|| length== null || width==null || prompt==""){//ввели ли все данные, квест
                    Toast.makeText(this, "Вы не ввели некоторые данные", Toast.LENGTH_LONG).show()
                    flag=false
                }
            }
            else {
                if (name=="" || discription==""|| length==null || width==null){//ввели ли все данные, экс
                    Toast.makeText(this, "Вы не ввели некоторые данные", Toast.LENGTH_LONG).show()
                    flag=false
                }
            }
            if(flag){
                ind+=1
                Log.d("length", "${makeExData.names} && ${makeExData.names.size} && $ind")
                if(ind<makeQueData.names.size||ind<makeExData.names.size){
                    inputName.setText("${if(type)makeExData.names[ind] else makeQueData.names[ind]}")
                    inputDiscription.setText("${if(type)makeExData.discriptions[ind] else makeQueData.discriptions[ind]}")
                    inputLength.setText("${if(type)makeExData.points[ind].longitude else makeQueData.points[ind].longitude}")
                    inputWidth.setText("${if(type)makeExData.points[ind].latitude else makeQueData.points[ind].latitude}")
                    inputPromt.setText("${if(type)"" else makeQueData.prompts[ind]}")
                    number.setText("${ind+1}")
                    previous.visibility=VISIBLE
                }
                else{
                    if(type!=true){//quest
                        makeQueData.discriptions.add(discription)
                        makeQueData.prompts.add(prompt)
                        makeQueData.names.add(name)
                        makeQueData.points.add(com.google.android.gms.maps.model.LatLng(width, length))
                    }
                    else{
                        makeExData.discriptions.add(discription)
                        makeExData.names.add(name)
                        makeExData.points.add(com.google.android.gms.maps.model.LatLng(width, length))
                    }
                    inputName.setText("")
                    inputDiscription.setText("")
                    inputLength.setText("")
                    inputWidth.setText("")
                    inputPromt.setText("")
                    number.setText("${ind+1}")
                    previous.visibility=VISIBLE
                }
            }
        }
        previous.setOnClickListener {
            var flag=true
            var name = inputName.getText().toString()
            var discription = inputDiscription.getText().toString()
            var length= inputLength.getText().toString().toDouble()
            var width= inputWidth.getText().toString().toDouble()
            var prompt= inputPromt.getText().toString()
            if (type!=true){//квест
                if (name=="" || discription==""|| length==null || width==null || prompt==""){//ввели ли все данные, квест
                    Toast.makeText(this, "Вы не ввели некоторые данные", Toast.LENGTH_LONG).show()
                    flag=false
                }
            }
            else {
                if (name=="" || discription==""|| length==null || width==null){//ввели ли все данные, экс
                    Toast.makeText(this, "Вы не ввели некоторые данные", Toast.LENGTH_LONG).show()
                    flag=false
                }
            }
            if (flag){
                if (ind==makeExData.names.size || ind==makeQueData.names.size){
                    if(type!=true){//quest
                        makeQueData.discriptions.add(discription)
                        makeQueData.prompts.add(prompt)
                        makeQueData.names.add(name)
                        makeQueData.points.add(com.google.android.gms.maps.model.LatLng(width, length))
                    }
                    else{
                        makeExData.discriptions.add(discription)
                        makeExData.names.add(name)
                        makeExData.points.add(com.google.android.gms.maps.model.LatLng(width, length))
                    }
                    Log.d("blya", "${makeExData.names} v if")
                }
                Log.d("blya", "${makeExData.names} posle if")
                ind-=1
                inputName.setText("${if(type)makeExData.names[ind] else makeQueData.names[ind]}")
                inputDiscription.setText("${if(type)makeExData.discriptions[ind] else makeQueData.discriptions[ind]}")
                inputLength.setText("${if(type)makeExData.points[ind].longitude else makeQueData.points[ind].longitude}")
                inputWidth.setText("${if(type)makeExData.points[ind].latitude else makeQueData.points[ind].latitude}")
                inputPromt.setText("${if(type)"" else makeQueData.prompts[ind]}")
                number.setText("${ind-1}")
                if(ind==0) previous.visibility= GONE
            }
        }
    }
}