package com.thanhtam.linhsondich.core

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.thanhtam.linhsondich.model.CanChi
import com.thanhtam.linhsondich.model.CanChiNgayThang
import kotlin.math.PI
import kotlin.math.sin

data class Lunar2Solar(
    var lunarYear: Int,
    var lunarMonth: Int,
    var lunarDay: Int,
    var lunarLeap: Boolean,
    var giodl:Int,
    var timeZoneOffset: Int,
    var context: Context
) {
    internal fun INT(number: Number): Int {
        return number.toInt()
    }

    internal fun DOUBLE(i: Int): Double {
        return i.toDouble()
    }

    internal fun jdFromDate(dd: Int, mm: Int, yyyy: Int): Int {
        var a: Int
        var aa:Int
        var y: Int
        var m: Int
        var jd: Int
        a = INT((14 - mm) / 12)
        y = yyyy + 4800 - a
        m = mm + 12 * a - 3
        jd =
            dd + INT((153 * m + 2) / 5) + 365 * y + INT(y / 4) - INT(y / 100) + INT(y / 400) - 32045
        if (jd < 2299161) {
            jd = dd + INT((153 * m + 2) / 5) + 365 * y + INT(y / 4) - 32083
        }
        if (giodl == 23) jd += 1
        return jd
    }

    internal fun jdToDate(jd: Int): SolarDate {
        var a: Int
        var b: Int
        var c: Int
        var d: Int
        var e: Int
        var m: Int
        var day: Int
        var month: Int
        var year: Int

        if (jd > 2299160) {
            // After 5/10/1582, Gregorian calendar
            a = jd + 32044
            b = INT((4 * a + 3) / 146097)
            c = a - INT((b * 146097) / 4)
        } else {
            b = 0
            c = jd + 32082
        }
        d = INT((4 * c + 3) / 1461)
        e = c - INT((1461 * d) / 4)
        m = INT((5 * e + 2) / 153)
        day = e - INT((153 * m + 2) / 5) + 1
        month = m + 3 - 12 * INT(m / 10)
        year = b * 100 + d - 4800 + INT(m / 10)

        return SolarDate(year, month, day)
    }

    internal fun newMoon(ak: Int): Double {
        var T: Double
        var T2: Double
        var T3: Double
        var dr: Double
        var Jd1: Double
        var M: Double
        var mPr: Double
        var F: Double
        var C1: Double
        var deltat: Double

        val k = DOUBLE(ak)
        T = k / 1236.85 // Time in Julian centuries from 1900 January 0.5
        T2 = T * T
        T3 = T2 * T
        dr = PI / 180
        Jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * T2 - 0.000000155 * T3
        Jd1 = Jd1 + 0.00033 * ((166.56 + 132.87 * T - 0.009173 * T2) * dr)  // Mean new moon
        M = 359.2242 + 29.10535608 * k - 0.0000333 * T2 - 0.00000347 * T3     // Sun's mean anomaly
        mPr = 306.0253 + 385.81691806 * k + 0.0107306 * T2 + 0.00001236 * T3 // Moon's mean anomaly
        F =
            21.2964 + 390.67050646 * k - 0.0016528 * T2 - 0.00000239 * T3     // Moon's argument of latitude
        C1 = (0.1734 - 0.000393 * T) * sin(M * dr) + 0.0021 * sin(2 * dr * M)
        C1 = C1 - 0.4068 * sin(mPr * dr) + 0.0161 * sin(dr * 2 * mPr)
        C1 = C1 - 0.0004 * sin(dr * 3 * mPr)
        C1 = C1 + 0.0104 * sin(dr * 2 * F) - 0.0051 * sin(dr * (M + mPr))
        C1 = C1 - 0.0074 * sin(dr * (M - mPr)) + 0.0004 * sin(dr * (2 * F + M))
        C1 = C1 - 0.0004 * sin(dr * (2 * F - M)) - 0.0006 * sin(dr * (2 * F + mPr))
        C1 = C1 + 0.0010 * sin(dr * (2 * F - mPr)) + 0.0005 * sin(dr * (2 * mPr + M))
        if (T < -11) {
            deltat = 0.001 + 0.000839 * T + 0.0002261 * T2 - 0.00000845 * T3 - 0.000000081 * T * T3
        } else {
            deltat = -0.000278 + 0.000265 * T + 0.000262 * T2
        }

        return Jd1 + C1 - deltat
    }

    internal fun getLunarMonth11(yyyy: Int, timeZoneOffset: Int): Int {
        var k: Int
        var off: Int
        var nm: Int
        var sunLong: Int

        off = jdFromDate(31, 12, yyyy) - 2415021
        k = INT(DOUBLE(off) / 29.530588853)
        nm = getNewMoonDay(k, timeZoneOffset)
        sunLong = getSunLongitude(nm, timeZoneOffset) // sun longitude at local midnight
        if (sunLong >= 9) {
            nm = getNewMoonDay(k - 1, timeZoneOffset)
        }
        return nm
    }

    internal fun getNewMoonDay(k: Int, timeZoneOffset: Int): Int {
        return INT(newMoon(k) + 0.5 + DOUBLE(timeZoneOffset) / 24)
    }

    internal fun getSunLongitude(jd: Int, timeZoneOffset: Int): Int {
        return INT(sunLongitude(jd - 0.5 - timeZoneOffset / 24.0) / PI * 6)
    }

    internal fun sunLongitude(jdn: Double): Double {
        var T: Double
        var T2: Double
        var dr: Double
        var M: Double
        var L0: Double
        var DL: Double
        var L: Double

        T = (jdn - 2451545.0) / 36525.0 // Time in Julian centuries from 2000-01-01 12:00:00 GMT
        T2 = T * T
        dr = PI / 180                                             // degree to radian
        M =
            357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2 // mean anomaly, degree
        L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2                  // mean longitude, degree
        DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * sin(dr * M)
        DL += (0.019993 - 0.000101 * T) * sin(dr * 2 * M) + 0.000290 * sin(dr * 3 * M)
        L = L0 + DL // true longitude, degree
        L *= dr
        L -= PI * 2 * DOUBLE(INT(L / (PI * 2))) // Normalize to (0, 2*PI)
        return L
    }

    internal fun getLeapMonthOffset(a11: Int, timeZoneOffset: Int): Int {
        var k: Int
        var last: Int
        var arc: Int
        var i: Int

        k = INT((DOUBLE(a11) - 2415021.076998695) / 29.530588853 + 0.5)
        last = 0
        i = 1 // We start with the month following lunar month 11
        arc = getSunLongitude(getNewMoonDay(k + i, timeZoneOffset), timeZoneOffset)

        while (arc != last && i < 14) {
            last = arc
            i++
            arc = getSunLongitude(getNewMoonDay(k + i, timeZoneOffset), timeZoneOffset)
        }
        return i - 1
    }

    fun lunar2solar(
    ): SolarDate {
        var k: Int
        var a11: Int
        var b11: Int
        var off: Int
        var leapOff: Int
        var leapMonth: Int
        var monthStart: Int

        if (lunarMonth < 11) {
            a11 = getLunarMonth11(lunarYear - 1, timeZoneOffset)
            b11 = getLunarMonth11(lunarYear, timeZoneOffset)
        } else {
            a11 = getLunarMonth11(lunarYear, timeZoneOffset)
            b11 = getLunarMonth11(lunarYear + 1, timeZoneOffset)
        }
        k = INT(0.5 + (DOUBLE(a11) - 2415021.076998695) / 29.530588853)
        off = lunarMonth - 11
        if (off < 0) {
            off += 12
        }
        if (b11 - a11 > 365) {
            leapOff = getLeapMonthOffset(a11, timeZoneOffset)
            leapMonth = leapOff - 2
            if (leapMonth < 0) {
                leapMonth += 12
            }
            if (lunarLeap && lunarMonth != leapMonth) {
                Toast.makeText(context, "Sai Tháng Nhuận", Toast.LENGTH_LONG).show()
                return SolarDate(0, 0, 0)
            } else if (lunarLeap || off >= leapOff) {
                off += 1
            }
        }
        monthStart = getNewMoonDay(k + off, timeZoneOffset)

        return jdToDate(monthStart + lunarDay - 1)
    }

    private fun getSunLongitudea(jdn: Double): Double {
        val T = (jdn - 2451545.0) / 36525 // Time in Julian centuries from 2000-01-01 12:00:00 GMT
        val T2 = T * T
        val dr: Double = Math.PI / 180 // degree to radian
        val M =
            357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2 // mean anomaly, degree
        val L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2 // mean longitude, degree
        var DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * Math.sin(dr * M)
        DL = DL + (0.019993 - 0.000101 * T) * Math.sin(dr * 2 * M) + 0.000290 * Math.sin(dr * 3 * M)
        var L = L0 + DL // true longitude, degree
        // obtain apparent longitude by correcting for nutation and aberration
        val omega = 125.04 - 1934.136 * T
        L = L - 0.00569 - 0.00478 * Math.sin(omega * dr)
        L = L * dr
        L = L - Math.PI * 2 * INT(L / (Math.PI * 2)) // Normalize to (0, 2*PI)
        return L
    }

    fun TietKhi(dd: Int, mm: Int, yyyy: Int, timeZone: Int): Int {
        return INT(
            getSunLongitudea(
                jdFromDate(
                    dd,
                    mm,
                    yyyy
                ) + 0.5 - timeZone / 24.0
            ) / Math.PI * 12
        )
    }

    val tietkhi = CanChi().tietkhi
    val chi = CanChi().chi
    val can = CanChi().can
    val chithang = CanChi().chithang
    val dd = lunar2solar().day
    val mm=lunar2solar().month
    val yyyy=lunar2solar().year
    val canngay = ((jdFromDate(lunar2solar().day,lunar2solar().month,lunar2solar().year) + 9) % 10).toInt()
    var chingay = ((jdFromDate(lunar2solar().day,lunar2solar().month,lunar2solar().year) + 1) % 12).toInt()
    fun _ngaythang(): String {
        val tt = TietKhi(lunar2solar().day,lunar2solar().month,lunar2solar().year, 7)
        var nguyetlenh: String = ""
        when (tietkhi[tt.toInt()]) {
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
        if (giodl % 2 != 0) giodl += 1
        if (giodl == 24) giodl = 0
        return ("$giodl Giờ - $dd/$mm/$yyyy dương lịch ( ${lunarDay}/${lunarMonth}/${lunarYear} âm lịch )" +
                "\nGiờ ${chi[giodl / 2]}-Ngày " + can[canngay] + " " + chi[chingay]
                + "-Tháng " + can[((lunarYear * 12 + lunarMonth + 3) % 10)] + " " + chithang[lunarMonth - 1]
                + "-Năm " + can[(lunarYear + 6) % 10] + " " + chi[(lunarYear + 8) % 12] + "\n" +
                "Tiết " + tietkhi[tt.toInt()]) + " Nguyệt lệnh $nguyetlenh"
    }

    var canChiNgayThang = CanChiNgayThang(
        canngay,
        chingay,
        ((lunarYear * 12 + lunarMonth + 3) % 10),
        lunarMonth - 1,
        (lunarYear + 6) % 10,
        (lunarYear + 8) % 12,
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

    fun TuanKhongNam(): String {
        var cannam = (lunarYear + 6) % 10
        var chinam = (lunarYear + 8) % 12
        var TK: String = ""
        var count: Int? = null
        var TuanGiap: Int? = null
        count = cannam
        while (count >= 0) {
            TuanGiap = chinam--
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