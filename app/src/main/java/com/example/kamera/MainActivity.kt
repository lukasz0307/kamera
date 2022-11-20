package com.example.kamera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kamera.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

//import java.util.jar.Manifest
//import kotlinx.android.synthetic.main.activity.main.*


class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= com.example.kamera.databinding.ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.isEnabled=false



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
        }
    }




}