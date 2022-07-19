package com.example.mylist.views.home.editGroupActivity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mylist.R
import com.example.mylist.data.datebase.AppDatabase
import com.example.mylist.data.group.Colour
import com.example.mylist.data.group.Group
import com.example.mylist.tools.EXTRA_DATA
import com.example.mylist.tools.getColours
import com.example.mylist.views.home.HomeViewModel
import com.example.mylist.views.home.addGroupActivity.ColorsAdapter
import com.example.mylist.views.home.addGroupActivity.ImageListAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_edit_group.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog_layout.*

const val TAG: String = "TAG"

class EditGroupActivity : AppCompatActivity(), ColorsAdapter.ColorEventListener {

    var selectedColor: Int = 0
    var selectedImage: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        val selectedGroup = intent.extras?.getParcelable<Group>(EXTRA_DATA)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_group)
        val viewModel = HomeViewModel(AppDatabase.getDatabaseInstance(this))


        backBtn.setOnClickListener { finish() }
        val adapter = ColorsAdapter(this, getColours(), this)
        colorSelectionRv.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        colorSelectionRv.adapter = adapter
        adapter.setColorPosition(selectedGroup!!.color)
        selectedColor = selectedGroup.color
        selectedImage = selectedGroup.image
        editGroupBtn.setOnClickListener {
            if (selectedImage == 0) {
                Toast.makeText(this, getString(R.string.pleaseSelectYourIcon), Toast.LENGTH_SHORT)
                    .show()
            } else {
                val group =
                    Group(
                        id = selectedGroup.id,
                        title = groupTitleEt.text.toString(),
                        counter = selectedGroup.counter,
                        isStar = false,
                        color = if (selectedColor == 0) R.color.customColor else selectedColor,
                        image = selectedImage
                    )

                viewModel.updateGroup(group)
                Log.i(TAG, "Selected Color: " + selectedColor)
                finish()
            }
        }

        imageItem.setImageDrawable(ContextCompat.getDrawable(this, selectedGroup.image))
        addImageBtn.setOnClickListener {
            showBottomSheetDialog()

        }
        groupTitleEt.setText(selectedGroup.title)

        addImageBtn.text = resources.getString (R.string.editPicture)

    }

    override fun onColorClicked(colour: Colour) {

        selectedColor = colour.colorId

    }


    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout)
        bottomSheetDialog.selectImageRv.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
        val adapter = ImageListAdapter(this, object : ImageListAdapter.ImageClickedEventListener {
            override fun imageClicked(image: Int) {
                selectedImage = image
                imageItem.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@EditGroupActivity,
                        image
                    )
                )
                addImageBtn.setText(resources.getString(R.string.editPicture))
                bottomSheetDialog.dismiss()
            }
        })

        bottomSheetDialog.selectImageRv.adapter = adapter

        bottomSheetDialog.show()

    }

}