package com.example.kamera

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.kamera.databinding.ActivityMainBinding
import java.util.jar.Manifest
//import kotlinx.android.synthetic.main.activity.main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityMainBinding.inflate(layoutInflater)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==111&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            val binding=ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }
    }
}