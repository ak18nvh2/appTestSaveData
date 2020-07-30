package com.example.dataroom

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DanhThiep {
    @PrimaryKey(autoGenerate = true)
    var mID : Int = 0

    @ColumnInfo(name = "DT_Name")
    var mName : String = ""

    @ColumnInfo(name = "DT_Age")
    var mAge : String = ""

    @ColumnInfo(name = "DT_I")
     var img : String = ""
}