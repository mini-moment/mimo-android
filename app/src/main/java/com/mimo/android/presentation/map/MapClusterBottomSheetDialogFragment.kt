package com.mimo.android.presentation.map

import android.app.Dialog
import android.content.Intent
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mimo.android.R
import com.mimo.android.databinding.FragmentMapClusterBottomSheetDialogBinding
import com.mimo.android.domain.model.MarkerData
import com.mimo.android.domain.model.findMarkerIndex
import com.mimo.android.presentation.base.BaseBottomSheetDialogFragment
import com.mimo.android.presentation.component.adapter.MapClusterAdapter
import com.mimo.android.presentation.util.getSizeY
import com.mimo.android.presentation.videodetail.VideoDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapClusterBottomSheetDialogFragment :
    BaseBottomSheetDialogFragment<FragmentMapClusterBottomSheetDialogBinding>(R.layout.fragment_map_cluster_bottom_sheet_dialog) {

    private lateinit var mapClusterAdapter: MapClusterAdapter

    var markerList: List<MarkerData> = emptyList()
    var clusterList: List<MarkerData> = emptyList()

    override fun initCreateDialog(): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)

        return dialog
    }

    override fun initView() {
        initFilterHeight()
        initAdapter()
        initData()
        clickMarker()
    }

    private fun initFilterHeight() { // 바텀 시트 높이 지정
        binding.crMapCluster.layoutParams.height = (getSizeY(requireContext()) * 0.8).toInt()
        binding.executePendingBindings()
    }

    private fun initAdapter() {
        mapClusterAdapter = MapClusterAdapter()
        binding.rcMapCluster.adapter = mapClusterAdapter
    }

    private fun initData() {
        val args: MapClusterBottomSheetDialogFragmentArgs by navArgs()
        clusterList = args.clusterList?.toMutableList() ?: emptyList()
        markerList = args.markerList?.toMutableList() ?: emptyList()
        mapClusterAdapter.submitList(clusterList)
        binding.address = args.address
    }

    private fun clickMarker() { // 특정 마커 클릭시
        mapClusterAdapter.onItemClickListener { markerData ->
            startActivity(Intent(requireActivity(), VideoDetailActivity::class.java).apply {
                putExtra("postList", markerList.toTypedArray())
                putExtra("postIndex", markerList.findMarkerIndex(markerData))
            })
        }
    }
}