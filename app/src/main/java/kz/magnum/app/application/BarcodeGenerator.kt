package kz.magnum.app.application

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer

object BarcodeGenerator {

    fun generateBonusCardBarcode(cardNumber: String, barcodeHeight: Int): Bitmap? {
        val displayMetrics = Resources.getSystem().displayMetrics
        val cardNumWidth = displayMetrics.widthPixels.toFloat()
        try {
            val writer = Code128Writer()
            val matrix = writer.encode(cardNumber, BarcodeFormat.CODE_128, cardNumWidth.toInt(), barcodeHeight)
            val height = matrix.height
            val width = matrix.width
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (matrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}