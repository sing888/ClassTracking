package com.project.group.rupp.dse.classtracking.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.activity.StudentScoreDetail
import com.project.group.rupp.dse.classtracking.adapter.RankingAdapter
import com.project.group.rupp.dse.classtracking.databinding.ActivityScoreStudentDetailBinding
import com.project.group.rupp.dse.classtracking.databinding.FragmentScoreStudentBinding
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.RoomMainViewModel
import com.project.group.rupp.dse.classtracking.viewmodels.StudentScoreViewModel

class StudentScoreFragment: Fragment(){
    private var _binding: FragmentScoreStudentBinding? = null
    private val binding get() = _binding!!
    private val roomMainViewModel: RoomMainViewModel by activityViewModels()
    private val studentScoreViewModel: StudentScoreViewModel by viewModels()

    private var classroomId = ""
    private val adapter = RankingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentScoreStudentBinding.inflate(inflater, container, false).apply {
            _binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var account_id = ""
//        var classroom_id = ""
//
//
//        val mediatorLiveData = MediatorLiveData<Pair<String, String>>()
//
//        mediatorLiveData.addSource(roomMainViewModel.account_id) { id ->
//            account_id = id
//            mediatorLiveData.value = Pair(account_id, classroom_id)
//        }
//
//        mediatorLiveData.addSource(roomMainViewModel.roomId) { id ->
//            classroom_id = id
//            mediatorLiveData.value = Pair(account_id, classroom_id)
//        }
//
//        mediatorLiveData.observe(viewLifecycleOwner, Observer { pair ->
//            val (accountId, classroomId) = pair
//            if (accountId.isNotEmpty() && classroomId.isNotEmpty()) {
//                Snackbar.make(binding.root, "accountId: $accountId, classroomId: $classroomId", Snackbar.LENGTH_SHORT).show()
//                studentScoreViewModel.getMemberId(requireContext(), classroomId)
//            }
//        })

        // get room id from RoomMainViewModel
        roomMainViewModel.roomId.observe(viewLifecycleOwner, Observer {
            studentScoreViewModel.getMemberId(requireContext(), it)
            classroomId = it
        })

        // get member id from StudentScoreViewModel
        studentScoreViewModel.memberIdModelUiState.observe(viewLifecycleOwner, Observer {
            when(it.status){
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    val data = it.data?.data
                    adapter.setMemberId(data?.member_id.toString())
                    if (classroomId.isNotEmpty()) {
                        studentScoreViewModel.getStudentScore(requireContext(), data?.member_id.toString(), classroomId)
                        studentScoreViewModel.getStudentScoreList(requireContext(), classroomId)
                    }
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        // get student score from StudentScoreViewModel
        studentScoreViewModel.studentScoreModelUiState.observe(viewLifecycleOwner, Observer {
            when(it.status){
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    val data = it.data?.data
                    binding.scoreStudentPercentage.text = data?.average_score.toString()
                    binding.scoreStudentPercentageMax.text = "max score: 100"
                    binding.scoreStudentRank.text = data?.rank.toString()
                    binding.scoreStudentRankMax.text = "All Student: "+data?.member_amount.toString()
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        // get student score list from StudentScoreViewModel
        studentScoreViewModel.studentScoreListUiState.observe(viewLifecycleOwner, Observer {
            when(it.status){
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    adapter.setData(it.data!!)
                    adapter.setListener{ index: RecyclerView.ViewHolder? ->
                        val student = it.data[index!!.adapterPosition]
                        // start new activity
                        val intent = Intent(requireContext(), StudentScoreDetail::class.java)
                        intent.putExtra("member_name", student.member_name)
                        intent.putExtra("member_id", student.member_id)
                        intent.putExtra("classroom_id", classroomId)
                        startActivity(intent)
                    }
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.scoreStudentRecyclerView.layoutManager = layoutManager
        binding.scoreStudentRecyclerView.adapter = adapter






    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}