package com.thanhtam.linhsondich.core

import com.thanhtam.linhsondich.model.chuoique
import com.thanhtam.linhsondich.model.haodong

data class CTNgauNhien(var cb1: Int,
                       var cb2: Int,
                       var cb3: Int,
                       var cb4: Int,
                       var cb5: Int,
                       var cb6: Int,
                       var h1: Int,
                       var h2: Int,
                       var h3: Int,
                       var h4: Int,
                       var h5: Int,
                       var h6: Int,
) {
    fun TinhHaoDong(): haodong {
        val haodongngaunhien: haodong =
            haodong(cb1, cb2, cb3, cb4, cb5, cb6)
        return haodongngaunhien
    }


    fun _ChuoiQueNgauNhien(): chuoique {
        val chuoiqueluchao = chuoique(
            h1,
            h2,
            h3,
            h4,
            h5,
            h6
        )
        return chuoiqueluchao
    }

    fun XuLyQueBienLucHao(hhao: Int, hd: Int): Int {
        var hao = hhao
        if (hd == 1) {
            when (hao) {
                0 -> hao = 1
                1 -> hao = 0
            }
        }
        return hao
    }

    fun _ChuoiQueBienNgauNhien(): chuoique {
        val haodong = TinhHaoDong()
        val chuoiquengaunhien = _ChuoiQueNgauNhien()
        var mangquebienngaunhien: chuoique = chuoique(0,0,0,0,0,0)
        mangquebienngaunhien.h1=XuLyQueBienLucHao(chuoiquengaunhien.h1,haodong.hd1)
        mangquebienngaunhien.h2=XuLyQueBienLucHao(chuoiquengaunhien.h2,haodong.hd2)
        mangquebienngaunhien.h3=XuLyQueBienLucHao(chuoiquengaunhien.h3,haodong.hd3)
        mangquebienngaunhien.h4=XuLyQueBienLucHao(chuoiquengaunhien.h4,haodong.hd4)
        mangquebienngaunhien.h5=XuLyQueBienLucHao(chuoiquengaunhien.h5,haodong.hd5)
        mangquebienngaunhien.h6=XuLyQueBienLucHao(chuoiquengaunhien.h6,haodong.hd6)
        return mangquebienngaunhien
    }
    fun _XuLyQueHoNgauNhien(): chuoique {

        val XuLyQueChinh = _ChuoiQueNgauNhien()
        val mangqueho: chuoique = chuoique(0,0,0,0,0,0)
        mangqueho.h1=XuLyQueChinh.h2
        mangqueho.h2=XuLyQueChinh.h3
        mangqueho.h3=XuLyQueChinh.h4
        mangqueho.h4=XuLyQueChinh.h3
        mangqueho.h5=XuLyQueChinh.h4
        mangqueho.h6=XuLyQueChinh.h5
        return mangqueho
    }
}