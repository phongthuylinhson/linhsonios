package com.thanhtam.linhsondich.model

class countViewModel {
    var view: Long = 0
    var uid: String =""
    constructor()
    constructor(view: Long, uid: String) {
        this.view = view
        this.uid = uid
    }
}
