package com.sscience.toolbarbehavior.activity

import android.os.Bundle
import android.util.Log
import com.sscience.toolbarbehavior.BaseActivity
import android.view.ViewGroup
import com.google.android.material.appbar.AppBarLayout
import com.sscience.toolbarbehavior.R
import kotlinx.android.synthetic.main.activiti_web.*
import kotlinx.android.synthetic.main.divider.*
import kotlin.math.abs


/**
 * @author SScience
 * @description
 * @email chentushen.science@gmail.com
 * @data 2019-07-28
 */
class WebViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activiti_web)

        web.loadUrl("https://developer.android.google.cn/index.html")
    }
}
