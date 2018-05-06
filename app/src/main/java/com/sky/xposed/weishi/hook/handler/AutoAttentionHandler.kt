/*
 * Copyright (c) 2018. The sky Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.xposed.weishi.hook.handler

import android.view.View
import com.sky.xposed.weishi.hook.HookManager
import com.sky.xposed.weishi.util.RandomUtil
import de.robv.android.xposed.XposedHelpers

class AutoAttentionHandler(hookManager: HookManager) : CommonHandler(hookManager), Runnable {

    var mPosition: Int = 0

    override fun run() {

        // 获取当前显示的ViewHolder
        val viewHolder = getViewHolder(mPosition) ?: return

        val view = XposedHelpers
                .getObjectField(viewHolder, "A") as View

        // 点赞
        mainPerformClick(view)
    }

    fun attention(position: Int) {

        mPosition = position

        if (!getConfigManager().isAutoAttention()) {
            // 不需要处理
            return
        }

        // 开始点赞
        getHandler().postDelayed(this, RandomUtil.randomLong(3000, 3500))
    }

    fun cancel() {
        mPosition = -1
        getHandler().removeCallbacks(this)
    }
}