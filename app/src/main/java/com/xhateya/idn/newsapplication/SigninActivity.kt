package com.xhateya.idn.newsapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.xhateya.idn.newsapplication.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var signinBinding: ActivitySigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signinBinding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(signinBinding.root)
        setContentView(R.layout.activity_signin)
        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()

        signinBinding.btnSingin.setOnClickListener(this)
        signinBinding.tvForgot.setOnClickListener(this)
        signinBinding.tvSignup.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnSingin -> signInEmailPassword()
            R.id.tvSignup -> startActivity(SignupActivity.getLaunchService(this))
            R.id.tvForgot -> startActivity(ForgotPasswordActivity.getLaunchService(this))
        }

    }

    private fun signInEmailPassword() {
        val email = signinBinding.etEmail.text.toString()
        val password = signinBinding.etPassword.text.toString()
        if (email.isEmpty()|| password.isEmpty()){
            Toast.makeText(this, "Isi dengan lengkap", Toast.LENGTH_SHORT).show()
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this, "Login Success",
                Toast.LENGTH_SHORT).show()
                startActivity(MainActivity.getLaunchService(this))
                return@addOnCompleteListener
            }else{
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val userStatus = FirebaseAuth.getInstance().currentUser
        if (userStatus != null){
            startActivity(MainActivity.getLaunchService(this))
        }
    }
    companion object{
        fun getLaunchService(from: Context)= Intent(from, SigninActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
}