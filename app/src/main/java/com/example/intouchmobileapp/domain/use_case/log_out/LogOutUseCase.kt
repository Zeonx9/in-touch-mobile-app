package com.example.intouchmobileapp.domain.use_case.log_out

import com.example.intouchmobileapp.domain.repository.ChatRepository
import com.example.intouchmobileapp.domain.repository.MessageRepository
import com.example.intouchmobileapp.domain.repository.PreferencesRepository
import com.example.intouchmobileapp.domain.repository.SelfRepository
import com.example.intouchmobileapp.domain.repository.UserRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
    private val selfRepository: SelfRepository,
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository
) {

    suspend operator fun invoke() {
        chatRepository.clear()
        messageRepository.clear()
        selfRepository.clear()
        userRepository.clear()
        preferencesRepository.updateLogged(false)
    }
}