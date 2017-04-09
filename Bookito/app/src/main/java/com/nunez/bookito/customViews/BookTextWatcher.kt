package com.nunez.bookito.customViews

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by paulnunez on 3/21/17.
 */

class BookTextWatcher(val listener: (String) -> Unit) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        listener.invoke(s.toString())
    }

    fun onTextChangedListener(listener: (String) -> Unit) {}

}
