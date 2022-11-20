package com.example.kamera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.kamera.databinding.ActivityMainBinding
import java.util.*

//import java.util.jar.Manifest
//import kotlinx.android.synthetic.main.activity.main.*


class MainActivity : AppCompatActivity() ,LocationListener{
    private lateinit var locationManager: LocationManager
    private lateinit var longitude: TextView
    private lateinit var latitude: TextView
    private lateinit var dostawca: TextView
    private lateinit var gps: Switch
    private val locationPermissionCode = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= com.example.kamera.databinding.ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.isEnabled=false
        hide_Location()
        getLocation()

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),111)
        }
        else
            binding.button.isEnabled=true


        binding.button.setOnClickListener {
            var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i,101)


        }


        //setContentView(R.layout.activity_main)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==101){
            val bindnig=ActivityMainBinding.inflate(layoutInflater)
            setContentView(bindnig.root)
            var obrazek:Bitmap?
            obrazek=data?.getParcelableExtra<Bitmap>("data")
            bindnig.image.setImageBitmap(obrazek)
            getLocation()
            hide_Location()
        }
    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }
    override fun onLocationChanged(location: Location) {
        val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        val country = addresses[0].getAddressLine(0)
        dostawca=findViewById(R.id.dostawca)
        latitude = findViewById(R.id.szerokosc)
        longitude = findViewById(R.id.dlugosc)
        latitude.text = "Szerokość: " + location.latitude
        longitude.text = "Długość: " + location.longitude
        dostawca.text="Dostawca: \n"+country
    }
     fun hide_Location(){
         gps=findViewById(R.id.gps)
         gps.setOnClickListener {
             dostawca=findViewById(R.id.dostawca)
             latitude = findViewById(R.id.szerokosc)
             longitude = findViewById(R.id.dlugosc)
             if(longitude.isVisible&&latitude.isVisible&&dostawca.isVisible){
                 longitude.visibility=View.INVISIBLE
                 latitude.visibility=View.INVISIBLE
                 dostawca.visibility=View.INVISIBLE
             }else{
                 longitude.visibility=View.VISIBLE
                 latitude.visibility=View.VISIBLE
                 dostawca.visibility=View.VISIBLE

             }

         }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


}