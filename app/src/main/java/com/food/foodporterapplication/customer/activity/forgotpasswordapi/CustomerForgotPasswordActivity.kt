package com.food.foodporterapplication.customer.activity.forgotpasswordapi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.cremation.funeralcremation.utils.ErrorUtil
import com.developer.kalert.KAlertDialog
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.otpverificationapi.CustomerOtpVerifyActivity
import com.food.foodporterapplication.customer.activity.forgotpasswordapi.model.ForgotPasswordBody
import com.food.foodporterapplication.customer.activity.forgotpasswordapi.model.ForgotPasswordModelView

import com.food.foodporterapplication.databinding.ActivityCusttomerForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCusttomerForgotPasswordBinding
    private val forgotPasswordModelView: ForgotPasswordModelView by viewModels()
    private lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCusttomerForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        binding.forgotPassArrowImg.setOnClickListener {
            finish()
        }

        binding.verifyBtn.setOnClickListener {
            forgotPasswordApi()
        }


        forgotPasswordObserver()
    }

    private fun forgotPasswordApi() {
        if (binding.userEmailEt.text.isNullOrEmpty()) {
            errorDialogs("Please enter your email")
        } else {
            val body = ForgotPasswordBody(
                email = binding.userEmailEt.text.toString()
            )

            forgotPasswordModelView.forgotUser(activity, body)
        }
    }

    private fun forgotPasswordObserver() {
        forgotPasswordModelView.progressIndicator.observe(this) {}
        forgotPasswordModelView.mForgotPasswordResponse.observe(this) {
            val success = it.peekContent().success
            val message = it.peekContent().message
            try {
                if (success == true) {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    val i = Intent(
                        this@CustomerForgotPasswordActivity,
                        CustomerOtpVerifyActivity::class.java
                    )
                    i.putExtra("email", binding.userEmailEt.text.toString())
                    startActivity(i)
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        forgotPasswordModelView.errorResponse.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    private fun errorDialogs(str: String) {
        KAlertDialog(this, KAlertDialog.ERROR_TYPE)
            .setTitleText(getString(R.string.login_error))
            .setContentText(str)
            .confirmButtonColor(R.color.white)
            .setConfirmClickListener(getString(R.string.OK), R.color.black, null)
            .show()
    }
}