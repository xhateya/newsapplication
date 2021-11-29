package com.xhateya.idn.newsapplication

import android.content.Context
import android.content.Intent
import android.database.DatabaseErrorHandler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.xhateya.idn.newsapplication.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mAuth : FirebaseAuth
    private lateinit var refUser : DatabaseReference
    private  var firebaseUserId : String = ""
    private lateinit var activitySignupBinding : ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignupBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(activitySignupBinding.root)
        mAuth = FirebaseAuth.getInstance()
        activitySignupBinding.btnSignup.setOnClickListener(this)

    }
    companion object{
        fun getLaunchService (from : Context)= Intent(from, SignupActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnSignup -> signupUser()
        }
    }

    private fun signupUser() {
        val fullName : String = activitySignupBinding.etNameSignup.text.toString()
        val email : String = activitySignupBinding.etEmailSignup.text.toString()
        val password : String = activitySignupBinding.etPasswordSignup.text.toString()
        val confirmPassword : String = activitySignupBinding.etConfirmPasswordSignup.text.toString()

        if (fullName == ""){
            Toast.makeText(this,"Not Empty", Toast.LENGTH_SHORT).show()
        }else if(email== ""){
            Toast.makeText(this,"Not Empty", Toast.LENGTH_SHORT).show()
        }else if (password == ""){
            Toast.makeText(this,"Not Empty", Toast.LENGTH_SHORT).show()
        }else if ((confirmPassword == "").equals(password)){
            Toast.makeText(this,"Password Mismatch", Toast.LENGTH_SHORT).show()
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{it ->
                if (it.isSuccessful){
                    firebaseUserId = mAuth.currentUser!!.uid
                    refUser = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserId)
                    val userHashMap = HashMap<String, Any>()
                    userHashMap["uid"] = firebaseUserId
                    userHashMap["fullname"] = fullName
                    userHashMap["email"] = email
                    userHashMap["linkedIn"] = ""
                    userHashMap["Instagram"] = ""
                    userHashMap["medium"] = ""
                    userHashMap["photo"] = ""

                    refUser.updateChildren(userHashMap).addOnCompleteListener{
                        if(it.isSuccessful){
                            startActivity(Intent(MainActivity.getLaunchService(this)))
                            finish()
                        }else{
                            Toast.makeText(this, "Register Failede" + it.exception!!.message.toString(),
                            Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }
            }
        }
    }

}
