package com.medicalhealth.healthapplication.view.doctorScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FragmentDoctorInfoBinding
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.utils.utils.getBitmapFromAssets
import com.medicalhealth.healthapplication.view.scheduleScreen.ScheduleActivity
import com.medicalhealth.healthapplication.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorInfoFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var binding: FragmentDoctorInfoBinding
    var doctorObj: Doctor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDoctorInfoBinding.inflate(inflater, container, false)
        buttonClickListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
    }

    private fun setUpObserver(){
        sharedViewModel.selectedDoctor.observe(viewLifecycleOwner){ doctor->

            val experienceText = getString(R.string.doctor_experience_years, doctor.experience)
            val focusText = getString(R.string.doctor_focus_text, doctor.focus)
            val availableTimingSlot = sharedViewModel.formatSchedule(doctor.startDay, doctor.endDay, doctor.startTime, doctor.endTime)
            with(binding){
                ivDoctorface.setImageBitmap(context?.let { getBitmapFromAssets(it, doctor.profileImageUrl) })
                tvExperienceCard.text = HtmlCompat.fromHtml(experienceText,HtmlCompat.FROM_HTML_MODE_COMPACT)
                tvDescription.text = HtmlCompat.fromHtml(focusText, HtmlCompat.FROM_HTML_MODE_COMPACT)
                tvDoctorName.text = doctor.name
                tvSpecialization.text = doctor.specialization
                tvRating.text = "${doctor.rating}"
                tvComment.text = "${doctor.commentCount}"
                tvTime.text = availableTimingSlot
                tvProfile.text = doctor.profile
                tvCareerPath.text = doctor.careerPath
                tvHighlights.text = doctor.highlights

                doctorObj=doctor
            }
        }
    }

     fun buttonClickListeners() {

         with(binding)
         {
             btnSchedule.setOnClickListener {
                 val intent = Intent(requireContext(), ScheduleActivity::class.java)
                 intent.putExtra("clicked_doctor",doctorObj)
                 startActivity(intent)
             }
         }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setTitle(getString(R.string.doctor_info))
    }
}
