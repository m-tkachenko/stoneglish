package pl.salo.stoneglish.data.model.home

import com.google.firebase.database.Exclude

data class Keyword(
    val word: String = "",
    val phonetic: String = "",
    val translate: String = "",
    @Exclude var isSpeaking: Boolean = false
)