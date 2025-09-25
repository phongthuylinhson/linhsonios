package com.thanhtam.linhsondich.model

import android.view.View
import android.widget.ImageView
import androidx.transition.Visibility
import com.thanhtam.linhsondich.R

data class HinhQue(
    var img: ImageView,
    var hd: Int?,
    var hao: Int,
    var chuoisoten: String?
) {
    init {
        var hi: Int? = null
        if (hao == 1) {
            img.setBackgroundResource(R.drawable.duong)
            hi = 1
        } else {
            img.setBackgroundResource(R.drawable.am)
            hi = 0
        }
        if (hd == 1 && hi == 1) img.setBackgroundResource(R.drawable.duongdong)
        if (hd == 1 && hi == 0) img.setBackgroundResource(R.drawable.amdong)
        if (chuoisoten=="Mai Hoa"){
            img.visibility = View.VISIBLE
        }
    }
}
data class HinhNho(
    var img: ImageView,
    var hd: Int?,
    var hao: Int
) {
    init {
        var hi: Int? = null
        if (hao == 1) {
            img.setImageResource(R.drawable.duong)
            hi = 1
        } else {
            img.setImageResource(R.drawable.am)
            hi = 0
        }
        if (hd == 1 && hi == 1) img.setImageResource(R.drawable.duongdong)
        if (hd == 1 && hi == 0) img.setImageResource(R.drawable.amdong)
    }
}



