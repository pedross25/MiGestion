package com.example.migestion.ui.navigation

import com.example.migestion.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.baseline_home_24,"home")
    object InvoiceScreen: BottomNavItem("Invoice Screen",R.drawable.baseline_group_24,"invoice_screen")
    object AddPost: BottomNavItem("Post",R.drawable.baseline_add_24,"add_post")
    object Notification: BottomNavItem("Notification",R.drawable.baseline_notifications_24,"notification")
    object Jobs: BottomNavItem("Jobs",R.drawable.baseline_work_24,"jobs")
    object Customers: BottomNavItem("Customers",R.drawable.baseline_contacts_24,"customers")
}