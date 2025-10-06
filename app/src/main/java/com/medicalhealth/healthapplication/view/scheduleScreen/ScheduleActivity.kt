package com.medicalhealth.healthapplication.view.scheduleScreen


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityScheduleBinding
import com.medicalhealth.healthapplication.databinding.BottomNavigationLayoutBinding
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.model.data.Users
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.utils.utils.getSystemBarInsets
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.adapter.DateAdapterForScheduling
import com.medicalhealth.healthapplication.view.adapter.TimeSlotAdapterForScheduling
import com.medicalhealth.healthapplication.view.doctorScreen.DoctorsActivity
import com.medicalhealth.healthapplication.view.homeScreen.MainActivity
import com.medicalhealth.healthapplication.viewModel.MainViewModel
import com.medicalhealth.healthapplication.viewModel.ScheduleCalenderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import kotlin.getValue

@AndroidEntryPoint
class ScheduleActivity : BaseActivity() {
    lateinit var binding: ActivityScheduleBinding
    private val viewModel: ScheduleCalenderViewModel by viewModels()

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var dummyDoctor: Doctor
    lateinit var userObj: Users

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            insets.getSystemBarInsets(v) {
                binding.root.setPadding(0, 0, 0, it.bottom)
            }
        }

        observeUserData()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeUserData(){
        lifecycleScope.launch {
            viewModel.currentUserDetails.collectLatest { resource ->
                Log.d("message", "$resource")
                when(resource){
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        if(resource.data != null) {
                            userObj = resource.data
                            getDoctorData()
                            spinnerSetUp()
                            setUpListeners()
                            dateRecyclerViewSetUp()
                            timeslotAdapterSetup()
                            listenToButtonClicks()
                            observeBookingStatus()
                            observeFavoriteStateOfDoctor()

                            viewModel.selectTodayDateAsDefault()
                            personalDetailsButtonSelection(binding.yourselfTextView)
                        }
                    }
                }
            }
        }
    }

    suspend fun observeFavoriteStateOfDoctor(){
        mainViewModel.currentUserDetails.collectLatest { resource ->
            if (resource is Resource.Success && resource.data != null) {
                val isFavorite = resource.data.favouriteDoctors.contains(dummyDoctor.id)
                dummyDoctor.isFavorite = isFavorite
                updateFavoriteButtonUI(isFavorite)
            }
        }
    }
   fun updateFavoriteButtonUI(isFavorite:Boolean){
       binding.favBtn.setImageResource(
           if (isFavorite) R.drawable.fav_icon_filled_darkblue else R.drawable.fav_icon
       )
   }
    private fun setUpListeners(){
       val bottomNavBinding = BottomNavigationLayoutBinding.bind(binding.bottomNavigationBar.root)

        with(bottomNavBinding){
            homeButton.setOnClickListener {
                returnToMain("home")
            }
            chatButton.setOnClickListener {
                returnToMain("chat")
            }
            profileButton.setOnClickListener {
                returnToMain("profile")
            }
            calenderButton.setOnClickListener {
                returnToMain("calendar")
            }

        }
    }
    fun returnToMain(tab: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.FRAGMENT_TO_LOAD_KEY, tab)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDoctorData() {
        dummyDoctor =intent.getSerializableExtra("clicked_doctor") as Doctor
        viewModel.setCurrentDoctor(dummyDoctor.id)
        Log.d("Doctor","${dummyDoctor.startDay   }${dummyDoctor.endDay   }")

        viewModel.setCurrentDoctor(dummyDoctor.id)
        viewModel.setDoctor(dummyDoctor)
        viewModel.currentDoctor.observe(this){ doctor->
          binding.titleText.text=doctor.name
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeBookingStatus() {

        viewModel.bookingStatus.observe(this) { resource ->

            when (resource) {
                is Resource.Loading -> {

                    with(binding){

                       submitButton.isEnabled = false
                       submitButton.text = getString(R.string.creating_booking)
                       submitButton.setTextColor(ContextCompat.getColor(this@ScheduleActivity,android.R.color.white))

                    }
              }
                is Resource.Success -> {
                  with(binding){
                     submitButton.isEnabled = true
                     submitButton.text = getString(R.string.book_appointment)

                  }
                }
                is Resource.Error -> {
                   with(binding){
                     submitButton.isEnabled = true
                     submitButton.text = getString(R.string.book_appointment)
                   }

                }
            }
        }

        //availability checking
        viewModel.availabilityStatus.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.TimeRecyclerView.alpha = 0.5f
                }

                is Resource.Success -> {
                    binding.TimeRecyclerView.alpha = 1.0f
                }

                is Resource.Error -> {
                    binding.TimeRecyclerView.alpha = 1.0f
                    Toast.makeText(
                        this,
                        "Could not check availability",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun timeslotAdapterSetup() {
        val timeSlotAdapter = TimeSlotAdapterForScheduling(viewModel)
        binding.TimeRecyclerView.layoutManager = GridLayoutManager(this, 5)
        binding.TimeRecyclerView.adapter = timeSlotAdapter
        viewModel.timeSlot.observe(this) { newTimeSlots ->
            timeSlotAdapter.updateTimeSlots(newTimeSlots)
        }
        viewModel.generateTimeSlots()
    }

    private fun spinnerSetUp() {
        val spinner = binding.monthSpinner
        val months = resources.getStringArray(R.array.months)
        val adapter = ArrayAdapter(this, R.layout.spinnner_month_item, months)
        adapter.setDropDownViewResource(R.layout.spinner_month_dropdown)
        spinner.adapter = adapter

        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
        spinner.setSelection(currentMonth)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                viewModel.generateMonthDates(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateRecyclerViewSetUp() {


        val dateAdapter = DateAdapterForScheduling(mutableListOf(),dummyDoctor,onDateClick = { selectedDate ->
            viewModel.onDateSelected(selectedDate) })

        with(binding) {
            scheduleRecyclerView.layoutManager = LinearLayoutManager(
                this@ScheduleActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            scheduleRecyclerView.adapter = dateAdapter
            viewModel.dateList.observe(this@ScheduleActivity) { newDateList ->
                dateAdapter.updateDates(newDateList)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun listenToButtonClicks() {

        with(binding)
        {
            backButton.setOnClickListener {
                onBackPressed()
            }
            dateFwdBtn.setOnClickListener {
                scheduleRecyclerView.smoothScrollBy(300, 0)
            }

            dateBackwardBtn.setOnClickListener {
                scheduleRecyclerView.smoothScrollBy(-300, 0)
            }
            maleBtn.setOnClickListener {
                genderButtonSelection(maleBtn)
            }
            femaleBtn.setOnClickListener {
                genderButtonSelection(femaleBtn)
            }
            otherBtn.setOnClickListener {
                genderButtonSelection(otherBtn)
            }
            favBtn.setOnClickListener {
                mainViewModel.toggleFavoriteStatus(dummyDoctor.id)
                dummyDoctor.isFavorite = !dummyDoctor.isFavorite

            }
            yourselfTextView.setOnClickListener {
                personalDetailsButtonSelection(yourselfTextView)
            }
            anotherPersonTextView.setOnClickListener {
                personalDetailsButtonSelection(anotherPersonTextView)
            }
           submitButton.setOnClickListener {

               if (viewModel.selectedDate.value == null) {
                   Toast.makeText(this@ScheduleActivity, "Please select a date", Toast.LENGTH_SHORT).show()
                   return@setOnClickListener
               }

               if (viewModel.selectedTimeSlot.value == null) {
                   Toast.makeText(this@ScheduleActivity, "Please select a time slot", Toast.LENGTH_SHORT).show()
                   return@setOnClickListener
               }
               createBooking()
           }
            infoBtn.setOnClickListener {
                val intent = Intent(this@ScheduleActivity, DoctorsActivity::class.java)
                intent.putExtra("SHOW_DOCTOR_INFO", true)
                intent.putExtra("doctor_object", dummyDoctor)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
                finish()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createBooking() {

        with(binding)
        {
            val patientName = if (isYourselfSelected()) {
               userObj.userName


            } else {
                fullNameEditText.text.toString().trim()
            }

            val patientAge = if (isYourselfSelected()) {

              calculateAgeFromDob(userObj.dateOfBirth)?.toInt()

            } else {
                ageEditText.text.toString().toIntOrNull() ?: 0
            }

            val patientGender =
                with(binding){
                    when {
                   maleBtn.isSelected -> getString(R.string.male)
                   femaleBtn.isSelected -> getString(R.string.female)
                   otherBtn.isSelected -> getString(R.string.other)
                    else -> getString(R.string.male)
                }
            }

            val problemDescription = descriptionEditText.text.toString()

            val personType = if (isYourselfSelected()) {
                "Yourself"
            } else {
                "AnotherPerson"
            }
           val bookedObject= viewModel.createBooking(
                patientName = patientName as String,
                patientAge = patientAge as Int,
                patientGender = patientGender,
                problemDescription = problemDescription,
                personType=personType,
                userId = userObj.uid)

               if( bookedObject != null ){
                   val intent = Intent(this@ScheduleActivity, ScheduleDetailsActivity::class.java)
                   intent.putExtra("booking_object", bookedObject)
                   intent.putExtra("doctor_object", dummyDoctor)
                   startActivity(intent)
               }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateAgeFromDob(dob: String): Int? {
        val formatters = listOf(
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("d/MM/yyyy"),
            DateTimeFormatter.ofPattern("d/M/yyyy"),
            DateTimeFormatter.ofPattern("dd/M/yyyy")
        )
        for( formatter in formatters){
            try {
                val birthDate = LocalDate.parse(dob, formatter)
                val current_date= LocalDate.now()
                return Period.between(birthDate, current_date).years
            }
            catch (e: DateTimeParseException) {
                continue
            }
        }
        return null
    }

    private fun isYourselfSelected(): Boolean {

            return binding.yourselfTextView.isSelected
        }
    @RequiresApi(Build.VERSION_CODES.O)
    fun personalDetailsButtonSelection(selectedTextView: TextView) {
        with(binding) {

            yourselfTextView.background = ContextCompat.getDrawable(this@ScheduleActivity, R.drawable.white_background_with_stroke)
            yourselfTextView.setTextColor(ContextCompat.getColor(this@ScheduleActivity, R.color.edittext_hintColor))
            anotherPersonTextView.background = ContextCompat.getDrawable(this@ScheduleActivity, R.drawable.white_background_with_stroke)
            anotherPersonTextView.setTextColor(ContextCompat.getColor(this@ScheduleActivity, R.color.edittext_hintColor))
            selectedTextView.background = ContextCompat.getDrawable(this@ScheduleActivity, R.drawable.dark_blue_round_corner)
            selectedTextView.setTextColor(ContextCompat.getColor(this@ScheduleActivity, R.color.white))

            yourselfTextView.isSelected = (selectedTextView == yourselfTextView)
            anotherPersonTextView.isSelected = (selectedTextView == anotherPersonTextView)

            if (selectedTextView == yourselfTextView) {
                fullNameEditText.isEnabled = false
                ageEditText.isEnabled = false
                fullNameEditText.alpha = 0.5f
                ageEditText.alpha = 0.5f

                fullNameEditText.hint = userObj.userName
                val age = calculateAgeFromDob(userObj.dateOfBirth)
                ageEditText.hint = age?.toString() ?: ""

            } else {

                fullNameEditText.isEnabled = true
                ageEditText.isEnabled = true
                fullNameEditText.alpha = 1.0f
                ageEditText.alpha = 1.0f

                fullNameEditText.hint = ""
                ageEditText.hint = ""
            }
        }
    }

     fun genderButtonSelection(selectedBtn: TextView) {

        with(binding) {

            maleBtn.background =
                ContextCompat.getDrawable(
                    this@ScheduleActivity,
                    R.drawable.white_background_with_stroke
                )
            maleBtn.setTextColor(
                ContextCompat.getColor(
                    this@ScheduleActivity,
                    R.color.edittext_hintColor
                )
            )

            femaleBtn.background =
                ContextCompat.getDrawable(
                    this@ScheduleActivity,
                    R.drawable.white_background_with_stroke
                )
            femaleBtn.setTextColor(
                ContextCompat.getColor(
                    this@ScheduleActivity,
                    R.color.edittext_hintColor
                )
            )

            otherBtn.background =
                ContextCompat.getDrawable(
                    this@ScheduleActivity,
                    R.drawable.white_background_with_stroke
                )
            otherBtn.setTextColor(
                ContextCompat.getColor(
                    this@ScheduleActivity,
                    R.color.edittext_hintColor
                )
            )

            selectedBtn.background =
                ContextCompat.getDrawable(this@ScheduleActivity, R.drawable.dark_blue_round_corner)
            selectedBtn.setTextColor(ContextCompat.getColor(this@ScheduleActivity, R.color.white))

            maleBtn.isSelected = (selectedBtn == maleBtn)
            femaleBtn.isSelected = (selectedBtn == femaleBtn)
            otherBtn.isSelected = (selectedBtn == otherBtn)
        }
    }
}





