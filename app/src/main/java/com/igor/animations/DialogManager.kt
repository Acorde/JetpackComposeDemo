package com.igor.animations

import android.content.Context

class DialogManager {
    constructor(title: String)
    constructor(context: Context, title: String)
    constructor(context: Context, title: String, message: String)


     class TitleBuilder {
        var mTitle: String? = null

        fun setTitle(title: String) = apply { mTitle = title }

        fun build() = DialogManager(title = mTitle!!)

     }

    class ContextDialogBuilder : GenericBuilder<ContextDialogBuilder>(){
        var mContext: Context? = null
        var mTitle: String? = null

        fun setTitle(title: String) = apply { mTitle = title }
        fun setContext(context: Context) = apply { mContext = context }

        fun build() = DialogManager(mContext!!, mTitle!!)
    }

    class MessageContextDialogBuilder : GenericBuilder<MessageContextDialogBuilder>() {
        var mContext: Context? = null
        var mTitle: String? = null
        var mMessage : String? = null

        fun setTitle(title: String) = apply { mTitle = title }
        fun setContext(context: Context) = apply { mContext = context }
        fun setMessage(message : String) = apply { mMessage = message }

        fun build() = DialogManager(mContext!!, mTitle!!, mMessage!!)
    }

}

