package com.mimo.android.presentation.map

import android.content.Intent
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mimo.android.R
import com.mimo.android.databinding.FragmentMapClusterBottomSheetDialogBinding
import com.mimo.android.domain.model.Post
import com.mimo.android.presentation.base.BaseBottomSheetDialogFragment
import com.mimo.android.presentation.component.adapter.MapClusterAdapter
import com.mimo.android.presentation.util.getSizeY
import com.mimo.android.presentation.video_detail.VideoDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MapClusterBottomSheetDialogFragment :
    BaseBottomSheetDialogFragment<FragmentMapClusterBottomSheetDialogBinding>(R.layout.fragment_map_cluster_bottom_sheet_dialog) {

    private lateinit var mapClusterAdapter: MapClusterAdapter
    private var markerList: List<Post> = emptyList()
    private var clusterList: List<Post> = emptyList()

    override fun initCreateDialog() = BottomSheetDialog(requireContext(), theme)

    override fun initView() {
        initFilterHeight()
        setRecyclerView()
        initData()
        clickMarker()
    }

    private fun initFilterHeight() { // 바텀 시트 높이 지정
        with(binding) {
            crMapCluster.layoutParams.height = (getSizeY(requireContext()) * 0.8).toInt()
            executePendingBindings()
        }
    }

    private fun setRecyclerView() {
        mapClusterAdapter = MapClusterAdapter()
        with(binding.rcMapCluster) {
            adapter = mapClusterAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

    private fun initData() {
        val args: MapClusterBottomSheetDialogFragmentArgs by navArgs()
        clusterList = args.clusterPostList?.let {
            Json.decodeFromString(it)
        } ?: emptyList()
        markerList = args.postList?.let {
            Json.decodeFromString(it)
        } ?: emptyList()
        mapClusterAdapter.submitList(clusterList)
        binding.address = args.address
    }

    private fun clickMarker() { // 특정 마커 클릭시
        mapClusterAdapter.onItemClickListener { postData ->
            val postList = Json.encodeToString(markerList)
            startActivity(
                Intent(requireActivity(), VideoDetailActivity::class.java).apply {
                    putExtra("postList", postList)
                    putExtra(
                        "postIndex",
                        markerList.indexOf(markerList.filter { it.id == postData.id }[0]),
                    )
                },
            )
        }
    }
}
