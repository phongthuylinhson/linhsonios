package com.thanhtam.linhsondich.core

data class MountainInfo(
    val name: String,
    val palace: Int, // Lo Shu number: 1=Kham, 2=Khon, 3=Chan, 4=Ton, 6=Can, 7=Doai, 8=Can, 9=Ly
    val type: String, // "Địa", "Thiên", "Nhân"
    val amDuong: String // "Dương", "Âm"
)

data class HkExtraInfo(
    val khongVi: String,
    val khongViColor: String,
    val khongViFontSize: Int,
    val thanhMonChinh: String,
    val thanhMonChinhColor: String,
    val thanhMonChinhFontSize: Int,
    val thanhMonPhu: String
)

class HuyenKhongLogic {

    // Danh sách 24 Sơn vị chi tiết
    val mountains = listOf(
        MountainInfo("Nhâm", 1, "Địa", "Dương"),
        MountainInfo("Tý", 1, "Thiên", "Âm"),
        MountainInfo("Quý", 1, "Nhân", "Âm"),
        MountainInfo("Sửu", 8, "Địa", "Âm"),
        MountainInfo("Cấn", 8, "Thiên", "Dương"),
        MountainInfo("Dần", 8, "Nhân", "Dương"),
        MountainInfo("Giáp", 3, "Địa", "Dương"),
        MountainInfo("Mão", 3, "Thiên", "Âm"),
        MountainInfo("Ất", 3, "Nhân", "Âm"),
        MountainInfo("Thìn", 4, "Địa", "Âm"),
        MountainInfo("Tốn", 4, "Thiên", "Dương"),
        MountainInfo("Tỵ", 4, "Nhân", "Dương"),
        MountainInfo("Bính", 9, "Địa", "Dương"),
        MountainInfo("Ngọ", 9, "Thiên", "Âm"),
        MountainInfo("Đinh", 9, "Nhân", "Âm"),
        MountainInfo("Mùi", 2, "Địa", "Âm"),
        MountainInfo("Khôn", 2, "Thiên", "Dương"),
        MountainInfo("Thân", 2, "Nhân", "Dương"),
        MountainInfo("Canh", 7, "Địa", "Dương"),
        MountainInfo("Dậu", 7, "Thiên", "Âm"),
        MountainInfo("Tân", 7, "Nhân", "Âm"),
        MountainInfo("Tuất", 6, "Địa", "Âm"),
        MountainInfo("Càn", 6, "Thiên", "Dương"),
        MountainInfo("Hợi", 6, "Nhân", "Dương")
    )

    private val palaceNames = mapOf(
        1 to "Khảm", 2 to "Khôn", 3 to "Chấn", 4 to "Tốn",
        6 to "Càn", 7 to "Đoài", 8 to "Cấn", 9 to "Ly"
    )

    // Cặp Thành Môn tiềm năng cho mỗi Cung Hướng
    private val potentialGates = mapOf(
        1 to listOf(6, 8), // Hướng Khảm: Càn, Cấn
        9 to listOf(4, 2), // Hướng Ly: Tốn, Khôn
        3 to listOf(4, 8), // Hướng Chấn: Tốn, Cấn
        7 to listOf(2, 6), // Hướng Đoài: Khôn, Càn
        8 to listOf(3, 1), // Hướng Cấn: Chấn, Khảm
        4 to listOf(9, 3), // Hướng Tốn: Ly, Chấn
        2 to listOf(7, 9), // Hướng Khôn: Đoài, Ly
        6 to listOf(1, 7)  // Hướng Càn: Khảm, Đoài
    )

    private val luongThienXichPath = listOf(5, 6, 7, 8, 9, 1, 2, 3, 4)

    fun getMountainByDegree(degree: Float): MountainInfo {
        var d = degree % 360
        if (d < 0) d += 360
        val index = (((d - 337.5f + 360f) % 360f) / 15f).toInt()
        return mountains[index % 24]
    }

    private fun phiTinh(trungCungStar: Int, isThuan: Boolean): Map<Int, Int> {
        val result = mutableMapOf<Int, Int>()
        var currentStar = trungCungStar
        result[5] = currentStar

        for (i in 1 until luongThienXichPath.size) {
            val palace = luongThienXichPath[i]
            if (isThuan) {
                currentStar = if (currentStar == 9) 1 else currentStar + 1
            } else {
                currentStar = if (currentStar == 1) 9 else currentStar - 1
            }
            result[palace] = currentStar
        }
        return result
    }

    fun calculateThanhMon(degree: Float, van: Int): Pair<String, String> {
        val huongMnt = getMountainByDegree(degree)
        val huongPalace = huongMnt.palace
        
        val gates = potentialGates[huongPalace] ?: return Pair("Không", "-")
        val vanBan = phiTinh(van, true)
        
        val validGateNames = mutableListOf<String>()
        for (gate in gates) {
            val vTinhAtGate = vanBan[gate] ?: 0
            if (checkThanhMon(van, huongMnt, gate, vTinhAtGate) != null) {
                val name = palaceNames[gate]
                if (name != null) validGateNames.add(name)
            }
        }
        
        val chinhResult = if (validGateNames.isEmpty()) "Không" else validGateNames.joinToString(" - ")
        return Pair(chinhResult, "-")
    }

    private fun checkThanhMon(nhaVan: Int, huongMnt: MountainInfo, targetPalace: Int, targetVanTinh: Int): Int? {
        if (targetVanTinh == 0) return null
        val originalPalace = if (targetVanTinh == 5) targetPalace else targetVanTinh
        val flightMnt = mountains.find { it.palace == originalPalace && it.type == huongMnt.type } ?: return null
        val isThuan = flightMnt.amDuong == "Dương"
        val tinhBanTam = phiTinh(targetVanTinh, isThuan)
        
        val starAtTargetPalace = tinhBanTam[targetPalace]
        return if (starAtTargetPalace == nhaVan) targetPalace else null
    }

    fun getKhongVi(degree: Float): String {
        var d = degree % 360
        if (d < 0) d += 360

        return when {
            d >= 4.0f && d <= 7.5f -> "Hợi – Nhâm"
            d >= 7.6f && d <= 11.9f -> "Nhâm – Tý"
            d >= 19.0f && d <= 22.5f -> "Nhâm Tý"
            d >= 22.6f && d <= 26.9f -> "Tý - Quý"
            d >= 34.0f && d <= 37.5f -> "Tý - Quý"
            d >= 37.6f && d <= 41.9f -> "Dần - Giáp"
            d >= 49.0f && d <= 52.5f -> "Dần - Giáp"
            d >= 52.6f && d <= 56.9f -> "Giáp - Mão"
            d >= 64.0f && d <= 67.5f -> "Giáp - Mão"
            d >= 67.6f && d <= 71.9f -> "Mão - Ất"
            d >= 79.0f && d <= 82.5f -> "Mão - Ất"
            d >= 82.6f && d <= 86.9f -> "Dần - Giáp"
            d >= 94.0f && d <= 97.5f -> "Dần - Giáp"
            d >= 97.6f && d <= 101.9f -> "Mão - Giáp"
            d >= 109.0f && d <= 112.5f -> "Mão – Giáp"
            d >= 112.6f && d <= 116.9f -> "Mão - Ất"
            d >= 124.0f && d <= 127.5f -> "Mão - Ất"
            d >= 127.6f && d <= 131.9f -> "Tỵ - Bính"
            d >= 139.0f && d <= 142.5f -> "Tỵ Bính"
            d >= 142.6f && d <= 146.9f -> "Ngọ - Bính"
            d >= 154.0f && d <= 157.5f -> "Ngọ - Bính"
            d >= 157.6f && d <= 161.9f -> "Ngọ - Đinh"
            d >= 169.0f && d <= 172.5f -> "Ngọ - Đinh"
            d >= 172.6f && d <= 176.9f -> "Tỵ - Bính"
            d >= 184.0f && d <= 187.5f -> "Tỵ - Bính"
            d >= 187.6f && d <= 191.9f -> "Bính – Ngọ"
            d >= 199.0f && d <= 202.5f -> "Bính – Ngọ"
            d >= 202.6f && d <= 206.9f -> "Ngọ - Đinh"
            d >= 214.0f && d <= 217.5f -> "Ngọ - Đinh"
            d >= 217.6f && d <= 221.9f -> "Đinh – Mùi"
            d >= 229.0f && d <= 232.5f -> "Đinh – Mùi"
            d >= 232.6f && d <= 236.9f -> "Canh – Dậu"
            d >= 244.0f && d <= 247.5f -> "Canh – Dậu"
            d >= 247.6f && d <= 251.9f -> "Dậu – Tân"
            d >= 259.0f && d <= 262.5f -> "Dậu – Tân"
            d >= 262.6f && d <= 266.9f -> "Thân – Canh"
            d >= 274.0f && d <= 277.5f -> "Thân – Canh"
            d >= 277.6f && d <= 281.9f -> "Canh – Dậu"
            d >= 289.0f && d <= 292.5f -> "Canh – Dậu"
            d >= 292.6f && d <= 296.9f -> "Tân – Dậu"
            d >= 304.0f && d <= 307.5f -> "Tân – Dậu"
            d >= 307.6f && d <= 311.9f -> "Nhâm – Hợi"
            d >= 319.0f && d <= 322.5f -> "Nhâm – Hợi"
            d >= 322.6f && d <= 326.9f -> "Nhâm – Tý"
            d >= 334.0f && d <= 337.5f -> "Nhâm – Tý"
            d >= 337.6f && d <= 341.9f -> "Tý – Quý"
            d >= 349.0f && d <= 352.5f -> "Tý – Quý"
            d >= 352.6f && d <= 356.9f -> "Hợi – Nhâm"
            else -> "Không"
        }
    }

    fun calculateExtraInfo(degree: Float, van: Int): HkExtraInfo {
        val khongVi = getKhongVi(degree)
        val (tmChinh, tmPhu) = calculateThanhMon(degree, van)
        
        // Thiết lập màu sắc và cỡ chữ mẫu
        val kvColor = if (khongVi == "Không") "#757575" else "#1976D2"
        val tmColor = if (tmChinh == "Không") "#757575" else "#B71C1C"

        return HkExtraInfo(
            khongVi = khongVi,
            khongViColor = kvColor,
            khongViFontSize = 22,
            thanhMonChinh = tmChinh,
            thanhMonChinhColor = tmColor,
            thanhMonChinhFontSize = 18,
            thanhMonPhu = tmPhu
        )
    }

    fun tinhVanTinh(van: Int): IntArray {
        val result = IntArray(9)
        val base = intArrayOf(4, 9, 2, 3, 5, 7, 8, 1, 6)
        val offset = van - 5
        for (i in 0..8) {
            var v = base[i] + offset
            while (v > 9) v -= 9
            while (v < 1) v += 9
            result[i] = v
        }
        return result
    }
}
