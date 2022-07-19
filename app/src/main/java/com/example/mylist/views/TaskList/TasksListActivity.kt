package com.example.mylist.views.TaskList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.R
import com.example.mylist.data.datebase.AppDatabase
import com.example.mylist.data.group.Group
import com.example.mylist.data.task.Task
import com.example.mylist.tools.*
import com.example.mylist.views.TaskList.addTask.AddNewTaskActivity
import kotlinx.android.synthetic.main.activity_tasks_list.*
import kotlinx.android.synthetic.main.activity_tasks_list.calendarEmptyState
import kotlinx.android.synthetic.main.activity_tasks_list.clearAllBtn
import kotlinx.android.synthetic.main.activity_tasks_list.searchCancelBtn
import kotlinx.android.synthetic.main.activity_tasks_list.searchEt

class TasksListActivity : AppCompatActivity(), TaskListAdapter.TaskEventListener {
    val viewModel = TaskViewModel(AppDatabase.getDatabaseInstance(this))
    val TAG = "TaskListActivity"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_list)
        /**
         * Get Selected Group From Previous Page
         */
        val selectedGroup = intent.extras?.getParcelable<Group>(EXTRA_DATA)

        tasksListRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        groupTitleTv.text = selectedGroup!!.title
        searchEtConfig(selectedGroup)


        viewModel.getTasks(selectedGroup.id!!).observe(this) {
            attackAdapterToList(it)
            selectedGroup.counter = it.size
            viewModel.updateGroup(selectedGroup)
        }
        /**
         * Clear Btn Click Interface
         */

        clearAllBtn.setOnClickListener {

            CustomAlertDialog(getString(R.string.alertDialogMessageTask),
                getString(R.string.alertDialogMessageTask),object:CustomAlertDialog.DialogListener{
                    override fun onYesBtnClicked() {
                        viewModel.clearTask(selectedGroup.id)
                    }
                }).show(supportFragmentManager,null)

        }

        /**
         * Back Btn Click Interface
         */
        backBtn.setOnClickListener {
            finish()
        }

        /**
         * When User Click AddNewTaskBtn its Navigate to Add Task Activity(Screen)
         */
        addNewTaskBtn.setOnClickListener {
            startActivity(Intent(this, AddNewTaskActivity::class.java).apply {
                putExtra(EXTRA_INTENT_ITEM, selectedGroup)
            })
        }
    }

    /**
     * When User Long Press(Hold On Item In List) its  Navigate To Edit Task Activity(Screen)
     */
    override fun onTaskItemLongClicked(task: Task) {
        startActivity(Intent(this, EditTaskActivity::class.java).apply {
            putExtra(EXTRA_INTENT_ITEM, task)

        })
    }

    /**
     * When User Click Check Btn (Check Circle) its Block Will Run
     */
    override fun onCheckButtonClicked(task: Task) {
        task.isComplete = !task.isComplete
        viewModel.updateTask(task)

    }


    fun attackAdapterToList(list: List<Task>) {
        if (list.isEmpty()) {
            calendarEmptyState.visibility = View.VISIBLE

        } else calendarEmptyState.setVisibility(View.GONE)
        val adapter = TaskListAdapter(this, list as ArrayList<Task>, this@TasksListActivity)
        tasksListRv.adapter = adapter


    }

    fun searchEtConfig(selectedGroup: Group) {
        /**
         * Add Text Change To Search Edit Text For Searching
         * it has 3 Method that we just need OnTextChange
         *
         */
        searchEt.addTextChangedListener(
            object : MyTextWatcher() {


                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.i(TAG, "onTextChanged: $s")
                    if (s!!.isNotEmpty()) {
                        Log.i(TAG, "onTextChanged: $s")
                        viewModel.searchTask(s.toString(), selectedGroup.id!!)
                            .observe(this@TasksListActivity) {
                                attackAdapterToList(it)
                            }
                    } else {
                        viewModel.getTasks(selectedGroup.id!!).observe(this@TasksListActivity) {
                            attackAdapterToList(it)
                        }

                    }
                }


            }
        )

        searchEt.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                searchCancelBtn.visibility = View.VISIBLE
                searchEt.isCursorVisible = true
            } else
                searchCancelBtn.visibility = View.INVISIBLE

        }
        searchEt.setOnClickListener {
            searchCancelBtn.visibility = View.VISIBLE
            searchEt.isCursorVisible = true
        }

        searchCancelBtn.setOnClickListener {
            searchCancelBtn.visibility = View.INVISIBLE
            searchEt.isClickable = false
            searchEt.isCursorVisible = false
            hideKeyboardFrom(this, searchCancelBtn)
            searchEt.text.clear()
        }
    }
    /**
     * When User Press(Click On Item In List) its Shows Alert Dialog For Deleting That Item
     */
    override fun onTaskItemClicked(task: Task) {
        CustomAlertDialog(getString(R.string.alertDialogOneTaskMessage),
            getString(R.string.alertDialogDeleteOneTaskTitle),object :CustomAlertDialog.DialogListener{
                override fun onYesBtnClicked() {
                    viewModel!!.deleteTask(task)
                }

            }).show(supportFragmentManager,null)
    }


}
