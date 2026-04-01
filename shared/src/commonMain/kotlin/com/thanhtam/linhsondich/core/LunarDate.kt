package com.thanhtam.linhsondich.core

data class LunarDate(val year: Int, val month: Int, val day: Int, val leap: Boolean) {
    override operator fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LunarDate) return false
        return year == other.year && month == other.month && day == other.day && leap == other.leap
    }

    override fun hashCode(): Int {
        var result = year
        result = 31 * result + month
        result = 31 * result + day
        result = 31 * result + if (leap) 1 else 0
        return result
    }
}
