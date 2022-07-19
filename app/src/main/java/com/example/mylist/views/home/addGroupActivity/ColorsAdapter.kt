package com.example.mylist.views.home.addGroupActivity

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mylist.R
import com.example.mylist.data.group.Colour
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.palette_item.view.*


class ColorsAdapter(
    val context: Context,
    val arrayListColours: ArrayList<Colour>,
    val eventListener: ColorEventListener
) :
    RecyclerView.Adapter<ColorsAdapter.ColorViewHolder>() {
    private var colorSelected: Int = 0
    private var colorPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.palette_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bindColor(arrayListColours[position])

    }

    override fun getItemCount(): Int = arrayListColours.size

    inner class ColorViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindColor(colour: Colour) {
            /*     containerView.colorItemBtn.setcolor(
                     if (ColorUtils.isWhiteText(colour.colorId)) Color.WHITE else Color.BLACK
                 )*/

            containerView.colorItemBtn.background =
                ContextCompat.getDrawable(context, R.drawable.round_button)
            //containerView.colorItemBtn.setBackgroundColor(ContextCompat.getColor(context,colour.colorId))
            //containerView.colorItemBtn.setBackgroundResource(R.drawable.round_button)
            containerView.colorItemBtn.background.setTint(
                ContextCompat.getColor(
                    context,
                    colour.colorId
                )
            )
            if (colour.isCheck) {
                containerView.colorItemBtn.foreground =
                    ContextCompat.getDrawable(context, R.drawable.ic_round_check_24)
                containerView.colorItemBtn.foreground.setTint(if
                        (ColorUtils.isWhiteText(colour.colorId)) Color.WHITE else Color.BLACK)

            }
            //containerView.colorItemBtn.background = ContextCompat.getDrawable(context, R.drawable.ic_round_check_24)

            else
                containerView.colorItemBtn.foreground = null


            containerView.colorItemBtn.tag = colour.colorId

            containerView.colorItemBtn.setOnClickListener {

                if (colorPosition != -1 && colorPosition != layoutPosition) {
                    arrayListColours.get(colorPosition).isCheck = false
                    notifyItemChanged(colorPosition)
                }
                colorPosition = layoutPosition
                colorSelected = it.tag as Int
                arrayListColours[colorPosition].isCheck = true
                notifyItemChanged(colorPosition)

                eventListener.onColorClicked(colour)

            }
        }


    }

    fun getColorSelected(): Int {
        return colorSelected
    }


    fun getColorPosition(): Int {
        return colorPosition
    }
    fun setColorPosition(selectedColor: Int){
        for (color in arrayListColours){
            if (color.colorId ==selectedColor){
                color.isCheck = true
                colorPosition = arrayListColours.indexOf(color)
                notifyItemChanged(arrayListColours.indexOf(color))
                colorSelected = color.colorId
            }
        }
    }


    interface ColorEventListener {

        fun onColorClicked(colour: Colour)

    }
}


