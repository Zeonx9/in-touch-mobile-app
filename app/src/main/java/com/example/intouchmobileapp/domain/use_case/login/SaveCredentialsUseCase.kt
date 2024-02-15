package com.example.intouchmobileapp.domain.use_case.login

import com.example.intouchmobileapp.domain.repository.PreferencesRepository
import javax.inject.Inject

class SaveCredentialsUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(login: String, password: String) {
        preferencesRepository.updateCredentials(login, password)
        preferencesRepository.updateLogged(true)
    }
}