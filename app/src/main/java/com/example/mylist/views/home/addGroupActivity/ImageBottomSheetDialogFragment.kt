package com.example.mylist.views.home.addGroupActivity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mylist.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_dialog_layout.*

class ImageBottomSheetDialogFragment : BottomSheetDialogFragment() {
    // val eventListener: ImageClickEventListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    fun newInstance(): ImageBottomSheetDialogFragment {
        return ImageBottomSheetDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_dialog_layout, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    interface ImageClickEventListener {
        fun onImageClicked(imageId: Int)
    }
}