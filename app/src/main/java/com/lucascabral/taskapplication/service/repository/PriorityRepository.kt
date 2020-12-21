package com.lucascabral.taskapplication.service.repository

import android.content.Context
import com.lucascabral.taskapplication.service.constants.TaskConstants
import com.lucascabral.taskapplication.service.model.PriorityModel
import com.lucascabral.taskapplication.service.repository.local.TaskDatabase
import com.lucascabral.taskapplication.service.repository.remote.PriorityService
import com.lucascabral.taskapplication.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PriorityRepository(context: Context) {

    private val mRemote = RetrofitClient.createService(PriorityService::class.java)
    private val mPriorityDatabase = TaskDatabase.getDatabase(context).priorityDAO()

    fun all() {
        val call: Call<List<PriorityModel>> = mRemote.list()
        call.enqueue(object : Callback<List<PriorityModel>>{
            override fun onResponse(call: Call<List<PriorityModel>>, response: Response<List<PriorityModel>>) {
                if (response.code() == TaskConstants.HTTP.SUCCESS){
                    mPriorityDatabase.clear()
                    response.body()?.let { mPriorityDatabase.save(it) }
                }
            }

            override fun onFailure(call: Call<List<PriorityModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}