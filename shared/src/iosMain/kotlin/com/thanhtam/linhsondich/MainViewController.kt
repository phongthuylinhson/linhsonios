package com.thanhtam.linhsondich

import androidx.compose.runtime.*
import androidx.compose.ui.window.ComposeUIViewController
import com.thanhtam.linhsondich.ui.*

fun MainViewController() = ComposeUIViewController {
    var currentRoute by remember { mutableStateOf("main") }

    // Logic điều hướng đơn giản
    when (currentRoute) {
        "main" -> MainScreen { route -> currentRoute = route }
        "luc_hao" -> LucHaoScreen()
        "huyen_khong" -> HkptScreen()
        "trach_cat" -> TrachCatScreen()
        "ngau_nhien" -> QueNgauNhienScreen()
        // "mai_hoa" -> MaiHoaScreen() 
        else -> MainScreen { currentRoute = it }
    }
}
