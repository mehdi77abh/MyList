package com.example.mylist.views.calendar.selectGroup

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.R
import com.example.mylist.data.datebase.AppDatabase
import com.example.mylist.data.group.Group
import com.example.mylist.tools.EXTRA_DATA
import com.example.mylist.tools.EXTRA_INTENT_ITEM
import com.example.mylist.tools.EXTRA_VARIABLE
import com.example.mylist.views.TaskList.TaskViewModel
import com.example.mylist.views.TaskList.addTask.AddNewTaskActivity
import com.example.mylist.views.home.HomeViewModel
import com.example.mylist.views.home.addGroupActivity.AddNewGroupActivity
import kotlinx.android.synthetic.main.select_group_dialog.*
import kotlinx.android.synthetic.main.select_group_dialog.view.*

class SelectGroupDialog(val selectedLong: Long,val groupList:List<Group>) : DialogFragment(), SelectGroupListAdapter.SelectGroupEventListener {
    //var selectedLongDate: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.select_group_dialog,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.newGroupIcon.setOnClickListener {
            startActivity(Intent(requireContext(),AddNewGroupActivity::class.java))
        }
        view.rvSelectGroups.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val adapter = SelectGroupListAdapter(groupList, this@SelectGroupDialog)
        view.rvSelectGroups.adapter = adapter
        dismissTv.setOnClickListener { v -> dismiss() }


    }
    override fun onGroupSelected(group: Group) {
        //Go To Add Task Frg With Selected Group
        val bundle = Bundle()
        bundle.putParcelable("selectedGroup", group)
        dismiss()
        startActivity(Intent(requireContext(), AddNewTaskActivity()::class.java).apply {
            putExtra(EXTRA_DATA, selectedLong)
            putExtra(EXTRA_INTENT_ITEM, group)
        })

    }
    override fun onResume() {
        super.onResume()
        val window: Window = dialog?.window!!
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window.attributes)

        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        window.attributes = lp
    }
}