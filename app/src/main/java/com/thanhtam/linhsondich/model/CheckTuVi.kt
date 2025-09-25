package com.thanhtam.linhsondich.model

import android.widget.EditText
class Check{}
data class CheckTuVi(
    val edty: EditText,
    val edtnamxem: EditText
) {
    var Nam = Integer.parseInt(edty.text.toString())
    var NamXem = edtnamxem.text.toString().toInt()

    fun kiemtra(): Boolean {
        if (Nam < 1500 || Nam > 3000 || edty.text
                .toString() == "" || edty.text.length != 4
        ) {
            edty.error = "Bạn nhập sai năm sinh"
            return false
        } else if (NamXem!! < 1500 || NamXem!! > 3000 || edtnamxem.text
                .toString() == "" || edtnamxem.text?.length != 4
        ) {
            edtnamxem.error = "Bạn nhập sai năm xem"
            return false
        }
        return true
    }
}
data class CheckDich(
    val edty: EditText,
) {
    var Nam = Integer.parseInt(edty.text.toString())
    fun kiemtra(): Boolean {
        if (Nam < 1500 || Nam > 3000 || edty.text
                .toString() == "" || edty.text.length != 4
        ) {
            edty.error = "Bạn nhập sai năm"
            return false
        }
        return true
    }
}
data class CheckSoQT(
    val edt_so: EditText,
) {
    fun kiemtra(): Boolean {
        if (edt_so.text.length != 4 || edt_so.text.contains("0")
        ) {
            edt_so.error = "Bạn nhập sai Số"
            return false
        }
        return true
    }

}
data class CheckSoKM(
    val edt_so: EditText,
) {
    fun kiemtra(): Boolean {
        if (edt_so.text.length != 1 || edt_so.text.contains("0")
        ) {
            edt_so.error = "Bạn nhập sai Số"
            return false
        }
        return true
    }

}
