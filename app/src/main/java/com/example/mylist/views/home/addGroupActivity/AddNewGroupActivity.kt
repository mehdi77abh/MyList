package com.example.mylist.views.home.addGroupActivity

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
import com.example.mylist.tools.getColours
import com.example.mylist.views.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_add_new_group.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog_layout.*

const val TAG: String = "TAG"

class AddNewGroupActivity : AppCompatActivity(), ColorsAdapter.ColorEventListener {

    var selectedColor: Int = 0
    var selectedImage: Int = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_group)
        val viewModel = HomeViewModel(AppDatabase.getDatabaseInstance(this))


        backBtn.setOnClickListener { finish() }
        val adapter = ColorsAdapter(this, getColours(), this)
        colorSelectionRv.layoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        colorSelectionRv.adapter = adapter

        saveNewGroupBtn.setOnClickListener {
            if (selectedImage == 0) {
                Toast.makeText(this, getString(R.string.pleaseSelectYourIcon), Toast.LENGTH_SHORT)
                    .show()
            } else {
                val group =
                    Group(
                        title = groupTitleEt.text.toString(),
                        counter = 0,
                        isStar = false,
                        color = if (selectedColor == 0) R.color.customColor else selectedColor,
                        image = selectedImage
                    )

                viewModel.saveGroup(group)
                Log.i(TAG, "Selected Color: " + selectedColor)
                finish()
            }
        }

        addImageBtn.setOnClickListener {
            showBottomSheetDialog()

        }
        addImageBtn.setText(resources.getString (R.string.addPicture))

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
                            this@AddNewGroupActivity,
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
