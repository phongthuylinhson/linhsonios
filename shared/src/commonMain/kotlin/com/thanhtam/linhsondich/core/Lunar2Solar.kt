package com.thanhtam.linhsondich.core

import com.thanhtam.linhsondich.model.CanChi
import com.thanhtam.linhsondich.model.CanChiNgayThang
import kotlin.math.PI
import kotlin.math.sin

data class Lunar2Solar(
    var lunarYear: Int,
    var lunarMonth: Int,
    var lunarDay: Int,
    var lunarLeap: Boolean,
    var giodl: Int,
    var timeZoneOffset: Int
) {
    internal fun jdFromDate(dd: Int, mm: Int, yyyy: Int): Int {
        val a = (14 - mm) / 12
        val y = yyyy + 4800 - a
        val m = mm + 12 * a - 3
        var jd = dd + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045
        if (jd < 2299161) {
            jd = dd + (153 * m + 2) / 5 + 365 * y + y / 4 - 32083
        }
        if (giodl == 23) jd += 1
        return jd
    }

    internal fun jdToDate(jd: Int): SolarDate {
        var a: Int
        var b: Int
        var c: Int
        if (jd > 2299160) {
            a = jd + 32044
            b = (4 * a + 3) / 146097
            c = a - (b * 146097) / 4
        } else {
            b = 0
            c = jd + 32082
        }
        val d = (4 * c + 3) / 1461
        val e = c - (1461 * d) / 4
        val m = (5 * e + 2) / 153
        val day = e - (153 * m + 2) / 5 + 1
        val month = m + 3 - 12 * (m / 10)
        val year = b * 100 + d - 4800 + (m / 10)
        return SolarDate(year, month, day)
    }

    internal fun newMoon(ak: Int): Double {
        val k = ak.toDouble()
        val T = k / 1236.85
        val T2 = T * T
        val T3 = T2 * T
        val dr = PI / 180.0
        var Jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * T2 - 0.000000155 * T3
        Jd1 = Jd1 + 0.00033 * sin((166.56 + 132.87 * T - 0.009173 * T2) * dr)
        val M = 359.2242 + 29.10535608 * k - 0.0000333 * T2 - 0.00000347 * T3
        val mPr = 306.0253 + 385.81691806 * k + 0.0107306 * T2 + 0.00001236 * T3
        val F = 21.2964 + 390.67050646 * k - 0.0016528 * T2 - 0.00000239 * T3
        var C1 = (0.1734 - 0.000393 * T) * sin(M * dr) + 0.0021 * sin(2.0 * dr * M)
        C1 = C1 - 0.4068 * sin(mPr * dr) + 0.0161 * sin(dr * 2.0 * mPr)
        C1 = C1 - 0.0004 * sin(dr * 3.0 * mPr)
        C1 = C1 + 0.0104 * sin(dr * 2.0 * F) - 0.0051 * sin(dr * (M + mPr))
        C1 = C1 - 0.0074 * sin(dr * (M - mPr)) + 0.0004 * sin(dr * (2.0 * F + M))
        C1 = C1 - 0.0004 * sin(dr * (2.0 * F - M)) - 0.0006 * sin(dr * (2.0 * F + mPr))
        C1 = C1 + 0.0010 * sin(dr * (2.0 * F - mPr)) + 0.0005 * sin(dr * (2.0 * mPr + M))
        val deltat = if (T < -11) 0.001 + 0.000839 * T + 0.0002261 * T2 - 0.00000845 * T3 - 0.000000081 * T * T3
        else -0.000278 + 0.000265 * T + 0.000262 * T2
        return Jd1 + C1 - deltat
    }

    internal fun getLunarMonth11(yyyy: Int, timeZoneOffset: Int): Int {
        val off = jdFromDate(31, 12, yyyy) - 2415021
        val k = (off.toDouble() / 29.530588853).toInt()
        var nm = getNewMoonDay(k, timeZoneOffset)
        val sunLong = getSunLongitude(nm, timeZoneOffset)
        if (sunLong >= 9) {
            nm = getNewMoonDay(k - 1, timeZoneOffset)
        }
        return nm
    }

    internal fun getNewMoonDay(k: Int, timeZoneOffset: Int): Int = (newMoon(k) + 0.5 + timeZoneOffset.toDouble() / 24.0).toInt()

    internal fun getSunLongitude(jd: Int, timeZoneOffset: Int): Int = (sunLongitude(jd - 0.5 - timeZoneOffset.toDouble() / 24.0) / PI * 6.0).toInt()

    internal fun sunLongitude(jdn: Double): Double {
        val T = (jdn - 2451545.0) / 36525.0
        val T2 = T * T
        val dr = PI / 180.0
        val M = 357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2
        val L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2
        var DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * sin(dr * M)
        DL = DL + (0.019993 - 0.000101 * T) * sin(dr * 2.0 * M) + 0.000290 * sin(dr * 3.0 * M)
        var L = L0 + DL
        L = L * dr
        L = L - PI * 2.0 * (L / (PI * 2.0)).toInt().toDouble()
        return L
    }

    internal fun getLeapMonthOffset(a11: Int, timeZoneOffset: Int): Int {
        val k = ((a11.toDouble() - 2415021.076998695) / 29.530588853 + 0.5).toInt()
        var last = 0
        var i = 1
        var arc = getSunLongitude(getNewMoonDay(k + i, timeZoneOffset), timeZoneOffset)
        while (arc != last && i < 14) {
            last = arc
            i++
            arc = getSunLongitude(getNewMoonDay(k + i, timeZoneOffset), timeZoneOffset)
        }
        return i - 1
    }

    fun computeSolarDate(): SolarDate {
        val a11: Int
        val b11: Int
        if (lunarMonth < 11) {
            a11 = getLunarMonth11(lunarYear - 1, timeZoneOffset)
            b11 = getLunarMonth11(lunarYear, timeZoneOffset)
        } else {
            a11 = getLunarMonth11(lunarYear, timeZoneOffset)
            b11 = getLunarMonth11(lunarYear + 1, timeZoneOffset)
        }
        val k = (0.5 + (a11.toDouble() - 2415021.076998695) / 29.530588853).toInt()
        var off = if (lunarMonth - 11 < 0) lunarMonth - 11 + 12 else lunarMonth - 11
        if (b11 - a11 > 365) {
            val leapOff = getLeapMonthOffset(a11, timeZoneOffset)
            val leapMonth = if (leapOff - 2 < 0) leapOff - 2 + 12 else leapOff - 2
            if (lunarLeap && lunarMonth != leapMonth) return SolarDate(0, 0, 0)
            if (lunarLeap || off >= leapOff) off = off + 1
        }
        val monthStart = getNewMoonDay(k + off, timeZoneOffset)
        return jdToDate(monthStart + lunarDay - 1)
    }

    private fun sunLongitudeApparent(jdn: Double): Double {
        val T = (jdn - 2451545.0) / 36525.0
        val T2 = T * T
        val dr = PI / 180.0
        val M = 357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2
        val L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2
        var DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * sin(dr * M)
        DL = DL + (0.019993 - 0.000101 * T) * sin(dr * 2.0 * M) + 0.000290 * sin(dr * 3.0 * M)
        var L = L0 + DL
        val omega = 125.04 - 1934.136 * T
        L = L - 0.00569 - 0.00478 * sin(omega * dr)
        L = L * dr
        L = L - PI * 2.0 * (L / (PI * 2.0)).toInt().toDouble()
        return L
    }

    fun getTietKhiValue(dd: Int, mm: Int, yyyy: Int, timeZone: Int): Int {
        return (sunLongitudeApparent(jdFromDate(dd, mm, yyyy).toDouble() + 0.5 - timeZone.toDouble() / 24.0) / PI * 12.0).toInt()
    }

    fun lunar2solar(): SolarDate = computeSolarDate()

    fun _ngaythang(): String {
        val sDate = computeSolarDate()
        val tt = getTietKhiValue(sDate.day, sDate.month, sDate.year, 7)
        val cc = CanChi()
        val tietkhiName = if (tt in 0..23) cc.tietkhi[tt] else ""
        var nguyetlenh = ""
        when (tietkhiName) {
            "Bạch lộ", "Thu phân" -> nguyetlenh = "Dậu"
            "Hàn lộ", "Sương giáng" -> nguyetlenh = "Tuất"
            "Lập đông", "Tiểu tuyết" -> nguyetlenh = "Hợi"
            "Đại tuyết", "Đông chí" -> nguyetlenh = "Tý"
            "Tiểu hàn", "Đại hàn" -> nguyetlenh = "Sửu"
            "Lập xuân", "Vũ thủy" -> nguyetlenh = "Dần"
            "Kinh trập", "Xuân phân" -> nguyetlenh = "Mão"
            "Thanh minh", "Cốc vũ" -> nguyetlenh = "Thìn"
            "Lập hạ", "Tiểu mãn" -> nguyetlenh = "Tỵ"
            "Mang chủng", "Hạ chí" -> nguyetlenh = "Ngọ"
            "Tiểu thử", "Đại thử" -> nguyetlenh = "Mùi"
            "Lập thu", "Xử thử" -> nguyetlenh = "Thân"
        }
        var h = giodl
        if (h % 2 != 0) h = h + 1
        if (h == 24) h = 0
        
        val jd = jdFromDate(sDate.day, sDate.month, sDate.year)
        val canngay = (jd + 9) % 10
        val chingay = (jd + 1) % 12
        
        return ("$h Giờ - ${sDate.day}/${sDate.month}/${sDate.year} dương lịch ( $lunarDay/$lunarMonth/$lunarYear âm lịch )" +
                "\nGiờ ${cc.chi[h / 2]}-Ngày ${cc.can[canngay]} ${cc.chi[chingay]}" +
                "-Tháng ${cc.can[(lunarYear * 12 + lunarMonth + 3) % 10]} ${cc.chithang[lunarMonth - 1]}" +
                "-Năm ${cc.can[(lunarYear + 6) % 10]} ${cc.chi[(lunarYear + 8) % 12]}\n" +
                "Tiết: $tietkhiName    Nguyệt lệnh: $nguyetlenh")
    }

    val canChiNgayThang: CanChiNgayThang get() {
        val sDate = computeSolarDate()
        val jd = jdFromDate(sDate.day, sDate.month, sDate.year)
        return CanChiNgayThang(
            (jd + 9) % 10,
            (jd + 1) % 12,
            (lunarYear * 12 + lunarMonth + 3) % 10,
            lunarMonth - 1,
            (lunarYear + 6) % 10,
            (lunarYear + 8) % 12,
            giodl
        )
    }

    fun TuanKhong(): String {
        val sDate = computeSolarDate()
        val jd = jdFromDate(sDate.day, sDate.month, sDate.year)
        val canngayValue = (jd + 9) % 10
        var chingayVal = (jd + 1) % 12
        val cc = CanChi()
        var TK = ""
        var count = canngayValue
        while (count >= 0) {
            var TuanGiap = chingayVal
            chingayVal = chingayVal - 1
            if (TuanGiap < 0) TuanGiap = TuanGiap + 12
            if (TuanGiap == 0 || TuanGiap == 2 || TuanGiap == 4 || TuanGiap == 6 || TuanGiap == 8 || TuanGiap == 10) {
                TK = cc.chi[TuanGiap]
            }
            count = count - 1
        }
        return TK
    }

    fun TuanKhongNam(): String {
        val cannamValue = (lunarYear + 6) % 10
        var chinamVal = (lunarYear + 8) % 12
        val cc = CanChi()
        var TK = ""
        var count = cannamValue
        while (count >= 0) {
            var TuanGiap = chinamVal
            chinamVal = chinamVal - 1
            if (TuanGiap < 0) TuanGiap = TuanGiap + 12
            if (TuanGiap == 0 || TuanGiap == 2 || TuanGiap == 4 || TuanGiap == 6 || TuanGiap == 8 || TuanGiap == 10) {
                TK = cc.chi[TuanGiap]
            }
            count = count - 1
        }
        return TK
    }
}
