package org.devio.proj.jetpack.love

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.devio.proj.jetpack.love.ui.theme.Gray
import org.devio.proj.jetpack.love.ui.theme.Orange
import java.util.*

/**
 * @author : zg.gu
 * @date : 2022/8/16 15:37
 * description :
 */
@Composable
fun CategoryBar() {
    val tabs = listOf<String>("动漫", "科幻", "爱情", "纪录片", "灾难", "动作", "剧情", "恐怖", "战争")
    var selected by remember { mutableStateOf(0) }
    LazyRow(
        modifier = Modifier.padding(0.dp, 8.dp),//外层边距
        contentPadding = PaddingValues(12.dp, 0.dp)//tab之间的间距
    ) {

        itemsIndexed(tabs) { index, name ->
            Column(//item view
                modifier = Modifier
                    .padding(12.dp, 4.dp)
                    .width(IntrinsicSize.Max)
                    .height(24.dp)
                    .clickable { selected = index }
            ) {
                Text(text = name, color = if (index == selected) Orange else Gray, fontSize = 16.sp)
                if (index == selected) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .clip(
                                RoundedCornerShape(1.dp)
                            )
                            .background(Orange)
                    )
                }

            }
        }

    }
}