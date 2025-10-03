package cy.volleybolley.auth.ui.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cy.volleybolley.auth.data.AuthNetworkClient
import cy.volleybolley.auth.data.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepositoryImpl: AuthRepositoryImpl) : ViewModel() {
    private val _idToken = MutableSharedFlow<String>()
    val idToken: SharedFlow<String> = _idToken
    fun onTokenReceived(token: String?) {
        token?.let {
            viewModelScope.launch {
                val response = authRepositoryImpl.loginWithGoogle(it)
                if (response.isSuccess) {
                    Log.d("ВьюМодельАвторизации",response.body.toString())
                } else {
                    Log.e("ВьюМодельАвторизации",response.resultCode.code.toString())
                }
            }
        }
    }
}
