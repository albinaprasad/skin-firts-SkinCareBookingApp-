package com.medicalhealth.healthapplication.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.view.DoctorsList
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FragmentDoctorsListBinding
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.view.adapter.DoctorListViewAdapter

import com.medicalhealth.healthapplication.viewModel.DoctorsListViewModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DoctorsList.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorsList : Fragment(), DoctorListViewAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private lateinit var _binding: FragmentDoctorsListBinding

    private val viewModel: DoctorsListViewModel by viewModels()
    private lateinit var adapter: DoctorListViewAdapter // Your custom adapter
    private lateinit var dataList: List<Doctor>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDoctorsListBinding.inflate(inflater, container, false)
        val view = _binding.root

        val adapter = DoctorListViewAdapter(requireContext(), emptyList(), this)

        _binding.doctorsRecyclerView.layoutManager = LinearLayoutManager(context)
        _binding.doctorsRecyclerView.adapter = adapter

        viewModel.doctors.observe(viewLifecycleOwner) { doctors ->
            (_binding.doctorsRecyclerView.adapter as DoctorListViewAdapter).updateData(doctors)
        }
        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()


    }

    interface OnFragmentInteractionListener {
        fun onTitleChange(newTitle: String)
    }

    private var listener: OnFragmentInteractionListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onInfoButtonClick() {
        listener?.onTitleChange("Doctor Info")
        replaceFragment(DoctorInfoFragment())

    }

    private fun replaceFragment(doctorInfoFragment: DoctorInfoFragment) {

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, doctorInfoFragment)
            .addToBackStack(null)
            .commit()

    }

    override fun onResume() {
        super.onResume()

        listener?.onTitleChange("Doctors")
    }


}