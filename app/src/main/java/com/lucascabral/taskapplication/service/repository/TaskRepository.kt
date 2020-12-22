package com.lucascabral.taskapplication.service.repository

import android.content.Context
import com.google.gson.Gson
import com.lucascabral.taskapplication.R
import com.lucascabral.taskapplication.service.constants.TaskConstants
import com.lucascabral.taskapplication.service.listener.APIListener
import com.lucascabral.taskapplication.service.model.TaskModel
import com.lucascabral.taskapplication.service.repository.remote.RetrofitClient
import com.lucascabral.taskapplication.service.repository.remote.TaskService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository(val context: Context) {

    private val mRemote = RetrofitClient.createService(TaskService::class.java)

    fun create(task: TaskModel, listener: APIListener<Boolean>) {
        val call: Call<Boolean> =
                mRemote.create(task.priorityId, task.description, task.dueDate, task.complete)
        call.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation =
                            Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.onFailure(validation)
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }
        })
    }
}