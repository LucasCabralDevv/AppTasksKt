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

    fun all(listener: APIListener<List<TaskModel>>) {
        val call: Call<List<TaskModel>> = mRemote.all()
        list(listener, call)
    }

    fun nextWeek(listener: APIListener<List<TaskModel>>) {
        val call: Call<List<TaskModel>> = mRemote.nextWeek()
        list(listener, call)
    }

    fun overDue(listener: APIListener<List<TaskModel>>) {
        val call: Call<List<TaskModel>> = mRemote.overdue()
        list(listener, call)
    }

    private fun list(listener: APIListener<List<TaskModel>>, call: Call<List<TaskModel>>) {
        call.enqueue(object : Callback<List<TaskModel>>{
            override fun onResponse(call: Call<List<TaskModel>>, response: Response<List<TaskModel>>) {
                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation =
                            Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.onFailure(validation)
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<List<TaskModel>>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }
        })
    }

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