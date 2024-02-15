package com.example.intouchmobileapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.intouchmobileapp.domain.use_case.log_out.LogOutUseCase
import com.example.intouchmobileapp.domain.use_case.log_out.StopStompConnectionUseCase
import com.example.intouchmobileapp.presentation.Screen
import com.example.intouchmobileapp.presentation.common.navigateAndReplaceStartDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val stopStompConnectionUseCase: StopStompConnectionUseCase,
) : ViewModel() {

    fun onEvent(event: SettingsScreenEvent) {
        when(event) {
            is SettingsScreenEvent.LogOutClickedEvent -> logOut(event.navController)
        }
    }
    private fun logOut (navController: NavController) {
        stopStompConnectionUseCase()
        viewModelScope.launch {
            logOutUseCase()
        }
        navController.navigateAndReplaceStartDestination(Screen.LogInScreen.route)
    }
}