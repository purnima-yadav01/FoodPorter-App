package com.food.foodporterapplication.customer.activity.confirmpasswordapi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.customer.activity.confirmpasswordapi.model.ConfirmPasswordBody
import com.food.foodporterapplication.customer.activity.confirmpasswordapi.model.ConfirmPasswordModelView
import com.food.foodporterapplication.customer.activity.loginwithphonemunberapi.CustomerLoginActivity
import com.food.foodporterapplication.customer.activity.otpverificationapi.CustomerOtpVerifyActivity
import com.food.foodporterapplication.databinding.ActivityConfirmPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmPasswordBinding
    private val confirmPasswordModelView: ConfirmPasswordModelView by viewModels()
    private lateinit var activity: Activity
    private var email = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConfirmPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this

        email = intent.getStringExtra("email").toString()

        binding.forgotPassArrowImg.setOnClickListener {
            finish()
        }

        binding.sendConfirmButton.setOnClickListener {
            if (binding.editTextNewPassword.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter new password", Toast.LENGTH_LONG).show()
            } else if (binding.editTextconfirmPassword.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_LONG).show()
            } else {
                confirmPasswordApi()
            }
        }


        confirmPasswordObserver()
    }

    private fun confirmPasswordApi() {
        val body = ConfirmPasswordBody(
            email = email,
            newPassword = binding.editTextNewPassword.text.toString(),
            confirmPassword = binding.editTextconfirmPassword.text.toString()
        )
        confirmPasswordModelView.confirmPasswordUser(activity, body)

    }

    private fun confirmPasswordObserver() {
        confirmPasswordModelView.progressIndicator.observe(this) {}
        confirmPasswordModelView.mConfirmPasswordResponse.observe(this) {
            val success = it.peekContent().success
            val message = it.peekContent().message
            try {
                if (success == true) {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    val i = Intent(this@ConfirmPasswordActivity, CustomerLoginActivity::class.java)
                    startActivity(i)

                } else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        confirmPasswordModelView.errorResponse.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }
}
