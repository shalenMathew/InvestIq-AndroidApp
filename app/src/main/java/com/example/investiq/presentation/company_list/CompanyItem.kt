package com.example.investiq.presentation.company_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.investiq.domain.model.CompanyListing
import com.example.investiq.ui.theme.PurpleGrey40
import com.example.investiq.ui.theme.PurpleGrey80
import com.example.investiq.ui.theme.poppins
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


@Composable
fun CompanyItem(
    company:CompanyListing,
    onClick:()->Unit
){

    val save = SwipeAction(
        icon = rememberVectorPainter(Icons.TwoTone.Star),
        background = Color.Green,
        onSwipe = {

        }
    )

    val unsave = SwipeAction(
        icon = rememberVectorPainter(Icons.TwoTone.Star),
        background = PurpleGrey40,
        onSwipe = {

        }
    )


    SwipeableActionsBox(
        startActions = listOf(unsave),
        endActions = listOf(save),
        swipeThreshold = 100.dp
    ) {

        Box(
            modifier= Modifier
                .padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(color = Color.Black)
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    onClick()
                }
        ){

            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
                , verticalAlignment = Alignment.CenterVertically
            ){

                Column(modifier=Modifier.weight(1f)) {
                    Text(text = company.name,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top=12.dp, start=15.dp, end = 10.dp),
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppins,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)

                    Text(text = "(${company.symbol})",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(top=8.dp, start=15.dp, bottom = 12.dp))

                }

                Text(text = company.exchange,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Light,
                    modifier=Modifier.padding(end=15.dp))


            }

        }

    }

}
