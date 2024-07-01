package com.mimo.android.presentation.map

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mimo.android.R
import com.mimo.android.databinding.FragmentMarkerBottomSheetDialogBinding
import com.mimo.android.presentation.base.BaseBottomSheetDialogFragment
import com.mimo.android.presentation.util.getSizeY
import com.mimo.android.presentation.video.upload.UploadVideoActivity
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarkerBottomSheetDialogFragment :
    BaseBottomSheetDialogFragment<FragmentMarkerBottomSheetDialogBinding>(R.layout.fragment_marker_bottom_sheet_dialog) {

    override fun initCreateDialog() = BottomSheetDialog(requireContext(), theme)
    private lateinit var location: LatLng

    override fun initView() {
        initFilterHeight()
        initData()
        clickUploadButton()
    }

    private fun initFilterHeight() {
        with(binding) {
            clMarkerBottomSheet.layoutParams.height = (getSizeY(requireContext()) * 0.2).toInt()
            executePendingBindings()
        }
    }

    private fun initData() {
        val args: MarkerBottomSheetDialogFragmentArgs by navArgs()
        location = LatLng(args.latitude.toDouble(), args.longitude.toDouble())
        binding.address = args.address
    }

    private fun clickUploadButton() {
        binding.btnUploadVideo.setOnClickListener {
            val intent = Intent(requireActivity(), UploadVideoActivity::class.java)
            intent.putExtras(
                bundleOf(
                    "latitude" to location.latitude,
                    "longitude" to location.longitude,
                ),
            )
            requireActivity().startActivity(intent)
            this@MarkerBottomSheetDialogFragment.dismiss()
        }
    }
}
