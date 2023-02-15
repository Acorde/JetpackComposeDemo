package com.igor.animations

class TestClass {

    fun test(){
        UserFactory.getUser(UserTypes.LOGIN_USER){

        }

        UserFactory.showDialog<DialogManager.TitleBuilder>(DialogTypes.TITLE_DIALOG){

        }
    }
}