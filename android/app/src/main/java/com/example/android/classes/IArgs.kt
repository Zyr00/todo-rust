package com.example.android.classes

import android.content.Context
import android.content.Intent
import android.os.Bundle

interface IArgs {
    fun fromArguments(arguments: Bundle): Todo
    fun toArguments(todo: Todo): Bundle
    fun <C> toIntent(todo: Todo, context: Context?, activity: Class<C>): Intent
    fun fromIntent(intent: Intent?): Todo
}