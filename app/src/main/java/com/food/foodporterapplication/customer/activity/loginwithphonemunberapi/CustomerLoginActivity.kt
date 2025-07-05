package com.food.foodporterapplication.customer.activity.loginwithphonemunberapi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.customer.activity.forgotpasswordapi.CustomerForgotPasswordActivity
import com.food.foodporterapplication.customer.activity.loginotpapi.LoginWithOtpActivity
import com.food.foodporterapplication.customer.activity.customerloginbyemail.EmailActivity
import com.food.foodporterapplication.customer.activity.customersignupapi.SignUpActivity
import com.food.foodporterapplication.customer.activity.loginwithphonemunberapi.model.LoginPhoneNumberBody
import com.food.foodporterapplication.customer.activity.loginwithphonemunberapi.model.LoginPhoneNumberModelView
import com.food.foodporterapplication.databinding.ActivityCustomerLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerLoginBinding
    private val loginNumberModelView: LoginPhoneNumberModelView by viewModels()
    private lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        // Back arrow
        binding.leftArrow.setOnClickListener {
            finish()
        }

        // Observers
        loginNumberObserver()

        // Go to signup
        binding.loginText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // Switch to email login
        binding.emailLayout.setOnClickListener {
            startActivity(Intent(this, EmailActivity::class.java))
        }

        // Login button click
        binding.loginBtn.setOnClickListener {
            loginNumberApi()
        }
    }

    private fun loginNumberApi() {
        val phoneNumber = binding.phoneEtName.text.toString().trim()

        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show()
            return
        }

        val body = LoginPhoneNumberBody(phone = phoneNumber)
        loginNumberModelView.loginPhoneUser(activity, body)
    }

    private fun loginNumberObserver() {
        // Loading spinner handler
        loginNumberModelView.progressIndicator.observe(this) { show ->
            // Optional: Handle loading spinner
        }

        // ✅ API response observer
        loginNumberModelView.mLoginPhoneNumberResponse.observe(this) {
            val response = it.peekContent() // Or use getContentIfNotHandled() if needed

            if (response.success == true) {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginWithOtpActivity::class.java)
                intent.putExtra("phone", binding.phoneEtName.text.toString().trim())
                startActivity(intent)

            } else {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            }
        }

        // ✅ Error response should be outside main response block
        loginNumberModelView.errorResponse.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

}
