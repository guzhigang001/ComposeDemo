package org.devio.proj.jetpack.love

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.devio.proj.jetpack.love.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState: ScaffoldState = rememberScaffoldState()
            val coroutineScope: CoroutineScope = rememberCoroutineScope()
            Scaffold(scaffoldState = scaffoldState,
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { snackbarHostState ->
                    SnackbarHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentHeight(Alignment.CenterVertically)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .background(color = BlackDark),
                        hostState = snackbarHostState,
                        snackbar = { snackbarData: SnackbarData ->
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = snackbarData.message
                            )
                        }
                    )

                }) {
                Column() {
                    //将NavBar挤下去
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(BackgroundWhite)
                            .verticalScroll(rememberScrollState())
                    ) {
                        TopBar()

                    }
                    NavBar(coroutineScope, scaffoldState)

                }
            }
        }

    }
}

@Composable
fun TopBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(28.dp, 28.dp, 28.dp, 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(R.drawable.avatar_rengwuxian), "头像",
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
            Text("小朱", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
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

@Composable/*底部导航Tab*/
fun NavBar(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    Row(
        modifier = Modifier//NavBar height 84 /左右水平间距 16 上下0
            .background(Color.White)
            .height(height = 84.dp)
            .padding(horizontal = 16.dp, vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically//上下居中
    ) {
        /*note 只能作为参数传递 子组件才能感受父组件RowScope的作用域 使用.weight 或者使用拓展函数wScope.NavItem*/
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
                .size(24.dp)
                .weight(1f),//NavBar Icon size
            tint = tint/*设置前置颜色*/
        )
    }
}

