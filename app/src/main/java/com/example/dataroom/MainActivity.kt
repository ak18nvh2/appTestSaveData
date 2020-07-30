package com.example.dataroom

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.myapplicationaaaaaaa.DanhThiepAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.security.Permission
import java.util.ArrayList
import java.util.jar.Manifest

private const val PERMISSION_REQUEST = 99

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var list = ArrayList<DanhThiep>()
    private var permissionArray = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    var danhThiepAdapter: DanhThiepAdapter? = null
    lateinit var db: AppDB
    lateinit var rvDT: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        danhThiepAdapter = DanhThiepAdapter(this)
        rvDT = findViewById<RecyclerView>(R.id.rv)
        rvDT.layoutManager = LinearLayoutManager(this)
        rvDT.adapter = danhThiepAdapter
        btn_Add.setOnClickListener(this)
        db = Room.databaseBuilder(this, AppDB::class.java, "DanhThiepDB")
            .fallbackToDestructiveMigration().build()
        var c = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (! checkPermisson(this, permissionArray)) {
                requestPermissions(permissionArray, PERMISSION_REQUEST)
            }
        }
        Thread {
            list = db.dtDAO().readDT() as ArrayList<DanhThiep>
            this.runOnUiThread(java.lang.Runnable {
                danhThiepAdapter?.setList(list)
            })
        }.start()
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun test(view: View) {
        val i = Intent(
            Intent.ACTION_OPEN_DOCUMENT,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(i, 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                var selectedImage: Uri? = data?.getData()
                Thread {
                    var danhThiep = DanhThiep()
                    danhThiep.mName = edt_Nhap.text.toString()
                    danhThiep.mAge = edt_Nhap.text.toString()
                    danhThiep.img = selectedImage.toString()
                    Log.d("luu", danhThiep.img)
                    db.dtDAO().saveDT(danhThiep)
                                   list = db.dtDAO().readDT() as ArrayList<DanhThiep>
                    this.runOnUiThread(java.lang.Runnable {
                        danhThiepAdapter?.setList(list)
                    })
                }.start()

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onClick(v: View?) {
        when (v) {
            btn_Add -> test(btn_Add)
        }
    }

    fun checkPermisson(context: Context, permissionArray: Array<String>): Boolean {
        var allSacces = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSacces = false
        }
        return allSacces
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    var requestAgain =
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                            permissions[i]
                        )
                    if (requestAgain) {
                        Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "go to settiong and enable", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (allSuccess) {
                Toast.makeText(this, "thanh cong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}