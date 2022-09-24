package com.example.homesbyaymber

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.homesbyaymber.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.login_dialog.*


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

        //Login Dialog box
        bind.loginBTN.setOnClickListener {

            val loginDialogBinding = layoutInflater.inflate(R.layout.login_dialog, null)
            val dialog = Dialog(this)
            dialog.setContentView(loginDialogBinding)
            dialog.setCancelable(true)
            dialog.show()

        //Check for current user
        val next = loginDialogBinding.findViewById<Button>(R.id.logNext)
            next.setOnClickListener {
            validateEmptyForm()
        }
            val cancel = loginDialogBinding.findViewById<Button>(R.id.cancel)
            cancel.setOnClickListener {
                dialog.dismiss()
            }

        }

        //Registration Dialog Box
        bind.registerBTN.setOnClickListener {

            val registerDialogBinding = layoutInflater.inflate(R.layout.register_dialog, null)
            val dialog = Dialog(this)
            dialog.setContentView(registerDialogBinding)
            dialog.setCancelable(true)
            dialog.show()

        //If checks for buttons on dialog box


        }

        bind.forgotPassBTN.setOnClickListener {

            val forgotpassDialogBinding = layoutInflater.inflate(R.layout.forgotpass_dialog, null)
            val dialog = Dialog(this)
            dialog.setContentView(forgotpassDialogBinding)
            dialog.setCancelable(true)
            dialog.show()

            //Dialog button press
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

    //Figure out how to see the edit text on a dialog box to render rest of code for validation
    private fun validateEmptyForm()
    {
        val warning = AppCompatResources.getDrawable(this,R.drawable.ic_baseline_warning_24)
        warning?.setBounds(0,0,warning.intrinsicWidth, warning.intrinsicHeight)

        when
        {
            TextUtils.isEmpty(logN.text.toString().trim())->
            {
                logN.setError("Please enter Username/Email",warning)

            }
            TextUtils.isEmpty(password.text.toString().trim())->
            {
                password.setError("Please enter Password",warning)
            }

            logN.text.toString().isNotEmpty()&&
                    password.text.toString().isNotEmpty()->
            {
                if(logN.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")))
                {
                    if(password.text.toString().length >= 6){

                        //Check if current user in firebase database
                        val email: String = logN.text.toString().trim(){it <= ' '}
                        val password: String = password.text.toString().trim(){it <= ' '}

                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    //dismiss progress bar

                                    val firebaseUser: FirebaseUser = task.result!!.user!!
                                    Toast.makeText(this,"Login Successful",
                                        Toast.LENGTH_SHORT)
                                        .show()


                                }
                                else //Login Failure
                                {
                                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                                        .addOnFailureListener { e->
                                            Toast.makeText(this,"Login Failed!, ${e.message}",
                                                Toast.LENGTH_SHORT).show()

                                        }
                                }
                            }
                    }
                    else{
                        password.setError("Reminder:: Passwords are at least 6 characters", warning)
                    }
                }
                else
                {
                    logN.setError("Please enter a valid email address", warning)
                }
            }
        }
    }

}