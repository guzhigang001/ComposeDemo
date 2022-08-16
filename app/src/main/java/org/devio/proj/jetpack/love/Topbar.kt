package org.devio.proj.jetpack.love

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.devio.proj.jetpack.love.ui.theme.Gray
import org.devio.proj.jetpack.love.ui.theme.LightPink

/**
 * @author : zg.gu
 * @date : 2022/8/16 10:35
 * description :
 */
@Composable
fun TopBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(28.dp, 28.dp, 28.dp, 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(R.drawable.avatar), "头像",
            Modifier
                .clip(CircleShape)
                .size(64.dp)
        )
        Column(
            Modifier
                .padding(start = 14.dp)
                .weight(1f)
        ) {
            Text("欢迎回来！", fontSize = 14.sp, color = Gray)
            Spacer(modifier = Modifier.height(2.dp))
            Text("志刚", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        //组件 图片等组件添加背景都勇Surface
        Surface(Modifier.clip(CircleShape), color = LightPink) {
            Image(
                painterResource(R.drawable.ic_notification_new), "通知",
                Modifier
                    .padding(10.dp)
                    .size(32.dp)
            )
        }
    }
}