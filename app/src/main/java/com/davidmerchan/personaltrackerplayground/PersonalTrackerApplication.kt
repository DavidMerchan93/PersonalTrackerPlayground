package com.davidmerchan.personaltrackerplayground

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for Personal Tracker Playground.
 * Annotated with [@HiltAndroidApp] to trigger Hilt's code generation and serve as the application-level dependency container.
 */
@HiltAndroidApp
class PersonalTrackerApplication : Application()
