package com.example.android.classes

import android.content.Context
import android.content.Intent
import android.os.Bundle

interface IArgs<T> {
    fun fromArguments(arguments: Bundle): T
    fun toArguments(value: T): Bundle
    fun <C> toIntent(value: T?, type: String, context: Context?, activity: Class<C>): Intent
    fun fromIntent(intent: Intent?): T
}