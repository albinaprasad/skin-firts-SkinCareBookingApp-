package com.medicalhealth.healthapplication.view.reviewScreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityReviewBinding
import com.medicalhealth.healthapplication.databinding.BottomNavigationLayoutBinding
import com.medicalhealth.healthapplication.view.homeScreen.MainActivity
import com.medicalhealth.healthapplication.viewModel.ReviewViewModel

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    private val viewModel: ReviewViewModel by viewModels()
    private lateinit var stars: List<ImageView>
    private lateinit var bottomNavBinding: BottomNavigationLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        stars = listOf(binding.starOne, binding.starTwo, binding.starThree, binding.starFour, binding.starFive)
        bottomNavBinding = BottomNavigationLayoutBinding.bind(binding.reviewBottomNavigationBar.root)
        setUpListeners()
        setUpObserveViewModel()
    }

    private fun setUpListeners(){
         with(binding){
             favoriteLayout.setOnClickListener {
                 viewModel.toggleFavoriteButton()
             }

         }
        stars.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                viewModel.setRating(index)
                fillStar(index)
            }
        }

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

    private fun setUpObserveViewModel(){
        viewModel.isFavorite.observe(this){
            with(binding) {
                if (it) {
                    favoriteImageView.setImageResource(R.drawable.fav_icon_filled_darkblue)
                }else{
                    favoriteImageView.setImageResource(R.drawable.fav_icon)
                }
            }
        }
    }

    private fun fillStar(clickedIndex: Int){
         stars.forEachIndexed { index, imageView ->
             if(index <= clickedIndex){
                 imageView.setImageResource(R.drawable.star_filled)
             }else{
                 imageView.setImageResource(R.drawable.star_icon)
             }
         }
    }

    private fun returnToMain(tab: String){
        val resultIntent = Intent()
        resultIntent.putExtra(MainActivity.FRAGMENT_TO_LOAD_KEY, tab)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

}