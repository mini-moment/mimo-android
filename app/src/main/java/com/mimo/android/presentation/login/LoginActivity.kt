package com.mimo.android.presentation.login

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mimo.android.R
import com.mimo.android.data.network.login.NaverLoginManager
import com.mimo.android.databinding.ActivityLoginBinding
import com.mimo.android.presentation.MainActivity
import com.mimo.android.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel: LoginViewModel by viewModels()
    override fun init() {
        collectLoginEvent()
        with(binding) {
            btnNaverLogin.setOnClickListener {
                NaverLoginManager.login(this@LoginActivity)
            }
        }
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
