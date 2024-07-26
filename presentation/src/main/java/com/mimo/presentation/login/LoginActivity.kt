package com.mimo.presentation.login

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
import com.mimo.presentation.MainActivity
import com.mimo.presentation.R
import com.mimo.presentation.base.BaseActivity
import com.mimo.presentation.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val loginViewModel: LoginViewModel by viewModels()
    private val splashViewModel: SplashViewModel by viewModels()
    private lateinit var splashScreen: SplashScreen
    private var isLoginSuccess = false
    private var isAnimationFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        startAnimation()
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

    private fun navigateToMain() {
        if (isLoginSuccess && isAnimationFinished) {
            startActivity(this@LoginActivity, MainActivity::class.java)
            finish()
        }
    }

    private fun collectUserPreferences() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashViewModel.event.collectLatest { loginEvent ->
                    when (loginEvent) {
                        is LoginEvent.Success -> {
                            isLoginSuccess = true
                            navigateToMain()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun startAnimation() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideUp =
                ObjectAnimator.ofFloat(
                    splashScreenView.view,
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenView.view.height.toFloat(),
                )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 1500
            slideUp.doOnEnd {
                splashScreenView.remove()
                isAnimationFinished = true
                navigateToMain()
            }
            slideUp.start()
        }
    }
}
