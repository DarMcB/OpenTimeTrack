package com.example.opentimetrack.data.repository

import com.example.opentimetrack.data.dao.TimeDao
import com.example.opentimetrack.data.entity.TimeInstance
import com.example.opentimetrack.data.entity.Type
import kotlinx.coroutines.flow.Flow


class TimeRepository(private val timeDao: TimeDao) {

    //Types
    fun getAllTypesStream(): Flow<List<Type>> = timeDao.getAllTypesNameAsc()

    fun getTypeStream(id: Int): Flow<Type?> = timeDao.getType(id)

    suspend fun insertType(type: Type) = timeDao.insertType(type)

    suspend fun updateType(type: Type) = timeDao.updateType(type)

    suspend fun deleteType(type: Type) = timeDao.deleteType(type)

    //Time Instances
    fun getAllTimeInstancesStream(typeId: Int): Flow<List<TimeInstance>> = timeDao.getAllTimeInstanceAndTypesDateAsc(typeId)

    fun getTimeInstanceStream(timeInstanceId: Int): Flow<TimeInstance?> = timeDao.getTimeInstance(timeInstanceId)

    suspend fun insertTimeInstance(timeInstance: TimeInstance)= timeDao.insertTimeInstance(timeInstance)

    suspend fun updateTimeInstance(timeInstance: TimeInstance) = timeDao.updateTimeInstance(timeInstance)

    suspend fun deleteTimeInstance(timeInstance: TimeInstance) = timeDao.deleteTimeInstance(timeInstance)
}