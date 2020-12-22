package com.lucascabral.taskapplication.service.repository.remote

import com.lucascabral.taskapplication.service.constants.TaskConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {
        private lateinit var retrofit: Retrofit
        private val baseUrl = "http://devmasterteam.com/CursoAndroidAPI/"
        private var personKey = ""
        private var tokenKey = ""

        private fun getRetrofitInstance(): Retrofit {

            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(object : Interceptor{
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

                    val request = chain.request()
                            .newBuilder()
                            .addHeader(TaskConstants.HEADER.PERSON_KEY, personKey)
                            .addHeader(TaskConstants.HEADER.TOKEN_KEY, tokenKey)
                            .build()
                    return chain.proceed(request)
                }
            })
            if (!Companion::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun addHeader(token: String, personKey: String) {
            this.personKey = personKey
            this.tokenKey = token
        }

        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstance().create(serviceClass)
        }
    }
}