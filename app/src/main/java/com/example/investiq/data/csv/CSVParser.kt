package com.example.investiq.data.csv

//import java.io.InputStream
//
//
//// here even when we are on same layer we are gonna depend upon interface , coz it provides an  abstraction
//
//// abstraction provides a flexibility coz even if tomorrow we introduce some changes on how we want to parse the data
//// we do it by having diff Implmentation of same interface
//
//interface CSVParser<T> {
//suspend fun parse(stream:InputStream):List<T>
//}