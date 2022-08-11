package com.example.caravan.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.caravan.domain.model.ProductEntity
import com.example.caravan.domain.model.ProductsList
import com.example.caravan.domain.model.UserEntity

@Database(
    entities = [ProductEntity::class, UserEntity::class],
    version = 2
)
abstract class CaravanDB: RoomDatabase() {

    abstract val caravanDao: CaravanDao

    companion object{
        const val DATABASE_NAME = "caravan_db"
    }

}