package com.food.foodporterapplication.customer.activity.otpverificationapi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.customer.activity.confirmpasswordapi.ConfirmPasswordActivity
import com.food.foodporterapplication.customer.activity.forgotpasswordapi.CustomerForgotPasswordActivity
import com.food.foodporterapplication.customer.activity.otpverificationapi.model.OtpVerifyBody
import com.food.foodporterapplication.customer.activity.otpverificationapi.model.OtpVerifyModelView
import com.food.foodporterapplication.databinding.ActivityCustomerOtpVerifyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerOtpVerifyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerOtpVerifyBinding
    private val otpVerifyModelView: OtpVerifyModelView by viewModels()
    private lateinit var activity: Activity
    private var email = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomerOtpVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        email = intent.getStringExtra("email").toString()

        binding.forgotPassArrowImg.setOnClickListener {
            finish()
        }

        binding.verifyAccountBtn.setOnClickListener {
            if (binding.otpTextview.otp.toString().trim().isEmpty()){
                Toast.makeText(this, "Please enter six digit otp", Toast.LENGTH_LONG).show()
            }else{
                otpVerifyApi()
            }
        }

        binding.resendOtpText.setOnClickListener {
            val intent = Intent(this@CustomerOtpVerifyActivity, CustomerForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        otpVerifyObserver()
    }

    private fun otpVerifyApi() {
      val body = OtpVerifyBody(
          email = email,
          otp = binding.otpTextview.otp.toString().trim()
      )
        otpVerifyModelView.otpVerifyUser(activity, body)
    }

    private fun otpVerifyObserver() {
        otpVerifyModelView.progressIndicator.observe(this) {}
        otpVerifyModelView.mOtpVerifyResponse.observe(this) {
            val success = it.peekContent().success
            val message = it.peekContent().message
            try {
                if (success == true) {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    val i = Intent(
                        this@CustomerOtpVerifyActivity,
                        ConfirmPasswordActivity::class.java
                    )
                    i.putExtra("email", email)
                    startActivity(i)
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        otpVerifyModelView.errorResponse.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }
    }
