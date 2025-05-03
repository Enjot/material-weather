package com.enjot.materialweather.settings.data

import androidx.work.WorkInfo


fun WorkInfo.State.isEnqueuedOrRunning(): Boolean {
    return this == WorkInfo.State.ENQUEUED || this == WorkInfo.State.RUNNING
}