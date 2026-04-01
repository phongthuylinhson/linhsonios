package com.thanhtam.linhsondich.model

data class MaiHoaModel(
    var thuongQuai: Int,
    var haQuai: Int
)

enum class MaiHoaType {
    THUONG, SO, TEN
}
