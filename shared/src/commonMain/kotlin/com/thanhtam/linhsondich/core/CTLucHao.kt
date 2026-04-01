package com.thanhtam.linhsondich.core

import com.thanhtam.linhsondich.model.chuoique
import com.thanhtam.linhsondich.model.haodong

class CTLucHao {

    fun tinhHaoDong(mangChecked: List<Boolean>): haodong {
        return haodong(
            if (mangChecked[0]) 1 else 0,
            if (mangChecked[1]) 1 else 0,
            if (mangChecked[2]) 1 else 0,
            if (mangChecked[3]) 1 else 0,
            if (mangChecked[4]) 1 else 0,
            if (mangChecked[5]) 1 else 0
        )
    }

    fun chuoiQueLucHao(mangDương: List<Boolean>): chuoique {
        return chuoique(
            if (mangDương[0]) 1 else 0,
            if (mangDương[1]) 1 else 0,
            if (mangDương[2]) 1 else 0,
            if (mangDương[3]) 1 else 0,
            if (mangDương[4]) 1 else 0,
            if (mangDương[5]) 1 else 0
        )
    }

    private fun xuLyQueBienLucHao(hhao: Int, hd: Int): Int {
        return if (hd == 1) {
            if (hhao == 0) 1 else 0
        } else {
            hhao
        }
    }

    fun chuoiQueBienLucHao(queChinh: chuoique, hd: haodong): chuoique {
        return chuoique(
            xuLyQueBienLucHao(queChinh.h1, hd.hd1),
            xuLyQueBienLucHao(queChinh.h2, hd.hd2),
            xuLyQueBienLucHao(queChinh.h3, hd.hd3),
            xuLyQueBienLucHao(queChinh.h4, hd.hd4),
            xuLyQueBienLucHao(queChinh.h5, hd.hd5),
            xuLyQueBienLucHao(queChinh.h6, hd.hd6)
        )
    }

    fun xuLyQueHoLucHao(queChinh: chuoique): chuoique {
        return chuoique(
            queChinh.h2,
            queChinh.h3,
            queChinh.h4,
            queChinh.h3,
            queChinh.h4,
            queChinh.h5
        )
    }
}
