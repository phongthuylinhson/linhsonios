package com.thanhtam.linhsondich.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thanhtam.linhsondich.core.Solar2Lunar
import com.thanhtam.linhsondich.model.NgayGioThangNam
import com.thanhtam.linhsondich.model.NgayGioThangNamDL
import java.util.*

class LiveDataNgayThangNam:ViewModel() {
    val cal = Calendar.getInstance()
    var namxem= cal.get(Calendar.YEAR)
    val TT:MutableLiveData<NgayGioThangNamDL> by lazy {
        MutableLiveData<NgayGioThangNamDL>()
    }
    val Test:MutableLiveData<NgayGioThangNam> by lazy {
        MutableLiveData<NgayGioThangNam>()
    }
    var dd=cal.get(Calendar.DATE)
    var mm=cal.get(Calendar.MONTH)+1
    var yy=cal.get(Calendar.YEAR)
    var hh=cal.get(Calendar.HOUR_OF_DAY)
    var mi=cal.get(Calendar.MINUTE)
    private val ntnam= Solar2Lunar(dd,mm,yy,hh,mi)

    var yyy= ntnam.Solar2Lunar()[2]
    var mmm=ntnam.Solar2Lunar()[1]
    var ddd = ntnam.Solar2Lunar()[0]
    var hhh = cal.get(Calendar.HOUR_OF_DAY)
    var mii = cal.get(Calendar.MINUTE)

    inner class test(dd: Int, mm: Int, yy: Int, hh: Int) {
        val ntn = Solar2Lunar(dd, mm, yy, hh,mi)
        var yyyy = ntn.Solar2Lunar()[2]
        var mmmm = ntn.Solar2Lunar()[1]
        var dddd = ntn.Solar2Lunar()[0]
        var hhhh = ntn.giodl
        var miii= ntn.phutdl
    }

    var hoaky: Int = 0
}