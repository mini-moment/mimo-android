package com.mimo.android.presentation.login

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mimo.android.R
import com.mimo.android.core.dataStore
import com.mimo.android.data.network.login.NaverLoginManager
import com.mimo.android.data.repository.DataStoreRepository
import com.mimo.android.databinding.ActivityLoginBinding
import com.mimo.android.presentation.MainActivity
import com.mimo.android.presentation.base.BaseActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private lateinit var dataStoreRepository: DataStoreRepository
    private lateinit var loginViewModel: LoginViewModel

    override fun init() {
        setDataStoreRepository()
        collectLoginEvent()
        with(binding) {
            btnNaverLogin.setOnClickListener {
                NaverLoginManager.login(this@LoginActivity)
            }
        }
    }

    private fun setDataStoreRepository() {
        dataStoreRepository = DataStoreRepository(application.dataStore)
        loginViewModel = LoginViewModel(dataStoreRepository)
    }

    private fun collectLoginEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.event.collectLatest { loginEvent ->
                    when (loginEvent) {
                        is LoginEvent.Success -> {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }

                        is LoginEvent.Error -> {
                            showMessage(loginEvent.errorMessage)
                        }
                    }
                }
            }
        }
    }
}
