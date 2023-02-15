package com.igor.animations

data class NotLoginUser(val userName : String, val age : String) : BaseUser() {

    class Builder : BaseBuilder(){
        var userName : String? = null
        var userAge : String? = null

        fun withUserName(n : String) = apply { userName = n }
        fun withToken(a : String) = apply { userAge = a}


        fun build() = NotLoginUser(userName!!, userAge!!)
    }
}