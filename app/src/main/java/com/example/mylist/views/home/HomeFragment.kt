package com.example.mylist.views.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.R
import com.example.mylist.data.datebase.AppDatabase
import com.example.mylist.data.group.Group
import com.example.mylist.tools.CustomAlertDialog
import com.example.mylist.tools.EXTRA_DATA
import com.example.mylist.tools.MyTextWatcher
import com.example.mylist.tools.hideKeyboardFrom
import com.example.mylist.views.TaskList.TasksListActivity
import com.example.mylist.views.home.addGroupActivity.AddNewGroupActivity
import com.example.mylist.views.home.editGroupActivity.EditGroupActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.custom_alert_dialog.*
import kotlinx.android.synthetic.main.group_menu_bottom_navigation.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.calendarEmptyState
import kotlinx.android.synthetic.main.home_fragment.clearAllBtn
import kotlinx.android.synthetic.main.home_fragment.searchCancelBtn
import kotlinx.android.synthetic.main.home_fragment.searchEt
import java.util.*


/**
 * Home Fragment is first page that shows to user
 * and it shows list of groups */
class HomeFragment : Fragment(), GroupListAdapter.OnGroupItemClick {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.home_fragment, container, false)

    var viewModel: HomeViewModel? = null
    val TAG = "HomeFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = HomeViewModel(AppDatabase.getDatabaseInstance(requireContext()))

        groupRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        /**
         *Calling WellComeTv Method
         */
        currentTimeTvSetText()
        searchEtConfig()


        /**
         * CallBack for Clear All Btn
         */
        clearAllBtn.setOnClickListener {

            CustomAlertDialog(getString(R.string.alertDialogMessageGroup),
                getString(R.string.alertDialogTitleGroup),
                object : CustomAlertDialog.DialogListener {
                    override fun onYesBtnClicked() {
                        viewModel?.deleteAllGroups()
                    }
                }).show(parentFragmentManager, null)


        }


        /**
         * getAllGroup And Set them To Rv
         * when u open The App this List shown To User
         */
        viewModel?.getAllGroups()!!.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                calendarEmptyState.visibility = View.VISIBLE

            } else calendarEmptyState.setVisibility(View.GONE)
            val groupListAdapter = GroupListAdapter(requireContext(), it, this)
            groupRv.adapter = groupListAdapter


        }

        /**
         * Start Activity  For Add New Group
         */
        addNewGroupBtn.setOnClickListener {
            startActivity(Intent(requireContext(), AddNewGroupActivity::class.java))
        }


    }


    private fun currentTimeTvSetText() {
        /**
         * This Method For Welcome Tv SetText
         * That's
         */
        val currentHourIn24Format: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when (currentHourIn24Format) {
            in 0..10 -> currentTimeTv.text = getString(R.string.goodMorning)
            in 11..15 -> currentTimeTv.text = getString(R.string.goodNoon)
            in 16..19 -> currentTimeTv.text = getString(R.string.goodEvening)
            in 20..23 -> currentTimeTv.text = getString(R.string.goodNight)
        }
    }


    /**
     * CallBacks For Group Menu
     */
    override fun onGroupMenuClicked(group: Group) {
        /**
         *When Click on Group Item Menu Bottom Sheet Dialog Created and shown to User
         *
         */
        /**
         *Create Bottom Sheet Dialog
         */
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        /**
         * Attack Bottom Sheet View to its Instance
         */
        bottomSheetDialog.setContentView(R.layout.group_menu_bottom_navigation)
        /**
         * Create OnClick For Bottom Sheet for its Buttons
         */
        //Edit Btn
        bottomSheetDialog.editGroupBtn.setOnClickListener {
            startActivity(Intent(requireContext(), EditGroupActivity::class.java).apply {
                putExtra(EXTRA_DATA, group)
            })
            bottomSheetDialog.dismiss()
        }
        //Delete Group Btn
        bottomSheetDialog.deleteGroupBtn.setOnClickListener {
            CustomAlertDialog(getString(R.string.alertDialogDeleteOneGroup),
                getString(R.string.alertDialogDeleteOneGroupTitle),
                object : CustomAlertDialog.DialogListener {
                    override fun onYesBtnClicked() {
                        viewModel!!.deleteGroup(group)
                    }

                }).show(parentFragmentManager,null)

        }
        /**
         * Calling Show Method For Show Showing The Bottom Sheet Fragment
         */
        bottomSheetDialog.show()
    }

    override fun onGroupItemClicked(group: Group) {
        startActivity(Intent(requireContext(), TasksListActivity::class.java).apply {
            putExtra(EXTRA_DATA, group)
        })
    }

    private fun searchEtConfig() {
        /**
         * Add Text Change To Search Edit Text For Searching
         * MyTextWatcher is a class That Implement TextWatcher
         * So U Don't need to Implement all Functions

         */
        searchEt.addTextChangedListener(object : MyTextWatcher() {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /**
                 * First We Check the Input String is Empty Or Not
                 * if its Not Empty We Search From  View Model And Attach The List To Adapter And Rv
                 * if its Empty (else) We Attach The all Groups to our Rv
                 */
                if (s!!.isNotEmpty()) {
                    Log.i(TAG, "onTextChanged: $s")

                    viewModel?.searchGroups(s.toString())!!.observe(viewLifecycleOwner) {
                        val adapter = GroupListAdapter(requireContext(), it, this@HomeFragment)
                        groupRv.adapter = adapter
                    }
                } else {
                    viewModel?.getAllGroups()!!.observe(viewLifecycleOwner) {
                        val adapter = GroupListAdapter(requireContext(), it, this@HomeFragment)
                        groupRv.adapter = adapter
                    }
                }
            }

        })

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
            hideKeyboardFrom(requireContext(), searchCancelBtn)
            searchEt.text.clear()
        }
    }
}