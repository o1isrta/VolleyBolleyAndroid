package cy.volleybolley.auth.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class AuthViewModel() : ViewModel() {
    private val _idToken = MutableSharedFlow<String>()
    val idToken: SharedFlow<String> = _idToken

    fun onTokenReceived(token: String?) {
        token?.let {
            viewModelScope.launch {
                _idToken.emit(it)
            }
        }
    }
}