package com.example.caravan.data.local

import androidx.room.*
import com.example.caravan.domain.model.ProductEntity
import com.example.caravan.domain.model.ProductsList
import com.example.caravan.domain.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CaravanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllProducts(note: ProductEntity)

    @Query("SELECT * FROM productentity")
    suspend fun getAllProducts(): ProductEntity

    @Query("DELETE FROM productentity")
    suspend fun deleteAll()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(note: UserEntity)

    @Query("SELECT * FROM UserEntity")
    suspend fun getUser(): UserEntity

    @Query("DELETE FROM UserEntity")
    suspend fun deleteUser()
}