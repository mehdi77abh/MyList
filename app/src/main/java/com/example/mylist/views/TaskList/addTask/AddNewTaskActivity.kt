package com.example.mylist.views.TaskList.addTask

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mylist.R
import com.example.mylist.data.datebase.AppDatabase
import com.example.mylist.data.group.Colour
import com.example.mylist.data.group.Group
import com.example.mylist.data.task.Task
import com.example.mylist.tools.*
import com.example.mylist.views.TaskList.TaskViewModel
import com.example.mylist.views.home.addGroupActivity.ColorsAdapter
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import ir.hamsaa.persiandatepicker.date.PersianDateImpl
import kotlinx.android.synthetic.main.activity_add_edit_task.*
import kotlinx.android.synthetic.main.activity_add_edit_task.colorSelectionRv
import java.util.*

class AddNewTaskActivity : AppCompatActivity(), ColorsAdapter.ColorEventListener {
    var selectedDateCalendar: Calendar = Calendar.getInstance()
    var selectedColor = 0
    var selectedGroup = 0;
    val TAG = "AddNewTaskActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)
        val viewModel = TaskViewModel(AppDatabase.getDatabaseInstance(this))
        val selectedGroup = intent.extras?.getParcelable<Group>(EXTRA_INTENT_ITEM)
        val selectedCalLong = intent.extras?.getLong(EXTRA_DATA)

        if (selectedCalLong != null) {
            if (selectedCalLong>0){
                selectedDateCalendar.timeInMillis = selectedCalLong
                val persianDateImpl= PersianDateImpl()
                persianDateImpl.setDate(selectedCalLong)
                dateEt.setText(persianDateImpl.persianLongDate)

            }

        }

        Log.i(TAG, "onCreate: "+selectedCalLong)
        val adapter = ColorsAdapter(this, getColours(), this)
        colorSelectionRv.layoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)


        titleTv.text = getString(R.string.addTaskTitle)
        colorSelectionRv.adapter = adapter
        backBtn.setOnClickListener { finish() }
        saveNewTask.text = getString(R.string.addNewTask)
        saveNewTask.icon = getDrawable(R.drawable.ic_round_add_24)


        dateEt.setOnClickListener {
            getDatePicker(this).setListener(object : PersianPickerListener {
                override fun onDateSelected(persianPickerDate: PersianPickerDate) {
                    //Log.i(TAG, "onDateSelected: " + persianPickerDate.persianLongDate.toString())
                    selectedDateCalendar = persianDateToCalendar(persianPickerDate)
                    val check  = selectedDateCalendar.timeInMillis - getNowCal().timeInMillis
                    Log.i(
                        TAG,
                        "selectedDateCal : ${selectedDateCalendar.timeInMillis} nowCalendar : ${getNowCal().timeInMillis}"
                    )
                    Log.i(TAG, "onDateSelected: $check")
                    if (check < 0) {
                        date_layout.error = getString(R.string.pleaseSelectDateCurrectly)
                    } else if (check >= 0) {
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

                Log.i(
                    TAG,
                    "onDateSelected: " + milliSecToPersianCal(selectedDateCalendar.timeInMillis)
                )
                selectedDateCalendar.set(Calendar.HOUR_OF_DAY, 0)
                selectedDateCalendar.set(Calendar.MINUTE, 0)
                selectedDateCalendar.set(Calendar.SECOND, 0)
                selectedDateCalendar.set(Calendar.MILLISECOND, 0)

                var task: Task? = null
                task = Task(
                    groupId = selectedGroup?.id!!,
                    title = titleEditEt.text.toString(),
                    description = desEditEt.text.toString(),
                    dateLong = selectedDateCalendar.timeInMillis,
                    date = selectedDateCalendar.time.toString(),
                    color = if (selectedColor == 0)  R.color.customColor else selectedColor,
                )
                Log.i(
                    "DateAndTime",
                    "Selected Date From Add New Task Act Long: " + selectedDateCalendar.timeInMillis
                )
                Log.i(
                    "DateAndTime",
                    "Selected Date From Add New Task Act String: " + selectedDateCalendar.time.toString()
                )

                viewModel.insertTask(task)
                finish()

            }

        }

    }

    override fun onColorClicked(colour: Colour) {
        selectedColor = colour.colorId
    }

    fun getNowCal(): Calendar {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        return cal
    }
}