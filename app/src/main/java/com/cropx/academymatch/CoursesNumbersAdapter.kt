package com.cropx.academymatch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class CoursesNumbersAdapter(
    private var dataSet: List<String>, private val interaction: ICoursesNumbersAdapter
) : RecyclerView.Adapter<CoursesNumbersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val editText: TextInputEditText = view.findViewById(R.id.course_number_edit_text)
        val addCourseButton: ImageView = view.findViewById(R.id.add_course)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val courseNumber = dataSet[position]
        holder.editText.setText(courseNumber)

        holder.editText.addTextChangedListener {
            interaction.onEditTextUpdated(position, it.toString())
        }

        holder.addCourseButton.setOnClickListener {
            interaction.onAddCourseClicked()
        }
    }

    override fun getItemCount() = dataSet.size

    fun getCoursesNumbers(): List<Int> {
        val finalList: MutableList<Int> = mutableListOf()
        dataSet.forEach { dataSetItem ->
            if (dataSetItem.isEmpty()) return@forEach

            finalList.add(dataSetItem.toInt())
        }
        return finalList
    }
}

interface ICoursesNumbersAdapter {
    fun onAddCourseClicked()
    fun onEditTextUpdated(position: Int, value: String)
}