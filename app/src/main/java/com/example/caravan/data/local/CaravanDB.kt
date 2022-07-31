package com.example.caravan.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.caravan.domain.model.ProductEntity
import com.example.caravan.domain.model.ProductsList

@Database(
    entities = [ProductEntity::class],
    version = 2
)
abstract class CaravanDB: RoomDatabase() {

    abstract val caravanDao: CaravanDao

    companion object{
        const val DATABASE_NAME = "caravan_db"
    }

}