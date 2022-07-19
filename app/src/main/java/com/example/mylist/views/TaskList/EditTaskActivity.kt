package com.example.mylist.views.TaskList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mylist.R
import com.example.mylist.data.datebase.AppDatabase
import com.example.mylist.data.group.Colour
import com.example.mylist.data.task.Task
import com.example.mylist.tools.*
import com.example.mylist.views.home.addGroupActivity.ColorsAdapter
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import ir.hamsaa.persiandatepicker.date.PersianDateImpl
import kotlinx.android.synthetic.main.activity_add_edit_task.*
import java.util.*

class EditTaskActivity : AppCompatActivity(), ColorsAdapter.ColorEventListener {

    var mCalendar = Calendar.getInstance()
    var selectedColor = 0

    val TAG = "AddNewTaskActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)
        val selectedTask = intent.extras!!.getParcelable<Task>(EXTRA_INTENT_ITEM)
        titleTv.text = getString(R.string.editTaskTitle)

        val viewModel = TaskViewModel(AppDatabase.getDatabaseInstance(this))
        val adapter = ColorsAdapter(this, getColours(), this)
        colorSelectionRv.layoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        val persianDate = PersianDateImpl()
        persianDate.setDate(selectedTask!!.dateLong)
        dateEt.setText(persianDate.persianLongDate)
        titleEditEt.setText(selectedTask.title)
        desEditEt.setText(selectedTask.description)
        adapter.setColorPosition(selectedTask.color)
        selectedColor = selectedTask.color
        mCalendar.timeInMillis = selectedTask.dateLong
        saveNewTask.text = getString(R.string.edit)
        saveNewTask.icon = getDrawable(R.drawable.ic_round_edit_24)
        /*val check = mCalendar.timeInMillis - Calendar.getInstance().timeInMillis
        if (check <= 0) {
            date_layout.error = getString(R.string.pleaseSelectDateCurrectly)
        } else {
            date_layout.isErrorEnabled = false
            dateAddEt.setText(persianDate.persianLongDate)
        }*/

        colorSelectionRv.adapter = adapter
        backBtn.setOnClickListener { finish() }
        dateEt.setOnClickListener {
            getDatePicker(this).setListener(object : PersianPickerListener {
                override fun onDateSelected(persianPickerDate: PersianPickerDate) {
                    Log.i(TAG, "onDateSelected: " + persianPickerDate.persianLongDate.toString())
                    mCalendar = persianDateToCalendar(persianPickerDate)
                    val check = mCalendar.timeInMillis - Calendar.getInstance().timeInMillis
                    if (check <= 0) {
                        date_layout.error = getString(R.string.pleaseSelectDateCurrectly)
                    } else {
                        date_layout.isErrorEnabled = false
                        dateEt.setText(persianPickerDate.persianLongDate)
                    }
                }


                override fun onDismissed() {

                }

            }).show()
        }
        saveNewTask.setOnClickListener {
            if (titleEditEt.text.toString().isEmpty()) {
                title_layout.error = "لطفا متن کار خود را وارد کنید"

            } else if (dateEt.text.toString().isEmpty()) {
                date_layout.error = "لطفا تاریخ را وارد کنید"
            } else {
                title_layout.isErrorEnabled = false
                date_layout.isErrorEnabled = false

                Log.i(TAG, "onDateSelected: " + milliSecToPersianCal(mCalendar.timeInMillis))
                mCalendar.set(Calendar.HOUR_OF_DAY, 0)
                mCalendar.set(Calendar.MINUTE, 0)
                mCalendar.set(Calendar.SECOND, 0)
                mCalendar.set(Calendar.MILLISECOND, 0)

                var task: Task? = null
                task = Task(
                    id = selectedTask.id,
                    groupId = selectedTask.groupId,
                    title = titleEditEt.text.toString(),
                    description = desEditEt.text.toString(),
                    dateLong = mCalendar.timeInMillis,
                    date = mCalendar.time.toString(),
                    color = if (selectedColor == 0) R.color.customColor else selectedColor,
                )
                Log.i(
                    "DateAndTime",
                    "Selected Date From Add New Task Act Long: " + mCalendar.timeInMillis
                )
                Log.i(
                    "DateAndTime",
                    "Selected Date From Add New Task Act String: " + mCalendar.time.toString()
                )
                Toast.makeText(this, getString(R.string.editTaskSuccess), Toast.LENGTH_SHORT).show()

                viewModel.updateTask(task)
                finish()

            }

        }


    }

    override fun onColorClicked(colour: Colour) {
        selectedColor = colour.colorId

    }
}