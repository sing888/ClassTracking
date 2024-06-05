package com.project.group.rupp.dse.classtracking.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.activity.ExamActivity
import com.project.group.rupp.dse.classtracking.activity.RoomActivity
import com.project.group.rupp.dse.classtracking.adapter.SubjectAdapter
import com.project.group.rupp.dse.classtracking.databinding.FragmentScoreTeacherBinding
import com.project.group.rupp.dse.classtracking.models.GetTeacherSubject
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.RoomMainViewModel
import com.project.group.rupp.dse.classtracking.viewmodels.TeacherScoreViewModel

class TeacherScoreFragment: Fragment() {
    private var _binding: FragmentScoreTeacherBinding? = null
    private val binding get() = _binding!!
    private val subjectScoreViewHolder: TeacherScoreViewModel by viewModels()
    private val roomMainViewModel: RoomMainViewModel by activityViewModels()

    private var data = listOf<GetTeacherSubject>()
    private var adapter = SubjectAdapter()
    var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentScoreTeacherBinding.inflate(inflater, container, false).apply {
            _binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subjectScoreViewHolder.subjectUiState.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                UiStateStatus.loading -> {

                }

                UiStateStatus.success -> {
                    binding.tvNoSubject.visibility = View.GONE
                    binding.recyclerViewRoom.visibility = View.VISIBLE
                    adapter.setDataset(it.data?.data!!)
                    data = it.data.data
                    binding.recyclerViewRoom.adapter = adapter
                    binding.recyclerViewRoom.layoutManager = linearLayoutManager
                }

                UiStateStatus.error -> {
                    binding.tvNoSubject.visibility = View.VISIBLE
                    binding.recyclerViewRoom.visibility = View.GONE
                }
            }
        })

        subjectScoreViewHolder.getTeacherSubject(requireContext(), roomMainViewModel.roomId.value!!)
        adapter.setViewModel(subjectScoreViewHolder)

        adapter.setListener { index: RecyclerView.ViewHolder ->
            val subject = data[index.adapterPosition]

            val intent = Intent(context, ExamActivity::class.java)
            intent.putExtra("classroom_id", roomMainViewModel.roomId.value)
            intent.putExtra("subject_name", data[index.adapterPosition].name)
            intent.putExtra("subject_id", data[index.adapterPosition].subject_id)
            intent.putExtra("classroom_name", roomMainViewModel.roomName.value)
            startActivity(intent)
        }

        binding.btnAddSubject.setOnClickListener {
            binding.flAddSubject.visibility = View.VISIBLE
        }
        binding.btnCancelSubject.setOnClickListener {
            binding.flAddSubject.visibility = View.GONE
        }

        binding.btnSaveSubject.setOnClickListener {
            if (binding.etSubjectName.text.toString().isEmpty()) {
                binding.etSubjectName.error = "Please enter subject name"
            } else {
                binding.flAddSubject.visibility = View.GONE
                binding.etSubjectName.clearFocus()
                binding.root.hideKeyboard()
                subjectScoreViewHolder.addSubject(
                    requireContext(),
                    binding.etSubjectName.text.toString(),
                    roomMainViewModel.roomId.value!!
                )
                binding.etSubjectName.text = null
                subjectScoreViewHolder.getTeacherSubject(
                    requireContext(),
                    roomMainViewModel.roomId.value!!
                )
            }
        }

        subjectScoreViewHolder.addSubjectUiState.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                UiStateStatus.loading -> {

                }

                UiStateStatus.success -> {
                    Snackbar.make(binding.root, "Success", Snackbar.LENGTH_SHORT).show()
                    subjectScoreViewHolder.getTeacherSubject(
                        requireContext(),
                        roomMainViewModel.roomId.value!!
                    )
                }

                UiStateStatus.error -> {
                    subjectScoreViewHolder.getTeacherSubject(
                        requireContext(),
                        roomMainViewModel.roomId.value!!
                    )
                }
            }
        })

        subjectScoreViewHolder.deleteSubjectUiState.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                UiStateStatus.loading -> {

                }

                UiStateStatus.success -> {
                    Snackbar.make(binding.root, "Success", Snackbar.LENGTH_SHORT).show()
                    subjectScoreViewHolder.getTeacherSubject(
                        requireContext(),
                        roomMainViewModel.roomId.value!!
                    )
                }

                UiStateStatus.error -> {
                    subjectScoreViewHolder.getTeacherSubject(
                        requireContext(),
                        roomMainViewModel.roomId.value!!
                    )
                }
            }
        })
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}