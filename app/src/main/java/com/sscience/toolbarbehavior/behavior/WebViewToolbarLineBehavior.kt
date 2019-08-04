package com.sscience.toolbarbehavior.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.sscience.toolbarbehavior.R

/**
 * @author SScience
 * @description
 * @email chentushen.science@gmail.com
 * @data 2019-08-04
 */
class WebViewToolbarLineBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<AppBarLayout>(context, attrs) {

    private var mDividerParams: AppBarLayout.LayoutParams? = null
    private var mMargin: Int = 0
    private var mDividerAlphaChangeOffset: Float = 100f
    private var mDividerWidthChangeOffset: Float = 100f
    private var mDividerAlphaRange: Float = 0f
    private var mDividerWidthRange: Float = 0f
    private var mLocationY: Int = 0
    private var mNewOffset: Int = 0
    private var mCurrentOffset: Float = 0f

    init {
        mMargin = context.resources.getDimensionPixelOffset(R.dimen.divider_margin)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: AppBarLayout, dependency: View): Boolean {
        return dependency is WebView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: AppBarLayout, dependency: View): Boolean {
        if (dependency is WebView) {
            val lp: CoordinatorLayout.LayoutParams = dependency.layoutParams as CoordinatorLayout.LayoutParams
            if (lp.topMargin != child.bottom) {
                lp.topMargin = child.bottom
                dependency.layoutParams = lp
                val dividerView: View = child.findViewById(R.id.divider)
                mDividerParams = dividerView.layoutParams as AppBarLayout.LayoutParams?
                val screenWidth = child.width
                dependency.setOnScrollChangeListener { _, _, y, _, _ ->
                    onStartScroll(dividerView, screenWidth, y)
                }
            }
        }
        return true
    }

    private fun onStartScroll(dividerView: View, screenWidth: Int, scrollY: Int) {
        if (scrollY >= 500) {
            return
        }
        mLocationY = scrollY // (0, 500px)
        mNewOffset = 0

        mNewOffset = when {  // offset: (0, 100px)
            mLocationY < 50 -> 0
            mLocationY > 150 -> 100
            else -> mLocationY - 50
        }
        mCurrentOffset = mNewOffset.toFloat()
        if (mDividerAlphaRange <= 1) {
            mDividerAlphaRange = mCurrentOffset / mDividerAlphaChangeOffset
            dividerView.alpha = mDividerAlphaRange
        }

        mNewOffset = when {  // offset: (0, 100px)
            mLocationY < 140 -> 0
            mLocationY > 240 -> 100
            else -> mLocationY - 140
        }
        mCurrentOffset = mNewOffset.toFloat()
        mDividerWidthRange = 1 - mCurrentOffset / mDividerWidthChangeOffset
        mDividerParams?.let {
            it.marginStart = (mMargin * mDividerWidthRange).toInt()
            it.marginEnd = (mMargin * mDividerWidthRange).toInt()
            dividerView.layoutParams = it
        }
    }
}
