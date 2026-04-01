package com.thanhtam.linhsondich.core

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
    internal fun INT(number: Number): Int = number.toInt()
    internal fun DOUBLE(i: Int): Double = i.toDouble()

    internal fun jdFromDate(dd: Int, mm: Int, yyyy: Int): Int {
        var a = INT((14 - mm) / 12)
        var y = yyyy + 4800 - a
        var m = mm + 12 * a - 3
        var jd = dd + INT((153 * m + 2) / 5) + 365 * y + INT(y / 4) - INT(y / 100) + INT(y / 400) - 32045
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
        if (jd > 2299160) {
            a = jd + 32044
            b = INT((4 * a + 3) / 146097)
            c = a - INT((b * 146097) / 4)
        } else {
            b = 0
            c = jd + 32082
        }
        val d = INT((4 * c + 3) / 1461)
        val e = c - INT((1461 * d) / 4)
        val m = INT((5 * e + 2) / 153)
        val day = e - INT((153 * m + 2) / 5) + 1
        val month = m + 3 - 12 * INT(m / 10)
        val year = b * 100 + d - 4800 + INT(m / 10)
        return SolarDate(year, month, day)
    }

    internal fun newMoon(ak: Int): Double {
        val k = DOUBLE(ak)
        val T = k / 1236.85
        val T2 = T * T
        val T3 = T2 * T
        val dr = PI / 180
        var Jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * T2 - 0.000000155 * T3
        Jd1 += 0.00033 * sin((166.56 + 132.87 * T - 0.009173 * T2) * dr)
        val M = 359.2242 + 29.10535608 * k - 0.0000333 * T2 - 0.000000347 * T3
        val mPr = 306.0253 + 385.81691806 * k + 0.0107306 * T2 + 0.00001236 * T3
        val F = 21.2964 + 390.67050646 * k - 0.0016528 * T2 - 0.00000239 * T3
        var C1 = (0.1734 - 0.000393 * T) * sin(M * dr) + 0.0021 * sin(2 * dr * M)
        C1 -= 0.4068 * sin(mPr * dr) + 0.0161 * sin(dr * 2 * mPr)
        C1 -= 0.0004 * sin(dr * 3 * mPr)
        C1 += 0.0104 * sin(dr * 2 * F) - 0.0051 * sin(dr * (M + mPr))
        C1 -= 0.0074 * sin(dr * (M - mPr)) + 0.0004 * sin(dr * (2 * F + M))
        C1 -= 0.0004 * sin(dr * (2 * F - M)) - 0.0006 * sin(dr * (2 * F + mPr))
        C1 += 0.0010 * sin(dr * (2 * F - mPr)) + 0.0005 * sin(dr * (2 * mPr + M))
        val deltat = if (T < -11) 0.001 + 0.000839 * T + 0.0002261 * T2 - 0.00000845 * T3 - 0.000000081 * T * T3
        else -0.000278 + 0.000265 * T + 0.000262 * T2
        return Jd1 + C1 - deltat
    }

    internal fun getLunarMonth11(yyyy: Int, timeZoneOffset: Int): Int {
        val off = jdFromDate(31, 12, yyyy) - 2415021
        val k = INT(DOUBLE(off) / 29.530588853)
        var nm = getNewMoonDay(k, timeZoneOffset)
        val sunLong = getSunLongitude(nm, timeZoneOffset)
        if (sunLong >= 9) {
            nm = getNewMoonDay(k - 1, timeZoneOffset)
        }
        return nm
    }

    internal fun getNewMoonDay(k: Int, timeZoneOffset: Int): Int = INT(newMoon(k) + 0.5 + DOUBLE(timeZoneOffset) / 24)

    internal fun getSunLongitude(jd: Int, timeZoneOffset: Int): Int = INT(sunLongitude(jd - 0.5 - timeZoneOffset / 24.0) / PI * 6)

    internal fun sunLongitude(jdn: Double): Double {
        val T = (jdn - 2451545.0) / 36525.0
        val T2 = T * T
        val dr = PI / 180
        val M = 357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2
        val L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2
        var DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * sin(dr * M)
        DL += (0.019993 - 0.000101 * T) * sin(dr * 2 * M) + 0.000290 * sin(dr * 3 * M)
        var L = L0 + DL
        L *= dr
        L -= PI * 2 * DOUBLE(INT(L / (PI * 2)))
        return L
    }

    internal fun getLeapMonthOffset(a11: Int, timeZoneOffset: Int): Int {
        val k = INT((DOUBLE(a11) - 2415021.076998695) / 29.530588853 + 0.5)
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

    fun lunar2solar(): SolarDate {
        val a11: Int
        val b11: Int
        if (lunarMonth < 11) {
            a11 = getLunarMonth11(lunarYear - 1, timeZoneOffset)
            b11 = getLunarMonth11(lunarYear, timeZoneOffset)
        } else {
            a11 = getLunarMonth11(lunarYear, timeZoneOffset)
            b11 = getLunarMonth11(lunarYear + 1, timeZoneOffset)
        }
        val k = INT(0.5 + (DOUBLE(a11) - 2415021.076998695) / 29.530588853)
        var off = if (lunarMonth - 11 < 0) lunarMonth - 11 + 12 else lunarMonth - 11
        if (b11 - a11 > 365) {
            val leapOff = getLeapMonthOffset(a11, timeZoneOffset)
            val leapMonth = if (leapOff - 2 < 0) leapOff - 2 + 12 else leapOff - 2
            if (lunarLeap && lunarMonth != leapMonth) return SolarDate(0, 0, 0)
            if (lunarLeap || off >= leapOff) off += 1
        }
        val monthStart = getNewMoonDay(k + off, timeZoneOffset)
        return jdToDate(monthStart + lunarDay - 1)
    }
}
