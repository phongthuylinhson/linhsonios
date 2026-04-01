package com.thanhtam.linhsondich.core

import com.thanhtam.linhsondich.model.CanChi
import com.thanhtam.linhsondich.model.CanChiNgayThang
import kotlin.math.PI
import kotlin.math.floor
import kotlin.math.sin

data class Solar2Lunar(var Ngay: Int, var Thang: Int, var Nam: Int, var Gio: Int, var Phut: Int) {
    var ngaydl = Ngay
    val thangdl = Thang
    val namdl = Nam
    var giodl = Gio
    var phutdl = Phut

    fun UniversalToJD(D: Int, M: Int, Y: Int): Double {
        val Y_d = Y.toDouble()
        val M_d = M.toDouble()
        val D_d = D.toDouble()
        val JD: Double = if (Y > 1582 || (Y == 1582 && M > 10) || (Y == 1582 && M == 10 && D > 14)) {
            367.0 * Y_d - floor(7.0 * (Y_d + floor((M_d + 9.0) / 12.0)) / 4.0) - floor(3.0 * (floor((Y_d + (M_d - 9.0) / 7.0) / 100.0) + 1.0) / 4.0) + floor(275.0 * M_d / 9.0) + D_d + 1721028.5
        } else {
            367.0 * Y_d - floor(7.0 * (Y_d + 5001.0 + floor((M_d - 9.0) / 7.0)) / 4.0) + floor(275.0 * M_d / 9.0) + D_d + 1729776.5
        }
        return JD
    }

    fun UniversalFromJD(JD: Double): IntArray {
        val A: Int
        val alpha: Int
        val dd: Int
        val F: Double
        val Z: Int = (JD + 0.5).toInt()
        F = JD + 0.5 - Z
        if (Z < 2299161) {
            A = Z
        } else {
            alpha = ((Z - 1867216.25) / 36524.25).toInt()
            A = (Z + 1 + alpha - (alpha / 4.0).toInt())
        }
        val B: Int = A + 1524
        val C: Int = ((B - 122.1) / 365.25).toInt()
        val D: Int = (365.25 * C).toInt()
        val E: Int = ((B - D) / 30.6001).toInt()
        dd = (B - D - (30.6001 * E).toInt() + F).toInt()
        val mm: Int = if (E < 14) {
            E - 1
        } else {
            E - 13
        }
        val yyyy: Int = if (mm < 3) {
            C - 4715
        } else {
            C - 4716
        }
        return intArrayOf(dd, mm, yyyy)
    }

    fun LocalFromJD(JD: Double): IntArray {
        return UniversalFromJD(JD + 7.0 / 24.0)
    }

    fun LocalToJD(D: Int, M: Int, Y: Int): Double {
        return UniversalToJD(D, M, Y) - 7.0 / 24.0
    }

    fun LunarMonth11(Y: Int): IntArray {
        val off = LocalToJD(31, 12, Y) - 2415021.076998695
        val k = floor(off / 29.530588853).toInt()
        var jd = NewMoon(k)
        val ret = LocalFromJD(jd)
        val sunLong =
            getSunLongitudea(LocalToJD(ret[0], ret[1], ret[2])) // sun longitude at local midnight
        if (sunLong > 3 * PI / 2) {
            jd = NewMoon(k - 1)
        }
        return LocalFromJD(jd)
    }

    fun NewMoon(k: Int): Double {
        val T = k.toDouble() / 1236.85 // Time in Julian centuries from 1900 January 0.5
        val T2 = T * T
        val T3 = T2 * T
        val dr: Double = PI / 180.0
        var Jd1 = 2415020.75933 + 29.53058868 * k.toDouble() + 0.0001178 * T2 - 0.000000155 * T3
        Jd1 += 0.00033 * sin((166.56 + 132.87 * T - 0.009173 * T2) * dr) // Mean new moon
        val M = 359.2242 + 29.10535608 * k.toDouble() - 0.0000333 * T2 - 0.00000347 * T3 // Sun's mean anomaly
        val Mpr =
            306.0253 + 385.81691806 * k.toDouble() + 0.0107306 * T2 + 0.00001236 * T3 // Moon's mean anomaly
        val F =
            21.2964 + 390.67050646 * k.toDouble() - 0.0016528 * T2 - 0.00000239 * T3 // Moon's argument of latitude
        var C1 =
            (0.1734 - 0.000393 * T) * sin(M * dr) + 0.0021 * sin(2.0 * dr * M)
        C1 = C1 - 0.4068 * sin(Mpr * dr) + 0.0161 * sin(dr * 2.0 * Mpr)
        C1 -= 0.0004 * sin(dr * 3.0 * Mpr)
        C1 = C1 + 0.0104 * sin(dr * 2.0 * F) - 0.0051 * sin(dr * (M + Mpr))
        C1 = C1 - 0.0074 * sin(dr * (M - Mpr)) + 0.0004 * sin(dr * (2.0 * F + M))
        C1 = C1 - 0.0004 * sin(dr * (2.0 * F - M)) - 0.0006 * sin(dr * (2.0 * F + Mpr))
        C1 += 0.0010 * sin(dr * (2.0 * F - Mpr)) + 0.0005 * sin(dr * (2.0 * Mpr + M))
        val deltat: Double = if (T < -11) {
            0.001 + 0.000839 * T + 0.0002261 * T2 - 0.00000845 * T3 - 0.000000081 * T * T3
        } else {
            -0.000278 + 0.000265 * T + 0.000262 * T2
        }
        return Jd1 + C1 - deltat
    }

    fun MOD(x: Int, y: Int): Int {
        var z = x - (y * floor(x.toDouble() / y)).toInt()
        if (z == 0) {
            z = y
        }
        return z
    }

    fun LunarYear(Y: Int): Array<IntArray> {
        val month11A = LunarMonth11(Y - 1)
        val jdMonth11A = LocalToJD(month11A[0], month11A[1], month11A[2])
        val k = floor(0.5 + (jdMonth11A - 2415021.076998695) / 29.530588853).toInt()
        val month11B = LunarMonth11(Y)
        val off = LocalToJD(month11B[0], month11B[1], month11B[2]) - jdMonth11A
        val leap = off > 365.0
        val ret = if (!leap) {
            Array(13) { IntArray(5) }
        } else {
            Array(14) { IntArray(5) }
        }
        ret[0] = intArrayOf(month11A[0], month11A[1], month11A[2], 0, 0)
        ret[ret.size - 1] = intArrayOf(month11B[0], month11B[1], month11B[2], 0, 0)
        for (i in 1 until ret.size - 1) {
            val nm = NewMoon(k + i)
            val a = LocalFromJD(nm)
            ret[i] = intArrayOf(a[0], a[1], a[2], 0, 0)
        }
        for (i in ret.indices) {
            ret[i][3] = MOD(i + 11, 12)
        }
        if (leap) {
            initLeapYear(ret)
        }
        return ret
    }

    fun initLeapYear(ret: Array<IntArray>) {
        val sunLongitudes = DoubleArray(ret.size)
        for (i in ret.indices) {
            val a = ret[i]
            val jdAtMonthBegin = LocalToJD(a[0], a[1], a[2])
            sunLongitudes[i] = getSunLongitudea(jdAtMonthBegin)
        }
        var found = false
        for (i in ret.indices) {
            if (found) {
                ret[i][3] = MOD(i + 10, 12)
                continue
            }
            if (i + 1 < ret.size) {
                val sl1 = sunLongitudes[i]
                val sl2 = sunLongitudes[i + 1]
                val hasMajorTerm = floor(sl1 / PI * 6.0) != floor(sl2 / PI * 6.0)
                if (!hasMajorTerm) {
                    found = true
                    ret[i][4] = 1
                    ret[i][3] = MOD(i + 10, 12)
                }
            }
        }
    }

    fun Solar2Lunar(): IntArray {
        var yy = namdl
        var ly = LunarYear(namdl)
        val month11 = ly[ly.size - 1]
        val jdToday = if (giodl == 23) LocalToJD(ngaydl + 1, thangdl, namdl)
        else LocalToJD(ngaydl, thangdl, namdl)
        
        val jdMonth11 = LocalToJD(month11[0], month11[1], month11[2])
        if (jdToday >= jdMonth11) {
            ly = LunarYear(namdl + 1)
            yy = namdl + 1
        }
        var i = ly.size - 1
        while (jdToday < LocalToJD(ly[i][0], ly[i][1], ly[i][2])) {
            i--
        }
        val dd = (jdToday - LocalToJD(ly[i][0], ly[i][1], ly[i][2])).toInt() + 1
        val mm = ly[i][3]
        if (mm >= 11) {
            yy--
        }
        return intArrayOf(dd, mm, yy, ly[i][4])
    }

    private fun getSunLongitudea(jdn: Double): Double {
        val T = (jdn - 2451545.0) / 36525.0
        val T2 = T * T
        val dr: Double = PI / 180.0
        val M = 357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2
        val L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2
        var DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * sin(dr * M)
        DL += (0.019993 - 0.000101 * T) * sin(dr * 2.0 * M) + 0.000290 * sin(dr * 3.0 * M)
        var L = L0 + DL
        val omega = 125.04 - 1934.136 * T
        L = L - 0.00569 - 0.00478 * sin(omega * dr)
        L *= dr
        L -= PI * 2.0 * floor(L / (PI * 2.0))
        return L
    }

    fun JD(): Long {
        val Thanga: Double = Thang.toDouble()
        val a = floor((14.0 - Thanga) / 12.0).toLong()
        val y = Nam.toLong() + 4800L - a
        val m = Thang.toLong() + 12L * a - 3L
        var jd: Long =
            Ngay.toLong() + floor((153.0 * m.toDouble() + 2.0) / 5.0).toLong() + 365L * y + floor(y.toDouble() / 4.0).toLong() - floor(y.toDouble() / 100.0).toLong() + floor(y.toDouble() / 400.0).toLong() - 32045L
        if (giodl == 23) jd += 1L
        return jd
    }

    fun JDTT(): Long {
        val Thanga: Double = Thang.toDouble()
        val a = floor((14.0 - Thanga) / 12.0).toLong()
        val y = Nam.toLong() + 4800L - a
        val m = Thang.toLong() + 12L * a - 3L
        var jd: Long =
            Ngay.toLong() + floor((153.0 * m.toDouble() + 2.0) / 5.0).toLong() + 365L * y + floor(y.toDouble() / 4.0).toLong() - floor(y.toDouble() / 100.0).toLong() + floor(y.toDouble() / 400.0).toLong() - 32045L
        return jd
    }

    fun jdn(dd: Int, mm: Int, yy: Int) : Double {
        val a = floor((14.0 - mm.toDouble()) / 12.0)
        val y = yy.toDouble() + 4800.0 - a
        val m = mm.toDouble() + 12.0 * a - 3.0
        val jd = dd.toDouble() + floor((153.0 * m + 2.0) / 5.0) + 365.0 * y + floor(y / 4.0) - floor(y / 100.0) + floor(y / 400.0) - 32045.0
        return jd
    }

    fun jdAtVST(dd: Int, mm: Int, yy: Int, hour: Int, minutes: Int) : Double {
        val ret = jdn(dd, mm, yy)
        return ret - 0.5 + (hour - 7.0) / 24.0 + minutes.toDouble() / 1440.0
    }

    fun solarLongitude(jd:Double): Double {
        val T = (jd - 2451545.0 ) / 36525.0
        val T2 = T * T
        val dr = PI / 180.0
        val M = 357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2
        val L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2
        var C = (1.914600 - 0.004817 * T - 0.000014 * T2) * sin(dr * M)
        C += (0.019993 - 0.000101 * T) * sin(dr * 2.0 * M) + 0.000290 * sin(dr * 3.0 * M)
        val theta = L0 + C
        val omega = 125.04 - 1934.136 * T
        var lambda = theta - 0.00569 - 0.00478 * sin(omega * dr)
        lambda -= 360.0 * floor(lambda / 360.0)
        return lambda
    }

    private val jdAt by lazy { jdAtVST(ngaydl, thangdl, namdl, giodl, phutdl) }
    val cc = CanChi()
    private val ntnResult by lazy { Solar2Lunar() }
    val canngay get() = ((JD() + 9) % 10).toInt()
    val chingay get() = ((JD() + 1) % 12).toInt()
    
    fun tietKhi(): String{
        var vt = 0
        val sl = solarLongitude(jdAt).toInt()
        for (i in cc.itietkhi.indices){
            if (cc.itietkhi[i] <= sl){
                vt = i
            }
        }
       return cc.tietkhi[vt]
    }
    fun nguyetLenh(): String {
        val tt = tietKhi()
        var nguyetlenh: String = ""
        when (tt) {
            "Bạch lộ", "Thu phân" -> nguyetlenh = "Dậu"
            "Hàn lộ", "Sương giáng" -> nguyetlenh = "Tuất"
            "Lập đông", "Tiểu tuyết" -> nguyetlenh = "Hợi"
            "Đại tuyết", "Đông chí" -> nguyetlenh = "Tí"
            "Tiểu hàn", "Đại hàn" -> nguyetlenh = "Sửu"
            "Lập xuân", "Vũ thủy" -> nguyetlenh = "Dần"
            "Kinh trập", "Xuân phân" -> nguyetlenh = "Mão"
            "Thanh minh", "Cốc vũ" -> nguyetlenh = "Thìn"
            "Lập hạ", "Tiểu mãn" -> nguyetlenh = "Tỵ"
            "Mang chủng", "Hạ chí" -> nguyetlenh = "Ngọ"
            "Tiểu thử", "Đại thử" -> nguyetlenh = "Mùi"
            "Lập thu", "Xử thử" -> nguyetlenh = "Thân"
        }
        return nguyetlenh
    }

    fun nguyetTuong(): String {
        val tt = tietKhi()
        var nguyettuong: String = ""
        when (tt) {
            "Lập hạ", "Cốc vũ" -> nguyettuong = "Dậu"
            "Thanh minh", "Xuân phân" -> nguyettuong = "Tuất"
            "Kinh trập", "Vũ thủy" -> nguyettuong = "Hợi"
            "Đại hàn", "Lập xuân" -> nguyettuong = "Tý"
            "Tiểu hàn", "Đông chí" -> nguyettuong = "Sửu"
            "Tiểu tuyết", "Đại tuyết" -> nguyettuong = "Dần"
            "Sương giáng", "Lập đông" -> nguyettuong = "Mão"
            "Thu phân", "Hàn lộ" -> nguyettuong = "Thìn"
            "Xử thử", "Bạch lộ" -> nguyettuong = "Tỵ"
            "Đại thử", "Lập thu" -> nguyettuong = "Ngọ"
            "Tiểu thử", "Hạ chí" -> nguyettuong = "Mùi"
            "Tiểu mãn", "Mang chủng" -> nguyettuong = "Thân"
        }
        return nguyettuong
    }

    fun _ngaythang(): String {
        val currentJD = JD()
        val currentGiodl = giodl
        val cangio = if(currentGiodl > 22)(currentJD - 1) * 2 % 10 else ((currentJD - 1) * 2 + floor((currentGiodl.toDouble() + 1.0) / 2.0).toLong()) % 10
        var displayGiodl = currentGiodl
        if (displayGiodl % 2 != 0) displayGiodl += 1
        if (displayGiodl == 24) displayGiodl = 0
        
        val ntn = ntnResult
        
        return ("Thời gian lập quẻ: $ngaydl/$thangdl/$namdl, ${
            currentGiodl.toString().padStart(2, '0')
        }:${phutdl.toString().padStart(2, '0')}      Theo Âm lịch: ${ntn[0]}/${ntn[1]}/${ntn[2]} ÂL" +
                "\nCan Chi: Giờ ${cc.can[cangio.toInt()]} ${cc.chi[displayGiodl / 2]}, Ngày " + cc.can[canngay] + " " + cc.chi[chingay]
                + ", Tháng " + cc.can[((ntn[2] * 12 + ntn[1] + 3) % 10)] + " " + cc.chithang[ntn[1] - 1]
                + ", Năm " + cc.can[(ntn[2] + 6) % 10] + " " + cc.chi[(ntn[2] + 8) % 12] + "\n" +
                "Tiết khí: ${tietKhi()}     Nhật thần: " + cc.chi[chingay] + "     Nguyệt lệnh: " + nguyetLenh())
    }


    fun _lichtrachcat(): String {
        val ntn = ntnResult
        return ("$ngaydl/$thangdl/$namdl dương lịch ( ${ntn[0]}/${ntn[1]}/${ntn[2]} âm lịch )" +
                "\nNgày " + cc.can[canngay] + " " + cc.chi[chingay]
                + " Tháng " + cc.can[((ntn[2] * 12 + ntn[1] + 3) % 10)] + " " + cc.chithang[ntn[1] - 1]
                + " Năm " + cc.can[(ntn[2] + 6) % 10] + " " + cc.chi[(ntn[2] + 8) % 12] + "\n" +
                "Tiết " + nguyetLenh())
    }

    val canChiNgayThang get() = CanChiNgayThang(
        canngay,
        chingay,
        ((ntnResult[2] * 12 + ntnResult[1] + 3) % 10),
        ntnResult[1] - 1,
        (ntnResult[2] + 6) % 10,
        (ntnResult[2] + 8) % 12,
        giodl
    )

    fun TuanKhong(): String {
        var TK: String = ""
        var count: Int = canngay
        var tempChingay = chingay
        while (count >= 0) {
            var TuanGiap = tempChingay--
            if (TuanGiap < 0) TuanGiap += 12
            if (TuanGiap == 0 || TuanGiap == 2 || TuanGiap == 4 || TuanGiap == 6 || TuanGiap == 8 || TuanGiap == 10) {
                TK = cc.chi[TuanGiap]
            }
            count--
        }
        return TK
    }
}
