package com.igor.jetpackcompose.pregnancy_texts

import com.igor.jetpackcompose.R


interface ModuleTexts{
    fun getCongiguration() : String?
    fun getDefaultText() : Int
    fun getData(){

    }
}
//
enum class DefaultTexts : ModuleTexts{
    CONTINUE;

    override fun getCongiguration(): String? {
        TODO("Not yet implemented")
    }

    override fun getDefaultText(): Int {
        TODO("Not yet implemented")

    }
    }
enum class PregnancyTexts : ModuleTexts {
    TITLE {
        override fun toString(): String {
            return super<PregnancyTexts>.toString()
        }
        override fun getCongiguration(): String? {
           return "Personal :: Corona Area :: Sections :: Questionnaire :: Questionnaire View :: Saturation"
        }

        override fun getDefaultText(): Int {
           return R.string.app_name
        }
    }, SITURATION {
        override fun getCongiguration(): String? {
            return "Personal :: Corona Area :: Sections :: Questionnaire :: Questionnaire View :: Saturation"
        }

        override fun getDefaultText(): Int {
            return R.string.app_name
        }
    }, NAME;

    override fun getCongiguration(): String? {
        return "Personal :: Corona Area :: Sections :: Questionnaire :: Questionnaire View :: Saturation"
    }

    override fun getDefaultText(): Int {
        return R.string.app_name
    }}

