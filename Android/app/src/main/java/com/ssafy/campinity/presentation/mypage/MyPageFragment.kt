package com.ssafy.campinity.presentation.mypage

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.campinity.R
import com.ssafy.campinity.databinding.FragmentMyPageBinding
import com.ssafy.campinity.domain.entity.community.NoteQuestionTitle
import com.ssafy.campinity.presentation.base.BaseFragment
import com.ssafy.campinity.presentation.community.note.CommunityNoteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val myPageViewModel by viewModels<MyPageViewModel>()
    private val communityNoteListAdapter by lazy {
        CommunityNoteListAdapter(this::showDialog)
    }

    override fun initView() {
        initRecyclerView()
        initListener()
        initSpinner()
    }

    private fun initListener() {
        binding.clEditProfile.setOnClickListener {
            navigate(MyPageFragmentDirections.actionMyPageFragmentToEditProfileFragment())
        }
        binding.ivArrowLeft.setOnClickListener { popBackStack() }
    }

    private fun initRecyclerView() {
        myPageViewModel.getNotes()
        binding.rvCommunityMyNote.apply {
            adapter = communityNoteListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_array,
            R.layout.spinner_txt
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.adapter = adapter
        }

        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p0?.getItemAtPosition(p2).toString() == "자유") {
                        myPageViewModel.etcNotesListdata.observe(viewLifecycleOwner) { response ->
                            response?.let {
                                communityNoteListAdapter.setNote(it.map { info ->
                                    NoteQuestionTitle(
                                        info.content,
                                        info.createdAt,
                                        info.messageId
                                    )
                                })
                            }
                        }
                    } else {
                        myPageViewModel.reviewNotesListData.observe(viewLifecycleOwner) { response ->
                            response?.let {
                                communityNoteListAdapter.setNote(it.map { info ->
                                    NoteQuestionTitle(
                                        info.content,
                                        info.createdAt,
                                        info.messageId
                                    )
                                })
                            }
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    return
                }
            }
    }

    private fun showDialog(noteQuestionId: String) {
        myPageViewModel.getDetailData(noteQuestionId)
        myPageViewModel.detailData.observe(viewLifecycleOwner) {
            val dialog = ReviewNoteDialog(requireContext(), it!!)
            dialog.show()
        }
    }
}