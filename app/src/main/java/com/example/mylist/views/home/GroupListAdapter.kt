package com.example.mylist.views.home

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.R
import com.example.mylist.data.group.Group
import com.example.mylist.tools.formatString
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.group_item.view.*

class GroupListAdapter(val context: Context, val groupList: List<Group>, val onGroupItemClick: OnGroupItemClick) :
    RecyclerView.Adapter<GroupListAdapter.GroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.group_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bindGroup(groupList[position])
    }

    override fun getItemCount(): Int = groupList.size


    inner class GroupViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindGroup(group: Group) {
            Log.i("TAG", "bindGroup: "+group.color)
            val colorInt = ContextCompat.getColor(context,group.color)
            containerView.groupImg.setImageDrawable(ContextCompat.getDrawable(context,group.image))
            containerView.groupTitleTv.text = group.title
            containerView.groupTitleTv.setTextColor(colorInt)
            containerView.groupCounterTv.text = formatString("یادداشت", group.counter.toString())
            containerView.groupCounterTv.setTextColor(colorInt)

            containerView.groupListItem.setBackgroundColor(colorInt)
            containerView.groupListItem.background.alpha = 20
            containerView.groupMenu.setColorFilter(colorInt)

            containerView.groupMenu.setOnClickListener {
                onGroupItemClick.onGroupMenuClicked(group)
            }
            containerView.setOnClickListener {
                onGroupItemClick.onGroupItemClicked(group)
            }


        }

    }

    interface OnGroupItemClick {
        fun onGroupMenuClicked(group: Group)
        fun onGroupItemClicked(group: Group)
    }
}