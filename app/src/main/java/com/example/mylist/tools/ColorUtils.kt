import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt

object ColorUtils {

    fun isWhiteText(@ColorInt color: Int): Boolean {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

           val yiq = (red * 299 + green * 587 + blue * 114) / 1000
        return yiq < 192
    }

    fun getDimensionDp(resID: Int, context: Context): Int {
        return (context.resources.getDimension(resID) / context.resources.displayMetrics.density).toInt()
    }

    fun dip2px(dpValue: Float, context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}