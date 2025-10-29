package edu.farmingdale.pizzapartybottomnavbar

import android.R
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.sync.Mutex


@Composable
fun Screen3() {
    var sliderValue by remember { mutableStateOf(0.5f) }
    var chkd by remember { mutableStateOf(true) }
    //create gradient pattern
    var gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE3E5EC),
            Color(0xFF87CCE8),
            Color(0xFF3F76A1)
        )
    )


    val context = LocalContext.current
    // ToDo 3: Make the UI look better by adding a gradient background (vertical) and padding
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //ToDo 2: the slider should be able to change the text value of the screen
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            Modifier.fillMaxWidth(),
            enabled = chkd
        )

        Text(
            text = "Slide me to check my value: ${String.format("%.2f", sliderValue)}!",
            fontSize = (sliderValue * 40).sp,  // font size from 0sp to 40sp
            style = TextStyle(textAlign = TextAlign.Center),
            color = Color.White
        )

        Button(onClick = {
            val newInt = Intent(Intent.ACTION_VIEW)
            newInt.setData(Uri.parse("tel:6314202000"))
            context.startActivity(newInt)
        }) {
            Text(fontSize = 20.sp, text = "Call me")
        }

        Checkbox(
            checked = chkd,
            onCheckedChange = { chkd = it },
            modifier = Modifier.padding(10.dp)
        )

    }

}


