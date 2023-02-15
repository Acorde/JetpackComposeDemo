package com.igor.animations

import kotlin.experimental.ExperimentalTypeInference

class UserFactory {

    companion object {
        fun getUser(userType : UserTypes, init : BaseBuilder.() -> Unit){
            when(userType){
                UserTypes.LOGIN_USER -> LoginUser.Builder().apply(init).build()
                UserTypes.NOT_LOGIN_USER -> NotLoginUser.Builder().apply(init).build()
            }
        }

        @OptIn(ExperimentalTypeInference::class)
        fun <T> test(@BuilderInference init : GenericBuilder<T>.() -> Unit){

        }
        @OptIn(ExperimentalTypeInference::class)
        fun <T> showDialog(dialogType : DialogTypes, @BuilderInference init : GenericBuilder<T>.() -> Unit){
            val value = GenericBuilder<T>().apply(init).fn!!()
            (value as DialogManager.TitleBuilder).build()
        }
    }

    fun test(){
        myDsl{
            getValue {
                DialogManager.TitleBuilder()
            }
        }
    }




}

@OptIn(ExperimentalTypeInference::class)
fun <T> myDsl(@BuilderInference specFn: DslSpec<T>.() -> Unit) {
    val value = DslSpec<T>().apply(specFn).fn!!()
    (value as DialogManager.TitleBuilder).build()
}

class DslSpec<T> {
    internal var fn: (() -> T)? = null
    fun getValue(fn: () -> T) {
        this.fn = fn
    }
}

//@OptIn(ExperimentalTypeInference::class)
//fun <T> myDsl(@BuilderInference specFn: GenericBuilder<T>.() -> Unit) {
//    val value = GenericBuilder<T>().apply(specFn).fn!!()
//}

open class GenericBuilder<T>{
    internal var fn : (() -> T)? = null
    fun getValue(fn: () -> T){
        this.fn = fn
    }
}


enum class DialogTypes{
    TITLE_DIALOG,
    CONTEXT_DIALOG,
    MESSAGE_DIALOG

}

enum class UserTypes{
    LOGIN_USER,
    NOT_LOGIN_USER
}