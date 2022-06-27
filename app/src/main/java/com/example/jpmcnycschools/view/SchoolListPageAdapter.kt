package com.example.jpmcnycschools.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jpmcnycschools.databinding.SchoolListItemBinding
import com.example.jpmcnycschools.model.SchoolResponse


class SchoolListPageAdapter(
    private val schoolList: MutableList<SchoolResponse> = mutableListOf(),
    private val openDetails: (SchoolResponse) -> Unit
): RecyclerView.Adapter<SchoolListPageAdapter.SchoolViewHolder>() {

    class SchoolViewHolder(val binding: SchoolListItemBinding
    ): RecyclerView.ViewHolder(binding.root)

    fun updateData(newSchools: List<SchoolResponse>) {
        schoolList.addAll(newSchools)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        return SchoolViewHolder(
            SchoolListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.binding.tvSchoolName.text = schoolList[position].school_name
        holder.binding.tvAddress.text = schoolList[position].primary_address_line_1
        holder.binding.tvPhoneNumber.text = schoolList[position].phone_number
        holder.binding.tvWebsite.text = schoolList[position].website

        holder.binding.root.setOnClickListener {
            openDetails(schoolList[position])
        }
    }

    override fun getItemCount(): Int = schoolList.size
}