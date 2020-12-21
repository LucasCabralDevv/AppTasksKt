package com.lucascabral.taskapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucascabral.taskapplication.service.listener.ApiListener
import com.lucascabral.taskapplication.service.listener.ValidationListener
import com.lucascabral.taskapplication.service.model.HeaderModel
import com.lucascabral.taskapplication.service.constants.TaskConstants
import com.lucascabral.taskapplication.service.repository.PersonRepository
import com.lucascabral.taskapplication.service.repository.local.SecurityPreferences

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)
    private val mSharedPreferences = SecurityPreferences(application)

    private val mLogin = MutableLiveData<ValidationListener>()
    var login: LiveData<ValidationListener> = mLogin

    private val mLoggedUser = MutableLiveData<Boolean>()
    var loggedUser: LiveData<Boolean> = mLoggedUser

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {

        mPersonRepository.login(email, password, object : ApiListener {
            override fun onSuccess(model: HeaderModel) {

                mSharedPreferences.store(TaskConstants.SHARED.TOKEN_KEY, model.token)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_KEY, model.personKey)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_NAME, model.name)

                mLogin.value = ValidationListener()
            }

            override fun onFailure(str: String) {

                mLogin.value = ValidationListener(str)
            }

        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {

        val token = mSharedPreferences.get(TaskConstants.SHARED.TOKEN_KEY)
        val personKey = mSharedPreferences.get(TaskConstants.SHARED.PERSON_KEY)
        val logged = (token.isNotEmpty() && personKey.isNotEmpty())

        mLoggedUser.value = logged
    }

}