package com.mimo.android.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import timber.log.Timber

abstract class BaseMapFragment<T : ViewDataBinding>(private val layoutResId: Int) : Fragment(),
    OnMapReadyCallback {

    private var _binding: T? = null
    val binding get() = _binding!!

    abstract var mapView: MapView?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initOnCreateView()
        Timber.d("onCreateView 호출됨")
        return binding.root
    }

    abstract fun initOnCreateView()


    override fun onMapReady(naverMap: NaverMap) {
        Timber.d("onMapReady 호출됨! $naverMap")
        initOnMapReady(naverMap)

    }

    abstract fun initOnMapReady(naverMap : NaverMap)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated 호출됨! $mapView")
        iniViewCreated()
    }

    abstract fun iniViewCreated()


    override fun onStart() {
        super.onStart()
        Timber.d("onStart $mapView")
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }
    abstract fun initOnResume()
    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onDestroy()
        _binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

}