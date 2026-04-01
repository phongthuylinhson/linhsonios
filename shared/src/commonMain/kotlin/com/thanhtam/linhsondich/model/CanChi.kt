package com.thanhtam.linhsondich.model

class CanChi {
    var can = arrayOf("Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ", "Canh", "Tân", "Nhâm", "Quý")
    var chi = arrayOf("Tí", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi")
    var chithang = arrayOf("Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi", "Tí", "Sửu")
    var quai = arrayOf("Càn", "Đoài", "Khảm", "Ly", "Chấn", "Tốn", "Cấn", "Khôn")
    var tietkhi = arrayOf(
        "Xuân phân", "Thanh minh", "Cốc vũ", "Lập hạ", "Tiểu mãn", "Mang chủng",
        "Hạ chí", "Tiểu thử", "Đại thử", "Lập thu", "Xử thử", "Bạch lộ",
        "Thu phân", "Hàn lộ", "Sương giáng", "Lập đông", "Tiểu tuyết", "Đại tuyết",
        "Đông chí", "Tiểu hàn", "Đại hàn", "Lập xuân", "Vũ thủy", "Kinh trập"
    )
    var itietkhi = arrayOf(0, 15, 30, 45, 60, 75, 90, 105, 120, 135, 150, 165, 180, 195, 210, 225, 240, 255, 270, 285, 300, 315, 330, 345)
    
    val _canchi = arrayOf(
        "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ", "Canh", "Tân", "Nhâm", "Quý",
        "Tí", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi"
    )
    val canchimoc = arrayOf("giáp", "ất", "dần", "mão")
    val canchikim = arrayOf("canh", "tân", "thân", "dậu")
    val canchihoa = arrayOf("bính", "đinh", "tỵ", "ngọ")
    val canchitho = arrayOf("mậu", "kỷ", "thìn", "tuất", "sửu", "mùi")
    val canchithuy = arrayOf("nhâm", "quý", "hợi", "tí")

    val canchinguhanh = arrayOf(canchikim, canchimoc, canchithuy, canchihoa, canchitho)
    val quansat = arrayOf("Quan", "Sát")
    val thucthuong = arrayOf("Thực", "Thương")
    val tykiep = arrayOf("Tỷ", "Kiếp")
    val ankieu = arrayOf("Ấn", "Kiêu")
    val taithien = arrayOf("Tài", "T.Tài")
    val thapthan = arrayOf(quansat, thucthuong, tykiep, ankieu, taithien)
}
