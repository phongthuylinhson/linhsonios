package com.thanhtam.linhsondich.core

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.thanhtam.linhsondich.R
import com.thanhtam.linhsondich.model.CanChi
import com.thanhtam.linhsondich.model.CanChiNgayThang
import com.thanhtam.linhsondich.model.chuoique
import com.thanhtam.linhsondich.model.haodong

///Phụ Mẫuc Thần
data class ThanSat(
    var context: Context,
    var canChiNgayThang: CanChiNgayThang,
    var theung: Int,
    var que: chuoique,
    var TIETKHI: String,
) {
    var canngay = canChiNgayThang.canngay
    var canthang = canChiNgayThang.canthang
    var chingay = canChiNgayThang.chingay
    var chithang = TIETKHI
    var mangcan = CanChi().can
    var mangchi = CanChi().chi
    var mangchithang = CanChi().chithang

    @SuppressLint("NotConstructor")
    fun ThanSat(chi: String, haodongg: Int): String {

        var chuoinhatgiai = ""
        var daohoa = ""
        var mocduc = ""
        var chuoimocduc = ""
        var hongloan = ""
        var chuoihongloan = ""
        var cothan = ""
        var chuoicothan = ""
        var quatu = ""
        var chuoiquatu = ""
        var amkim = ""
        var chuoiamkim = ""
        var thientac = ""
        var chuoithientac = ""
        var taisat = ""
        var chuoitaisat = ""
        var tuongtinh = ""
        var chuoituongtinh = ""
        var chuoiLocton = ""
        var quaithan = ""
        var vitriquaithan = ""
        var mangmomon = arrayOf("", "")
        var momon = ""
        var chuoidichma = ""
        var dichma = ""
        var bachho = ""
        var chuoibachho = ""
        var chuoinhatduc = ""
        var nhatduc = ""
        var chuoiduongnhan = ""
        var duongnhan = ""
        var chuoivanxuong = ""
        var vanxuong = ""
        var quynhan = ""
        var chuoimuutinh = ""
        var muutinh = ""
        var chuoihoacai = ""
        var hoacai = ""
        var chuoidaohoa = ""
        var locton = ""
        var kiepsat = ""
        var chuoikiepsat = ""
        when (chingay) {
            0, 4, 8 -> {
                chuoidichma = "Dần"
                chuoimuutinh = "Tuất"
                chuoihoacai = "Thìn"
                chuoidaohoa = "Dậu"
                chuoikiepsat = "Tỵ"
                chuoitaisat = "Ngọ"
                chuoituongtinh = "Tí"
            }
            1, 5, 9 -> {
                chuoidichma = "Hợi"
                chuoimuutinh = "Mùi"
                chuoihoacai = "Sửu"
                chuoidaohoa = "Ngọ"
                chuoikiepsat = "Dần"
                chuoitaisat = "Mão"
                chuoituongtinh = "Dậu"
            }
            2, 6, 10 -> {
                chuoidichma = "Thân"
                chuoimuutinh = "Thìn"
                chuoihoacai = "Tuất"
                chuoidaohoa = "Mão"
                chuoikiepsat = "Hợi"
                chuoitaisat = "Tí"
                chuoituongtinh = "Ngọ"
            }
            3, 7, 11 -> {
                chuoidichma = "Tỵ"
                chuoimuutinh = "Sửu"
                chuoihoacai = "Mùi"
                chuoidaohoa = "Tí"
                chuoikiepsat = "Thân"
                chuoitaisat = "Dậu"
                chuoituongtinh = "Mão"
            }
        }

        when (chingay) {
            0, 11, 1 -> {
                chuoicothan = "Dần"
                chuoiquatu = "Tuất"
            }
            10, 8, 9 -> {
                chuoicothan = "Hợi"
                chuoiquatu = "Mùi"
            }
            5, 6, 7 -> {
                chuoicothan = "Thân"
                chuoiquatu = "Thìn"
            }
            3, 2, 4 -> {
                chuoicothan = "Tỵ"
                chuoiquatu = "Sửu"
            }
        }

        /////// Mộc Dục
        when (chingay) {
            0, 11, 1, 10, 4, 7 -> {
                chuoimocduc = "Dậu"
            }
            8, 9 -> {
                chuoimocduc = "Ngọ"
            }
            5, 6 -> {
                chuoimocduc = "Mão"
            }
            3, 2 -> {
                chuoimocduc = "Tí"
            }
        }
        if (chuoimocduc == chi) mocduc = " " + context.getString(R.string.mocduc) + "."

        ///////Hồng Loan
        when (canngay) {
            0, 1 -> chuoihongloan = "Ngọ Thân"
            2 -> chuoihongloan = "Dần"
            3 -> chuoihongloan = "Mùi"
            4, 5 -> chuoihongloan = "Thìn"
            6 -> chuoihongloan = "Tuất"
            7 -> chuoihongloan = "Dậu"
            8 -> chuoihongloan = "Tí"
            9 -> chuoihongloan = "Thân"
        }
        if (chuoihongloan.contains(chi)) hongloan =
            " " + context.getString(R.string.hongloand) + "."
        ///////
        when (canngay) {
            0 -> {
                chuoiduongnhan = "Mão"
                chuoiLocton = "Dần"
            }
            1 -> {
                chuoiduongnhan = "Dần"
                chuoiLocton = "Mão"
            }
            2, 4 -> {
                chuoiduongnhan = "Ngọ"
                chuoiLocton = "Tỵ"
            }
            3, 5 -> {
                chuoiduongnhan = "Tỵ"
                chuoiLocton = "Ngọ"
            }
            6 -> {
                chuoiduongnhan = "Dậu"
                chuoiLocton = "Thân"
            }
            7 -> {
                chuoiduongnhan = "Thân"
                chuoiLocton = "Dậu"
            }
            8 -> {
                chuoiduongnhan = "Tí"
                chuoiLocton = "Hợi"
            }
            9 -> {
                chuoiduongnhan = "Hợi"
                chuoiLocton = "Tí"
            }
        }

        //////// Ám Kim
        when (chingay) {
            0, 3, 6, 9 -> chuoiamkim = "Tỵ"
            10, 7, 4, 1 -> chuoiamkim = "Dậu"
            5, 8, 11, 2 -> chuoiamkim = "Sửu"
        }
        if (chuoiamkim == chi) amkim = " " + context.getString(R.string.amkim) + "."

        //////// Tướng Tinh
        if (chuoituongtinh == chi) tuongtinh = " " + context.getString(R.string.tuong) + "."
        ////// Thiên Tặc
        when (chithang) {
            "Dần" -> chuoithientac = "Thìn"
            "Mão" -> chuoithientac = "Dậu"
            "Thìn" -> chuoithientac = "Dần"
            "Tỵ" -> chuoithientac = "Mùi"
            "Ngọ" -> chuoithientac = "Tí"
            "Mùi" -> chuoithientac = "Tỵ"
            "Thân" -> chuoithientac = "Tuất"
            "Dậu" -> chuoithientac = "Mão"
            "Tuất" -> chuoithientac = "Thân"
            "Hợi" -> chuoithientac = "Sửu"
            "Tí" -> chuoithientac = "Ngọ"
            "Sửu" -> chuoithientac = "Hợi"
        }
        if (chuoithientac == chi) thientac = " " + context.getString(R.string.thientac) + "."

        ///Dao Hoa
        if (chi == chuoidaohoa) {
            daohoa = " " + context.getString(R.string.daohoad) + "."
        }

        ///////Kiep Sat
        if (chi == chuoikiepsat) {
            kiepsat = " " + context.getString(R.string.kiepsatd) + "."
        }

        ///////Tai Sat
        if (chi == chuoitaisat) {
            taisat = " " + context.getString(R.string.taisatd) + "."
        }

        /////Lộc Tồn
        if (chuoiLocton == chi) {
            locton = " " + context.getString(R.string.locton) + "."
        }

        ////// Quái Thân
        val chuoia = arrayOf("Tí", "Sửu", "Dần", "Mão", "Thìn", "Tỵ")
        val chuoib = arrayOf("Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi")
        val chuoique = arrayOf(que.h1, que.h2, que.h3, que.h4, que.h5, que.h6)
        if (chuoique[theung - 1] == 0) {
            vitriquaithan = chuoib[theung - 1]
        }
        if (chuoique[theung - 1] == 1) {
            vitriquaithan = chuoia[theung - 1]
        }
        if (chi == vitriquaithan) {
            quaithan = " " + context.getString(R.string.quaithan) + "."
        }

        ////Mộ Môn
        when (canngay) {
            0, 1 -> mangmomon = arrayOf("Thân", "Dậu")
            2, 3 -> mangmomon = arrayOf("Tí", "Hợi")
            4, 5 -> mangmomon = arrayOf("Dần", "Mão")
            6, 7 -> mangmomon = arrayOf("Tỵ", "Ngọ")
            8, 9 -> mangmomon = arrayOf("Thìn", "Tuất", "Sửu", "Mùi")
        }
        for (i in mangmomon.indices) {
            if (chi == mangmomon[i]) {
                if (haodongg == 1) {
                    momon = " " + context.getString(R.string.momon) + "."
                }
            }
        }

        ///// Dịch Mã
        if (chuoidichma == chi) {
            dichma = " " + context.getString(R.string.dichma) + "."
        }

        /////// Bạch Hổ
        when (chithang) {
            "Dần" -> chuoibachho = "Thân"
            "Mão" -> chuoibachho = "Dậu"
            "Thìn" -> chuoibachho = "Tuất"
            "Tỵ" -> chuoibachho = "Hợi"
            "Ngọ" -> chuoibachho = "Tí"
            "Mùi" -> chuoibachho = "Sửu"
            "Thân" -> chuoibachho = "Dần"
            "Dậu" -> chuoibachho = "Mão"
            "Tuất" -> chuoibachho = "Thìn"
            "Hợi" -> chuoibachho = "Tỵ"
            "Tí" -> chuoibachho = "Ngọ"
            "Sửu" -> chuoibachho = "Mùi"
        }
        if (chi == chuoibachho) {
            bachho = " " + context.getString(R.string.bachho) + "."
        }

        ///Nhat Duc
        when (canngay) {
            0, 5 -> chuoinhatduc = "Dần"
            1, 6 -> chuoinhatduc = "Thân"
            2, 4, 9, 7 -> chuoinhatduc = "Dần"
            3, 8 -> chuoinhatduc = "Hợi"
        }
        if (chi == chuoinhatduc) {
            nhatduc = " " + context.getString(R.string.nhatduc) + "."
        }

        ///Duong Nhan
        if (chi == chuoiduongnhan) {
            duongnhan = " " + context.getString(R.string.duongnhan) + "."
        }

        ///VanXuong
        when (canngay) {
            0 -> chuoivanxuong = "Tỵ"
            1 -> chuoivanxuong = "Ngọ"
            2, 4 -> chuoivanxuong = "Thân"
            3, 5 -> chuoivanxuong = "Dậu"
            6 -> chuoivanxuong = "Hợi"
            7 -> chuoivanxuong = "Tí"
            8 -> chuoivanxuong = "Dần"
            9 -> chuoivanxuong = "Mão"
        }
        if (chi == chuoivanxuong) {
            vanxuong = " " + context.getString(R.string.vanxuongd) + "."
        }

        ///Quy Nhan
        if ((canngay == 0 || (canngay == 4)) && (chi == "Sửu" || chi == "Mùi")) {
            quynhan = " " + context.getString(R.string.quynhan) + "."
        }
        if ((canngay == 1 || (canngay == 5)) && (chi == "Thân" || chi == "Tí")) {
            quynhan = " " + context.getString(R.string.quynhan) + "."
        }
        if ((canngay == 2 || (canngay == 3)) && (chi == "Hợi" || chi == "Dậu")) {
            quynhan = " " + context.getString(R.string.quynhan) + "."
        }
        if ((canngay == 6 || (canngay == 7)) && (chi == "Ngọ" || chi == "Dần")) {
            quynhan = " " + context.getString(R.string.quynhan) + "."
        }
        if ((canngay == 8 || (canngay == 9)) && (chi == "Mão" || chi == "Tỵ")) {
            quynhan = " " + context.getString(R.string.quynhan) + "."
        }

        ///Muu Tinh
        when (chingay) {
        }
        if (chuoimuutinh == chi) {
            muutinh = " " + context.getString(R.string.muutinh) + "."
        }

        ///Hoa Cai
        if (chuoihoacai == chi) {
            hoacai = " " + context.getString(R.string.hoacaid) + "."
        }

        ///// Cô Thần Quả Tú
        if (chuoicothan == chi) cothan = " " + context.getString(R.string.cothand) + "."
        if (chuoiquatu == chi) quatu = " " + context.getString(R.string.quatud) + "."
        return locton + quynhan + quaithan + daohoa + hongloan + mocduc + nhatduc + vanxuong + duongnhan + tuongtinh + hoacai + cothan + quatu + muutinh + momon + kiepsat + taisat + thientac + amkim + dichma
    }

}

data class ThuocTinh(var tv: TextView, var cung: String, var thuocTinh: String) {
    init {
        if (thuocTinh == "") tv.text = cung
        else tv.text = "$cung - $thuocTinh"
    }
}

data class CTTPT(
    var tv1: TextView,
    var tv2: TextView,
    var tv3: TextView,
    var tv4: TextView,
    var tv5: TextView,
    var tv6: TextView,
    var pt1: String,
    var pt2: String,
    var vtpt1: Int,
    var vtpt2: Int,
) {
    init {
        val mangtextview = arrayOf(tv1, tv2, tv3, tv4, tv5, tv6)
        for (i in 0..5) {
            when (vtpt1) {
                i + 1 -> mangtextview[i].text = pt1
            }
            when (vtpt2) {
                i + 1 -> mangtextview[i].text = pt2
            }
        }
    }
}

///Tuần Không
data class CTTTK(
    var tv1: TextView,
    var tv2: TextView,
    var tv3: TextView,
    var tv4: TextView,
    var tv5: TextView,
    var tv6: TextView,
    var tv7: TextView,
    var tv8: TextView,
    var tv9: TextView,
    var tv10: TextView,
    var tv11: TextView,
    var tv12: TextView,
    var TK: String,
    var chi1: String,
    var chi2: String,
    var chi3: String,
    var chi4: String,
    var chi5: String,
    var chi6: String,
    var chi7: String,
    var chi8: String,
    var chi9: String,
    var chi10: String,
    var chi11: String,
    var chi12: String,
) {
    init {
        val mangtextview =
            arrayOf(tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12)
        val mangchi =
            arrayOf(chi1, chi2, chi3, chi4, chi5, chi6, chi7, chi8, chi9, chi10, chi11, chi12)
        for (i in 0..11) {
            if (TK == "Tí" && (mangchi[i] == "Hợi" || mangchi[i] == "Tuất")) {
                mangtextview[i].text = "K"
            }
            if (TK == "Dần" && (mangchi[i] == "Sửu" || mangchi[i] == "Tí")) {
                mangtextview[i].text = "K"
            }
            if (TK == "Thìn" && (mangchi[i] == "Dần" || mangchi[i] == "Mão")) {
                mangtextview[i].text = "K"
            }
            if (TK == "Ngọ" && (mangchi[i] == "Tỵ" || mangchi[i] == "Thìn")) {
                mangtextview[i].text = "K"
            }
            if (TK == "Thân" && (mangchi[i] == "Ngọ" || mangchi[i] == "Mùi")) {
                mangtextview[i].text = "K"
            }
            if (TK == "Tuất" && (mangchi[i] == "Dậu" || mangchi[i] == "Thân")) {
                mangtextview[i].text = "K"
            }
        }
    }
}

data class LucThan(var tv: TextView, var cung: String, var chi: String) {
    init {
        if (cung == "Càn" || cung == "Đoài") {
            when (chi) {
                "Tí", "Hợi" -> tv.text = "Tử Tôn"
                "Sửu", "Thìn", "Tuất", "Mùi" -> tv.text = "Phụ Mẫu"
                "Dần", "Mão" -> tv.text = "Thê Tài"
                "Tỵ", "Ngọ" -> tv.text = "Quan Quỷ"
                "Thân", "Dậu" -> tv.text = "Huynh Đệ"
            }
        }
        if (cung == "Ly") {
            when (chi) {
                "Tí", "Hợi" -> tv.text = "Quan Quỷ"
                "Sửu", "Thìn", "Tuất", "Mùi" -> tv.text = "Tử Tôn"
                "Dần", "Mão" -> tv.text = "Phụ Mẫu"
                "Tỵ", "Ngọ" -> tv.text = "Huynh Đệ"
                "Thân", "Dậu" -> tv.text = "Thê Tài"
            }
        }
        if (cung == "Chấn" || cung == "Tốn") {
            when (chi) {
                "Tí", "Hợi" -> tv.text = "Phụ Mẫu"
                "Sửu", "Thìn", "Tuất", "Mùi" -> tv.text = "Thê Tài"
                "Dần", "Mão" -> tv.text = "Huynh Đệ"
                "Tỵ", "Ngọ" -> tv.text = "Tử Tôn"
                "Thân", "Dậu" -> tv.text = "Quan Quỷ"
            }
        }
        if (cung == "Khảm") {
            when (chi) {
                "Tí", "Hợi" -> tv.text = "Huynh Đệ"
                "Sửu", "Thìn", "Tuất", "Mùi" -> tv.text = "Quan Quỷ"
                "Dần", "Mão" -> tv.text = "Tử Tôn"
                "Tỵ", "Ngọ" -> tv.text = "Thê Tài"
                "Thân", "Dậu" -> tv.text = "Phụ Mẫu"
            }
        }
        if (cung == "Cấn" || cung == "Khôn") {
            when (chi) {
                "Tí", "Hợi" -> tv.text = "Thê Tài"
                "Sửu", "Thìn", "Tuất", "Mùi" -> tv.text = "Huynh Đệ"
                "Dần", "Mão" -> tv.text = "Quan Quỷ"
                "Tỵ", "Ngọ" -> tv.text = "Phụ Mẫu"
                "Thân", "Dậu" -> tv.text = "Tử Tôn"
            }
        }
    }
}

data class LucThu(
    var canngay: Int,
    var tv1: TextView,
    var tv2: TextView,
    var tv3: TextView,
    var tv4: TextView,
    var tv5: TextView,
    var tv6: TextView,
) {
    init {
        val ArrLucThu1 =
            arrayOf("Thanh Long", "Chu Tước", "Câu Trần", "Đằng Xà", "Bạch Hổ", "Huyền Vũ")
        val ArrLucThu2 =
            arrayOf("Chu Tước", "Câu Trần", "Đằng Xà", "Bạch Hổ", "Huyền Vũ", "Thanh Long")
        val ArrLucThu3 =
            arrayOf("Câu Trần", "Đằng Xà", "Bạch Hổ", "Huyền Vũ", "Thanh Long", "Chu Tước")
        val ArrLucThu4 =
            arrayOf("Đằng Xà", "Bạch Hổ", "Huyền Vũ", "Thanh Long", "Chu Tước", "Câu Trần")
        val ArrLucThu5 =
            arrayOf("Bạch Hổ", "Huyền Vũ", "Thanh Long", "Chu Tước", "Câu Trần", "Đằng Xà")
        val ArrLucThu6 =
            arrayOf("Huyền Vũ", "Thanh Long", "Chu Tước", "Câu Trần", "Đằng Xà", "Bạch Hổ")
        val ArrTView = arrayOf(tv1, tv2, tv3, tv4, tv5, tv6)
        if (canngay == 0 || canngay == 1) {
            for (i in 0..5) {
                ArrTView[i].text = ArrLucThu1[i]
            }
        }
        if (canngay == 2 || canngay == 3) {
            for (i in 0..5) {
                ArrTView[i].text = ArrLucThu2[i]
            }
        }
        if (canngay == 4) {
            for (i in 0..5) {
                ArrTView[i].text = ArrLucThu3[i]
            }
        }
        if (canngay == 5) {
            for (i in 0..5) {
                ArrTView[i].text = ArrLucThu4[i]
            }
        }
        if (canngay == 6 || canngay == 7) {
            for (i in 0..5) {
                ArrTView[i].text = ArrLucThu5[i]
            }
        }
        if (canngay == 8 || canngay == 9) {
            for (i in 0..5) {
                ArrTView[i].text = ArrLucThu6[i]
            }
        }
    }
}

class CTDichResult {

    fun TinhTheUng(
        tu1: TextView,
        tu2: TextView,
        tu3: TextView,
        tu4: TextView,
        tu5: TextView,
        tu6: TextView,
        tuchinh: Int,
    ) {
        when (tuchinh) {
            1 -> {
                tu1.text = "(T)"
                tu4.text = "(Ư)"
            }
            2 -> {
                tu2.text = "(T)"
                tu5.text = "(Ư)"
            }
            3 -> {
                tu3.text = "(T)"
                tu6.text = "(Ư)"
            }
            4 -> {
                tu4.text = "(T)"
                tu1.text = "(Ư)"
            }
            5 -> {
                tu5.text = "(T)"
                tu2.text = "(Ư)"
            }
            6 -> {
                tu6.text = "(T)"
                tu3.text = "(Ư)"
            }
        }
    }
}

data class HighLight(
    var ltchic: TextView,
    var ltc: TextView,
    var tu: TextView,
    var tkc: TextView,
    var pt: TextView,
    var ltchib: TextView,
    var ltb: TextView,
    var tkb: TextView,
    var lth: TextView,
    var haodong: Int,
    var ntn: String,
    var context: Context
) {
    init {
        val lucHao = "Lục Hào"
        val ngauNhien = "Ngẫu Nhiên"
        when (haodong) {
            1 -> {
                ltchic.setTextColor(ContextCompat.getColor(context,R.color.reda))
                ltc.setTextColor(ContextCompat.getColor(context,R.color.reda))
                tu.setTextColor(ContextCompat.getColor(context,R.color.reda))
                tkc.setTextColor(ContextCompat.getColor(context,R.color.reda))
                pt.setTextColor(ContextCompat.getColor(context,R.color.reda))
                ltchib.setTextColor(ContextCompat.getColor(context,R.color.reda))
                ltb.setTextColor(ContextCompat.getColor(context,R.color.reda))
                tkb.setTextColor(ContextCompat.getColor(context,R.color.reda))
                lth.setTextColor(ContextCompat.getColor(context,R.color.reda))
            }
        }
//        if (ntn == lucHao || ntn == ngauNhien) {
//            Log.d("HighLight", "ntn: $ntn")
//        } else {
//            when (haodong) {
//                0 -> {
//                    ltchib.text = ""
//                    ltb.text = ""
//                    tkb.text = ""
//                }
//            }
//        }
    }
}
