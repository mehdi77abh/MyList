package com.example.mylist.tools

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.example.mylist.R
import kotlinx.android.synthetic.main.custom_alert_dialog.*


/*object CustomAlertDialog {

    fun createAlertDialog(context: Context,message:String,title:String,dialogListener: DialogListener){
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setView(
            LayoutInflater.from(context)
                .inflate(R.layout.custom_alert_dialog, null,false)
        )
        alertDialog.alertDialogMessage.setText( message)
        alertDialog.alertDialogTitle.text = title

        alertDialog.show()
        alertDialog.dialogNoBtn.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.dialogYesBtn.setOnClickListener {
            dialogListener.onYesBtnClicked()
            alertDialog.dismiss()
        }
    }
    interface DialogListener{
        fun onYesBtnClicked()
    }
}*/
class CustomAlertDialog(val message:String,val title:String,val dialogListener: DialogListener): DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_alert_dialog,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setCanceledOnTouchOutside(true)

        alertDialogMessage.text = message
        alertDialogTitle.text = title
        dialogNoBtn.setOnClickListener {
            dismiss()
        }
        dialogYesBtn.setOnClickListener {
            dialogListener.onYesBtnClicked()
            dismiss()
        }
    }
    interface DialogListener{
        fun onYesBtnClicked()
    }

    override fun onResume() {
        super.onResume()
        val window: Window = dialog?.window!!
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window.attributes)

        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.attributes = lp
    }
}