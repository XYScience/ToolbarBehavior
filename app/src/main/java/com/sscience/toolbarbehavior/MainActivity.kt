package com.sscience.toolbarbehavior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sscience.toolbarbehavior.activity.WebViewActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClick(v: View?) {
        var intent: Intent? = null
        when (v?.id) {
            R.id.btnWebView -> intent = Intent(this@MainActivity, WebViewActivity::class.java)
        }
        startActivity(intent)
    }
}
