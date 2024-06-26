package com.mimo.android.presentation.video

import androidx.navigation.fragment.NavHostFragment
import com.mimo.android.R
import com.mimo.android.databinding.ActivityVideoBinding
import com.mimo.android.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoActivity : BaseActivity<ActivityVideoBinding>(R.layout.activity_video) {

    private lateinit var navHostFragment: NavHostFragment

    override fun init() {
        initNavigation()
    }

    private fun initNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_video) as NavHostFragment
    }
}
