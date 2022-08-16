package org.devio.proj.jetpack.love

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.devio.proj.jetpack.love.ui.theme.Gray
import org.devio.proj.jetpack.love.ui.theme.Orange

/**
 * @author : zg.gu
 * @date : 2022/8/16 10:12
 * description : 可用ButtonNavigation
 */

@Composable/*底部导航Tab*/
fun NavBar(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    Row(
        modifier = Modifier//NavBar height 84 /左右水平间距 16 上下0
            .background(Color.White)
            .height(height = 84.dp)
            .padding(horizontal = 16.dp, vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically//上下居中
    ) {
        /*note 只能作为参数传递 子组件才能感受父组件RowScope的作用域 使用.weight 或者使用拓展函数Scope.NavItem*/
        NavItem(
            painterId = R.drawable.home,
            contentDescription = "Home",
            tint = Orange,
            coroutineScope,
            scaffoldState
        )
        NavItem(
            painterId = R.drawable.movie,
            contentDescription = "Movie",
            tint = Gray,
            coroutineScope,
            scaffoldState
        )
        NavItem(
            painterId = R.drawable.group,
            contentDescription = "Group",
            tint = Gray,
            coroutineScope,
            scaffoldState
        )
        NavItem(
            painterId = R.drawable.shop,
            contentDescription = "Shop",
            tint = Gray,
            coroutineScope,
            scaffoldState
        )
        NavItem(
            painterId = R.drawable.person,
            contentDescription = "Person",
            tint = Gray,
            coroutineScope,
            scaffoldState
        )

    }
}

@Composable
fun RowScope.NavItem(
    @DrawableRes painterId: Int,
    contentDescription: String,
    tint: Color,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState
) {

    /*为了有点击效果 图片外层套上Button 当然也可以用modifier中的属性*/
    Button(
        onClick = {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = contentDescription,
                    duration = SnackbarDuration.Short
                )
            }
        },
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight(),
        shape = RectangleShape,
        colors = ButtonDefaults.outlinedButtonColors(),
        //hint 点击效果 且取消了button边框阴影
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Icon(
            painter = painterResource(id = painterId),
            contentDescription = contentDescription,
            modifier = Modifier
                .size(28.dp)
                .weight(1f),//NavBar Icon size
            tint = tint/*设置前置颜色*/
        )
    }
}

