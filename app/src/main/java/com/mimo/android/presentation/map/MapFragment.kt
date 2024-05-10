package com.mimo.android.presentation.map

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mimo.android.R
import com.mimo.android.databinding.FragmentMapBinding
import com.mimo.android.presentation.base.BaseMapFragment
import com.mimo.android.presentation.util.checkLocationPermission
import com.mimo.android.presentation.util.makeMarker
import com.mimo.android.presentation.util.requestMapPermission
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MapFragment : BaseMapFragment<FragmentMapBinding>(R.layout.fragment_map) {

    private val mapViewModel: MapViewModel by viewModels()

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource // 현재 위치
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override var mapView: MapView? = null


    override fun initOnCreateView() {
        initMapView()
    }

    override fun initOnMapReady(naverMap: NaverMap) {
        Timber.d("네이버 지도 확인")
        initNaverMap(naverMap)
        setMarker()
        mapViewModel.getMarkerList(0.0, 0.0, 0.0)
    }

    override fun iniViewCreated() {

    }

    private fun initMapView() { // mapView 초기화
        Timber.d("initMapView 호출됨 ${mapView}")
        mapView = binding.mapView
        mapView?.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun initNaverMap(naverMap: NaverMap) { // 위치 및 naverMap 세팅
        this.naverMap = naverMap
        this.naverMap.locationSource = locationSource
        getLastLocation(naverMap)
    }

    private fun getLastLocation(map: NaverMap) { // 마지막 위치 가져오기
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        checkLocationPermission(requireActivity())
        requireContext().requestMapPermission {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    val loc = if (location == null) {
                        Timber.d("위치 확인 안됨")
                        LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
                    } else {
                        Timber.d("현재 위치 확인")
                        LatLng(location.latitude, location.longitude)
                    }
                    map.cameraPosition = CameraPosition(loc, DEFAULT_ZOOM)
                    map.locationTrackingMode = LocationTrackingMode.Follow
                }
        }
    }

    private fun setMarker() { // Cluster 붙이기
        mapViewModel.markerList.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                Timber.d("요요 마커용")
                val markers = makeMarker(it)
                markers.map = naverMap
            }
        }
    }


    companion object {
        const val DEFAULT_LATITUDE = 37.563242272383114
        const val DEFAULT_LONGITUDE = 126.92566852521531
        const val DEFAULT_ZOOM = 15.0
        const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}
