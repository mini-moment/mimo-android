package com.mimo.android.presentation.map

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mimo.android.R
import com.mimo.android.databinding.FragmentMapBinding
import com.mimo.android.presentation.base.BaseMapFragment
import com.mimo.android.presentation.util.checkLocationPermission
import com.mimo.android.presentation.util.deleteMarker
import com.mimo.android.presentation.util.makeMarker
import com.mimo.android.presentation.util.requestMapPermission
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.CircleOverlay
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
    private lateinit var circle: CircleOverlay

    override var mapView: MapView? = null
    var radius = 0.0

    override fun initOnCreateView() {
        initMapView()
    }

    override fun initOnMapReady(naverMap: NaverMap) {
        Timber.d("네이버 지도 확인")
        initNaverMap(naverMap)
        setMarker()
        setCameraChangeListener()
    }

    override fun iniViewCreated() {
        clickLocationSearchBtn()
    }

    override fun initOnResume() {

    }

    private fun initMapView() { // mapView 초기화
        mapView = binding.mapView
        mapView?.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun initNaverMap(naverMap: NaverMap) { // 위치 및 naverMap 세팅
        this.naverMap = naverMap
        this.naverMap.locationSource = locationSource
        circle = CircleOverlay()
        getLastLocation(naverMap)
    }

    private fun getLastLocation(map: NaverMap) { // 마지막 위치 가져오기
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        checkLocationPermission(requireActivity())
        requireContext().requestMapPermission {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                val loc = if (location == null) {
                    LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
                } else {
                    LatLng(location.latitude, location.longitude)
                }
                map.cameraPosition = CameraPosition(loc, DEFAULT_ZOOM)
                map.locationTrackingMode = LocationTrackingMode.Follow
                getMarkerList(loc.latitude, loc.longitude, DEFAULT_ZOOM)
            }
        }
    }

    private fun getMarkerList(latitude: Double, longitude: Double, round: Double) {
        mapViewModel.getMarkerList(
            latitude, longitude, 3 * Math.pow(2.0, 22 - round) / 1000
        )
        setCircleOverlay(LatLng(latitude, longitude), round)
    }

    private fun setMarker() { // Cluster 붙이기
        mapViewModel.markerList.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                mapViewModel.currentMarkerList.value?.let {
                    deleteMarker(it)
                }
                val markers = makeMarker(it)
                mapViewModel.setCurrentMarkerList(markers)
                markers.map = naverMap
            }
        }
    }

    private fun setCircleOverlay(location: LatLng, zoom: Double) {//범위 생성
        circle.map = null
        circle.center = LatLng(location.latitude, location.longitude)
        radius = 3 * Math.pow(2.0, 22 - zoom) / 1000
        circle.radius = radius * 1000
        circle.color = ContextCompat.getColor(requireContext(), R.color.light_purple_opacity_5)
        circle.map = naverMap
    }

    private fun setCameraChangeListener() { //제스처시 현재 위치 검색
        naverMap.addOnCameraChangeListener { reason, animated ->
            Timber.d("reason 확인 $reason")
            if (reason == CameraUpdate.REASON_GESTURE) {
                binding.locationSearchVisible = true
            }
        }
    }

    private fun clickLocationSearchBtn() {//현재 위치 검색 클릭
        binding.btnLocationSearch.setOnClickListener {
            binding.locationSearchVisible = false
            naverMap.cameraPosition.apply {
                getMarkerList(this.target.latitude, this.target.longitude, this.zoom)
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




