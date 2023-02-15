package com.igor.animations

import androidx.annotation.DrawableRes

class MyDialog {
    private var title: String? = null
    private var content: String? = null
    private var confirmButtonTitle: String? = null
    private var rejectButtonTitle: String? = null

    @DrawableRes
    private var icon: Int? = null


    fun show() {
        // set dialog content here and show at the end
    }

    class Builder {
        private var dialog: MyDialog = MyDialog()

        fun title(title: String) = apply { dialog.title = title }

        fun icon(@DrawableRes icon: Int) = apply { dialog.icon = icon }

        fun content(content: String) = apply { dialog.content = content }

        fun confirmTitle(confirmTitle: String) = apply { dialog.confirmButtonTitle = confirmTitle }

        fun rejectButtonTitle(rejectButtonTitle: String) = apply { dialog.rejectButtonTitle = rejectButtonTitle }

        fun build() = dialog
    }
}