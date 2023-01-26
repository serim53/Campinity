package com.ssafy.campinity.presentation.community

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.ssafy.campinity.R
import com.ssafy.campinity.common.util.CustomDialog
import com.ssafy.campinity.common.util.CustomDialogInterface
import com.ssafy.campinity.databinding.FragmentCommunityCampsiteBinding
import com.ssafy.campinity.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class CommunityCampsiteFragment :
    BaseFragment<FragmentCommunityCampsiteBinding>(R.layout.fragment_community_campsite),
    CustomDialogInterface {

    private lateinit var textViewList: List<TextView>
    private lateinit var fabList: List<ConstraintLayout>
    private lateinit var mapView: MapView
    private val darkBackground = Paint()
    private val moveValues: List<Float> = listOf(800f, 600f, 400f, 200f)
    private var isFabOpen = false

    override fun initView() {
        initListener()
    }

    override fun onResume() {
        super.onResume()
        mapView = MapView(activity)
        binding.mvCommunityMap.addView(mapView)
    }

    override fun onPause() {
        super.onPause()
        binding.mvCommunityMap.removeView(mapView)
    }

    override fun onFinishButton() {
        onDestroyView()
    }

    private fun initListener() {
        binding.fabHelp.setOnClickListener {
        }

        binding.fabGetHelp.setOnClickListener {
            CustomDialog(
                requireContext(),
                this,
                R.layout.dialog_write_event_note,
                R.id.iv_cancel_help_dialog,
                R.id.btn_make_note_help
            ).show()
        }

        binding.fabReview.setOnClickListener {
            CustomDialog(
                requireContext(),
                this,
                R.layout.dialog_write_review_note,
                R.id.iv_close_write_review_note_dialog,
                R.id.tv_make_review
            ).show()
        }

        binding.fabFreeNote.setOnClickListener {
            CustomDialog(
                requireContext(),
                this,
                R.layout.dialog_write_free_note,
                R.id.iv_close_write_review_note_dialog,
                R.id.tv_make_review
            ).show()
        }

        // SlidingUpPanel
        val slidePanel = binding.slCommunityFrame
        // 이벤트 리스너 추가
        slidePanel.addPanelSlideListener(PanelEventListener())

        // 패널 열고 닫기
        binding.ibSelectCampsiteCondition.setOnClickListener {
            val state = slidePanel.panelState
            // 닫힌 상태일 경우 열기
            if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            }
        }

        darkBackground.color = Color.BLACK
        darkBackground.alpha = 100

        binding.apply {
            textViewList = listOf(tvFabHelp, tvFabGetHelp, tvFabReview, tvFabFreeNote)
            fabList = listOf(clFabHelp, clFabGetHelp, clFabReview, clFabFreeNote)

            fabMain.setOnClickListener {
                if (isFabOpen) {
                    for (i in fabList) {
                        returnFab(i)
                    }
                    clMapBackSite.setBackgroundColor(Color.TRANSPARENT)
                    for (i in textViewList) {
                        eraseTv(i)
                    }
                } else {
                    for (i in 0..3) {
                        moveFab(fabList[i], moveValues[i])
                    }
                    clMapBackSite.setBackgroundColor(darkBackground.color)
                    for (i in textViewList) {
                        writeTv(i)
                    }
                }
                isFabOpen = !isFabOpen
            }
        }
    }

    private fun eraseTv(textView: TextView) {
        textView.visibility = View.GONE
    }

    private fun writeTv(textView: TextView) {
        textView.visibility = View.VISIBLE
    }

    private fun returnFab(clFab: ConstraintLayout) {
        ObjectAnimator.ofFloat(clFab, "translationY", 0f).apply { start() }
    }

    private fun moveFab(clFab: ConstraintLayout, moveValue: Float) {
        ObjectAnimator.ofFloat(clFab, "translationY", -moveValue).apply { start() }
    }

    // 이벤트 리스너
    inner class PanelEventListener : SlidingUpPanelLayout.PanelSlideListener {
        // 패널이 슬라이드 중일 때
        override fun onPanelSlide(panel: View?, slideOffset: Float) {}

        // 패널의 상태가 변했을 때
        override fun onPanelStateChanged(
            panel: View?,
            previousState: SlidingUpPanelLayout.PanelState?,
            newState: SlidingUpPanelLayout.PanelState?
        ) {
        }
    }
}