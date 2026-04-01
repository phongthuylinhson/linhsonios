package com.thanhtam.linhsondich.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thanhtam.linhsondich.core.CTLucHao

@Composable
fun LucHaoScreen() {
    val ctLucHao = remember { CTLucHao() }
    
    // Trạng thái của 6 hào (true = Dương, false = Âm)
    val mangDuong = remember { mutableStateListOf(true, true, true, true, true, true) }
    // Trạng thái của 6 hào động
    val mangHaoDong = remember { mutableStateListOf(false, false, false, false, false, false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lập Quẻ Lục Hào",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = LS_RED
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Danh sách 6 hào từ trên xuống (Hào 6 đến Hào 1)
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            for (i in 5 downTo 0) {
                HaoItem(
                    index = i + 1,
                    isDuong = mangDuong[i],
                    isDong = mangHaoDong[i],
                    onHaoClick = { mangDuong[i] = !mangDuong[i] },
                    onDongChange = { mangHaoDong[i] = it }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* Xử lý lập quẻ */ },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = LS_RED),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("Lập Quẻ", color = Color.White, fontSize = 18.sp)
        }
    }
}

@Composable
fun HaoItem(
    index: Int,
    isDuong: Boolean,
    isDong: Boolean,
    onHaoClick: () -> Unit,
    onDongChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Hào $index", modifier = Modifier.width(60.dp), fontWeight = FontWeight.Bold)

        // Hình vẽ Hào
        Box(
            modifier = Modifier
                .weight(1f)
                .height(30.dp)
                .background(if (isDuong) LS_RED else Color.Transparent)
                .clickable { onHaoClick() },
            contentAlignment = Alignment.Center
        ) {
            if (!isDuong) {
                // Hào Âm (đứt khúc)
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.weight(0.45f).fillMaxHeight().background(LS_RED))
                    Spacer(modifier = Modifier.weight(0.1f))
                    Box(modifier = Modifier.weight(0.45f).fillMaxHeight().background(LS_RED))
                }
            }
            Text(if (isDuong) "Dương" else "Âm", color = Color.White, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Checkbox Hào động
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isDong,
                onCheckedChange = onDongChange,
                colors = CheckboxDefaults.colors(checkedColor = LS_RED)
            )
            Text("Động", fontSize = 12.sp)
        }
    }
}
