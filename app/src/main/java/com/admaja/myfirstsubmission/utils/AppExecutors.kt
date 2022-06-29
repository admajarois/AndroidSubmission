package com.admaja.myfirstsubmission.utils

import android.os.Looper
import android.os.Handler
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {
    val diskIO: Executor = Executors.newSingleThreadExecutor()

    private class MainThreadExecutor: Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }

    }
}