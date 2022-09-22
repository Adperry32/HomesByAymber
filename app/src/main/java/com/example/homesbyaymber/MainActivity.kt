package com.example.homesbyaymber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.homesbyaymber.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavFrag {

    private lateinit var bind : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        //Switch to main page
        bind.guestBTN.setOnClickListener {
            fragNav(HomesFragment(), false)
        }
    }

    override fun fragNav(fragment: Fragment, addToStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.main, fragment)
        if(addToStack){
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}