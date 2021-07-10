package com.example.android.tasks

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpDelete
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
            listener.onPre();
            val url = "$API_URL$path"
            url.httpGet()
                .responseObject(deserializer) { _, _, result ->
                    when (result) {
                        is Result.Success -> listener.onSuccess(result.value)
                        is Result.Failure -> listener.onFailure(result)
                    }
                }
        }

        fun delete(path: String) {
            listener.onPre()
            val url = "$API_URL$path"
            url.httpDelete()
                .responseString { _, _, result ->
                    when (result) {
                        is Result.Success -> listener.onSuccess(result.value)
                        is Result.Failure -> listener.onFailure(result)
                    }
                }
        }
    }

    interface ApiListener {
        fun onPre()
        fun onSuccess(result: Any)
        fun onFailure(result: Result.Failure<FuelError>)
    }
}