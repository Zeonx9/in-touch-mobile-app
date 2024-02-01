package com.example.intouchmobileapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.intouchmobileapp.domain.use_case.log_out.LogOutUseCase
import com.example.intouchmobileapp.presentation.Screen
import com.example.intouchmobileapp.presentation.common.navigateAndReplaceStartDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    fun logOut (navController: NavController) {
        viewModelScope.launch {
            logOutUseCase()
        }
        navController.navigateAndReplaceStartDestination(Screen.LogInScreen.route)
    }
}