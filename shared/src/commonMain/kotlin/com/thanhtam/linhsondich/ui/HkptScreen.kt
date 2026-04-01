package com.thanhtam.linhsondich.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thanhtam.linhsondich.core.HuyenKhongLogic

@Composable
fun HkptScreen() {
    var selectedVan by remember { mutableStateOf(9) }
    var degreeText by remember { mutableStateOf("") }
    val logic = remember { HuyenKhongLogic() }
    
    val vanTinh = remember(selectedVan) { logic.tinhVanTinh(selectedVan) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tinh Bàn Huyền Không",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB71C1C) // Màu lsred
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Khu vực hiển thị Tinh Bàn (9 ô)
        TinhBanGrid(vanTinh)

        Spacer(modifier = Modifier.height(32.dp))

        // Khu vực nhập liệu
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Chọn vận: ", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            // Thay vì Spinner phức tạp, ta dùng một Slider hoặc đơn giản là một Text có thể click
            Slider(
                value = selectedVan.toFloat(),
                onValueChange = { selectedVan = it.toInt() },
                valueRange = 1f..9f,
                steps = 7,
                modifier = Modifier.weight(1f)
            )
            Text("Vận $selectedVan", modifier = Modifier.padding(start = 8.dp))
        }

        OutlinedTextField(
            value = degreeText,
            onValueChange = { degreeText = it },
            label = { Text("Hướng nhà (Độ)") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun TinhBanGrid(stars: IntArray) {
    Column {
        for (row in 0..2) {
            Row {
                for (col in 0..2) {
                    val index = row * 3 + col
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(2.dp)
                            .background(Color(0xFFB71C1C)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stars[index].toString(),
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
