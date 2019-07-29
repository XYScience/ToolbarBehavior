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

    private var mDividerParams: AppBarLayout.LayoutParams? = null
    private var mMargin: Int = 0
    private var mMarginLeftRight: Int = 0
    private var mListFirstChildInitY: Int = 0
    private var mDividerAlphaChangeEndY: Int = 0
    private var mDividerAlphaChangeOffset: Int = 0
    private var mDividerWidthChangeEndY: Int = 0
    private var mDividerWidthChangeInitY: Int = 0
    private var mDividerWidthChangeOffset: Int = 0
    private var mDividerInitWidth: Int = 0
    private var mDividerAlphaRange: Float = 0.toFloat()
    private var mDividerWidthRange: Float = 0.toFloat()
    private var mLocationY: Int = 0
    private var mNewOffset: Int = 0
    private var mCurrentOffset: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activiti_web)

        mMargin = resources.getDimensionPixelOffset(R.dimen.divider_margin)
        mMarginLeftRight = 2 * resources.getDimensionPixelOffset(R.dimen.divider_margin)
        mDividerAlphaChangeOffset = resources.getDimensionPixelOffset(R.dimen.line_alpha_range_change_offset)
        mDividerWidthChangeOffset = resources.getDimensionPixelOffset(R.dimen.divider_width_change_offset)
        mDividerParams = divider.layoutParams as AppBarLayout.LayoutParams?
        appbar.post {
            mDividerInitWidth = divider.measuredWidth
            mListFirstChildInitY = appbar.measuredHeight  // 56dp
            mDividerAlphaChangeEndY = mListFirstChildInitY - mDividerAlphaChangeOffset
            mDividerWidthChangeInitY =
                mListFirstChildInitY - resources.getDimensionPixelOffset(R.dimen.divider_width_start_count_offset)
            mDividerWidthChangeEndY = mDividerWidthChangeInitY - mDividerWidthChangeOffset
        }

        web.loadUrl("https://developer.android.google.cn/index.html")

        web.setOnScrollChangeListener { _, _, y, _, _ ->
            startScaleRange(y)
        }
    }

    private fun startScaleRange(scrollY: Int) {

        if (scrollY >= 300) {
            return
        }
        mLocationY = 300 - scrollY
        mNewOffset = 0

        mNewOffset = when {
            mLocationY < mDividerAlphaChangeEndY -> mDividerAlphaChangeOffset
            mLocationY > mListFirstChildInitY -> 0
            else -> //            (0,10)
                mListFirstChildInitY - mLocationY
        }
        mCurrentOffset = mNewOffset
        if (mDividerAlphaRange <= 1) {
            mDividerAlphaRange = abs(mCurrentOffset) / mDividerAlphaChangeOffset.toFloat()
            divider.alpha = mDividerAlphaRange
        }

        mNewOffset = when {
            mLocationY < mDividerWidthChangeEndY -> mDividerWidthChangeOffset
            mLocationY > mDividerWidthChangeInitY -> 0
            else -> //            (10,35)
                mDividerWidthChangeInitY - mLocationY
        }
        mCurrentOffset = mNewOffset
        mDividerWidthRange = 1 - abs(mCurrentOffset) / mDividerWidthChangeOffset.toFloat()
        mDividerParams?.marginStart = (mMargin * mDividerWidthRange).toInt()
        mDividerParams?.marginEnd = (mMargin * mDividerWidthRange).toInt()
        divider.layoutParams = mDividerParams
    }
}
