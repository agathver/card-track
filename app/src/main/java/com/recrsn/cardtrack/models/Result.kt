package com.recrsn.cardtrack.models

class Result<T>(val data: T?, val error: String? = null) {
    fun <U> map(func: (it: T) -> U): Result<U> {
        return if (data != null) {
            success(func.invoke(data))
        } else {
            @Suppress("UNCHECKED_CAST")
            this as Result<U>
        }
    }

    val success = data != null

    companion object {
        fun <T> success(data: T) = Result(data)
        fun <T> error(error: String): Result<T> = Result(null, error)
    }
}
