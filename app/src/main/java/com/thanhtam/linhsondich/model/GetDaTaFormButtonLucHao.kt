package com.thanhtam.linhsondich.model


import android.widget.Button

class GetDaTaFormButtonLucHao(
    h1: Button,
    h2: Button,
    h3: Button,
    h4: Button,
    h5: Button,
    h6: Button,
) {

    fun haoluchao(h: Button) {
        h.setOnClickListener {
            if (h.text.toString()=="Dương") h.text="Âm"
            else h.text="Dương"
        }
    }

    init {
        haoluchao(h1)
        haoluchao(h2)
        haoluchao(h3)
        haoluchao(h4)
        haoluchao(h5)
        haoluchao(h6)
    }
}