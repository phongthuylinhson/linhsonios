package com.thanhtam.linhsondich.model

@Parcelize
data class CanChiNgayThang(
    val canngay: Int,
    val chingay: Int,
    val canthang: Int,
    val chithang: Int,
    val cannam: Int,
    val chinam: Int,
    val chigio: Int
) : Parcelable
