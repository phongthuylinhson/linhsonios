package com.thanhtam.linhsondich.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TrachCatScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Lịch Trạch Cát", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = LS_RED)

        Spacer(modifier = Modifier.height(20.dp))

        // Card hiển thị thông tin ngày
        Card(
            elevation = 4.dp,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFFFFF9C4) // Màu vàng nhẹ
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Thứ Hai, 20/05/2024 (Dương lịch)", fontWeight = FontWeight.Bold)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text("Ngày 13 tháng 4 năm Giáp Thìn (Âm lịch)", color = LS_RED)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Chi tiết Can Chi
        InfoRow("Giờ", "Giáp Thân")
        InfoRow("Ngày", "Giáp Tuất")
        InfoRow("Tháng", "Kỷ Tỵ")
        InfoRow("Năm", "Giáp Thìn")
        InfoRow("Tiết khí", "Lập Hạ")

        Spacer(modifier = Modifier.height(24.dp))

        Text("Giờ Hoàng Đạo", fontWeight = FontWeight.Bold, color = LS_RED)
        Text("Dần, Thìn, Tỵ, Thân, Dậu, Hợi", modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Bold)
    }
}
