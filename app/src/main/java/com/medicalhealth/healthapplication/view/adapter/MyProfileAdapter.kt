package com.medicalhealth.healthapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.databinding.ItemProfileScreenListBinding
import com.medicalhealth.healthapplication.model.data.ProfileOption
import com.medicalhealth.healthapplication.util.ViewExtension.gone
import com.medicalhealth.healthapplication.util.ViewExtension.show

class MyProfileAdapter(
    private var profileOptionList: List<ProfileOption>,
    private val onOptionSelected: (ProfileOption) -> Unit
) : RecyclerView.Adapter<MyProfileAdapter.OptionViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyProfileAdapter.OptionViewHolder {
         val binding = ItemProfileScreenListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyProfileAdapter.OptionViewHolder, position: Int) {
        val option = profileOptionList[position]
        with(holder.binding){
            imageIconImageView.setImageResource(option.iconResId)
            optionNameTextView.text = option.optionName
            if(option.showNextIcon){
                btnNext.show()
            }else{
                btnNext.gone()
            }
        }
        holder.itemView.setOnClickListener {
            onOptionSelected(option)
        }
    }

    override fun getItemCount(): Int {
        return profileOptionList.size
    }

    inner class OptionViewHolder(val binding: ItemProfileScreenListBinding): RecyclerView.ViewHolder(binding.root)

    fun updateData(newOptionsList: List<ProfileOption>){
        profileOptionList = newOptionsList
        notifyDataSetChanged()
    }

}