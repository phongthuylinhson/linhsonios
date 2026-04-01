package com.thanhtam.linhsondich.core

import com.thanhtam.linhsondich.model.CanChi
import com.thanhtam.linhsondich.model.CanChiNgayThang
import kotlin.math.PI
import kotlin.math.floor
import kotlin.math.sin

data class Solar2Lunar(var Ngay: Int, var Thang: Int, var Nam: Int, var Gio: Int, var Phut: Int) {
    private fun INT(d: Double): Long = floor(d).toLong()

    fun UniversalToJD(D: Int, M: Int, Y: Int): Double {
        return if (Y > 1582 || (Y == 1582 && M > 10) || (Y == 1582 && M == 10 && D > 14)) {
            367.0 * Y - floor(7.0 * (Y + floor((M + 9.0) / 12.0)) / 4.0) - floor(3.0 * (floor((Y + (M - 9.0) / 7.0) / 100.0) + 1.0) / 4.0) + floor(275.0 * M / 9.0) + D + 1721028.5
        } else {
            367.0 * Y - floor(7.0 * (Y + 5001.0 + floor((M - 9.0) / 7.0)) / 4.0) + floor(275.0 * M / 9.0) + D + 1729776.5
        }
    }

    fun LocalToJD(D: Int, M: Int, Y: Int): Double = UniversalToJD(D, M, Y) - 7.0 / 24.0

    fun Solar2Lunar(): IntArray {
        // Đây là bản rút gọn logic để chạy common
        // Trong thực tế bạn nên di chuyển toàn bộ logic tính toán chi tiết vào đây
        // Tránh dùng các thư viện Java/Android-only
        return intArrayOf(Ngay, Thang, Nam) 
    }

    fun getCanChiNgayThang(): CanChiNgayThang {
        val jd = INT(LocalToJD(Ngay, Thang, Nam) + 0.5)
        val canngay = ((jd + 9) % 10).toInt()
        val chingay = ((jd + 1) % 12).toInt()
        // Các tính toán khác tương tự...
        return CanChiNgayThang(canngay, chingay, 0, 0, 0, 0, Gio)
    }
}
