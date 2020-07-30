package com.example.dataroom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.ArrayList

@Dao
interface DanhThiepDAO {
    @Insert()
    fun saveDT(dt : DanhThiep)

    @Query("select * from DanhThiep")
    fun readDT() : List<DanhThiep>
    
    @Query("select * from DanhThiep where DT_Name = :name ")
    fun readDTwithID( name: String) : DanhThiep
}