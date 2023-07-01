/*package com.example.exquegid

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.logging.Logger.global

class MainMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    //lateinit var Excursion:ExcursionDataClass
    //lateinit var Quest:QuestDataClass
    private var PERMISSION_ID=52
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var point:LatLng
    var ind:Int=0
    var type:Boolean=true
    var DB= Firebase.firestore
    //var name=findViewById<TextView>(R.id.Name)
   // var whereIButton=findViewById<Button>(R.id.where)
    //lateinit var name:TextView
    //lateinit var whereIButton:Button
    //lateinit var sydney:LatLng

    /*suspend fun getEx(id:Int):ExcursionDataClass{
            val excursion=ExcursionDataClass("0", 0, "0", mutableListOf(), mutableListOf(), mutableListOf())
            DB.collection("Excursion").document("$id")
                .get()
                .addOnCompleteListener{task->.await()
                    if (task.isSuccessful) {
                    val test = task.result?.data
                    if (test != null) {
                        excursion.discriptions=test["discriptions"] as MutableList<String>
                        excursion.names=test["names"] as MutableList<String>
                        val geopoint=test["points"] as MutableList<GeoPoint>
                        excursion.points=geopoint.map { LatLng(it.latitude, it.longitude) }.toMutableList()
                    }
                    Log.d("testDB", test.toString())
                }}.await()
            return excursion
    }
    suspend fun getQue(id:Int):QuestDataClass{
            val quest=QuestDataClass("0", 0, "0", mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf())
            DB.collection("Quest").document("$id")
                .get()
                .addOnCompleteListener{task->
                    if (task.isSuccessful) {
                    val test = task.result?.data
                    if (test != null) {
                        quest.discriptions=test["discriptions"] as MutableList<String>
                        //quest.names=test["names"] as MutableList<String>
                        val geopoint=test["points"] as MutableList<GeoPoint>
                        quest.points=geopoint.map { LatLng(it.latitude, it.longitude) }.toMutableList()

                    }
                        return@addOnCompleteListener quest
                    Log.d("testDB",test.toString())

                }}.await()
        Log.d("aaaaaaaaaaaaa","before return ${quest.discriptions}")

            //point=quest.points[0]
            //name.setText(quest.names[0])
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var name=findViewById<TextView>(R.id.Name)
        var whereIButton=findViewById<Button>(R.id.where)
        type = intent.getBooleanExtra("type", true)//true=ex, false=que
        val ind=intent.getIntExtra("ind", 0)
            //point=quest.points[0]
            //name.setText(quest.names[0])
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun CheckPermission():Boolean{
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun RequestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf( android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
    }

    private fun isLocationEnable():Boolean{
        var locationManager:LocationManager =getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) //|| locationManager.isLocationEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==PERMISSION_ID){
            if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Log.d("debugggg", "you have sperm")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getLastLocation(){
        if(CheckPermission()){
            Log.d("debugggg", "CheckPermission==true")
            if(isLocationEnable()){
                Log.d("debugggg", "isLocationEnable")
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                    var location:Location?=task.result
                    if(location==null){
                        Toast.makeText(this, "${location}", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, "${location}", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else{
                Toast.makeText(this, "Пожалуйста, включите геолокацию", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            RequestPermission()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        //var name=findViewById<TextView>(R.id.Name)
        //var whereIButton=findViewById<Button>(R.id.where)
        mMap = googleMap
        var name=findViewById<TextView>(R.id.Name)
        var whereIButton=findViewById<Button>(R.id.where)
        // Add a marker in Sydney and move the camera
        if(type){
                point=Excursion.points[0]
                name.text=Excursion.names[0]
        }
        else{
            point=Quest.points[0]
            name.text=Quest.names[0]
            mMap.addMarker(MarkerOptions().position(point).title("Marker in Sydney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(point))
        }
        //val sydney = point
        whereIButton.setOnClickListener {
            getLastLocation()
        }
    }
}*/
package com.example.exquegid

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.droidbyme.dialoglib.DroidDialog
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_main_map.*


class MainMap : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var name:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_map)
        Log.d("TESTEST", "onCreate")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(this, permissions, 0)
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                println("locationCallback")
                locationResult ?: return
            }
        }
        mLocationRequest = LocationRequest.create();
        mLocationRequest.interval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.fastestInterval = 3000
        name=findViewById<TextView>(R.id.Name)
        name.text= Quest.names[0]
    }

    fun amIConnected(): Boolean {
        val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return
        }
        else{
            fusedLocationClient.requestLocationUpdates(
                mLocationRequest,
                locationCallback,
                Looper.getMainLooper()
            )}
    }
    //    var textof=findViewById<TextView>(R.id.nameMap)
    var i=0
    override fun onMapReady(map: GoogleMap) {
        Log.d("TESTEST", "tetette")
        mMap = map
        //if we have permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        //Name.text= Quest.names[i]
        val SPB = Quest.points[i]
        mMap.addMarker(MarkerOptions().position(SPB).title("Marker in SPB"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SPB))
        Past.setOnClickListener {
            if (i== 0) {
                i= Quest.points.size-1
                mMap.clear()
                val SPB = Quest.points[i]
                name.text= Quest.names[i]
                mMap.addMarker(MarkerOptions().position(SPB).title("Marker in SPB"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(SPB))
                //val polyline1 = mMap.addPolyline(PolylineOptions().add( for(j in testQuest.locations[i])))
            }
            else{
                i+=1
                mMap.clear()
                val SPB = Quest.points[i]
                name.text= Quest.names[i]
                mMap.addMarker(MarkerOptions().position(SPB).title("Marker in SPB"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(SPB))
                //val polyline1 = mMap.addPolyline(PolylineOptions().add( for(j in testQuest.locations[i])))
            }
            //progressBar.progress=(((i+1)*100/ Quest.points.size)).toInt()
        }
        Next.setOnClickListener {
            if (i== Quest.points.size-1) {
                i=0
                mMap.clear()
                val SPB = Quest.points[i]
                name.text= Quest.names[i]
                mMap.addMarker(MarkerOptions().position(SPB).title("Marker in SPB"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(SPB))
                //val polyline1 = mMap.addPolyline(PolylineOptions().add( for(j in testQuest.locations[i])))
            }
            else{
                i+=1
                mMap.clear()
                val SPB = Quest.points[i]
                name.text= Quest.names[i]
                mMap.addMarker(MarkerOptions().position(SPB).title("Marker in SPB"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(SPB))
                //val polyline1 = mMap.addPolyline(PolylineOptions().add( for(j in testQuest.locations[i])))
            }
            //progressBar.progress=(((i+1)*100/ Quest.points.size)).toInt()
        }
        Information.setOnClickListener {
            DroidDialog.Builder(this)
                .title("${Quest.names[i]}")
                .content("${Quest.discriptions[i]}")
                .show()
        }
        map.isMyLocationEnabled = true
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this)
        startLocationUpdates()
    }
    //if press on button in icon of user
    override fun onMyLocationClick(location: Location) {
        //if we have permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        if(!amIConnected()){
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
        else {
            var spb = Location("")
            spb.latitude = Quest.points[i].latitude
            spb.longitude = Quest.points[i].longitude
            val builder = LatLngBounds.Builder()
            /*for (el in Quest.locations[i])
            {
                builder.include(el)
            }
            val bounds = builder.build()*/
            val loc = fusedLocationClient.lastLocation.addOnSuccessListener { res_loc: Location? ->
                if (res_loc != null) {
                    Toast.makeText(
                        this,
                        "Rasstoyanie is ${res_loc.distanceTo(spb) / 1000} km",
                        Toast.LENGTH_LONG
                    ).show()
                    val my_loc = LatLng(res_loc.latitude, res_loc.longitude)
                    Log.d("Location", res_loc.toString())
                    /*val res = bounds.contains(my_loc)
                    if (res) {
                        Toast.makeText(this, "you guessed the place", Toast.LENGTH_LONG).show()
                        if (i== testQuest.point.size-1) {
                            i=0
                            mMap.clear()
                            val SPB = testQuest.point[i]
                            mMap.addMarker(MarkerOptions().position(SPB).title("Marker in SPB"))
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(SPB))
                        }
                        else{
                            i+=1
                            mMap.clear()
                            val SPB = testQuest.point[i]
                            mMap.addMarker(MarkerOptions().position(SPB).title("Marker in SPB"))
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(SPB))
                        }
                        //progressBar.progress=(((i+1)*100/ testQuest.point.size)).toInt()
                    }
                    else Toast.makeText(this, "you don't guesse the place", Toast.LENGTH_LONG).show()*/
                }
                Log.d("Location", res_loc.toString())
            }
        }
    }
    //if press on button in top right corner
    override fun onMyLocationButtonClick(): Boolean {
        //if we have permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return false
        }
        if(!amIConnected()){//if user have internete
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
        else {//
            var spb = Location("")
            spb.latitude = Quest.points[i].latitude
            spb.longitude = Quest.points[i].longitude
            /*val builder = LatLngBounds.Builder()
            for (el in Quest.locations[i])
            {
                builder.include(el)
            }
            val bounds = builder.build()*/
            val loc = fusedLocationClient.lastLocation.addOnSuccessListener { res_loc: Location? ->
                if (res_loc != null) {
                    Toast.makeText(
                        this,
                        "Rasstoyanie is ${res_loc.distanceTo(spb) / 1000} km",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Location", res_loc.toString())
                    val my_loc = LatLng(res_loc.latitude, res_loc.longitude)
                    /*val res = bounds.contains(my_loc)
                    if (res) {
                        Toast.makeText(this, "you guessed the place", Toast.LENGTH_LONG).show()
                        if (i== Quest.points.size-1) {
                            i=0
                            mMap.clear()
                            val SPB = Quest.points[i]
                            mMap.addMarker(MarkerOptions().position(SPB).title("Marker in SPB"))
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(SPB))
                        }
                        else{
                            i+=1
                            mMap.clear()
                            val SPB = Quest.points[i]
                            mMap.addMarker(MarkerOptions().position(SPB).title("Marker in SPB"))
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(SPB))
                        }
                        //progressBar.progress=(((i+1)*100/ Quest.points.size)).toInt()
                    }
                    else Toast.makeText(this, "you don't guesse the place", Toast.LENGTH_LONG).show()*/
                }
                Log.d("Location", res_loc.toString())
            }
        }
        return false
    }
}