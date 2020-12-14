package com.lucascabral.taskapplication.service.listener

import com.lucascabral.taskapplication.service.HeaderModel


interface ApiListener {

    fun onSuccess(model: HeaderModel)

    fun onFailure(str: String)
}