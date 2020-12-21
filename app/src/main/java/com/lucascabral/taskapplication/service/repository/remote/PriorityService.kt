package com.lucascabral.taskapplication.service.repository.remote

import com.lucascabral.taskapplication.service.model.PriorityModel
import retrofit2.Call
import retrofit2.http.GET

interface PriorityService {

    @GET("Priority")
    fun list(): Call<List<PriorityModel>>
}