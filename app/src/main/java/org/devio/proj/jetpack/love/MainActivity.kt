package org.devio.proj.jetpack.love

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import org.devio.proj.jetpack.love.ui.theme.*

class MainActivity : ComponentActivity() {
    var currentLove: Love? by mutableStateOf(null)
    var currentLovePageState by mutableStateOf(LovePageState.Closed)
    var cardSize by mutableStateOf(IntSize(0, 0))
    var fullSize by mutableStateOf(IntSize(0, 0))
    var cardOffset by mutableStateOf(IntOffset(0, 0))

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState: ScaffoldState = rememberScaffoldState()
            val coroutineScope: CoroutineScope = rememberCoroutineScope()
            Scaffold(scaffoldState = scaffoldState,
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { snackbarHostState ->
                    SnackbarHost(/*note 弹框*/
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentHeight(Alignment.CenterVertically)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                                /*加入以下2行 否则想过类似Toast 无法看清*/
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = Color.White),
                        hostState = snackbarHostState,
                        snackbar = { snackbarData: SnackbarData ->
                            Card {
                                Text(
                                    modifier = Modifier.padding(16.dp),
                                    text = snackbarData.message
                                )
                            }
                        }
                    )

                }) {
                Column(Modifier.onSizeChanged { fullSize = it }) {
                    //将NavBar挤下去
                    Column(Modifier.fillMaxWidth().weight(1f).background(BackgroundWhite)
                        .verticalScroll(rememberScrollState()))  {
                        //顶部tab
                        TopBar()
                        SearchBar()
                        CategoryBar()
                        TopLovesArea(
                            { cardSize = it },
                            { love, offset ->
                                currentLove = love
                                currentLovePageState = LovePageState.Opening
                                cardOffset = offset
                            })
                    }
                    NavBar(coroutineScope, scaffoldState)

                }

                LoveDetailsPage(currentLove, currentLovePageState, cardSize, fullSize, cardOffset, {
                    currentLovePageState = LovePageState.Closing
                }, {
                    currentLovePageState = LovePageState.Closed
                })
            }
        }

    }
}


@ExperimentalMaterialApi
@Composable
fun LoveDetailsPage(
    love: Love?,
    pageState: LovePageState,
    cardSize: IntSize,
    fullSize: IntSize,
    cardOffset: IntOffset,
    onPageClosing: () -> Unit,
    onPageClosed: () -> Unit
) {
    var animReady by remember { mutableStateOf(false) }
    val background by animateColorAsState(
        if (pageState > LovePageState.Closed) Color(0xfff8f8f8) else Color.White,
        finishedListener = {
            if (pageState == LovePageState.Closing) {
                onPageClosed()
                animReady = false
            }
        })
    val cornerSize by animateDpAsState(if (pageState > LovePageState.Closed) 0.dp else 16.dp)
    val paddingSize by animateDpAsState(if (pageState > LovePageState.Closed) 10.dp else 6.dp)
    val size by animateIntSizeAsState(if (pageState > LovePageState.Closed) fullSize else cardSize)
    val titleOuterPaddingHorizontal by animateDpAsState(if (pageState > LovePageState.Closed) 14.dp else 0.dp)
    val titlePaddingHorizontal by animateDpAsState(if (pageState > LovePageState.Closed) 16.dp else 8.dp)
    val titlePaddingTop by animateDpAsState(if (pageState > LovePageState.Closed) 18.dp else 12.dp)
    val titlePaddingBottom by animateDpAsState(if (pageState > LovePageState.Closed) 16.dp else 8.dp)
    val titleOffsetY by animateDpAsState(if (pageState > LovePageState.Closed) (-40).dp else 0.dp)
    val titleFontSize by animateFloatAsState(if (pageState > LovePageState.Closed) 18f else 15f)
    val titleFontWeight by animateIntAsState(if (pageState > LovePageState.Closed) 900 else 700)
    val titleSpacing by animateDpAsState(if (pageState > LovePageState.Closed) 10.dp else 4.dp)
    val subtitleFontSize by animateFloatAsState(if (pageState > LovePageState.Closed) 15f else 14f)
    val badgeCornerSize by animateDpAsState(if (pageState > LovePageState.Closed) 15.dp else 10.dp)
    val badgeWidth by animateDpAsState(if (pageState > LovePageState.Closed) 90.dp else 0.dp)
    val badgeHeight by animateDpAsState(if (pageState > LovePageState.Closed) 66.dp else 0.dp)
    val badgeBackground by animateColorAsState(
        if (pageState > LovePageState.Closed) Color(0xfffa9e51) else Color(
            0xfffef1e6
        )
    )
    val badgeContentColor by animateColorAsState(
        if (pageState > LovePageState.Closed) Color.White else Color(
            0xfffa9e51
        )
    )
    val imageCornerSize by animateDpAsState(if (pageState > LovePageState.Closed) 32.dp else 16.dp)
    val imageRatio by animateFloatAsState(if (pageState > LovePageState.Closed) 1f else 1.35f)
    val fullOffset = remember { IntOffset(0, 0) }
    val offsetAnimatable = remember { Animatable(IntOffset(0, 0), IntOffset.VectorConverter) }
    LaunchedEffect(pageState) {
        when (pageState) {
            LovePageState.Opening -> {
                animReady = true
                offsetAnimatable.snapTo(cardOffset)
                offsetAnimatable.animateTo(fullOffset)
            }
            LovePageState.Closing -> {
                offsetAnimatable.snapTo(fullOffset)
                offsetAnimatable.animateTo(cardOffset)
            }
            else -> {}
        }
    }
    if (pageState != LovePageState.Closed && animReady) {
        Box(
            Modifier
                .offset { offsetAnimatable.value }
                .clip(RoundedCornerShape(cornerSize))
                .width(with(LocalDensity.current) { size.width.toDp() })
                .height(with(LocalDensity.current) { size.height.toDp() })
                .background(background)
                .padding(paddingSize)
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Image(
                    painterResource(love!!.imageId),
                    "图像",
                    Modifier
                        .clip(RoundedCornerShape(imageCornerSize))
                        .fillMaxWidth()
                        .aspectRatio(imageRatio),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                Row(
                    Modifier
                        .offset(0.dp, titleOffsetY)
                        .padding(titleOuterPaddingHorizontal, 0.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(
                            titlePaddingHorizontal,
                            titlePaddingTop,
                            titlePaddingHorizontal,
                            titlePaddingBottom
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column() {
                        Text(
                            love.name,
                            color = Color.Black,
                            fontSize = titleFontSize.sp,
                            fontWeight = FontWeight(titleFontWeight)
                        )
                        Spacer(Modifier.height(titleSpacing))
                        Text(love.category, color = Color(0xffb4b4b4), fontSize = subtitleFontSize.sp)
                    }
                    Spacer(Modifier.weight(1f))
                    Box(
                        Modifier
                            .width(badgeWidth)
                            .height(badgeHeight)
                            .clip(RoundedCornerShape(badgeCornerSize))
                            .background(badgeBackground)
                            .padding(6.dp, 11.dp, 8.dp, 8.dp)
                    ) {
                        Text(
                            love.scoreText,
                            Modifier.align(Alignment.TopCenter),
                            color = badgeContentColor,
                            fontSize = 14.sp
                        )
                        Row(
                            Modifier.align(Alignment.BottomCenter),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painterResource(R.drawable.ic_star),
                                "",
                                Modifier.size(24.dp),
                                tint = badgeContentColor
                            )
                            Text(love.score.toString(), color = badgeContentColor, fontSize = 14.sp)
                        }
                    }
                }
                Text(
                    "TA 的评价",
                    Modifier
                        .offset(0.dp, titleOffsetY)
                        .padding(14.dp, 24.dp, 14.dp, 14.dp), fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
                Text(
                    love.description,
                    Modifier
                        .offset(0.dp, titleOffsetY)
                        .padding(14.dp, 0.dp), fontSize = 15.sp, color = Color(0xffb4b4b4)
                )
            }
            Surface(

                onClick = { onPageClosing() },
                modifier = Modifier.padding(14.dp, 32.dp),
                color = Color.White,
                shape = CircleShape,
                indication = rememberRipple()
            ) {
                Icon(
                    painterResource(R.drawable.ic_back),
                    "返回",
                    Modifier
                        .padding(8.dp)
                        .size(26.dp), tint = Color.Black
                )
            }
            Text(
                "详情",
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(44.dp),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Surface(
                { },
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(14.dp, 32.dp),
                color = Color.White,
                shape = CircleShape,
                indication = rememberRipple()
            ) {
                Icon(
                    painterResource(R.drawable.ic_more),
                    "更多",
                    Modifier
                        .padding(8.dp)
                        .size(26.dp), tint = Color.Black
                )
            }
        }
    }
}



