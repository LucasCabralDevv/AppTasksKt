package com.lucascabral.taskapplication.service.listener

import com.lucascabral.taskapplication.service.model.HeaderModel


interface APIListener<T> {

    fun onSuccess(model: T)

    fun onFailure(str: String)
}