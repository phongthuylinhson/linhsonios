package com.thanhtam.linhsondich.core

import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import com.thanhtam.linhsondich.model.chuoique
import com.thanhtam.linhsondich.model.haodong
import com.thanhtam.linhsondich.model.model

class CTMaiHoa(
    var Ngay: Int,
    var Thang: Int,
    var Nam: Int,
    var Gio: Int,
    var somaihoa:Int,
    var rdo_thuong: RadioButton,
    var rdo_so: RadioButton,
    var rdo_ten: RadioButton,
//    var rdo_series: RadioButton,
    var edt_so: EditText,
    var edt_ten: EditText,
) {
    var d = Ngay
    var m = Thang
    var y = Nam
    var h = Gio
    var h1: Int? = null
    var h2: Int? = null
    var h3: Int? = null
    var h4: Int? = null
    var h5: Int? = null
    var h6: Int? = null
    var hd1: Int = 0
    var hd2: Int = 0
    var hd3: Int = 0
    var hd4: Int = 0
    var hd5: Int = 0
    var hd6: Int = 0
    val chuoiso = edt_so.text.toString()
    val lengthten= edt_ten.text.length
    fun checkedttenso(): Boolean {
        if (rdo_so.isChecked) {
            if (TextUtils.isEmpty(edt_so.text.toString())) {
                edt_so.error = "Bạn Chưa Nhập Số"
                return false
            }
            if (chuoiso.contains("0")&&(chuoiso.length==1||chuoiso.length==3)){
                edt_so.error = "Không Nhập Số 0"
                return false
            }
        }
        if (rdo_ten.isChecked) {
            if (TextUtils.isEmpty(edt_ten.text.toString())) {
                edt_ten.error = "Bạn Chưa Nhập Số"
                return false
            }
        }
        return true
    }

    fun _XuLyQueChinh(): chuoique {
        if (h % 2 != 0) h += 1
        if (h == 24) h = 0
        var Mang = model(0, 0)
        if (rdo_thuong.isChecked) {
            Mang._ThuongQuai = (d + m + y) % 8
            Mang._HaQuai = (d + m + y + ((h / 2) + 1)) % 8
        }
        if (rdo_so.isChecked) {
            when (chuoiso.length) {
                3 -> {
                    Mang._ThuongQuai = chuoiso[0].code % 8
                    Mang._HaQuai = chuoiso[1].code % 8
                }
                1 -> {
                    Mang._ThuongQuai = (chuoiso.toInt()+ d + m + y) % 8
                    Mang._HaQuai = (chuoiso.toInt()+d + m + y + ((h / 2) + 1)) % 8
                }
                else -> {
                    if (chuoiso.length%2==0){
                        for (i in 0 until chuoiso.length/2){
                            Mang._ThuongQuai += chuoiso[i].toString().toInt()
                        }
                        for (i in chuoiso.length/2 until chuoiso.length){
                            Mang._HaQuai += chuoiso[i].toString().toInt()
                        }
                    } else {
                        for (i in 0 until chuoiso.length/2+1){
                            Mang._ThuongQuai += chuoiso[i].toString().toInt()
                        }
                        for (i in chuoiso.length/2 until chuoiso.length){
                            Mang._HaQuai += chuoiso[i].toString().toInt()
                        }

                    }
                    Mang._ThuongQuai = Mang._ThuongQuai%8
                    Mang._HaQuai = Mang._HaQuai%8
                }
            }
        }
        if (rdo_ten.isChecked) {
            Mang._ThuongQuai = (d + m + y) % 8
            Mang._HaQuai = (d + m + y + ((h / 2) + 1)+lengthten) % 8
        }
        if (Mang._ThuongQuai == 8) Mang._ThuongQuai = 0
        if (Mang._HaQuai == 8) Mang._HaQuai = 0
        when (Mang._ThuongQuai) {
            0 -> {
                h6 = 0
                h5 = 0
                h4 = 0
            }
            1 -> {
                h6 = 1
                h5 = 1
                h4 = 1
            }
            2 -> {
                h6 = 0
                h5 = 1
                h4 = 1
            }
            3 -> {
                h6 = 1
                h5 = 0
                h4 = 1
            }
            4 -> {
                h6 = 0
                h5 = 0
                h4 = 1
            }
            5 -> {
                h6 = 1
                h5 = 1
                h4 = 0
            }
            6 -> {
                h6 = 0
                h5 = 1
                h4 = 0
            }
            7 -> {
                h6 = 1
                h5 = 0
                h4 = 0
            }
        }
        when (Mang._HaQuai) {
            0 -> {
                h3 = 0
                h2 = 0
                h1 = 0
            }
            1 -> {
                h3 = 1
                h2 = 1
                h1 = 1
            }
            2 -> {
                h3 = 0
                h2 = 1
                h1 = 1
            }
            3 -> {
                h3 = 1
                h2 = 0
                h1 = 1
            }
            4 -> {
                h3 = 0
                h2 = 0
                h1 = 1
            }
            5 -> {
                h3 = 1
                h2 = 1
                h1 = 0
            }
            6 -> {
                h3 = 0
                h2 = 1
                h1 = 0
            }
            7 -> {
                h3 = 1
                h2 = 0
                h1 = 0
            }
        }
        return chuoique(h1!!, h2!!, h3!!, h4!!, h5!!, h6!!)
    }

    fun _XuLyHaoDong(): haodong {

        if (Gio % 2 != 0) Gio += 1
        if (Gio == 24) Gio = 0
        var hd=0
        if (rdo_thuong.isChecked) hd= (d + m + y + ((Gio / 2) + 1)) % 6
        if (rdo_ten.isChecked) hd= (d + m + y + ((Gio / 2) + 1)+lengthten) % 6
        if (rdo_so.isChecked){
            when (chuoiso.length) {
                3 -> {
                    hd = if (somaihoa == 0) chuoiso[2].code % 6
                    else (chuoiso[0].code + chuoiso[1].code + chuoiso[2].code) % 6
                }
                1 -> {
                    hd= (chuoiso.toInt() + d + m + y + ((Gio / 2) + 1)) % 6
                }
                else -> {
                    for (i in chuoiso.indices){
                        hd+=chuoiso[i].toString().toInt()
                    }
                    hd %= 6
                }
            }
        }
            when (hd) {
                1 -> hd1 = 1
                2 -> hd2 = 1
                3 -> hd3 = 1
                4 -> hd4 = 1
                5 -> hd5 = 1
                6, 0 -> hd6 = 1
            }

        var haodong = haodong(hd1, hd2, hd3, hd4, hd5, hd6)
        return haodong
    }

    fun XuLyQueBien(haoquechinh: Int, hd: Int): Int {
        var hao = haoquechinh
        if (hd == 1) {
            if (hao == 0) hao = 1
            else hao = 0
        } else hao = haoquechinh
        return hao
    }

    fun _XuLyQueBien(): chuoique {
        val XuLyQueChinh = _XuLyQueChinh()
        val XuLyHaoDong = _XuLyHaoDong()
        var mangquebien: chuoique = chuoique(0, 0, 0, 0, 0, 0)
        mangquebien.h1 = XuLyQueBien(XuLyQueChinh.h1, XuLyHaoDong.hd1)
        mangquebien.h2 = XuLyQueBien(XuLyQueChinh.h2, XuLyHaoDong.hd2)
        mangquebien.h3 = XuLyQueBien(XuLyQueChinh.h3, XuLyHaoDong.hd3)
        mangquebien.h4 = XuLyQueBien(XuLyQueChinh.h4, XuLyHaoDong.hd4)
        mangquebien.h5 = XuLyQueBien(XuLyQueChinh.h5, XuLyHaoDong.hd5)
        mangquebien.h6 = XuLyQueBien(XuLyQueChinh.h6, XuLyHaoDong.hd6)
        return mangquebien
    }

    fun _XuLyQueHo(): chuoique {

        val XuLyQueChinh = _XuLyQueChinh()
        val mangqueho: chuoique = chuoique(0, 0, 0, 0, 0, 0)
        mangqueho.h1 = XuLyQueChinh.h2
        mangqueho.h2 = XuLyQueChinh.h3
        mangqueho.h3 = XuLyQueChinh.h4
        mangqueho.h4 = XuLyQueChinh.h3
        mangqueho.h5 = XuLyQueChinh.h4
        mangqueho.h6 = XuLyQueChinh.h5
        return mangqueho
    }
}