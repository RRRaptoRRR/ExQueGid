package com.example.exquegid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val type=intent.getBooleanExtra("Type", true)
        var textInTop=findViewById<TextView>(R.id.textView3)
        var name=findViewById<TextView>(R.id.name)
        var mark=findViewById<TextView>(R.id.markValue)
        var cost=findViewById<TextView>(R.id.costValue)
        var discription=findViewById<TextView>(R.id.discription)
        var choise=findViewById<Button>(R.id.choise)
        var next=findViewById<Button>(R.id.next)
        var past=findViewById<Button>(R.id.past)
        var DB= Firebase.firestore
        var id=0
        if(type==true) {
            DB.collection("Excursion").document("$id")
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val test = task.result?.data
                            if (test != null) {
                                name.text = test["name"].toString()
                                //mark.text=test["mark"].toString()
                                discription.text = test["discription"].toString()
                                cost.text = test["cost"].toString()
                            }
                            Log.d("testDB", test.toString())
                        }
                    }
        }
        else{
            DB.collection("Quest").document("$id")
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val test = task.result?.data
                            if (test != null) {
                                name.text=test["name"].toString()
                                //mark.text=test["mark"].toString()
                                discription.text=test["discription"].toString()
                                cost.text=test["cost"].toString()
                            }
                            Log.d("testDB",test.toString())
                        }
                    }
        }

        past.setOnClickListener {
            id-=1
            if(type==true){
                DB.collection("Excursion").document("$id")
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val test = task.result?.data
                                if (test != null) {
                                    name.text=test["name"].toString()
                                    //mark.text=test["mark"].toString()
                                    discription.text=test["discription"].toString()
                                    cost.text=test["cost"].toString()
                                }
                                Log.d("testDB",test.toString())
                            }
                        }
            }
            else{
                DB.collection("Quest").document("$id")
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val test = task.result?.data
                                if (test != null) {
                                    name.text=test["name"].toString()
                                    //mark.text=test["mark"].toString()
                                    discription.text=test["discription"].toString()
                                    cost.text=test["cost"].toString()
                                }
                                Log.d("testDB",test.toString())
                            }
                        }
            }
        }
        next.setOnClickListener {
            id+=1
            if(type==true){
                DB.collection("Excursion").document("$id")
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val test = task.result?.data
                                if (test != null) {
                                    name.text=test["name"].toString()
                                    //mark.text=test["mark"].toString()
                                    discription.text=test["discription"].toString()
                                    cost.text=test["cost"].toString()
                                }
                                Log.d("testDB",test.toString())
                            }
                        }
            }
            else{
                DB.collection("Quest").document("$id")
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val test = task.result?.data
                                if (test != null) {
                                    name.text=test["name"].toString()
                                    //mark.text=test["mark"].toString()
                                    discription.text=test["discription"].toString()
                                    cost.text=test["cost"].toString()
                                }
                                Log.d("testDB",test.toString())
                            }
                        }
            }
        }
        choise.setOnClickListener {
            if(type==true) {
                /*var excursion=ExcursionDataClass("0", 0, "0", mutableListOf(), mutableListOf(), mutableListOf())
                DB.collection("Excursion").document("$id")
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val test = task.result?.data
                            if (test != null) {
                                excursion.discriptions=test["discriptions"] as MutableList<String>
                                excursion.names=test["names"] as MutableList<String>
                                val geopoint=test["points"] as MutableList<GeoPoint>
                                excursion.points=geopoint.map { LatLng(it.latitude, it.longitude) }.toMutableList()
                            }
                            Log.d("testDB", test.toString())
                        }
                    }*/
                val intent = Intent(this, MainMap::class.java).putExtra("type", true).putExtra("id", id)
                startActivity(intent)
            }
            else{
                /*var quest=QuestDataClass("0", 0, "0", mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())
                DB.collection("Quest").document("$id")
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val test = task.result?.data
                            if (test != null) {
                                quest.discriptions=test["discriptions"] as MutableList<String>
                                //quest.names=test["names"] as MutableList<String>
                                val geopoint=test["points"] as MutableList<GeoPoint>
                                quest.points=geopoint.map { LatLng(it.latitude, it.longitude) }.toMutableList()
                            }
                            Log.d("testDB",test.toString())
                        }
                    }*/
                val intent = Intent(this, MainMap::class.java).putExtra("type", false).putExtra("id", id)
                startActivity(intent)
            }
        }
    }
}