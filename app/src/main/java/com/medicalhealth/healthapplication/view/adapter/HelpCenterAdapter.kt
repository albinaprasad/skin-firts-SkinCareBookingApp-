package com.medicalhealth.healthapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.databinding.ItemHelpCenterBinding
import com.medicalhealth.healthapplication.model.data.ItemOption

class HelpCenterAdapter(
    private var itemOptionList: List<ItemOption>,
    private val onOptionSelected: (ItemOption) -> Unit
) : RecyclerView.Adapter<HelpCenterAdapter.OptionViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HelpCenterAdapter.OptionViewHolder {
        val binding =
            ItemHelpCenterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HelpCenterAdapter.OptionViewHolder, position: Int) {
        val option = itemOptionList[position]
        with(holder.itemBinding) {
            helpCenterImageView.setImageResource(option.iconResId)
            optionNameTextView.text = option.optionName
        }
        holder.itemView.setOnClickListener {
            onOptionSelected(option)
        }
    }

    override fun getItemCount(): Int {
        return itemOptionList.size
    }

    inner class OptionViewHolder(val itemBinding: ItemHelpCenterBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    fun updateData(newOptionList: List<ItemOption>) {
        itemOptionList = newOptionList
        notifyDataSetChanged()
    }

}