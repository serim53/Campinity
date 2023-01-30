package com.ssafy.campinity.presentation.search

import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.getDeviceHeightPx
import com.ssafy.campinity.common.util.px
import com.ssafy.campinity.databinding.FragmentSearchMainBinding
import com.ssafy.campinity.presentation.base.BaseFragment

class SearchMainFragment : BaseFragment<FragmentSearchMainBinding>(R.layout.fragment_search_main) {
    private lateinit var behaviorList: BottomSheetBehavior<LinearLayout>
    private lateinit var behaviorArea: BottomSheetBehavior<FragmentContainerView>
    private lateinit var behaviorFilter: BottomSheetBehavior<FragmentContainerView>

    override fun initView() {
        behaviorList = BottomSheetBehavior.from(binding.llList)
        behaviorArea = BottomSheetBehavior.from(binding.fcvArea)
        behaviorFilter = BottomSheetBehavior.from(binding.fcvFilter)

        initListener()
        initBehaviorList()
        initBehaviorArea()
    }

    private fun initListener() {
        binding.rlShowList.setOnClickListener {
            behaviorList.state = BottomSheetBehavior.STATE_EXPANDED
            binding.clSearch.setBackgroundResource(R.drawable.bg_rect_bilbao_under_radius30)
            binding.rlSearchAgain.visibility = View.GONE
            binding.rlShowMap.visibility = View.VISIBLE
            binding.rlShowList.visibility = View.GONE
        }

        binding.rlShowMap.setOnClickListener {
            behaviorList.state = BottomSheetBehavior.STATE_COLLAPSED
            binding.viewEmptySpace.visibility = View.INVISIBLE
            binding.clSearch.background = null
            binding.rlSearchAgain.visibility = View.VISIBLE
            binding.rlShowList.visibility = View.VISIBLE
            binding.rlShowMap.visibility = View.GONE
        }

        binding.rlArea.setOnClickListener {
            binding.clSearch.visibility = View.GONE
            behaviorArea.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun initBehaviorList() {
        behaviorList.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.rlShowList.visibility = View.VISIBLE
                        binding.rlShowMap.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.viewEmptySpace.visibility = View.VISIBLE
                        binding.clSearch.setBackgroundResource(R.drawable.bg_rect_bilbao_under_radius30)
                        binding.rlSearchAgain.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        binding.viewEmptySpace.visibility = View.INVISIBLE
                        binding.clSearch.background = null
                        binding.rlSearchAgain.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {}
                    BottomSheetBehavior.STATE_HIDDEN -> {}
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    private fun initBehaviorArea() {
        var isDragging = false

        behaviorArea.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        isDragging = false
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        isDragging = false
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        isDragging = true
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {}
                    BottomSheetBehavior.STATE_HIDDEN -> {}
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (isDragging)
                    if (slideOffset <=
                        (getDeviceHeightPx(requireContext()) - 115.px(requireContext())).toFloat()
                        / getDeviceHeightPx(requireContext())
                    ) {
                        binding.clSearch.apply {
                            visibility = View.VISIBLE
                            alpha = 1 - 1.2F * slideOffset
                        }
                    } else {
                        binding.clSearch.visibility = View.GONE
                    }
            }
        })
    }
}