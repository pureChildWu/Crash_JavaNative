package com.shinfo.crash_javaandndk

import android.Manifest
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shinfo.crashtrack.Test
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

        Log.e(javaClass.name,cacheDir.absolutePath)
        Log.e(javaClass.name,codeCacheDir.absolutePath)
        Log.e(javaClass.name,dataDir.absolutePath)
        Log.e(javaClass.name,externalCacheDir?.absolutePath!!)
        Log.e(javaClass.name,filesDir.absolutePath)
        Log.e(javaClass.name,noBackupFilesDir.absolutePath)
        Log.e(javaClass.name,obbDir.absolutePath)

        //java Exception test
//        throw Exception("Test ...........WU")
        //native Exception test
//        Test.test()
    }

    //anr test
    fun touch(view: View) {
        while (true){
            Log.e(javaClass.name,"1231245")
            Thread.sleep(3_000)
        }
    }

    fun checkPermission() : Boolean {
        val arrayListOf = arrayListOf<String>()
        if (ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            arrayListOf.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            arrayListOf.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (arrayListOf.size > 0){
            ActivityCompat.requestPermissions(this,arrayListOf.toArray(arrayOfNulls(2)),1)
            return false
        }
        return true
    }
}