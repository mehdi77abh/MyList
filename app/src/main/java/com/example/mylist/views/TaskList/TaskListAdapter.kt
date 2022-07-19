package com.example.mylist.views.TaskList

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.R
import com.example.mylist.data.task.Task
import com.example.mylist.tools.*
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.task_item.view.*
import kotlin.time.Duration.Companion.hours

class TaskListAdapter(
    val context: Context,
    val taskList: ArrayList<Task>,
    val taskEventListener: TaskEventListener
) :
    RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) =
        holder.bind(taskList[position])

    override fun getItemCount(): Int = taskList.size


    inner class TaskViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(task: Task) {
            val colorInt = ContextCompat.getColor(context, task.color)

            containerView.titleTv.text = task.title
            containerView.descriptionTv.text = if(task.description.equals("")) "بدون توضیحات" else task.description
            val persianDate = milliSecToPersianCal(task.dateLong)
            Log.i("TAG", "bind: " + persianDate.persianDay.hours.toString())
            containerView.dayTv.text = persianDate.persianDayOfWeekName
            containerView.dateTv.text = FaNum.convert(persianDate.persianDay.toString())
            containerView.dateTv.setTextColor(colorInt)
            containerView.monthTv.text = persianDate.persianMonthName
            containerView.importanceView.setColorFilter(colorInt)
            containerView.checkBtn.setBackgroundResource(R.drawable.shape_checkbox_default)



            containerView.checkBtn.setOnClickListener {

                containerView.checkBtn.setBackgroundResource(R.drawable.shape_checkbox_check)
                containerView.checkBtn.setImageResource(R.drawable.ic_baseline_check_24_white)


                taskEventListener.onCheckButtonClicked(task)


            }


            containerView.setOnLongClickListener {
                taskEventListener.onTaskItemLongClicked(task)
                return@setOnLongClickListener false

            }
            containerView.setOnClickListener {
                taskEventListener.onTaskItemClicked(task)
            }



        }
    }

    interface TaskEventListener {
        fun onTaskItemLongClicked(task: Task)
        fun onCheckButtonClicked(task: Task)
        fun onTaskItemClicked(task:Task)
    }
}