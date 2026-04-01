package com.thanhtam.linhsondich.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun QueNgauNhienScreen() {
    var step by remember { mutableStateOf(0) } // 0: Sẵn sàng, 1: Đang lắc, 2: Kết quả hào 1...
    var currentHaoIdx by remember { mutableStateOf(0) }
    val ketQuaHao = remember { mutableStateListOf<Int>() } // Lưu 6 hào (0-3)
    val isShaking = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Gieo Quẻ Ngẫu Nhiên", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = LS_RED)
        
        Spacer(modifier = Modifier.height(20.dp))

        // Khu vực hiển thị 6 hào đã gieo (từ trên xuống: Hào 6 -> Hào 1)
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Bottom) {
            for (i in 5 downTo 0) {
                if (i < ketQuaHao.size) {
                    HaoDisplay(ketQuaHao[i])
                } else {
                    Box(modifier = Modifier.fillMaxWidth().height(30.dp).padding(vertical = 4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Khu vực 3 đồng xu
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(3) { Coin(isShaking.value) }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Nút bấm điều khiển
        Button(
            onClick = {
                if (ketQuaHao.size < 6) {
                    if (!isShaking.value) {
                        isShaking.value = true
                    } else {
                        isShaking.value = false
                        ketQuaHao.add(Random.nextInt(4))
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(55.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = LS_RED),
            shape = RoundedCornerShape(28.dp)
        ) {
            val buttonText = when {
                ketQuaHao.size == 6 -> "Xem Kết Quả"
                isShaking.value -> "Dừng"
                else -> "Gieo Hào ${ketQuaHao.size + 1}"
            }
            Text(buttonText, color = Color.White, fontSize = 18.sp)
        }

        TextButton(onClick = { ketQuaHao.clear() }) {
            Text("Làm mới", color = Color.Gray)
        }
    }
}

@Composable
fun Coin(shaking: Boolean) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (shaking) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation =射线(200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(Color(0xFFFFD700)) // Màu vàng đồng
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color(0xFFB8860B)))
        Text("LSD", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 10.sp)
    }
}

@Composable
fun HaoDisplay(type: Int) {
    // 0: Am, 1: Duong, 2: Am Dong, 3: Duong Dong
    Row(
        modifier = Modifier.fillMaxWidth().height(35.dp).padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        when (type) {
            0 -> HaoAm()
            1 -> HaoDuong()
            2 -> HaoAm(isDong = true)
            3 -> HaoDuong(isDong = true)
        }
    }
}

@Composable
fun HaoDuong(isDong: Boolean = false) {
    Box(
        modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight().background(LS_RED),
        contentAlignment = Alignment.Center
    ) {
        if (isDong) Text("○", color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun HaoAm(isDong: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight()) {
        Box(modifier = Modifier.weight(0.45f).fillMaxHeight().background(LS_RED))
        Spacer(modifier = Modifier.weight(0.1f))
        Box(modifier = Modifier.weight(0.45f).fillMaxHeight().background(LS_RED))
        if (isDong) {
            // Hiển thị dấu hiệu hào động
        }
    }
}
