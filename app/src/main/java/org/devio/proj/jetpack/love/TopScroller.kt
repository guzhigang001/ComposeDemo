package org.devio.proj.jetpack.love

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.devio.proj.jetpack.love.ui.theme.BlackLight
import org.devio.proj.jetpack.love.ui.theme.Gray
import org.devio.proj.jetpack.love.ui.theme.Orange

/**
 * @author : zg.gu
 * @date : 2022/8/16 17:51
 * description : 动画的核心原理：
 *
 * 给详情页设置四种状态：未打开、已打开、打开中以及关闭中
 * 通过状态来渐变式操作详情页中的各个属性的值
 * 比如每个组件的尺寸和偏移，从而达到详情页的显示和关闭动作的动画形式的呈现
 */


@Composable
fun TopLovesArea(
    onCardSizedChanged: (IntSize) -> Unit,
    onCardClicked: (love: Love, offset: IntOffset) -> Unit
) {
    Column {
        Row(
            Modifier
                .padding(24.dp, 12.dp)
                .fillMaxWidth()
        ) {
            Text("Top 100", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))
            Text("查看全部", fontSize = 15.sp, color = Gray)
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(24.dp),/*item之间的间隔 */
            contentPadding = PaddingValues(24.dp, 0.dp)/*LazyRow左右间隔 */
        ) {

            itemsIndexed(loves) { index, love ->
                var intOffset: IntOffset? by remember { mutableStateOf(null) }
                Button(/*note button 默认有阴影卡片效果(MaterialTheme) elevation: ButtonElevation? = ButtonDefaults.elevation() */
                    onClick = { onCardClicked(love, intOffset!!) },
                    Modifier
                        .width(220.dp)
                        /*hint 动画跳转*/
                        .onSizeChanged { if (index == 0) onCardSizedChanged(it) }
                        .onGloballyPositioned {
                            /*note 定位到点击的item并计算x,y 坐标 推算出动画的轨迹*/
                            val offset = it.localToRoot(Offset(0f, 0f))
                            intOffset = IntOffset(offset.x.toInt(), offset.y.toInt())
                        },
                    shape = RoundedCornerShape(16.dp),
                    /*两头加上空白 contentPadding 和 padding() 的区别是，在滑动的时候内容不会在两头被切掉，而是会触达到滑动区域的边缘 */
                    contentPadding = PaddingValues(6.dp),
                    colors = ButtonDefaults.outlinedButtonColors(Color.White),
                    elevation = ButtonDefaults.elevation(defaultElevation = 0.5.dp)
                ) {
                    Column {
                        Image(
                            painterResource(love.imageId), "image",
                            Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .fillMaxWidth()
                                .aspectRatio(1.35f)/*note 可以避免图片尺寸不同而导致item被拉伸*/,
                            contentScale = ContentScale.Crop,//居中剪切
                            alignment = Alignment.Center
                        )

                        Row(
                            Modifier.padding(8.dp, 12.dp, 8.dp, 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(love.name, color = Color.Black, fontSize = 15.sp)
                                Spacer(Modifier.height(4.dp))
                                Text(love.category, color = Gray, fontSize = 14.sp)
                            }
                            Spacer(Modifier.weight(1f))
                            Row(
                                Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(BlackLight)
                                    .padding(6.dp, 11.dp, 8.dp, 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_star), "", Modifier.size(24.dp),
                                    tint = Orange
                                )
                                Text(love.score.toString(), color = Orange, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
    }

}

data class Love(
    val name: String,
    val category: String,
    val score: Float,
    val scoreText: String,
    @DrawableRes val imageId: Int,
    val description: String
)


//note 使用mutableListOf<Love>() 而是用mutableStateListOf 可以在ViewModel中做数据的CRUD 并反馈到界面上
val loves = mutableStateListOf<Love>(
    Love(
        "航海王",
        "喜剧/冒险",
        4.4f,
        "比较喜欢",
        R.drawable.hanghaiwang,
        "传奇海盗哥尔•D•罗杰在临死前曾留下关于其毕生的财富“One Piece”的消息，由此引得群雄并起"
    ),
    Love(
        "灌篮高手",
        "青春/热血/友情",
        5.0f,
        "完美无限",
        R.drawable.guanlangaos,
        "湘北高中的一年级新生樱木花道在初中就有了被50个女孩抛弃的“悲惨”经历"
    ),
    Love("What If?", "漫威/科幻", 4.4f, "比较喜欢", R.drawable.whatif, "辛酸、后悔、耻辱——人类诞生的负面感情化作诅咒潜伏于日常当中。"),
    Love(
        "咒术回战",
        "魔法/奇幻",
        4.6f,
        "比较喜欢",
        R.drawable.zhoushuhuizhan,
        "辛酸、后悔、耻辱——人类诞生的负面感情化作诅咒潜伏于日常当中。"
    ),
    Love(
        "火影",
        "动作/热血",
        4.8f,
        "非常喜欢",
        R.drawable.huoying,
        "十多年前一只拥有巨大威力的妖兽“九尾妖狐”袭击了木叶忍者村。"
    ),
)


enum class LovePageState {
    Closing, Closed, Opening, Open
}