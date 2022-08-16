package org.devio.proj.jetpack.love

import android.provider.CalendarContract
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.PaintCompat
import org.devio.proj.jetpack.love.ui.theme.Gray
import org.devio.proj.jetpack.love.ui.theme.Orange

/**
 * @author : zg.gu
 * @date : 2022/8/16 11:21
 * description :
 */

@Composable
fun SearchBar() {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(56.dp)//search height
            .clip(RoundedCornerShape(28.dp))//圆角背景和圆角大小28
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically//内容居中
    ) {
        //记录文本状态 onValueChange在变化时 searchTex就会获取到用户输入"事件"
        // 从而使得在使用searchText地方的组件得意相应"状态"
        var searchText by remember { mutableStateOf("") }
        //note BasicTextField允许用户通过软键盘或者硬键盘编辑文字，但是没有提供提示或占位符等装饰
        //更多参考：https://juejin.cn/post/7079265116306276359
        BasicTextField(
            value = searchText,
            onValueChange = { inputText -> searchText = inputText },
            modifier = Modifier
                .padding(start = 24.dp)
                .weight(1f),
            textStyle = TextStyle(fontSize = 16.sp),
        ) { innerTextFiled ->//()->Unit  decorationBox内部负责编写输入框样式
            if (searchText.isEmpty()) {
                Text(text = "搜索", color = Gray, fontSize = 16.sp)
            }
            innerTextFiled()////自定义样式这行代码是关键，没有这一行输入文字后无法展示，光标也看不到
        }

        //note animateColorAsState 专门为颜色切换做动画
        val background by animateColorAsState(
            targetValue = if (searchText.isEmpty()) Orange else Color.White
        )
        Box(//类似FrameLayout
            Modifier
                .padding(6.dp)
                .fillMaxHeight()//撑满父布局高度
                .aspectRatio(1f)//背景比例1:1
                .clip(CircleShape)
                .background(background)/*Color(0xfffa9e51)*/
        ) {
            val tint by animateColorAsState(
                targetValue = if (searchText.isEmpty()) Color.White else Gray
            )
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "搜索",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),//Box有这个属性 所以没用Surface
                tint = tint
            )
        }
    }
}