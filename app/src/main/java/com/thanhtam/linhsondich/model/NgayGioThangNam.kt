package com.thanhtam.linhsondich.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NgayGioThangNam(
    var ngay: Int,
    var gio: Int,
    var thang: Int,
    var nam: Int,
    var phut: Int,
) : Parcelable