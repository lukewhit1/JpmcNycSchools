package com.example.jpmcnycschools.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jpmcnycschools.OpenDetails
import com.example.jpmcnycschools.R
import com.example.jpmcnycschools.UIState
import com.example.jpmcnycschools.databinding.FragmentSchoolListBinding
import com.example.jpmcnycschools.model.SchoolResponse
import com.example.jpmcnycschools.view.SchoolListPageAdapter

class SchoolListFragment: ViewModelFragment(), OpenDetails {
    private var _binding: FragmentSchoolListBinding? = null
    private val binding: FragmentSchoolListBinding get() = _binding!!

    private lateinit var schoolListPageAdapter: SchoolListPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolListBinding.inflate(layoutInflater)
        viewModel.setLoadingState()
        configureObserver()
        return binding.root
    }

    private fun configureObserver() {
        viewModel.schoolResponse.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UIState.Success<*> -> {
                    renderList(state.response as List<SchoolResponse>)
                }
                is UIState.Error -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvErrorText.text = state.error.message
                        tvErrorText.visibility = View.VISIBLE
                    }
                }
                is UIState.Loading -> {
                    viewModel.getSchoolList()
                }
            }
        }
    }

    private fun renderList(response: List<SchoolResponse>) {

        binding.apply {
            pbLoading.visibility = View.GONE

            rvSchoolList.apply {
                schoolListPageAdapter = SchoolListPageAdapter(openDetails = :: openDetails)
                adapter = schoolListPageAdapter
                schoolListPageAdapter.updateData(response)
            }
        }

    }

    override fun openDetails(school: SchoolResponse) {
        viewModel.setSchoolDetails()
        //requireActivity().supportFragmentManager.setFragmentResult("requestKey", bundleOf("bundleKey" to school))

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, SchoolDetailsFragment(school))
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}