package com.thanhtam.linhsondich.model

/**
 * Data class để lưu trữ 3 giá trị phi tinh trong một ô của Cửu Cung.
 * Sử dụng 'var' và cung cấp giá trị mặc định là chuỗi rỗng.
 */
data class MagicSquareCell(
    var vanTinh: String = "",
    var sonTinh: String = "",
    var huongTinh: String = ""
)
    