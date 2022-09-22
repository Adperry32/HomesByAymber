package com.example.homesbyaymber

import androidx.fragment.app.Fragment
import com.android.volley.toolbox.HurlStack

interface Navigator {
    fun fragNavigation(fragment: Fragment, addToStack: Boolean)
}