package com.thanhtam.linhsondich.core

/**
 * Logic tính toán Huyền Không Phi Tinh dùng chung cho Android và iOS
 */
class HuyenKhongLogic {

    // Thứ tự bay của 9 sao trong Lạc Thư: Trung -> Tây Bắc -> Tây -> Đông Bắc -> Nam -> Bắc -> Tây Nam -> Đông -> Đông Nam
    private val path = intArrayOf(4, 5, 6, 7, 8, 0, 1, 2, 3) 

    /**
     * Tính toán ma trận Tinh Bàn (9 ô)
     * @param van Vận hiện tại (1-9)
     * @param degree Độ số hướng nhà
     * @return Mảng 9 số đại diện cho các sao trong 9 cung
     */
    fun tinhVanTinh(van: Int): IntArray {
        val result = IntArray(9)
        for (i in 0..8) {
            // Công thức bay thuận: (sao_gốc + bước_nhảy - 1) % 9 + 1
            val step = i 
            val pos = path[i]
            var star = (van + step)
            while (star > 9) star -= 9
            result[pos] = star
        }
        return result
    }

    /**
     * Xác định Sơn của hướng dựa trên độ số (24 Sơn)
     * @param degree Độ số (0-359)
     * @return Chỉ số của Sơn (0-23)
     */
    fun xacDinhSon(degree: Float): Int {
        var d = degree % 360
        if (d < 0) d += 360
        // Mỗi sơn chiếm 15 độ. 0 độ là chính giữa Sơn Tý (Bắc)
        // Sơn Tý từ 352.5 đến 7.5
        val offset = (d + 7.5f) % 360
        return (offset / 15).toInt()
    }

    // Các logic phức tạp hơn về Sơn Tinh, Hướng Tinh và Bay Thuận/Nghịch 
    // sẽ được tiếp tục xây dựng dựa trên dữ liệu 24 Sơn.
}
