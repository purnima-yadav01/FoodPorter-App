package com.food.foodporterapplication.customer.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActionBar
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.food.foodporterapplication.R
import com.food.foodporterapplication.databinding.ActivityRatingBinding

class RatingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRatingBinding
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBackImg.setOnClickListener {
            finish()
        }

        binding.rateYourPage.setOnClickListener {
            ratingDialog()
        }

    }

    private fun ratingDialog() {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.rating_dialog)
        val ratingBar = dialog.findViewById<RatingBar>(R.id.ratingBar)
        val ratingsText = dialog.findViewById<TextView>(R.id.ratingsText)
        val submitBtn = dialog.findViewById<AppCompatButton>(R.id.submitBtn)
        val cancelBtn = dialog.findViewById<AppCompatButton>(R.id.cancelBtn)

        // Set dialog window size
        val window = dialog.window
        val lp = window?.attributes
        lp?.apply {
            width = ActionBar.LayoutParams.MATCH_PARENT
            height = ActionBar.LayoutParams.WRAP_CONTENT
        }

        window?.attributes = lp

        // Zoom-in animation when showing the dialog
        val scaleX = ObjectAnimator.ofFloat(dialog.window?.decorView, "scaleX", 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(dialog.window?.decorView, "scaleY", 0f, 1f)
        val animatorSet = AnimatorSet().apply {
            play(scaleX).with(scaleY)
            duration = 400
        }

        dialog.setOnShowListener {
            animatorSet.start()
        }

        // Zoom-out animation when dismissing the dialog
        dialog.setOnDismissListener {
            val dismissScaleX = ObjectAnimator.ofFloat(dialog.window?.decorView, "scaleX", 1f, 0f)
            val dismissScaleY = ObjectAnimator.ofFloat(dialog.window?.decorView, "scaleY", 1f, 0f)
            val dismissAnimatorSet = AnimatorSet().apply {
                play(dismissScaleX).with(dismissScaleY)
                duration = 300
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        dialog.dismiss()
                    }
                })
            }

            dismissAnimatorSet.start()
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        submitBtn.setOnClickListener {
          dialog.dismiss()
        }

        dialog.show()
    }

}