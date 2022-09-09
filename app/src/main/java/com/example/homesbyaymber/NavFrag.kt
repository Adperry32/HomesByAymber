package com.example.homesbyaymber

import androidx.fragment.app.Fragment

interface NavFrag {
    fun fragNav(fragment: Fragment, addToStack: Boolean)
}