package com.example.demo_testtardyface

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.provider.MediaStore

import android.graphics.Bitmap
import androidx.core.app.ActivityCompat.requestPermissions

import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.image)
        findViewById<Button>(R.id.tv_hello_world).setOnClickListener {
            if(askPermission()) {
                gotoTardy()
            }
        }
    }

    fun gotoTardy() {
        val intent = Intent("com.tigerit.facedetector.action.capture")
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100) {
            if(data==null) {
                Toast.makeText(this, "data: Null", Toast.LENGTH_SHORT).show()
            } else {
                val uri = data.extras!!.getParcelable<Uri>("image")
                Toast.makeText(this, uri?.toString(), Toast.LENGTH_SHORT).show()
                imageView.setImageURI(uri)
            }
        }
    }

    fun askPermission(): Boolean {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    this,
                    arrayOf( Manifest.permission.READ_EXTERNAL_STORAGE),
                    1245
                )
                return false
            }

        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1245) {
            for (result in grantResults) {
                if (result != 0) { //denied
                    Toast.makeText(this, "Permission required to continue", Toast.LENGTH_SHORT)
                        .show()

                    return
                }
            }
        }
    }
}