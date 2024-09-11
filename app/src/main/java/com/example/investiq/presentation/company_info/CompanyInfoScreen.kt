package com.example.investiq.presentation.company_info

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.investiq.ui.theme.Orange
import com.example.investiq.ui.theme.poppins


@Composable
fun CompanyInfoScreen(
 viewModel: CompanyInfoViewModel= hiltViewModel()
){

val state = viewModel.state

  if(state.error==null ){

   Column(modifier= Modifier
    .fillMaxSize()
    .background(color = Color.Black)
    .verticalScroll(rememberScrollState())) {

    if (state.stockPrice!=null){
     state.companyDetail?.let { companyDetail ->

      Box(modifier = Modifier
       .fillMaxWidth()
       .wrapContentHeight(),
       contentAlignment = Alignment.Center){

       Column(modifier = Modifier
        .wrapContentSize()
        .background(color = Color.Black)) {

        Text(text = companyDetail.symbol ,
         color = Color.White,
         fontSize = 20.sp,
         modifier = Modifier
          .padding(top = 15.dp)
          .align(Alignment.CenterHorizontally),
         fontWeight = FontWeight.Medium,
         fontFamily = poppins,
        )

        Text(text = companyDetail.companyName,
         color = Color.White,
         fontSize = 25.sp,
         modifier = Modifier
          .padding(bottom = 5.dp, start = 20.dp, end = 20.dp)
          .align(Alignment.CenterHorizontally),
         fontWeight = FontWeight.SemiBold,
         fontFamily = poppins,
         maxLines = 1,
         overflow = TextOverflow.Ellipsis
        )

       }
      }

      Box(modifier = Modifier
       .fillMaxWidth()
       .wrapContentHeight(),
       contentAlignment = Alignment.Center){

       Column(modifier = Modifier
        .wrapContentSize()
       ) {

        state.stockPrice.let { quote->

         Text(text = "$ "+quote.price,
          color = Color.White,
          fontSize = 30.sp,
          modifier = Modifier
           .padding(top = 15.dp)
           .align(Alignment.CenterHorizontally),
          fontWeight = FontWeight.Bold,
          fontFamily = poppins,
         )


         if(quote.changesPercentage.toString().first() == '-'){

          Text(text = quote.changesPercentage.toString()+"%",
           color = Color.Red,
           fontSize = 18.sp,
           modifier = Modifier
            .padding(top = 2.dp)
            .align(Alignment.CenterHorizontally),
           fontWeight = FontWeight.SemiBold,
           fontFamily = poppins,
          )

         }else{
          Text(text = "+"+quote.changesPercentage+"%",
           color = Color.Green,
           fontSize = 18.sp,
           modifier = Modifier
            .padding(top = 2.dp)
            .align(Alignment.CenterHorizontally),
           fontWeight = FontWeight.SemiBold,
           fontFamily = poppins,
          )
         }

        }

       }
      }



      Column(modifier = Modifier.fillMaxWidth()) {

       Spacer(modifier = Modifier.height(30.dp))

       Text(text ="Chart Summary",
        modifier = Modifier
         .align(Alignment.CenterHorizontally),
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = poppins,
        color = Color.White)

       Spacer(modifier = Modifier.height(30.dp))

       StockChart(infos = state.intraDayInfo.reversed(), modifier = Modifier
        .padding(start = 5.dp, top = 15.dp)
        .fillMaxWidth()
        .height(250.dp))

       Spacer(modifier = Modifier.height(10.dp))

       Text(text ="Fundamentals",
        modifier = Modifier
         .align(Alignment.Start)
         .padding(start = 12.dp, top = 20.dp),
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = poppins,
        color = Color.White)

       Spacer(modifier = Modifier.height(5.dp))

       state.stockPrice.let { quote->
           Row(modifier = Modifier.padding(top = 2.dp)){
            Text(text ="Market Capitalization : ",
             modifier = Modifier
              .padding(start = 12.dp),
             fontSize = 15.sp,
             fontWeight = FontWeight.Medium,
             fontFamily = poppins,
             color = Color.LightGray)

            Text(text ="$ " +quote.marketCap.toString(),
             modifier = Modifier,
             fontSize = 15.sp,
             fontWeight = FontWeight.Medium,
             fontFamily = poppins,
             color = Color.White)

           }
           Row(modifier = Modifier.padding(top = 2.dp)){
            Text(text ="Average Volume : ",
             modifier = Modifier
              .padding(start = 12.dp),
             fontSize = 15.sp,
             fontWeight = FontWeight.Medium,
             fontFamily = poppins,
             color = Color.LightGray)

            Text(text =quote.avgVolume.toString(),
             modifier = Modifier,
             fontSize = 15.sp,
             fontWeight = FontWeight.Medium,
             fontFamily = poppins,
             color = Color.White)

           }
           Row(modifier = Modifier.padding(top = 2.dp)){
            Text(text ="Price to Earning Ratio : ",
             modifier = Modifier
              .padding(start = 12.dp),
             fontSize = 15.sp,
             fontWeight = FontWeight.Medium,
             fontFamily = poppins,
             color = Color.LightGray)

            Text(text =quote.pe.toString(),
             modifier = Modifier,
             fontSize = 15.sp,
             fontWeight = FontWeight.Medium,
             fontFamily = poppins,
             color = Color.White)

           }
           Row(modifier = Modifier.padding(top = 2.dp)){
            Text(text ="Discounted cash flow : ",
             modifier = Modifier
              .padding(start = 12.dp),
             fontSize = 15.sp,
             fontWeight = FontWeight.Medium,
             fontFamily = poppins,
             color = Color.LightGray)

            Text(text =state.companyDetail.dcf.toString(),
             modifier = Modifier,
             fontSize = 15.sp,
             fontWeight = FontWeight.Medium,
             fontFamily = poppins,
             color = Color.White)

           }
           Row(modifier = Modifier.padding(top = 2.dp)){
            Text(text ="Earning Per Share: ",
             modifier = Modifier
              .padding(start = 12.dp),
             fontSize = 15.sp,
             fontWeight = FontWeight.Medium,
             fontFamily = poppins,
             color = Color.LightGray)

            Text(text =quote.eps.toString(),
             modifier = Modifier,
             fontSize = 15.sp,
             fontWeight = FontWeight.Medium,
             fontFamily = poppins,
             color = Color.White)

           }
       }
      }

     }
    }

   }
  }

 if( state.isLoading || state.error!=null){

   Box(modifier = Modifier
    .fillMaxSize()
    .background(color = Color.Black), contentAlignment = Alignment.Center){
    if(state.isLoading){
     CircularProgressIndicator(color = Orange)
    }else {
     Text(text = state.error ?: "",
      color = Color.White,
      fontSize = 15.sp,
      modifier = Modifier.padding(start = 12.dp, end = 12.dp))
    }
   }
  }

}