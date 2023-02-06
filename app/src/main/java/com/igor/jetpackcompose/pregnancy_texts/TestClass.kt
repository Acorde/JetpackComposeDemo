package com.igor.jetpackcompose.pregnancy_texts

class TestClass {

    fun test(){
        MeuhedetText(DefaultTexts.CONTINUE)
    }

}

class MeuhedetText(val moduleText : ModuleTexts){

    fun getData(){
        moduleText.getCongiguration()
        moduleText.getDefaultText()
    }
}