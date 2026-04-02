package com.example.opentimetrack.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.opentimetrack.data.entity.TimeInstance
import com.example.opentimetrack.data.entity.Type
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeDao {

    //TypeDao
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertType(type: Type)

    @Update()
    suspend fun updateType(type: Type)

    @Delete()
    suspend fun deleteType(type: Type)

    @Query("SELECT * FROM Type ORDER BY name ASC")
    fun getAllTypesNameAsc(): Flow<List<Type>>

    @Query("SELECT * FROM Type ORDER BY name DESC")
    fun getAllTypesNameDesc(): Flow<List<Type>>

    @Query("SELECT * FROM Type WHERE id = :id")
    fun getType(id: Int): Flow<Type>

    //TimeInstanceDao
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTimeInstance(timeInstance: TimeInstance)

    @Update()
    suspend fun updateTimeInstance(timeInstance: TimeInstance)

    @Delete()
    suspend fun deleteTimeInstance(timeInstance: TimeInstance)

    @Query("SELECT * FROM TimeInstance WHERE id = :timeInstanceId")
    fun getTimeInstance(timeInstanceId: Int): Flow<TimeInstance>

    @Query("DELETE FROM TimeInstance WHERE typeId = :typeId")
    suspend fun deleteTimeInstances(typeId: Int)

    //TimeInstanceAndTypeDao
    @Transaction
    @Query("SELECT * FROM TimeInstance WHERE typeId = :typeId ORDER BY date ASC")
    fun getAllTimeInstanceAndTypesDateAsc(typeId: Int): Flow<List<TimeInstance>>

    /*
        @Transaction
        @Query("SELECT * FROM TimeInstanceAndType WHERE typeId = :typeId ORDER BY date DESC")
        fun getAllTimeInstanceAndTypesDateDesc(typeId: Int): Flow<List<TimeInstanceAndType>>

        @Transaction
        @Query("SELECT * FROM TimeInstanceAndType WHERE typeId = :typeId ORDER BY time ASC")
        fun getAllTimeInstanceAndTypesTimeAsc(typeId: Int): Flow<List<TimeInstanceAndType>>

        @Transaction
        @Query("SELECT * FROM TimeInstanceAndType WHERE typeId = :typeId ORDER BY time DESC")
        fun getAllTimeInstanceAndTypesTimeDesc(typeId: Int): Flow<List<TimeInstanceAndType>>

     */
}