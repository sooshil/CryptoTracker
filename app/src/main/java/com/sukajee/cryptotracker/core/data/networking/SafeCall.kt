package com.sukajee.cryptotracker.core.data.networking

import com.sukajee.cryptotracker.core.domain.util.NetworkError
import com.sukajee.cryptotracker.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET_CONNECTION)
    } catch (e: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION_ERROR)
    } catch (e: Exception) {
        // This can also catch the coroutine cancellation exception.
        coroutineContext.ensureActive() // cancel job on cancellation exception, we rethrow and parent knows about it.
        return Result.Error(NetworkError.UNKNOWN_ERROR)
    }

    return responseToResult(response)
}