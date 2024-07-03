package com.mimo.android.presentation.login

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        startAnimation()
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        collectUserPreferences()
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
                            startActivity(this@LoginActivity, MainActivity::class.java)
                        }

                        is LoginEvent.Error -> {
                            showMessage(loginEvent.errorMessage)
                        }
                    }
                }
            }
        }
    }

    private fun collectUserPreferences() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashViewModel.event.collectLatest { loginEvent ->
                    when (loginEvent) {
                        is LoginEvent.Success -> {
                            startActivity(this@LoginActivity, MainActivity::class.java)
                            finish()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun startAnimation() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.view.height.toFloat(),
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 1000
            slideUp.doOnEnd { splashScreenView.remove() }
            slideUp.start()
        }
    }
}
