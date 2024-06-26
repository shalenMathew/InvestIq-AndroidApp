package com.example.investiq.presentation.company_list

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.investiq.R
import com.example.investiq.domain.model.CompanyListing
import com.example.investiq.ui.theme.Orange
import com.example.investiq.ui.theme.PurpleGrey40
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn( ExperimentalMaterialApi::class)
@Composable
@Destination(start = true)
fun CompanyListScreen(
    navigator: DestinationsNavigator,
    viewmodel:CompanyListingViewmodel = hiltViewModel(),
){


    var clickedSearch by remember {
        mutableStateOf(false)
    }

    val progress by animateFloatAsState(targetValue = if(clickedSearch) 1f else 0f, label = "", animationSpec = tween(2000))




    val state = viewmodel.state

    val  pullRefreshState = rememberPullRefreshState(
        refreshing = viewmodel.state.isRefreshing ,
        onRefresh = {
            viewmodel.getCompanyListing(fetchFromRemote = false) // make it true later
        })

 val willRefresh by remember {
     derivedStateOf {
         pullRefreshState.progress > 1f
     }
 }

    val cardOffset by animateIntAsState(
        targetValue = when{
            state.isRefreshing -> 250
            pullRefreshState.progress in 0f..1f -> (250*pullRefreshState.progress).roundToInt()
            pullRefreshState.progress > 1f -> (250 + ((pullRefreshState.progress - 1f) * .1f) * 100).roundToInt()
            else -> 0
        },
        label = "cardOffset" )

    val cardRotation by animateFloatAsState(
        targetValue = when{
            state.isRefreshing || pullRefreshState.progress>1f -> 5f
            pullRefreshState.progress > 0f -> 5 * pullRefreshState.progress
            else -> 0f
        } ,
        label = "cardRotation"  )

    // vibration on pull
    val hapticFeedback = LocalHapticFeedback.current
    LaunchedEffect(key1 = willRefresh) {
        when{
            willRefresh->{
                hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                delay(70)
                hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                delay(100)
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            }

            !viewmodel.state.isRefreshing && pullRefreshState.progress > 0f -> {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            }
        }
    }
    // --


// animating orange background
    val animatedOffset by animateDpAsState(
        targetValue = when {
            state.isRefreshing -> 200.dp
            pullRefreshState.progress in 0f..1f -> (pullRefreshState.progress * 200).dp
            pullRefreshState.progress > 1f -> (200 + (((pullRefreshState.progress - 1f) * .1f) * 200)).dp
            else -> 0.dp
        }, label = ""
    )

    val isBackgroundVisible = pullRefreshState.progress > 0f

    if (isBackgroundVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Orange) // Orange color
                .graphicsLayer {
                    translationY = animatedOffset.toPx()
                }
        )
    }
// --

    Box(modifier= Modifier
        .fillMaxSize()
        .pullRefresh(pullRefreshState)
         ) {


            if(state.error.isNotEmpty() && state.companyList.isEmpty()){
                
               Box(modifier= Modifier
                   .padding(12.dp)
                   .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(5.dp))
                   .wrapContentWidth()
                   .wrapContentHeight()
                   .padding(10.dp)
                   .align(Alignment.Center)
                   , contentAlignment = Alignment.Center
               ){
                   Text(text = state.error,
                       color = Color.Black,
                       fontSize = 15.sp
                       )
               }

            }

                Column(modifier=Modifier.fillMaxSize()){

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)){

                       if (pullRefreshState.progress==0f){

                           OutlinedTextField(  value = state.searchQuery ,
                               onValueChange = {value->
                                   viewmodel.onEvent(CompanyListingEvent.OnSearchQueryChanged(value))
                               },
                               modifier = Modifier
                                   .padding(
                                       bottom = 15.dp,
                                       start = 16.dp,
                                       end = 16.dp,
                                       top = 10.dp
                                   )
                                   .fillMaxWidth()
                                   .onFocusChanged { focusState ->
                                       clickedSearch = focusState.isFocused
                                   }
                                   .animatedBorder({ progress }, Orange, Color.Black),
                               maxLines = 1,
                               placeholder = { Text(text = "Search...")}
                               , colors = TextFieldDefaults. outlinedTextFieldColors(
                                   focusedBorderColor =Color.Transparent,
                                   unfocusedBorderColor = PurpleGrey40 ,
                                   placeholderColor = Color.Black,
                                   disabledPlaceholderColor = Color.Yellow),
                               shape = MaterialTheme.shapes.extraLarge)

                       }

                    }

                    LazyColumn(modifier=Modifier.fillMaxSize()) {
                        itemsIndexed(state.companyList){ index, companyItem ->

                            CompanyItem(
                                Modifier
                                    .zIndex((state.companyList.size - index).toFloat())
                                    .graphicsLayer {
                                        rotationZ = cardRotation * if (index % 2 == 0) 1 else -1
                                        translationY = (cardOffset * ((5f - (index + 1)) / 5f)).dp
                                            .roundToPx()
                                            .toFloat()
                                    },
                                company = companyItem,
                                onClick = {} )
                        }
                    }
                }

        CustomIndicator(viewmodel.state.isRefreshing,pullRefreshState)

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomIndicator(isRefreshing:Boolean,pullRefreshState:PullRefreshState){

    // handling animation

    val animatedOffset by animateDpAsState(
        targetValue = when {
            isRefreshing -> 200.dp
            pullRefreshState.progress in 0f..1f -> (pullRefreshState.progress * 200).dp
            pullRefreshState.progress > 1f -> (200 + (((pullRefreshState.progress - 1f) * .1f) * 200)).dp
            else -> 0.dp
        }, label = ""
    )

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(resId = R.raw.anim3))

    val isAnimationVisible by remember {
        derivedStateOf {
            animatedOffset>0.dp
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .offset(y = (-200).dp)
            .offset { IntOffset(0, animatedOffset.roundToPx()) }
    ){

        // Check if animation should be visible
        if (isAnimationVisible) {
            // Centered Column for animation and loading text
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // LottieAnimation
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.size(100.dp)
                )
                // Spacer to create space between animation and text
                Spacer(modifier = Modifier.height(2.dp))
                // AnimatedLoadingText
                AnimatedLoadingText()
            }
        }
    }
    }

@Composable
fun AnimatedLoadingText() {
    var dotCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500) // Adjust delay time as needed
            dotCount = (dotCount + 1) % 4 // Update dot count
        }
    }

    Text(
        buildAnnotatedString {
            append("Refreshing")
            repeat(dotCount) {
                append(".")
            }
        },
        modifier = Modifier.padding(top = 8.dp),
        textAlign = TextAlign.Center,
        color = Color.White,
        fontSize = 15.sp,
    )
}

fun Modifier.animatedBorder
            (provideProgress: () -> Float,
             colorFocused: Color,
             colorUnfocused: Color) = this.drawWithCache {
    val width = size.width
    val height = size.height

    val shape = CircleShape

    // Only works with RoundedCornerShape...
    val outline = shape.createOutline(size, layoutDirection, this) as Outline.Rounded

    // ... correction: Only works with same corner sizes everywhere
    val radius = outline.roundRect.topLeftCornerRadius.x
    val diameter = 2 * radius

    // Clockwise path
    val pathCw = Path()

    // Start top center
    pathCw.moveTo(width / 2, 0f)

    // Line to right
    pathCw.lineTo(width - radius, 0f)

    // Top right corner
    pathCw.arcTo(Rect(width - diameter, 0f, width, diameter), -90f, 90f, false)

    // Right edge
    pathCw.lineTo(width, height - radius)

    // Bottom right corner
    pathCw.arcTo(Rect(width - diameter, height - diameter, width, height), 0f, 90f, false)

    // Line to bottom center
    pathCw.lineTo(width / 2, height)

    // As above, but mirrored horizontally
    val pathCcw = Path()
    pathCcw.moveTo(width / 2, 0f)
    pathCcw.lineTo(radius, 0f)
    pathCcw.arcTo(Rect(0f, 0f, diameter, diameter), -90f, -90f, false)
    pathCcw.lineTo(0f, height - radius)
    pathCcw.arcTo(Rect(0f, height - diameter, diameter, height), 180f, -90f, false)
    pathCcw.lineTo(width / 2, height)

    val pmCw = PathMeasure().apply {
        setPath(pathCw, false)
    }
    val pmCcw = PathMeasure().apply {
        setPath(pathCcw, false)
    }

    fun DrawScope.drawIndicator(progress: Float, pathMeasure: PathMeasure) {
        val subPath = Path()
        pathMeasure.getSegment(0f, pathMeasure.length * EaseOut.transform(progress), subPath)
        drawPath(subPath, colorFocused, style = Stroke(3.dp.toPx(), cap = StrokeCap.Round))
    }

    onDrawBehind {
        // Draw the shape
        drawOutline(outline, colorUnfocused, style = Stroke(2.dp.toPx()))

        // Draw the indicators
        drawIndicator(provideProgress(), pmCw)
        drawIndicator(provideProgress(), pmCcw)
    }
}
