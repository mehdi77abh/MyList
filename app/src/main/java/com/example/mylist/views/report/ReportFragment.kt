package com.example.mylist.views.report

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.R
import com.example.mylist.data.datebase.AppDatabase
import com.example.mylist.data.task.Task
import com.example.mylist.tools.CustomAlertDialog
import com.example.mylist.tools.EXTRA_INTENT_ITEM
import com.example.mylist.tools.FaNum
import com.example.mylist.views.TaskList.EditTaskActivity
import com.example.mylist.views.TaskList.TaskListAdapter
import kotlinx.android.synthetic.main.report_fragment.*

class ReportFragment : Fragment() , TaskListAdapter.TaskEventListener{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.report_fragment, container, false)
    }
    var viewModel:ReportViewModel? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ReportViewModel(AppDatabase.getDatabaseInstance(requireContext()))

        viewModel?.percentOfSuccessTask!!.observe(viewLifecycleOwner){
            percentOfSuccessTaskTv.text = "${FaNum.convert(it.toString())}%"

        }

        viewModel!!.getNumberOfTask(isComplete = false).observe(viewLifecycleOwner){
            notDoneTaskTv.text ="${FaNum.convert(it.toString())}"
        }
        viewModel!!.getNumberOfTask(isComplete = true).observe(viewLifecycleOwner){
            doneTaskNumberTv.text ="${FaNum.convert(it.toString())}"
        }
        viewModel!!.getNumberOfGroups().observe(viewLifecycleOwner){
            groupNumberTv.text = "${FaNum.convert(it.toString())}"
        }
        viewModel!!.getNumberOfAllTasks().observe(viewLifecycleOwner){
            taskNumberTv.text = "${FaNum.convert(it.toString())}"
        }

        doneTaskRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)

        viewModel!!.getCompleteTasks().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                reportEmptyState.visibility = View.VISIBLE

            } else reportEmptyState.visibility = View.GONE
            val adapter = ReportListAdapter(requireContext(), it as ArrayList<Task>, this)
            doneTaskRv.adapter= adapter
        }

    }

    override fun onTaskItemLongClicked(task: Task) {
        startActivity(Intent(requireContext(),EditTaskActivity::class.java).apply {
            putExtra(EXTRA_INTENT_ITEM,task)
        })
    }

    override fun onCheckButtonClicked(task: Task) {
        task.isComplete = !task.isComplete
        viewModel!!.updateTask(task)

    }

    override fun onTaskItemClicked(task: Task) {
    CustomAlertDialog(getString(R.string.alertDialogOneTaskMessage),
        getString(R.string.alertDialogDeleteOneTaskTitle),object :CustomAlertDialog.DialogListener{
            override fun onYesBtnClicked() {
                viewModel!!.deleteTask(task)
            }

        }).show(parentFragmentManager,null)

    }


}
