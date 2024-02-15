package com.example.intouchmobileapp.domain.use_case.log_out

import com.example.intouchmobileapp.data.remote.api.StompApi
import com.example.intouchmobileapp.domain.repository.SelfRepository
import javax.inject.Inject

class StopStompConnectionUseCase @Inject constructor(
    private val stompApi: StompApi,
    private val selfRepository: SelfRepository
) {
    operator fun invoke() {
        stompApi.sendDisconnectSignal(selfRepository.user)
        stompApi.disconnect()
    }

}