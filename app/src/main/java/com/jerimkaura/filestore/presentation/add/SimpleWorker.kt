package com.jerimkaura.filestore.presentation.add

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jerimkaura.filestore.util.showAlert

class SimpleWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {
    override fun doWork(): Result {
        showAlert(applicationContext, "Work started for client" )
        Log.d("==============>", "doWork: WORK!!!")
        return Result.Success()
    }
}