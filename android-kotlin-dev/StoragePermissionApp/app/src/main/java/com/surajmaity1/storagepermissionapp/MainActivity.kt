package com.surajmaity1.storagepermissionapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val STORAGE_REQUEST_PERMISSION_CODE = 1
    private lateinit var button: Button
    private val externalStoragePermission = android.Manifest.permission.READ_EXTERNAL_STORAGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.permissionBtn)

        button.setOnClickListener {
            if (
                ContextCompat.checkSelfPermission(this, externalStoragePermission)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this, "Permission Allowed", Toast.LENGTH_SHORT).show()
            }
            else {
                storagePermissionRequest();
            }
        }
    }

    private fun storagePermissionRequest() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, externalStoragePermission)){
            AlertDialog.Builder(this)
                .setTitle("Storage Permission Needed")
                .setMessage("This is the message why Permission Needed")
                .setPositiveButton("Ok") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        STORAGE_REQUEST_PERMISSION_CODE)
                }
                .setNegativeButton("Cancel") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .create()
                .show()
        }
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_REQUEST_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Allowed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}