package com.enjot.materialweather.data.util

import androidx.work.WorkInfo


fun WorkInfo.State.isEnqueuedOrRunning(): Boolean {
    return this == WorkInfo.State.ENQUEUED || this == WorkInfo.State.RUNNING
}