package com.mimo.android.presentation.map

import android.app.Dialog
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mimo.android.R
import com.mimo.android.databinding.FragmentMapClusterBottomSheetDialogBinding
import com.mimo.android.presentation.base.BaseBottomSheetDialogFragment
import com.mimo.android.presentation.map.adapter.MapClusterAdapter
import com.mimo.android.presentation.util.getSizeY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapClusterBottomSheetDialogFragment :
    BaseBottomSheetDialogFragment<FragmentMapClusterBottomSheetDialogBinding>(R.layout.fragment_map_cluster_bottom_sheet_dialog) {

    private lateinit var mapClusterAdapter: MapClusterAdapter

    override fun initCreateDialog(): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)

        return dialog
    }

    override fun initView() {
        initFilterHeight()
        initAdapter()
        initData()
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
        args.clusterList?.run {
            mapClusterAdapter.submitList(this.toMutableList())
        }
    }
}