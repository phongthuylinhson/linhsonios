package com.thanhtam.linhsondich.model

import android.content.Context
import android.widget.Button
import android.widget.PopupMenu
import com.thanhtam.linhsondich.R

data class setThang(
    var edtt: Button,
    var context: Context,
) {
    fun menu_thang() {
        val menu = PopupMenu(context, edtt)
        menu.menuInflater.inflate(R.menu.menu_thang, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.thang1 -> edtt.text = "1"
                R.id.thang2 -> edtt.text = "2"
                R.id.thang3 -> edtt.text = "3"
                R.id.thang4 -> edtt.text = "4"
                R.id.thang5 -> edtt.text = "5"
                R.id.thang6 -> edtt.text = "6"
                R.id.thang7 -> edtt.text = "7"
                R.id.thang8 -> edtt.text = "8"
                R.id.thang9 -> edtt.text = "9"
                R.id.thang10 -> edtt.text = "10"
                R.id.thang11 -> edtt.text = "11"
                R.id.thang12 -> edtt.text = "12"
            }
            false
        }
        menu.show()
    }
}

data class setNgay(
    var edtd: Button,
    var context: Context,
) {
    fun menu_ngay() {
        val menu = PopupMenu(context, edtd)
        menu.menuInflater.inflate(R.menu.menu_ngay, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.ngay1 -> edtd.text = "1"
                R.id.ngay2 -> edtd.text = "2"
                R.id.ngay3 -> edtd.text = "3"
                R.id.ngay4 -> edtd.text = "4"
                R.id.ngay5 -> edtd.text = "5"
                R.id.ngay6 -> edtd.text = "6"
                R.id.ngay7 -> edtd.text = "7"
                R.id.ngay8 -> edtd.text = "8"
                R.id.ngay9 -> edtd.text = "9"
                R.id.ngay10 -> edtd.text = "10"
                R.id.ngay11 -> edtd.text = "11"
                R.id.ngay12 -> edtd.text = "12"
                R.id.ngay13 -> edtd.text = "13"
                R.id.ngay14 -> edtd.text = "14"
                R.id.ngay15 -> edtd.text = "15"
                R.id.ngay16 -> edtd.text = "16"
                R.id.ngay17 -> edtd.text = "17"
                R.id.ngay18 -> edtd.text = "18"
                R.id.ngay19 -> edtd.text = "19"
                R.id.ngay20 -> edtd.text = "20"
                R.id.ngay21 -> edtd.text = "21"
                R.id.ngay22 -> edtd.text = "22"
                R.id.ngay23 -> edtd.text = "23"
                R.id.ngay24 -> edtd.text = "24"
                R.id.ngay25 -> edtd.text = "25"
                R.id.ngay26 -> edtd.text = "26"
                R.id.ngay27 -> edtd.text = "27"
                R.id.ngay28 -> edtd.text = "28"
                R.id.ngay29 -> edtd.text = "29"
                R.id.ngay30 -> edtd.text = "30"
                R.id.ngay31 -> edtd.text = "31"
            }
            false
        }
        menu.show()
    }
}

data class setGio(
    var edtg: Button,
    var context: Context,
) {
    fun menu_gio() {
        val menu = PopupMenu(context, edtg)
        menu.menuInflater.inflate(R.menu.menu_gio, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.gio0 -> edtg.text = "0"
                R.id.gio1 -> edtg.text = "1"
                R.id.gio2 -> edtg.text = "2"
                R.id.gio3 -> edtg.text = "3"
                R.id.gio4 -> edtg.text = "4"
                R.id.gio5 -> edtg.text = "5"
                R.id.gio6 -> edtg.text = "6"
                R.id.gio7 -> edtg.text = "7"
                R.id.gio8 -> edtg.text = "8"
                R.id.gio9 -> edtg.text = "9"
                R.id.gio10 -> edtg.text = "10"
                R.id.gio11 -> edtg.text = "11"
                R.id.gio12 -> edtg.text = "12"
                R.id.gio13 -> edtg.text = "13"
                R.id.gio14 -> edtg.text = "14"
                R.id.gio15 -> edtg.text = "15"
                R.id.gio16 -> edtg.text = "16"
                R.id.gio17 -> edtg.text = "17"
                R.id.gio18 -> edtg.text = "18"
                R.id.gio19 -> edtg.text = "19"
                R.id.gio20 -> edtg.text = "20"
                R.id.gio21 -> edtg.text = "21"
                R.id.gio22 -> edtg.text = "22"
                R.id.gio23 -> edtg.text = "23"
            }
            false
        }
        menu.show()
    }
}

data class setPhut(
    var edtp: Button,
    var context: Context,
) {
    fun menu_phut() {
        val menu = PopupMenu(context, edtp)
        menu.menuInflater.inflate(R.menu.menu_phut, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.phut0 -> edtp.text = "0"
                R.id.phut1 -> edtp.text = "1"
                R.id.phut2 -> edtp.text = "2"
                R.id.phut3 -> edtp.text = "3"
                R.id.phut4 -> edtp.text = "4"
                R.id.phut5 -> edtp.text = "5"
                R.id.phut6 -> edtp.text = "6"
                R.id.phut7 -> edtp.text = "7"
                R.id.phut8 -> edtp.text = "8"
                R.id.phut9 -> edtp.text = "9"
                R.id.phut10 -> edtp.text = "10"
                R.id.phut11 -> edtp.text = "11"
                R.id.phut12 -> edtp.text = "12"
                R.id.phut13 -> edtp.text = "13"
                R.id.phut14 -> edtp.text = "14"
                R.id.phut15 -> edtp.text = "15"
                R.id.phut16 -> edtp.text = "16"
                R.id.phut17 -> edtp.text = "17"
                R.id.phut18 -> edtp.text = "18"
                R.id.phut19 -> edtp.text = "19"
                R.id.phut20 -> edtp.text = "20"
                R.id.phut21 -> edtp.text = "21"
                R.id.phut22 -> edtp.text = "22"
                R.id.phut23 -> edtp.text = "23"
                R.id.phut24 -> edtp.text = "24"
                R.id.phut25 -> edtp.text = "25"
                R.id.phut26 -> edtp.text = "26"
                R.id.phut27 -> edtp.text = "27"
                R.id.phut28 -> edtp.text = "28"
                R.id.phut29 -> edtp.text = "29"
                R.id.phut30 -> edtp.text = "30"
                R.id.phut31 -> edtp.text = "31"
                R.id.phut32 -> edtp.text = "32"
                R.id.phut33 -> edtp.text = "33"
                R.id.phut34 -> edtp.text = "34"
                R.id.phut35 -> edtp.text = "35"
                R.id.phut36 -> edtp.text = "36"
                R.id.phut37 -> edtp.text = "37"
                R.id.phut38 -> edtp.text = "38"
                R.id.phut39 -> edtp.text = "39"
                R.id.phut40 -> edtp.text = "40"
                R.id.phut41 -> edtp.text = "41"
                R.id.phut42 -> edtp.text = "42"
                R.id.phut43 -> edtp.text = "43"
                R.id.phut44 -> edtp.text = "44"
                R.id.phut45 -> edtp.text = "45"
                R.id.phut46 -> edtp.text = "46"
                R.id.phut47 -> edtp.text = "47"
                R.id.phut48 -> edtp.text = "48"
                R.id.phut49 -> edtp.text = "49"
                R.id.phut50 -> edtp.text = "50"
                R.id.phut51 -> edtp.text = "51"
                R.id.phut52 -> edtp.text = "52"
                R.id.phut53 -> edtp.text = "53"
                R.id.phut54 -> edtp.text = "54"
                R.id.phut55 -> edtp.text = "55"
                R.id.phut56 -> edtp.text = "56"
                R.id.phut57 -> edtp.text = "57"
                R.id.phut58 -> edtp.text = "58"
                R.id.phut59 -> edtp.text = "59"
            }
            false
        }
        menu.show()
    }
}
