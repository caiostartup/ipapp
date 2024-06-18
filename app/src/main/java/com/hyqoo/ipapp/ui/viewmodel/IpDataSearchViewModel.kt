package com.hyqoo.ipapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyqoo.ipapp.data.repository.getValueOrNull
import com.hyqoo.ipapp.domain.GetIpDataUseCase
import com.hyqoo.ipapp.model.IpData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class IpDataSearchViewModel @Inject constructor(
    private val getIpDataUseCase: GetIpDataUseCase
): ViewModel(){

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String>
        get() = _searchQuery

    private val _ipDataUiState: MutableStateFlow<IpData?> = MutableStateFlow(null)

    val ipDataUiState: StateFlow<IpData?>
        get() = _ipDataUiState

    val state: StateFlow<HomeScreenUiState>
        get() = _state

    private val _state = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Ready)

    fun handleTextChange(ip: String){ viewModelScope.launch {
            _searchQuery.emit(ip)
        }
    }
    fun handleSearch(){
        _state.value = HomeScreenUiState.Loading
        viewModelScope.launch {
            getIpDataUseCase.invoke(_searchQuery.value).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null,
            ).collect { ipData ->
                ipData?.let { it ->
                    it.message?.let { message ->
                        _ipDataUiState.value = null
                        _state.value = HomeScreenUiState.Error(message)
                    } ?: run {
                        _ipDataUiState.value = ipData
                        _state.value = HomeScreenUiState.Ready
                    }
                } ?: run {
                    _ipDataUiState.value = null
                    _state.value = HomeScreenUiState.Ready
                }
            }
        }
    }

    fun isValidIp(emailStr: String?) =
        Pattern
            .compile(
                "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE
            ).matcher(emailStr).find()

}

sealed interface HomeScreenUiState {
    data object Loading : HomeScreenUiState

    data class Error(
        val errorMessage: String
    ) : HomeScreenUiState

    data object Ready : HomeScreenUiState
}


