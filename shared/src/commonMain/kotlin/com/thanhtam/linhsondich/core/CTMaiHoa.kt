package com.thanhtam.linhsondich.core

import com.thanhtam.linhsondich.model.MaiHoaModel
import com.thanhtam.linhsondich.model.MaiHoaType
import com.thanhtam.linhsondich.model.chuoique
import com.thanhtam.linhsondich.model.haodong

class CTMaiHoa(
    private val ngay: Int,
    private val thang: Int,
    private val nam: Int,
    private val gio: Int,
    private val soMaiHoa: Int,
    private val type: MaiHoaType,
    private val chuoiSo: String,
    private val ten: String
) {
    private var h1: Int = 0
    private var h2: Int = 0
    private var h3: Int = 0
    private var h4: Int = 0
    private var h5: Int = 0
    private var h6: Int = 0

    fun tinhQueChinh(): chuoique {
        var normalizedGio = if (gio % 2 != 0) gio + 1 else gio
        if (normalizedGio == 24) normalizedGio = 0
        val chiGioIdx = (normalizedGio / 2) + 1

        val model = MaiHoaModel(0, 0)

        when (type) {
            MaiHoaType.THUONG -> {
                model.thuongQuai = (ngay + thang + nam) % 8
                model.haQuai = (ngay + thang + nam + chiGioIdx) % 8
            }
            MaiHoaType.SO -> {
                when (chuoiSo.length) {
                    3 -> {
                        model.thuongQuai = chuoiSo[0].code % 8
                        model.haQuai = chuoiSo[1].code % 8
                    }
                    1 -> {
                        val soInput = chuoiSo.toIntOrNull() ?: 0
                        model.thuongQuai = (soInput + ngay + thang + nam) % 8
                        model.haQuai = (soInput + ngay + thang + nam + chiGioIdx) % 8
                    }
                    else -> {
                        var tq = 0
                        var hq = 0
                        val mid = if (chuoiSo.length % 2 == 0) chuoiSo.length / 2 else chuoiSo.length / 2 + 1
                        for (i in 0 until mid) tq += chuoiSo[i].toString().toIntOrNull() ?: 0
                        for (i in mid until chuoiSo.length) hq += chuoiSo[i].toString().toIntOrNull() ?: 0
                        model.thuongQuai = tq % 8
                        model.haQuai = hq % 8
                    }
                }
            }
            MaiHoaType.TEN -> {
                model.thuongQuai = (ngay + thang + nam) % 8
                model.haQuai = (ngay + thang + nam + chiGioIdx + ten.length) % 8
            }
        }

        if (model.thuongQuai == 0) model.thuongQuai = 8
        if (model.haQuai == 0) model.haQuai = 8

        // Convert Quai to Hào
        convertQuaiToHao(model.thuongQuai, isUpper = true)
        convertQuaiToHao(model.haQuai, isUpper = false)

        return chuoique(h1, h2, h3, h4, h5, h6)
    }

    private fun convertQuaiToHao(quai: Int, isUpper: Boolean) {
        val q = if (quai == 8) 0 else quai
        val h_a: Int
        val h_b: Int
        val h_c: Int
        when (q) {
            0 -> { h_a = 0; h_b = 0; h_c = 0 }
            1 -> { h_a = 1; h_b = 1; h_c = 1 }
            2 -> { h_a = 0; h_b = 1; h_c = 1 }
            3 -> { h_a = 1; h_b = 0; h_c = 1 }
            4 -> { h_a = 0; h_b = 0; h_c = 1 }
            5 -> { h_a = 1; h_b = 1; h_c = 0 }
            6 -> { h_a = 0; h_b = 1; h_c = 0 }
            7 -> { h_a = 1; h_b = 0; h_c = 0 }
            else -> { h_a = 0; h_b = 0; h_c = 0 }
        }
        if (isUpper) {
            h6 = h_a; h5 = h_b; h4 = h_c
        } else {
            h3 = h_a; h2 = h_b; h1 = h_c
        }
    }

    fun tinhHaoDong(): haodong {
        var normalizedGio = if (gio % 2 != 0) gio + 1 else gio
        if (normalizedGio == 24) normalizedGio = 0
        val chiGioIdx = (normalizedGio / 2) + 1

        var hd = 0
        when (type) {
            MaiHoaType.THUONG -> hd = (ngay + thang + nam + chiGioIdx) % 6
            MaiHoaType.TEN -> hd = (ngay + thang + nam + chiGioIdx + ten.length) % 6
            MaiHoaType.SO -> {
                when (chuoiSo.length) {
                    3 -> hd = if (soMaiHoa == 0) chuoiSo[2].code % 6 else (chuoiSo[0].code + chuoiSo[1].code + chuoiSo[2].code) % 6
                    1 -> hd = ((chuoiSo.toIntOrNull() ?: 0) + ngay + thang + nam + chiGioIdx) % 6
                    else -> {
                        var sum = 0
                        for (c in chuoiSo) sum += c.toString().toIntOrNull() ?: 0
                        hd = sum % 6
                    }
                }
            }
        }
        if (hd == 0) hd = 6

        return haodong(
            if (hd == 1) 1 else 0,
            if (hd == 2) 1 else 0,
            if (hd == 3) 1 else 0,
            if (hd == 4) 1 else 0,
            if (hd == 5) 1 else 0,
            if (hd == 6) 1 else 0
        )
    }

    private fun xuLyQueBien(haoQueChinh: Int, hd: Int): Int {
        return if (hd == 1) (if (haoQueChinh == 0) 1 else 0) else haoQueChinh
    }

    fun tinhQueBien(queChinh: chuoique, hd: haodong): chuoique {
        return chuoique(
            xuLyQueBien(queChinh.h1, hd.hd1),
            xuLyQueBien(queChinh.h2, hd.hd2),
            xuLyQueBien(queChinh.h3, hd.hd3),
            xuLyQueBien(queChinh.h4, hd.hd4),
            xuLyQueBien(queChinh.h5, hd.hd5),
            xuLyQueBien(queChinh.h6, hd.hd6)
        )
    }

    fun tinhQueHo(queChinh: chuoique): chuoique {
        return chuoique(
            queChinh.h2,
            queChinh.h3,
            queChinh.h4,
            queChinh.h3,
            queChinh.h4,
            queChinh.h5
        )
    }
}
