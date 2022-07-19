package com.example.mylist.views.report

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.R
import com.example.mylist.data.task.Task
import com.example.mylist.tools.FaNum
import com.example.mylist.tools.milliSecToPersianCal
import com.example.mylist.views.TaskList.TaskListAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.task_item.view.*
import kotlin.time.Duration.Companion.hours

class ReportListAdapter(
    val context: Context,
    val tasksList: ArrayList<Task>,
    val taskEventListener: TaskListAdapter.TaskEventListener
) :
    RecyclerView.Adapter<ReportListAdapter.HistoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        HistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.task_item, parent, false
            )
        )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) =
        holder.bind(tasksList[position])

    override fun getItemCount(): Int = tasksList.size

    inner class HistoryViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(task: Task) {
            val colorInt = ContextCompat.getColor(context, task.color)

            containerView.titleTv.text = task.title
            containerView.descriptionTv.text = task.description
            val persianDate = milliSecToPersianCal(task.dateLong)
            Log.i("TAG", "bind: " + persianDate.persianDay.hours.toString())
            containerView.dayTv.text = persianDate.persianDayOfWeekName
            containerView.dateTv.text = FaNum.convert(persianDate.persianDay.toString())
            containerView.dateTv.setTextColor(colorInt)
            containerView.alpha = 0.7F
            containerView.monthTv.text = persianDate.persianMonthName
            containerView.importanceView.setColorFilter(colorInt)
            containerView.checkBtn.setBackgroundResource(R.drawable.shape_checkbox_check)
            containerView.checkBtn.setImageResource(R.drawable.ic_baseline_check_24_white)



            containerView.checkBtn.setOnClickListener {

                containerView.checkBtn.setBackgroundResource(R.drawable.shape_checkbox_default)
                containerView.checkBtn.setImageResource(0)


                taskEventListener.onCheckButtonClicked(task)



            }



            containerView.setOnClickListener {
                taskEventListener.onTaskItemLongClicked(task)
            }


        }


    }

    interface TaskEventListener {
        fun onTaskItemClicked(task: Task)
        fun onCheckButtonClicked(task: Task)
    }
}