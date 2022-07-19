package com.example.mylist.views.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.R
import com.example.mylist.data.task.Task
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.task_item_calendar_fragment.view.*
import kotlinx.android.synthetic.main.task_item_calendar_fragment.view.checkBtn
import kotlinx.android.synthetic.main.task_item_calendar_fragment.view.descriptionTv
import kotlinx.android.synthetic.main.task_item_calendar_fragment.view.titleTv

class CalendarItemListAdapter(val context: Context, val tasksList: List<Task>,val CalendarListEvent: CalendarListEvent) :
    RecyclerView.Adapter<CalendarItemListAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.task_item_calendar_fragment,parent,false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindView(tasksList[position])
    }

    override fun getItemCount(): Int = tasksList.size

    inner class ItemViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindView(task: Task) {
            val colorInt = ContextCompat.getColor(context,task.color)
            containerView.titleTv.text = task.title
            containerView.descriptionTv.text = "(${task.description})"
            containerView.colorImageView.setColorFilter(colorInt)
            containerView.taskListItem.setBackgroundColor(colorInt)
            containerView.taskListItem.background.alpha = 20
            containerView.checkBtn.setOnClickListener {
                containerView.checkBtn.setBackgroundResource(R.drawable.shape_checkbox_check)
                containerView.checkBtn.setImageResource(R.drawable.ic_baseline_check_24_white)

            }
            containerView.checkBtn.setOnClickListener {
                CalendarListEvent.OnCalendarListItemClicked(task)
            }

        }
    }

}

interface CalendarListEvent{
    fun OnCalendarListItemClicked(task:Task)
}