package com.mimo.android.presentation

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mimo.android.R
import com.mimo.android.databinding.ActivityMainBinding
import com.mimo.android.presentation.base.BaseActivity
import com.mimo.android.presentation.video.VideoActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navHostFragment: NavHostFragment
    override fun init() {
        initNavigation()
    }

    private fun initNavigation() { // 네비게이션 세팅
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        with(binding) {
            bottomNavigationMain.apply {
                setupWithNavController(navHostFragment.navController)
            }
            btnUploadVideo.setOnClickListener {
                startActivity(this@MainActivity,VideoActivity::class.java)
            }
        }
    }
}
