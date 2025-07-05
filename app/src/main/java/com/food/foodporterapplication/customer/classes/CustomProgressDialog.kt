package com.food.foodporterapplication.customer.classes

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.food.foodporterapplication.R

@SuppressLint("MissingInflatedId")
class CustomProgressDialog(context: Context) {

    private var dialog: CustomDialog
    private var cpTitle: TextView
    private var cpCardView: CardView
    private var progressBar: ProgressBar

    fun start(title: String = "") {
        if (dialog.context is Activity) {
            val activity = dialog.context as Activity
            if (!activity.isFinishing && !activity.isDestroyed) {
                cpTitle.text = title
                dialog.show()
            } else {

            }
        } else {

        }
    }

    fun stop() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            safeDismiss()
        } else {
            Handler(Looper.getMainLooper()).post {
                safeDismiss()
            }
        }
    }

    private fun safeDismiss() {
        if (dialog.isShowing) {
            val ctx = dialog.context
            if (ctx is Activity) {
                if (!ctx.isFinishing && !ctx.isDestroyed) {
                    try {
                        dialog.dismiss()
                    } catch (e: Exception) {

                    }
                } else {
                }
            } else {

            }
        } else {

        }
    }

    init {
        val inflater = (context as Activity).layoutInflater
        val view = inflater.inflate(R.layout.progress_dialog, null)

        cpTitle = view.findViewById(R.id.cp_title)
        cpCardView = view.findViewById(R.id.cp_cardview)
        progressBar = view.findViewById(R.id.cp_pbar)

        // Card Color
        cpCardView.setCardBackgroundColor(Color.parseColor("#A47D2D"))

        // Progress Bar Color
        setColorFilter(progressBar.indeterminateDrawable, ResourcesCompat.getColor(context.resources, R.color.black, null))

        // Text Color
        cpTitle.setTextColor(ResourcesCompat.getColor(context.resources, R.color.black, null))

        // Custom Dialog initialization
        dialog = CustomDialog(context)
        dialog.setContentView(view)
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    class CustomDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
        init {
            window?.decorView?.rootView?.setBackgroundResource(R.color.background_color)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}