package com.thanhtam.linhsondich.core

import android.widget.Button
import android.widget.CheckBox
import com.thanhtam.linhsondich.model.chuoique
import com.thanhtam.linhsondich.model.haodong

data class CTLucHao(
    var cb1: CheckBox,
    var cb2: CheckBox,
    var cb3: CheckBox,
    var cb4: CheckBox,
    var cb5: CheckBox,
    var cb6: CheckBox,
    var bt1: Button,
    var bt2: Button,
    var bt3: Button,
    var bt4: Button,
    var bt5: Button,
    var bt6: Button,
) {
    var _haodong = arrayOf(0, 0, 0, 0, 0, 0)
    var manghaodong = arrayOf(cb1, cb2, cb3, cb4, cb5, cb6)
    fun TinhHaoDong(): haodong {
        for (i in 0..5) {
            if (manghaodong[i].isChecked) {
                _haodong[i] = 1
            }
        }
        val haodongluchao: haodong =
            haodong(_haodong[0], _haodong[1], _haodong[2], _haodong[3], _haodong[4], _haodong[5])
        return haodongluchao
    }


    fun _ChuoiQueLucHao(): chuoique {
        var mangbutton = arrayOf(bt1, bt2, bt3, bt4, bt5, bt6)
        var mangchuoiqueluchao = arrayOf(0, 0, 0, 0, 0, 0)
        for (i in 0..5) {
            if (mangbutton[i].text == "Dương") mangchuoiqueluchao[i] = 1
        }
        val chuoiqueluchao = chuoique(
            mangchuoiqueluchao[0],
            mangchuoiqueluchao[1],
            mangchuoiqueluchao[2],
            mangchuoiqueluchao[3],
            mangchuoiqueluchao[4],
            mangchuoiqueluchao[5]
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

    fun _ChuoiQueBienLucHao(): chuoique {
        val haodong = TinhHaoDong()
        val chuoiqueluchao = _ChuoiQueLucHao()
        var mangquebienluchao: chuoique = chuoique(0,0,0,0,0,0)
        mangquebienluchao.h1=XuLyQueBienLucHao(chuoiqueluchao.h1,haodong.hd1)
        mangquebienluchao.h2=XuLyQueBienLucHao(chuoiqueluchao.h2,haodong.hd2)
        mangquebienluchao.h3=XuLyQueBienLucHao(chuoiqueluchao.h3,haodong.hd3)
        mangquebienluchao.h4=XuLyQueBienLucHao(chuoiqueluchao.h4,haodong.hd4)
        mangquebienluchao.h5=XuLyQueBienLucHao(chuoiqueluchao.h5,haodong.hd5)
        mangquebienluchao.h6=XuLyQueBienLucHao(chuoiqueluchao.h6,haodong.hd6)
        return mangquebienluchao
    }
    fun _XuLyQueHoLucHao(): chuoique{
//        val qmh = arr1[0] + arr1[1] + arr1[2] + arr2[0] + arr2[1] + arr2[2]

        val XuLyQueChinh = _ChuoiQueLucHao()
        val mangqueho:chuoique= chuoique(0,0,0,0,0,0)
        mangqueho.h1=XuLyQueChinh.h2
        mangqueho.h2=XuLyQueChinh.h3
        mangqueho.h3=XuLyQueChinh.h4
        mangqueho.h4=XuLyQueChinh.h3
        mangqueho.h5=XuLyQueChinh.h4
        mangqueho.h6=XuLyQueChinh.h5
        return mangqueho
    }
}