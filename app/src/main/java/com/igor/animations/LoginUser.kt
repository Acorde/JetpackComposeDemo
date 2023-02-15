package com.igor.animations

data class LoginUser(val userName : String, val token : String) : BaseUser() {

    class Builder : BaseBuilder(){
        var userName : String? = null
        var userToken : String? = null

        fun withUserName(n : String) = apply { userName = n }
        fun withToken(t : String) = apply { userToken = t }


        fun build() = LoginUser(userName!!, userToken!!)
    }
}