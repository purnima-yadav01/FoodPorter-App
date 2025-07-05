package com.food.foodporterapplication.customer.activity.customersignupapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.cremation.funeralcremation.utils.ErrorUtil
import com.food.foodporterapplication.R
import com.food.foodporterapplication.customer.activity.loginwithphonemunberapi.CustomerLoginActivity
import com.food.foodporterapplication.customer.activity.customerloginbyemail.EmailActivity
import com.food.foodporterapplication.customer.activity.customersignupapi.model.CustomerSignUpBody
import com.food.foodporterapplication.customer.activity.customersignupapi.model.CustomerSignUpModelView
import com.food.foodporterapplication.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val customerSignUpModelView: CustomerSignUpModelView by viewModels()
    private lateinit var activity: SignUpActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        binding.signUpBtn.setOnClickListener {
            customerSignUpAPi()
        }

        binding.loginTextview.setOnClickListener {
            val i = Intent(this@SignUpActivity, CustomerLoginActivity::class.java)
            startActivity(i)
        }

        binding.passwordIcon.setImageResource(R.drawable.hidden)
        binding.passwordIcon.setColorFilter(R.color.black)
        binding.passwordIcon.setOnClickListener {
            showHidePassword()
        }

        customerSignUpObserver()

    }

    private fun customerSignUpAPi() {
        val signUpBody = CustomerSignUpBody(
            firstName = binding.userFirstName.text.toString(),
            lastName = binding.lastNameEt.text.toString(),
            email = binding.emailAddressEt.text.toString(),
            countryCode = binding.spinnerCountryCode.selectedCountryCodeWithPlus.toString(),
            phone = binding.phoneEtName.text.toString(),
            password = binding.passwordEt.text.toString(),
            role = "customer"
        )

        customerSignUpModelView.signUpUser(this, signUpBody)
    }

    private fun customerSignUpObserver() {
        customerSignUpModelView.progressIndicator.observe(this, Observer {

        })
        customerSignUpModelView.mRegisterResponse.observe(this) {
            var message = it.peekContent().message
            var success = it.peekContent().success

            if (success == true) {

                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                val intent = Intent(this@SignUpActivity, EmailActivity::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            }
        }
        customerSignUpModelView.errorResponse.observe(this){
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    private fun showHidePassword() {
        if (binding.passwordEt.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
            binding.passwordEt.transformationMethod =
                PasswordTransformationMethod.getInstance()
            binding.passwordIcon.setImageResource(R.drawable.hidden)
        } else {
            binding.passwordEt.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            binding.passwordIcon.setImageResource(R.drawable.visible_password)

        }
    }
}