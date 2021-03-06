package com.heybeach.profile.domain.data

import com.heybeach.http.HttpParams
import com.heybeach.http.RequestMethod
import com.heybeach.http.executeHttpRequest
import com.heybeach.http.readResponseAndClose
import com.heybeach.profile.domain.model.SignupModel
import com.heybeach.profile.domain.model.User
import com.heybeach.profile.domain.model.UserResponse
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import java.io.InputStream

const val AUTH = "x-auth"

class AuthService {

    fun signUp(email: String, password: String) = doAuth("user/register", email, password)

    fun login(email: String, password: String) = doAuth("user/login", email, password)

    fun logOut(authToken: String): Deferred<Any> {
        val params = HttpParams("user/logout", RequestMethod.DELETE, headers = hashMapOf(AUTH to authToken))
        return executeHttpRequest(params) { _, _ -> Any() }
    }

    fun fetchUser(authToken: String): Deferred<User> {
        val params = HttpParams("user/me", RequestMethod.GET, headers = hashMapOf(AUTH to authToken))
        return executeHttpRequest(params) { stream, _ ->
            parseUser(stream)
        }
    }

    private fun doAuth(path: String, email: String, password: String): Deferred<UserResponse> {
        val payload = SignupModel(email, password).toJsonString()
        val params = HttpParams(path, RequestMethod.POST, body = payload)
        return executeHttpRequest(params) { stream, headers ->
            val user = parseUser(stream)
            val authToken = headers[AUTH]!![0]
            UserResponse(user, authToken)
        }
    }
}

fun parseUser(stream: InputStream): User {
    val responseString = stream.readResponseAndClose()
    val jsonObject = JSONObject(responseString)
    return User(jsonObject.optString("_id"), jsonObject.optString("email"))
}