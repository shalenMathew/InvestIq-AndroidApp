package com.example.investiq.presentation.company_list


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.investiq.data.mappers.toCompanyFavItem
import com.example.investiq.domain.model.CompanyItem
import com.example.investiq.presentation.company_favorites.CompanyFavoriteEvent
import com.example.investiq.presentation.company_favorites.CompanyFavoriteViewModel
import com.example.investiq.ui.theme.CustomGreen
import com.example.investiq.ui.theme.CustomRed
import com.example.investiq.ui.theme.Gold
import com.example.investiq.ui.theme.poppins
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


@Composable
fun CompanyItem(
    modifier: Modifier,
    company:CompanyItem,
    onClick:()->Unit,
    viewmodel:CompanyFavoriteViewModel
){

    val context = LocalContext.current

    val save = SwipeAction(
        icon = rememberVectorPainter(Icons.Filled.Star),
        background = CustomGreen,
        onSwipe = {
           viewmodel.onEvent(CompanyFavoriteEvent.insertData(company.toCompanyFavItem()))
            Toast.makeText(context, "Added to ur favorites", Toast.LENGTH_SHORT).show()
        }
    )

    val unsave = SwipeAction(
        icon = rememberVectorPainter(Icons.TwoTone.Star),
        background = CustomRed,
        onSwipe = {
            viewmodel.onEvent(CompanyFavoriteEvent.deleteData(company.toCompanyFavItem()))
            Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
        }
    )


    SwipeableActionsBox(
        startActions = listOf(unsave),
        endActions = listOf(save),
        swipeThreshold = 65.dp
    ) {

        Box(
            modifier= modifier
                .padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(color = Color.Black)
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ){

            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ){

                Column(modifier=Modifier.weight(1f)) {

                    Text(text = company.name,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top=15.dp, start=15.dp, end = 10.dp, bottom = 2.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppins,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)

                    Text(text = company.symbol,
                        color = Color.White,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(top=2.dp, start=15.dp, end = 10.dp, bottom = 15.dp),
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppins,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)


                }

                Column {
                    Text(text = "$ " + company.price,
                        color = Gold,
                        fontSize = 18.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        modifier=Modifier.padding(end=15.dp, start = 12.dp, bottom = 2.dp))

                    Text(text = company.exchangeShortName,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Medium,
                        modifier=Modifier.padding(end=15.dp, start = 12.dp, top = 2.dp))

                }

            }
        }
    }
}
