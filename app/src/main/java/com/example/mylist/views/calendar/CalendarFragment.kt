package com.example.mylist.views.calendar


import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.R
import com.example.mylist.data.datebase.AppDatabase
import com.example.mylist.data.task.Task
import com.example.mylist.tools.FaNum
import com.example.mylist.views.TaskList.TaskViewModel
import com.example.mylist.views.calendar.selectGroup.SelectGroupDialog
import com.mohamadian.persianhorizontalexpcalendar.PersianHorizontalExpCalendar.PersianHorizontalExpCalListener
import com.mohamadian.persianhorizontalexpcalendar.enums.PersianCustomMarks
import com.mohamadian.persianhorizontalexpcalendar.enums.PersianViewPagerType
import com.mohamadian.persianhorizontalexpcalendar.model.CustomGradientDrawable
import ir.hamsaa.persiandatepicker.date.PersianDateImpl
import ir.hamsaa.persiandatepicker.util.PersianHelper
import kotlinx.android.synthetic.main.calendar_fragment.*
import org.joda.time.Chronology
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.chrono.PersianChronologyKhayyam
import java.util.*


class CalendarFragment : Fragment() {
    val TAG = "DateAndTime"
    var changedTime: DateTime = DateTime()
    private var mCalendar = Calendar.getInstance()
    var viewModel: TaskViewModel? = null
    private val perChr: Chronology =
        PersianChronologyKhayyam.getInstance(DateTimeZone.forID("Asia/Tehran"))
    private val nowTime: DateTime = DateTime(perChr)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.calendar_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val persianDate = PersianDateImpl()
        viewModel = TaskViewModel(AppDatabase.getDatabaseInstance(requireContext()))
        setEvents()
        val yearName = PersianHelper.toPersianNumber(persianDate.persianYear.toString())
        yearTv.text = yearName
        dayOfMonthTv.text = FaNum.convert(persianDate.persianDay.toString())
        monthNameTv.text = persianDate.persianMonthName

        persianDate.setDate(changedTime.toDate())
        setSelectedTvString(persianDate)
        tasksListRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        persianCalendarSettings()
        addTaskForDateBtn.setOnClickListener {

            viewModel!!.getGroups().observe(viewLifecycleOwner){

                SelectGroupDialog(mCalendar.timeInMillis,it).show(parentFragmentManager,null)

            }

        }
        setList(persianDate.timestamp)


        persianCalendar.setPersianHorizontalExpCalListener(object :
            PersianHorizontalExpCalListener {
            override fun onCalendarScroll(dateTime: DateTime) {
                Log.i(TAG, "onCalendarScroll: " + dateTime.toDate().toString())
                changedTime = dateTime
                persianDate.setDate(dateTime.toDate())
                //val yearName = PersianHelper.toPersianNumber(persianDate.persianYear.toString())
                setSelectedTvString(persianDate)

            }

            override fun onDateSelected(dateTime: DateTime) {
                mCalendar.time = dateTime.toDate()
                mCalendar.set(Calendar.HOUR_OF_DAY, 0)
                mCalendar.set(Calendar.MINUTE, 0)
                mCalendar.set(Calendar.SECOND, 0)
                mCalendar.set(Calendar.MILLISECOND, 0)
                persianDate.setDate(dateTime.toDate())
                setSelectedTvString(persianDate)
                setList(persianDate.timestamp)

                Log.i(TAG, "Selected Date From Calendar String: ${mCalendar.time}")
                Log.i(TAG, "Selected Date From Calendar Long: ${mCalendar.timeInMillis}")
            }

            override fun onChangeViewPager(persianViewPagerType: PersianViewPagerType) {
                Log.i(TAG, "onChangeViewPager: " + persianViewPagerType.name)
            }
        });
    }


    fun scrollToToday() {
        persianCalendar
            .scrollToDate(nowTime)
    }

    fun scrollToNextMonth() {
        persianCalendar
            .scrollToDate(changedTime.plusMonths(1).plusDays(0))
    }

    fun scrollToPervMonth() {
        persianCalendar.scrollToDate(changedTime.minusMonths(1).minusDays(0))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun persianCalendarSettings() {
        persianCalendar.setDayLabelsTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.teal_200
            )
        )
        persianCalendar.setMarkTodayCustomGradientDrawable(
            CustomGradientDrawable(
                GradientDrawable.RECTANGLE, ContextCompat.getColor(requireContext(), R.color.blue),
            ).setstroke(1, Color.BLACK).setcornerRadius(16).setTextColor(Color.WHITE)
        )

        persianCalendar.setMarkSelectedDateCustomGradientDrawable(
            CustomGradientDrawable(
                GradientDrawable.RECTANGLE,
                ContextCompat.getColor(requireContext(), R.color.teal_200),
            ).setstroke(1, Color.BLACK).setcornerRadius(16).setTextColor(Color.BLACK)
        )

        persianCalendar.setDaysTypeface(resources.getFont(R.font.vazir))
        persianCalendar.setTitleTypeface(resources.getFont(R.font.vazir))
        persianCalendar.setDayLabelsTypeface(resources.getFont(R.font.vazir))
        persianCalendar.setTodayButtonTypeface(resources.getFont(R.font.vazir))

        nextMonthBtn.setOnClickListener {
            scrollToPervMonth()
        }
        pervMonthBtn.setOnClickListener {
            scrollToNextMonth()

        }
        todayBtn.setOnClickListener {
            scrollToToday()
        }
    }

    fun setSelectedTvString(persianDate: PersianDateImpl) {
        persianDate.persianMonthName
        val year = PersianHelper.toPersianNumber(persianDate.persianYear.toString())
        val day = PersianHelper.toPersianNumber(persianDate.persianDay.toString())
        monthFrameTv.text = persianDate.persianLongDate

        //selectedDateTv.text = "$day ${persianDate.persianMonthName} $year"
        val string = "$day ${persianDate.persianMonthName}"
        val addNoteDefaultString = "افزودن یادداشت برای"
        val sourceString = "$addNoteDefaultString <font color='blue'><b>$string</b></font>"
        addTaskForDateBtn.text =
            HtmlCompat.fromHtml(sourceString, HtmlCompat.FROM_HTML_MODE_LEGACY);


    }

    private fun setList(longDate: Long) {
        viewModel?.getTasksListForCalendar(longDate)!!.observe(viewLifecycleOwner) { tasks ->

            Log.i(TAG, "setList: " + tasks.toString())
            if (tasks.isEmpty()) {
                calendarEmptyState.visibility = View.VISIBLE

            } else calendarEmptyState.setVisibility(View.GONE)
            val adapter = CalendarItemListAdapter(requireContext(), tasks,object :CalendarListEvent{
                override fun OnCalendarListItemClicked(task: Task) {
                    task.isComplete =!task.isComplete
                    viewModel!!.updateTask(task)
                }
            })
            tasksListRv.setAdapter(adapter)
        }
    }

    private fun setEvents() {
        viewModel?.getNotCompleteTasks()!!.observe(viewLifecycleOwner) { tasks ->
            val persianDate = PersianDateImpl()
            for (task in tasks) {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = task.dateLong
                persianDate.setDate(task.dateLong)
                val dateTime = DateTime(
                    persianDate.persianYear,
                    persianDate.persianMonth,
                    persianDate.persianDay,
                    0,
                    0,
                    0
                )

                persianCalendar.markDate(
                    dateTime,
                    PersianCustomMarks.SmallOval_Bottom,
                    Color.RED
                )
            }
        }
    }

}
