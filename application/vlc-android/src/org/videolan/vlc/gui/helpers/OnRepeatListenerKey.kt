/*
 * ************************************************************************
 *  OnRepeatListenerKey.kt
 * *************************************************************************
 * Copyright © 2020 VLC authors and VideoLAN
 * Author: Nicolas POMEPUY
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 * **************************************************************************
 *
 *
 */

package org.videolan.vlc.gui.helpers

import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.Lifecycle

/**
 *
 * @param initialInterval Initial interval in millis
 * @param normalInterval Normal interval in millis
 * @param clickListener The OnClickListener to trigger
 */
class OnRepeatListenerKey(private val initialInterval: Int = DEFAULT_INITIAL_DELAY, private val normalInterval: Int = DEFAULT_NORMAL_DELAY, speedUpDelay: Int = DEFAULT_SPEEDUP_DELAY, private val clickListener: View.OnClickListener, listenerLifecycle: Lifecycle) : View.OnKeyListener, OnRepeatListener(initialInterval, normalInterval, speedUpDelay, clickListener, listenerLifecycle) {

    override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (view == null || event == null) return false
        if (keyCode != KeyEvent.KEYCODE_DPAD_CENTER) return false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
               if (downView != view) startRepeating(view)
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                stopRepeating(view)
                return true
            }
        }
        return false
    }

}
