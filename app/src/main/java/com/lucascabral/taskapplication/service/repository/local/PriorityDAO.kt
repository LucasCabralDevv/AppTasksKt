package com.lucascabral.taskapplication.service.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lucascabral.taskapplication.service.model.PriorityModel

@Dao
interface PriorityDAO {

    @Insert
    fun save(list: List<PriorityModel>)

    @Query("SELECT * FROM priority")
    fun list(): List<PriorityModel>

    @Query("SELECT description FROM priority WHERE id = :id")
    fun getDescription(id: Int): String

    @Query("DELETE FROM priority")
    fun clear()
}