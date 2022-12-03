package com.example.crm.utils

import android.app.Activity
import android.text.TextUtils
import android.view.View
import com.example.crm.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class Helper {
companion object {
    fun validateWidget(textInputLayout: TextInputLayout): Pair<Boolean, String> {
        val text = textInputLayout.editText?.text.toString().trim()
        val pair: Pair<Boolean, String> =
            if (TextUtils.isEmpty(text)) {
                val message = textInputLayout.context.getString(R.string.empty_fields_hint)
                Pair(false, message)
            } else {
                textInputLayout.error = null
                Pair(true, "")
            }

        return pair
    }

    fun showSnackBar(activity: Activity, message: String?) {
        val rootView: View = activity.window.decorView.findViewById(android.R.id.content)
        Snackbar.make(rootView, message!!, Snackbar.LENGTH_LONG).show()
    }
}
}