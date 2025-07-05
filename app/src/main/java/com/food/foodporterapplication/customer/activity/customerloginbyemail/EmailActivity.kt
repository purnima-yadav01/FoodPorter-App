package com.food.foodporterapplication.customer.activity.customerloginbyemail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.cremation.funeralcremation.utils.ErrorUtil
import com.developer.kalert.KAlertDialog
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.searchbyrestanddihses.CustomerUserDashboardActivity
import com.food.foodporterapplication.customer.activity.customerloginbyemail.model.CustomerLoginEmailBody
import com.food.foodporterapplication.customer.activity.customerloginbyemail.model.CustomerLoginEmailModelView
import com.food.foodporterapplication.customer.activity.customersignupapi.SignUpActivity
import com.food.foodporterapplication.customer.activity.forgotpasswordapi.CustomerForgotPasswordActivity
import com.food.foodporterapplication.customer.application.FoodPorter
import com.food.foodporterapplication.databinding.ActivityEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmailBinding
    private val loginEmailModelView: CustomerLoginEmailModelView by viewModels()
    private lateinit var activity: EmailActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        binding.leftArrow.setOnClickListener {
            finish()
        }

        binding.passwordIcon.setImageResource(R.drawable.hidden)
        binding.passwordIcon.setColorFilter(R.color.black)
        binding.passwordIcon.setOnClickListener {
            showHidePassword()
        }

        // Forgot Password
        binding.forgotPasswordText.setOnClickListener {
            startActivity(Intent(this, CustomerForgotPasswordActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            customerLoginEmailApi()
        }

        binding.signupText.setOnClickListener {
            val i = Intent(this@EmailActivity, SignUpActivity::class.java)
            startActivity(i)
        }

        customerLoginEmailObserver()
    }

    private fun showHidePassword() {
        if (binding.editTextPassword.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
            binding.editTextPassword.transformationMethod =
                PasswordTransformationMethod.getInstance()
            binding.passwordIcon.setImageResource(R.drawable.hidden)
        } else {
            binding.editTextPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            binding.passwordIcon.setImageResource(R.drawable.visible_password)

        }
    }

    private fun customerLoginEmailApi() {
        val email = binding.emailAddressEt.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        if (email.isEmpty()) {
            errorDialogs("Please enter your email address")
            return
        }

        if (password.isEmpty()) {
            errorDialogs("Please enter your password")
            return
        }

        val loginBody = CustomerLoginEmailBody(email = email, password = password)
        loginEmailModelView.loginUpUser(this, loginBody)

    }

    private fun customerLoginEmailObserver() {
        loginEmailModelView.progressIndicator.observe(this) {
        }

        loginEmailModelView.mLoginResponse.observe(this) { event ->
            event.getContentIfNotHandled()?.let { response ->
                val message = response.message
                val success = response.success

                if (success == true) {
                    FoodPorter.encryptedPrefs.token = response.token.toString()
                    FoodPorter.encryptedPrefs.userId = response.user.toString()
                    FoodPorter.encryptedPrefs.isFirstTime = false

                    Toast.makeText(this@EmailActivity, message, Toast.LENGTH_LONG).show()
                    val intent = Intent(this@EmailActivity, CustomerUserDashboardActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@EmailActivity, message, Toast.LENGTH_LONG).show()
                }
            }
        }

        loginEmailModelView.errorResponse.observe(this) {
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