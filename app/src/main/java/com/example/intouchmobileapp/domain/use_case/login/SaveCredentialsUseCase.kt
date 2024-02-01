package com.example.intouchmobileapp.domain.use_case.login

import android.util.Log
import com.example.intouchmobileapp.domain.repository.PreferencesRepository
import javax.inject.Inject

class SaveCredentialsUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(login: String, password: String) {
        preferencesRepository.updateCredentials(login, password)
        preferencesRepository.updateLogged(true)
        Log.i(javaClass.name, "new Credentials have been saved")
    }
}