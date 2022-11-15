package com.example.kamera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.LocationManager
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.kamera.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

//import java.util.jar.Manifest
//import kotlinx.android.synthetic.main.activity.main.*


class MainActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

private var PERMISSION_ID=1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= com.example.kamera.databinding.ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.isEnabled=false

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

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
    private fun getLastLocation(){
        if (CheckPermission()){
            if (isLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location = task.result
                    if (location==null){

                    }else{
                        val textView: TextView=findViewById(R.id.szerokosc)as TextView
                        textView.setText("Your Current Coordinates are: \nLat: "+location.latitude+";Long: "+location.longitude)
                    }
                }
            }else{
                Toast.makeText(this,"Proszę udzielić zgodę na dostęp do lokalizacji",Toast.LENGTH_LONG).show()
            }
        }else{
            RequestPermission()
        }
    }


    private fun CheckPermission():Boolean{
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED||
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            return true
        }

        return false
    }

    private fun RequestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSION_ID)

    }

    private fun isLocationEnabled():Boolean{
        var locationManager: LocationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==101){
            val bindnig=ActivityMainBinding.inflate(layoutInflater)
            setContentView(bindnig.root)
            var obrazek:Bitmap?
            obrazek=data?.getParcelableExtra<Bitmap>("data")
            bindnig.image.setImageBitmap(obrazek)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==111&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            val binding=ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            binding.button.isEnabled=true
            if (requestCode==PERMISSION_ID){
                if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Log.d("Debug:","Musisz wyrazić zgode")
                }
            }
        }
    }
}