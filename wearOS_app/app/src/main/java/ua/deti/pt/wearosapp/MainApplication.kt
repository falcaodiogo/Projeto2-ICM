package ua.deti.pt.wearosapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application()

const val TAG = "Measuring Data Sample"