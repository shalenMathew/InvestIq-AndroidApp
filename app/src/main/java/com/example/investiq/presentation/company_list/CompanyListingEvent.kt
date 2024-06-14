package com.example.investiq.presentation.company_list

sealed class CompanyListingEvent(){
    // event simply means every single action an user can make in  an ui this below are the changes an user can make
    object Refresh:CompanyListingEvent()
    data class OnSearchQueryChanged(val query:String):CompanyListingEvent()
}
