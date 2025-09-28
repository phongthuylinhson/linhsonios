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
        val JD: Double = if (Y > 1582 || Y == 1582 && M > 10 || Y == 1582 && M == 10 && D > 14) {
            367 * Y - INT((7 * (Y + INT(((M + 9) / 12).toDouble())) / 4).toDouble()) - INT(
                (3 * (INT(
                    ((Y + (M - 9) / 7) / 100).toDouble()
                ) + 1) / 4).toDouble()
            ) + INT(
                (275 * M / 9).toDouble()
            ) + D + 1721028.5
        } else {
            367 * Y - INT((7 * (Y + 5001 + INT(((M - 9) / 7).toDouble())) / 4).toDouble()) + INT((275 * M / 9).toDouble()) + D + 1729776.5
        }
        return JD
    }

    fun UniversalFromJD(JD: Double): IntArray {
        val A: Int
        val alpha: Int
        val dd: Int
        val F: Double
        val Z: Int = INT(JD + 0.5).toInt()
        F = JD + 0.5 - Z
        if (Z < 2299161) {
            A = Z
        } else {
            alpha = INT((Z - 1867216.25) / 36524.25).toInt()
            A = (Z + 1 + alpha - INT((alpha / 4).toDouble())).toInt()
        }
        val B: Int = A + 1524
        val C: Int = INT((B - 122.1) / 365.25).toInt()
        val D: Int = INT(365.25 * C).toInt()
        val E: Int = INT((B - D) / 30.6001).toInt()
        dd = INT(B - D - INT(30.6001 * E) + F).toInt()
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
        val k = INT(off / 29.530588853).toInt()
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
        val T = k / 1236.85 // Time in Julian centuries from 1900 January 0.5
        val T2 = T * T
        val T3 = T2 * T
        val dr: Double = PI / 180
        var Jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * T2 - 0.000000155 * T3
        Jd1 += 0.00033 * sin((166.56 + 132.87 * T - 0.009173 * T2) * dr) // Mean new moon
        val M = 359.2242 + 29.10535608 * k - 0.0000333 * T2 - 0.00000347 * T3 // Sun's mean anomaly
        val Mpr =
            306.0253 + 385.81691806 * k + 0.0107306 * T2 + 0.00001236 * T3 // Moon's mean anomaly
        val F =
            21.2964 + 390.67050646 * k - 0.0016528 * T2 - 0.00000239 * T3 // Moon's argument of latitude
        var C1 =
            (0.1734 - 0.000393 * T) * sin(M * dr) + 0.0021 * sin(2 * dr * M)
        C1 = C1 - 0.4068 * sin(Mpr * dr) + 0.0161 * sin(dr * 2 * Mpr)
        C1 -= 0.0004 * sin(dr * 3 * Mpr)
        C1 = C1 + 0.0104 * sin(dr * 2 * F) - 0.0051 * sin(dr * (M + Mpr))
        C1 = C1 - 0.0074 * sin(dr * (M - Mpr)) + 0.0004 * sin(dr * (2 * F + M))
        C1 = C1 - 0.0004 * sin(dr * (2 * F - M)) - 0.0006 * sin(dr * (2 * F + Mpr))
        C1 += 0.0010 * sin(dr * (2 * F - Mpr)) + 0.0005 * sin(dr * (2 * Mpr + M))
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
        var ret: Array<IntArray>? = null
        val month11A = LunarMonth11(Y - 1)
        val jdMonth11A = LocalToJD(month11A[0], month11A[1], month11A[2])
        val k = floor(0.5 + (jdMonth11A - 2415021.076998695) / 29.530588853).toInt()
        val month11B = LunarMonth11(Y)
        val off = LocalToJD(month11B[0], month11B[1], month11B[2]) - jdMonth11A
        val leap = off > 365.0
        ret = if (!leap) {
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
            val sl1 = sunLongitudes[i]
            val sl2 = sunLongitudes[i + 1]
            val hasMajorTerm = floor(sl1 / PI * 6) != floor(sl2 / PI * 6)
            if (!hasMajorTerm) {
                found = true
                ret[i][4] = 1
                ret[i][3] = MOD(i + 10, 12)
            }
        }
    }

    fun Solar2Lunar(): IntArray {
        var yy = namdl
        var ly = LunarYear(namdl) // Please cache the result of this computation for later use!!!
        val month11 = ly[ly.size - 1]
        var jdToday = LocalToJD(0, 0, 0)
        if (giodl == 23) jdToday = LocalToJD(ngaydl + 1, thangdl, namdl)
        else jdToday = LocalToJD(ngaydl, thangdl, namdl)
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

    private fun INT(d: Double): Long {
        return floor(d).toLong()
    }

    private fun getSunLongitudea(jdn: Double): Double {
        val T = (jdn - 2451545.0) / 36525 // Time in Julian centuries from 2000-01-01 12:00:00 GMT
        val T2 = T * T
        val dr: Double = PI / 180 // degree to radian
        val M =
            357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2 // mean anomaly, degree
        val L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2 // mean longitude, degree
        var DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * sin(dr * M)
        DL += (0.019993 - 0.000101 * T) * sin(dr * 2 * M) + 0.000290 * sin(dr * 3 * M)
        var L = L0 + DL // true longitude, degree
        // obtain apparent longitude by correcting for nutation and aberration
        val omega = 125.04 - 1934.136 * T
        L = L - 0.00569 - 0.00478 * sin(omega * dr)
        L *= dr
        L -= PI * 2 * INT(L / (PI * 2)) // Normalize to (0, 2*PI)
        return L
    }

    fun JD(): Long {
        val Thanga: Double = Thang.toDouble()
        val a = INT((14 - Thanga) / 12)
        val y = Nam + 4800 - a
        val m = Thang + 12 * a - 3
        var jd: Long =
            Ngay + INT(((153 * m + 2) / 5).toDouble()) + 365 * y + INT((y / 4).toDouble()) - INT(
                (y / 100).toDouble()
            ) + INT((y / 400).toDouble()) - 32045
        if (giodl == 23) jd += 1
        return jd
    }
    fun JDTT(): Long {
        val Thanga: Double = Thang.toDouble()
        val a = INT((14 - Thanga) / 12)
        val y = Nam + 4800 - a
        val m = Thang + 12 * a - 3
        var jd: Long =
            Ngay + INT(((153 * m + 2) / 5).toDouble()) + 365 * y + INT((y / 4).toDouble()) - INT(
                (y / 100).toDouble()
            ) + INT((y / 400).toDouble()) - 32045
        return jd
    }

    fun INTT(d: Double): Double {
        return floor(d)
    }
    fun jdn(dd: Int, mm: Int, yy: Int) : Double {
        val a = INTT(((14 - mm) / 12).toDouble())
        val y = yy+4800-a
        val m = mm+12*a-3
        val jd = dd + INTT((153*m+2)/5) + 365*y + INTT(y/4) - INTT(y/100) + INTT(y/400) - 32045
        return jd
    }

    fun jdAtVST(dd: Int, mm: Int, yy: Int, hour: Int, minutes: Int) : Double {
        val ret = jdn(dd, mm, yy)
        return ret - 0.5 + (hour-7)/24.0 + minutes/1440.0
    }

    fun solarLongitude(jd:Double): Double {
        //var T, T2, dr, M, L0, C, lambda, theta, omega
        val T = (jd - 2451545.0 ) / 36525 // Time in Julian centuries from 2000-01-01 12:00:00 GMT
        val T2 = T*T
        val dr = Math.PI/180 // degree to radian
        // mean anomaly, degree
        val M = 357.52910 + 35999.05030*T - 0.0001559*T2 - 0.00000048*T*T2
        // mean longitude, degree
        val L0 = 280.46645 + 36000.76983*T + 0.0003032*T2
        // Sun's equation of center
        var C = (1.914600 - 0.004817*T - 0.000014*T2)* sin(dr*M)
        C += (0.019993 - 0.000101 * T) * sin(dr * 2 * M) + 0.000290 * sin(dr * 3 * M)
        val theta = L0 + C // true longitude, degree
        // obtain apparent longitude by correcting for nutation and aberration
        val omega = 125.04 - 1934.136 * T
        var lambda = theta - 0.00569 - 0.00478 * sin(omega * dr)
        // Normalize to (0, 360)
        lambda -= 360 * (INTT(lambda / 360)) // Normalize to (0, 2*PI)
        return lambda
    }

    val jdAt = jdAtVST(ngaydl,thangdl,namdl,giodl,phutdl)
    val tietKhi = CanChi().tietkhi
    val itietKhi = CanChi().itietkhi
    val chi = CanChi().chi
    val can = CanChi().can
    val chithang = CanChi().chithang
    val ntn = Solar2Lunar()
    val canngay = ((JD() + 9) % 10).toInt()
    var chingay = ((JD() + 1) % 12).toInt()
    val canngayTT = ((JDTT() + 9) % 10).toInt()
    val chingayTT = ((JDTT() + 1) % 12).toInt()
    fun tietKhi(): String{
        var vt = 0
        for (i in itietKhi.indices){
            if (itietKhi[i]<=solarLongitude(jdAt).toInt()){
                vt = i
            }
        }
       return tietKhi[vt]
    }
    fun nguyetLenh(): String {
        var tt=""
        var vt = 0
        for (i in itietKhi.indices){
            if (itietKhi[i]<=solarLongitude(jdAt).toInt()){
                vt = i
            }
        }
        tt=tietKhi[vt]
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
        var tt=""
        var vt = 0
        for (i in itietKhi.indices){
            if (itietKhi[i]<=solarLongitude(jdAt).toInt()){
                vt = i
            }
        }
        tt=tietKhi[vt]
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
        val cangio = if(giodl>22)(JD() - 1) * 2 % 10 else ((JD() - 1) * 2 + INT((giodl.toDouble() + 1) / 2)) % 10
        val giodlll = giodl
        if (giodl % 2 != 0) giodl += 1
        if (giodl == 24) giodl = 0
        //Log.d("canchigio", "_ngaythang: ${can[cangio.toInt()]} ${chi[giodl / 2]}")
        return ("Thời gian lập quẻ: $ngaydl/$thangdl/$namdl, ${
            String.format(
                "%02d",
                giodlll
            )
        }:${String.format("%02d", phutdl)}      Theo Âm lịch: ${ntn[0]}/${ntn[1]}/${ntn[2]} ÂL" +
                "\nCan Chi: Giờ ${can[cangio.toInt()]} ${chi[giodl / 2]}, Ngày " + can[canngay] + " " + chi[chingay]
                + ", Tháng " + can[((ntn[2] * 12 + ntn[1] + 3) % 10)] + " " + chithang[ntn[1] - 1]
                + ", Năm " + can[(ntn[2] + 6) % 10] + " " + chi[(ntn[2] + 8) % 12] + "\n" +
                "Tiết khí: ${tietKhi()}     Nhật thần: " + chi[chingay] + "     Nguyệt lệnh: " + nguyetLenh())
    }


    fun _lichtrachcat(): String {
        //val tietkhi = tietkhi()
        return ("$ngaydl/$thangdl/$namdl dương lịch ( ${ntn[0]}/${ntn[1]}/${ntn[2]} âm lịch )" +
                "\nNgày " + can[canngay] + " " + chi[chingay]
                + "Tháng " + can[((ntn[2] * 12 + ntn[1] + 3) % 10)] + " " + chithang[ntn[1] - 1]
                + "Năm " + can[(ntn[2] + 6) % 10] + " " + chi[(ntn[2] + 8) % 12] + "\n" +
                "Tiết " + nguyetLenh())
    }

    var canChiNgayThang = CanChiNgayThang(
        canngay,
        chingay,
        ((ntn[2] * 12 + ntn[1] + 3) % 10),
        ntn[1] - 1,
        (ntn[2] + 6) % 10,
        (ntn[2] + 8) % 12,
        giodl
    )

    fun TuanKhong(): String {
        var TK: String = ""
        var count: Int? = null
        var TuanGiap: Int? = null
        count = canngay
        while (count >= 0) {
            TuanGiap = chingay--
            if (TuanGiap == 0 || TuanGiap == -12) {
                TuanGiap = 0
                TK = chi[TuanGiap]
            }
            if (TuanGiap == 2 || TuanGiap == -10) {
                TuanGiap = 2
                TK = chi[TuanGiap]
            }
            if (TuanGiap == 4 || TuanGiap == -8) {
                TuanGiap = 4
                TK = chi[TuanGiap]
            }
            if (TuanGiap == 6 || TuanGiap == -6) {
                TuanGiap = 6
                TK = chi[TuanGiap]
            }
            if (TuanGiap == 8 || TuanGiap == -4) {
                TuanGiap = 8
                TK = chi[TuanGiap]
            }
            if (TuanGiap == 10 || TuanGiap == -2) {
                TuanGiap = 10
                TK = chi[TuanGiap]
            }
            count--
        }
        return TK
    }
}