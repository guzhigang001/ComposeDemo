package org.devio.proj.jetpack.love

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.devio.proj.jetpack.love.ui.theme.BlackDark
import org.devio.proj.jetpack.love.ui.theme.Gray

/**
 * @author : zg.gu
 * @date : 2022/8/17 11:01
 * description :
 */
@Composable
fun PlaceArea() {
    val hotMovie = HotMovie("龙珠", "超级赛亚人大战宇宙之主", "热血/动作", R.drawable.longzhu)
    Column(Modifier.padding(24.dp, 24.dp, 24.dp, 0.dp)) {
        Text(
            "当前最热", fontSize = 16.sp, fontWeight = FontWeight.Bold
        )
        Surface(
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(8.dp),
        ) {
            Row(Modifier.height(IntrinsicSize.Max)) {
                Image(
                    painterResource(hotMovie.imageId),
                    "image",
                    Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .size(80.dp),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                Column(
                    Modifier
                        .padding(12.dp, 0.dp)
                        .fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(hotMovie.name, fontSize = 16.sp,fontWeight = FontWeight.Bold)
                    Text(hotMovie.desc, fontSize = 14.sp, color = BlackDark)
                    Text(hotMovie.type, fontSize = 14.sp, color = Gray)
                }
            }
        }
    }
}

data class HotMovie(
    val name: String,
    val desc: String,
    val type: String,
    @DrawableRes val imageId: Int
)