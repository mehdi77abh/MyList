package com.example.mylist.views.calendar.selectGroup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.R
import com.example.mylist.data.group.Group
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.select_group_item.view.*

class SelectGroupListAdapter(val groups: List<Group>, val eventListener: SelectGroupEventListener) :
    RecyclerView.Adapter<SelectGroupListAdapter.GroupViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_group_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bindGroups(groups[position])
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    inner class GroupViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),LayoutContainer {
       fun bindGroups(group: Group){
           containerView.txtTitle.text = group.title
           containerView.setOnClickListener {
               eventListener.onGroupSelected(group)
           }
       }

    }

    interface SelectGroupEventListener {
        fun onGroupSelected(group: Group)
    }


}