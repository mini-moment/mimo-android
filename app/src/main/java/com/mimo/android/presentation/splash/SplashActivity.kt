package com.mimo.android.presentation.splash

import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mimo.android.R
import com.mimo.android.databinding.ActivitySplashBinding
import com.mimo.android.presentation.MainActivity
import com.mimo.android.presentation.base.BaseActivity
import com.mimo.android.presentation.login.LoginActivity
import com.mimo.android.presentation.login.LoginEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel: SplashViewModel by viewModels()
    override fun init() {
        collectUserPreferences()
    }

    private fun collectUserPreferences() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collectLatest { loginEvent ->
                    when (loginEvent) {
                        is LoginEvent.Success -> {
                            startActivity(this@SplashActivity, MainActivity::class.java)
                        }

                        is LoginEvent.Error -> {
                            startActivity(this@SplashActivity, LoginActivity::class.java)
                        }
                    }
                    finish()
                }
            }
        }
    }
}
