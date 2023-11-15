package org.videolan.vlc.webserver.gui.webserver.onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.core.animation.doOnRepeat
import org.videolan.vlc.webserver.R
import java.security.SecureRandom

class WebserverOnboardingSslFragment : WebserverOnboardingFragment() {
    private lateinit var browserLink: View
    private lateinit var titleView: TextView
    private lateinit var data: TextView
    private val animSet = AnimatorSet()
    override fun getDefaultViewForTalkback() = titleView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.webserver_onboarding_ssl, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleView = view.findViewById(R.id.welcome_title)
        browserLink = view.findViewById(R.id.browser_link)
        data = view.findViewById(R.id.data)

        var iteration = 0
        browserLink.pivotX = 0F
        val slideHorizontalAnimator = ObjectAnimator.ofFloat(browserLink, View.SCALE_X, 0F, 1F)
        slideHorizontalAnimator.interpolator = AccelerateDecelerateInterpolator()
        slideHorizontalAnimator.duration = 2000
        slideHorizontalAnimator.repeatCount = ValueAnimator.INFINITE

        var last = 0L
        slideHorizontalAnimator.addUpdateListener {
            if (System.currentTimeMillis() - last > 150) {
                generateRandomData()
                last = System.currentTimeMillis()
            }
        }
        slideHorizontalAnimator.doOnRepeat {
            when (iteration % 2) {
                0 -> {
                    browserLink.pivotX = browserLink.width.toFloat()
                }

                1 -> {
                    browserLink.pivotX = 0F
                }

            }
            iteration++
        }
        animSet.playTogether(slideHorizontalAnimator)
        animSet.start()
    }

    private fun generateRandomData() {
        data.text = buildString {
            for (i in 0..7) {
                append(if (SecureRandom().nextBoolean()) "1" else "0")
            }
        }
    }

    override fun onDestroy() {
        animSet.cancel()
        super.onDestroy()
    }

    companion object {
        fun newInstance(): WebserverOnboardingSslFragment {
            return WebserverOnboardingSslFragment()
        }
    }
}