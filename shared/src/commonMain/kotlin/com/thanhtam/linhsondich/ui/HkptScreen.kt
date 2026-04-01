package com.thanhtam.linhsondich.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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

// Màu đỏ chủ đạo của ứng dụng
val LS_RED = Color(0xFFB71C1C)
// Màu xanh nhấn mạnh cho kết quả quan trọng
val RESULT_BLUE = Color(0xFF1976D2)

@Composable
fun HkptScreen() {
    var selectedVan by remember { mutableStateOf(9) }
    var huongNha by remember { mutableStateOf("") }
    var huongCua by remember { mutableStateOf("") }
    val logic = remember { HuyenKhongLogic() }
    
    val vanTinh = remember(selectedVan) { logic.tinhVanTinh(selectedVan) }
    
    // Lấy thông tin bổ sung an toàn
    val extraInfo = remember(huongNha, selectedVan) {
        val degree = huongNha.toFloatOrNull() ?: -1f // Trả về -1 nếu chưa nhập
        if (degree >= 0) {
            logic.calculateExtraInfo(degree, selectedVan)
        } else {
            null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tinh Bàn Huyền Không",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = LS_RED
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Khu vực hiển thị Tinh Bàn
        TinhBanGrid(vanTinh)

        Spacer(modifier = Modifier.height(24.dp))

        // Bảng điều khiển nhập liệu
        Card(
            elevation = 4.dp,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Chọn vận:",
                        modifier = Modifier.width(100.dp),
                        fontWeight = FontWeight.Bold,
                        color = LS_RED
                    )
                    VanSelector(selectedVan) { selectedVan = it }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Hướng nhà:",
                        modifier = Modifier.width(100.dp),
                        fontWeight = FontWeight.Bold,
                        color = LS_RED
                    )
                    DegreeInput(huongNha) { huongNha = it }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Hướng cửa:",
                        modifier = Modifier.width(100.dp),
                        fontWeight = FontWeight.Bold,
                        color = LS_RED
                    )
                    DegreeInput(huongCua) { huongCua = it }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


    }
}

@Composable
fun TinhBanGrid(stars: IntArray) {
    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .border(2.dp, LS_RED)
    ) {
        for (row in 0..2) {
            Row(modifier = Modifier.weight(1f)) {
                for (col in 0..2) {
                    val index = row * 3 + col
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(0.5.dp, Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stars[index].toString(),
                            color = LS_RED,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VanSelector(current: Int, onSelect: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = LS_RED)
        ) {
            Text("Vận $current", color = Color.White)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            (1..9).forEach { v ->
                DropdownMenuItem(onClick = {
                    onSelect(v)
                    expanded = false
                }) {
                    Text("Vận $v")
                }
            }
        }
    }
}

@Composable
fun DegreeInput(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.height(50.dp).fillMaxWidth(),
        placeholder = { Text("Nhập độ") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = LS_RED
        )
    )
}
