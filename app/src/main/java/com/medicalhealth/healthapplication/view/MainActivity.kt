package com.medicalhealth.healthapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.databinding.ActivityMainBinding
import com.medicalhealth.healthapplication.view.adapter.DateAdapter
import com.medicalhealth.healthapplication.view.adapter.DoctorAdapter
import com.medicalhealth.healthapplication.view.adapter.ScheduleAdapter
import com.medicalhealth.healthapplication.view.doctorScreen.DoctorsActivity
import com.medicalhealth.healthapplication.view.favoriteScreen.FavoriteDoctorsActivity
import com.medicalhealth.healthapplication.view.fragment.BottomNavigationFragment
import com.medicalhealth.healthapplication.view.notificationScreen.NotificationActivity
import com.medicalhealth.healthapplication.view.settingScreen.SettingsActivity

import com.medicalhealth.healthapplication.viewModel.MainViewModel

class MainActivity : BaseActivity() {
        private lateinit var mainBinding: ActivityMainBinding
        private val viewModel: MainViewModel by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            mainBinding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(mainBinding.root)

            setUpRecyclerView()
            setUpListeners()
            mainBinding.favBtn.setOnClickListener {
                val intent = Intent(this, HelpCenterActivity::class.java)
                startActivity(intent)
            }
        }

    override fun onResume() {
        super.onResume()
        setSelectedMenu(BottomNavigationFragment.MenuTypes.HOME)
    }

    private fun setUpRecyclerView() {
            viewModel.dates.value?.let { dates ->
                val dateAdapter = DateAdapter(dates) { selectedDate ->

                    viewModel.selectDate(selectedDate)
                }
                with(mainBinding) {
                    dateRecyclerView.layoutManager =
                        LinearLayoutManager(
                            this@MainActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                    dateRecyclerView.adapter = dateAdapter
                    viewModel.dates.observe(this@MainActivity) { updatedDates ->
                        dateAdapter.notifyDataSetChanged()
                    }
                }
            }

            with(mainBinding) {
                scheduleRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                viewModel.appointment.observe(this@MainActivity) { appointmentsList ->
                    val appointmentAdapter = ScheduleAdapter(appointmentsList)
                    scheduleRecyclerView.adapter = appointmentAdapter
                }
                doctorImageButton.setOnClickListener {
                    val intent = Intent(this@MainActivity,DoctorsActivity::class.java)
                    startActivity(intent)
                }

                doctorRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                viewModel.doctors.observe(this@MainActivity) { doctorsList ->
                    val adapter = doctorsList?.let {
                        DoctorAdapter(it) { doctor ->
                            viewModel.toggleFavoriteStatus(doctor.id)
                        }
                    }
                    doctorRecyclerView.adapter = adapter
                }
            }
    }

    private fun setUpListeners(){
        with(mainBinding){
            doctorImageButton.setOnClickListener {
                val intent = Intent(this@MainActivity, DoctorsActivity::class.java)
                startActivity(intent)
            }
            notificationBtn.setOnClickListener {
                val intent = Intent(this@MainActivity, NotificationActivity::class.java)
                startActivity(intent)
            }

            settingsImage.setOnClickListener {
                val settingsIntent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(settingsIntent)
            }
        }
    }
}
