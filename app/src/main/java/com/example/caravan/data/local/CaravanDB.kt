package com.example.caravan.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.caravan.domain.model.*

@Database(
    entities = [ProductEntity::class, UserEntity::class, CatsEntity::class, ProductItemEntity::class, SavedCartOrder::class],
    version = 4
)
abstract class CaravanDB: RoomDatabase() {

    abstract val caravanDao: CaravanDao

    companion object{
        const val DATABASE_NAME = "caravan_db"
    }

}