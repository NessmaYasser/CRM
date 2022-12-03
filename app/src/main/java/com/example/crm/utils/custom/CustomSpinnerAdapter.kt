package com.example.crm.utils.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.example.crm.R
import kotlinx.android.synthetic.main.unit_spinner_item.view.*

class CustomSpinnerAdapter(context: Context, dropDownResId : Int, var data : List<String>): ArrayAdapter<String>(context, dropDownResId, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    /**
     * Gets a view for spinner dropdown and spinner selected item
     *
     * @param position: position in data list
     * @param convertView: view to recycle
     * @param parent: parent container
     * @param dropdown: to differentiate between dropdown view and selected view
     */
    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.unit_spinner_item, parent, false)

        if(position == 0)
            view.unit_name_tv.setTextColor(ContextCompat.getColor(context, R.color.warm_gray))
        else
            view.unit_name_tv.setTextColor(ContextCompat.getColor(context, R.color.green_500))

        view.unit_name_tv.text = data[position]
        view.unit_tv.visibility = View.GONE
        return view
    }

}