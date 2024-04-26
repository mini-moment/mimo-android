package com.mimo.android.presentation

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mimo.android.R
import com.mimo.android.databinding.ActivityMainBinding
import com.mimo.android.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navHostFragment: NavHostFragment


    override fun init() {
        //UI 관련 초기화 작업
        initNavigation()
    }


    //네비게이션 세팅
    private fun initNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        binding.bottomNavigationMain.apply {
            setupWithNavController(navHostFragment.navController)
        }
    }
}
