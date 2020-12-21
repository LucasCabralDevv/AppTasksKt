package com.lucascabral.taskapplication.service.repository

import android.content.Context
import com.google.gson.Gson
import com.lucascabral.taskapplication.R
import com.lucascabral.taskapplication.service.model.HeaderModel
import com.lucascabral.taskapplication.service.constants.TaskConstants
import com.lucascabral.taskapplication.service.listener.ApiListener
import com.lucascabral.taskapplication.service.repository.remote.PersonService
import com.lucascabral.taskapplication.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository(val context: Context) {

    private val mRemote = RetrofitClient.createService(PersonService::class.java)

    fun login(email: String, password: String, listener: ApiListener) {

        val call: Call<HeaderModel> = mRemote.login(email, password)
        call.enqueue(object : Callback<HeaderModel> {
            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {

                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation =
                            Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.onFailure(validation)
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }
        })
    }

    fun create(name: String, email: String, password: String, listener: ApiListener) {

        val call: Call<HeaderModel> = mRemote.create(name, email, password, true)
        call.enqueue(object : Callback<HeaderModel> {
            override fun onResponse(call: Call<HeaderModel>, response: Response<HeaderModel>) {

                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val validation =
                            Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.onFailure(validation)
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<HeaderModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
            }
        })
    }

}