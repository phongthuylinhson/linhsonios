package com.thanhtam.linhsondich.model

/**
 * Data class này chứa thông tin chi tiết của từng Sơn vị (từng phần nhỏ trong 24 sơn).     * Nó kế thừa thông tin từ SonVi và bổ sung các thuộc tính riêng cho việc tính toán.
 */
data class SonViChiTiet(
    val name: String,
    val startDeg: Float,
    val endDeg: Float,
    val phiTinh: Int,       // Cung Bát Quái chứa nó (1-9)
    val amDuong: String,    // Âm hay Dương để tính phi tinh Thuận/Nghịch
    val nguyenLong: String, // Nguyên Long (Thiên, Địa, Nhân)
    val isChinhHuong: Boolean, // Là chính hướng hay kiêm hướng?
    val theQuai: Int?       // Sao Thế Quái (có thể null)
)
    