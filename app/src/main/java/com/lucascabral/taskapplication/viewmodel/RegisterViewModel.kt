package com.lucascabral.taskapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucascabral.taskapplication.service.HeaderModel
import com.lucascabral.taskapplication.service.constants.TaskConstants
import com.lucascabral.taskapplication.service.listener.ApiListener
import com.lucascabral.taskapplication.service.listener.ValidationListener
import com.lucascabral.taskapplication.service.repository.PersonRepository
import com.lucascabral.taskapplication.service.repository.local.SecurityPreferences

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)
    private val mSharedPreferences = SecurityPreferences(application)

    private val mCreate = MutableLiveData<ValidationListener>()
    var create: LiveData<ValidationListener> = mCreate

    fun create(name: String, email: String, password: String) {
        mPersonRepository.create(name, email, password, object : ApiListener{
            override fun onSuccess(model: HeaderModel) {
                mSharedPreferences.store(TaskConstants.SHARED.TOKEN_KEY, model.token)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_KEY, model.personKey)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_NAME, model.name)

                mCreate.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                mCreate.value = ValidationListener(str)
            }
        })
    }

}