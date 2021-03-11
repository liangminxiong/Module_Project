package com.lmx.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.lmx.common.ext.logd
import java.util.*

class HomeMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity_main)
        val items: MutableList<String> =
            ArrayList()
        for (i in 10..30) {
            if (i == 0) {
                items.add("免费")
            } else {
                items.add(i.toString() + "钻")
            }
        }

        findViewById<TextView>(R.id.tvHomeDate).setOnClickListener {
            val pvOptions = OptionsPickerBuilder(this,
                OnOptionsSelectListener { options1: Int, options2: Int, options3: Int, view: View? ->
                    "$options1 + $options2 + $options3 + view = $view".logd()
                }
            ).setItemVisibleCount(7).setLineSpacingMultiplier(2.5f).build<String>()

            pvOptions.setSelectOptions(0)
            pvOptions.setPicker(items)
            pvOptions.show()
        }
    }

}