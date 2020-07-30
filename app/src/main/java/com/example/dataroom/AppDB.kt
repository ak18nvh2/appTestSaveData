package com.example.dataroom

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(DanhThiep::class)],version = 4)
abstract class AppDB:RoomDatabase() {
    abstract fun dtDAO() : DanhThiepDAO
}