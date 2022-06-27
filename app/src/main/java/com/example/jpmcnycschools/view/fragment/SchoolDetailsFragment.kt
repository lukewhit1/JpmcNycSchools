package com.example.jpmcnycschools.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.jpmcnycschools.UIState
import com.example.jpmcnycschools.databinding.FragmentSchoolDetailsBinding
import com.example.jpmcnycschools.model.SatResponse
import com.example.jpmcnycschools.model.SchoolResponse

class SchoolDetailsFragment: ViewModelFragment() {
    private var _binding: FragmentSchoolDetailsBinding? = null
    private val binding: FragmentSchoolDetailsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchoolDetailsBinding.inflate(layoutInflater)
        val passedBundle: SchoolResponse? = arguments?.getParcelable("KEY")
        renderContactAndOverview(passedBundle)
        configureObserver(passedBundle?.dbn)
        return binding.root
    }

    private fun configureObserver(dbn: String?) {
        viewModel.schoolSatResponse.observe(viewLifecycleOwner) {
            when(it) {
                is UIState.Success<*> -> {
                    val newSchools = it.response as? List<SatResponse>
                    newSchools?.let {
                        dbn?.let { schoolDbn ->
                            renderScoreDetails(it, schoolDbn)
                        } ?: showError("Error at school dbn null")
                    } ?: showError("Error at casting SAT")
                }
                is UIState.Error -> {
                    binding.apply {
                        pbDetailsLoading.visibility = View.GONE
                        tvDetailsError.text = it.error.message
                        tvDetailsError.visibility = View.VISIBLE
                    }
                }
                is UIState.Loading -> { viewModel.getSatScores() }
            }
        }
    }


    private fun renderScoreDetails(satDetails: List<SatResponse>, dbn: String?) {
        satDetails.firstOrNull { it.dbn == dbn }?.let {
            binding.apply {
                tvMathScore.text = it.mathAvg
                tvReadingScore.text = it.readingAvg
                tvWritingScore.text = it.writingAvg
            }
        }
    }

    private fun renderContactAndOverview(school: SchoolResponse?) {
        binding.apply {
            pbDetailsLoading.visibility = View.GONE

            tvSchoolName.text = school?.school_name
            tvAddress.text = school?.primary_address_line_1
            tvPhoneNumber.text = school?.phone_number
            tvWebsite.text = school?.website

            tvOverviewParagraph.text = school?.overview_paragraph
        }
    }

    private fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error occurred")
            .setMessage(message)
            .setNegativeButton("CLOSE") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}