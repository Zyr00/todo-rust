package com.example.android.tasks

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class ApiTask {
    companion object {
        private const val API_URL = "http://192.168.1.120:5000/api"
        private lateinit var listener: ApiListener

        fun setListener(_listener: ApiListener) {
            listener = _listener
        }

        fun <T : Any> get(path: String, deserializer: ResponseDeserializable<T>) {
            val url = "$API_URL$path"
            url.httpGet()
                .responseObject(deserializer) { _, _, result ->
                    when (result) {
                        is Result.Success -> listener.onSuccess(result.value)
                        is Result.Failure -> listener.onFailure(result)
                        else -> listener.onOther(result)
                    }
                }
        }
    }

    interface ApiListener {
        fun onSuccess(result: Any)
        fun onFailure(result: Result.Failure<FuelError>)
        fun onOther(result: Result<Any, FuelError>)
    }
}