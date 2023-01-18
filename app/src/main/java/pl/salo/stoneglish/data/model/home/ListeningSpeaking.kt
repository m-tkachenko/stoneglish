package pl.salo.stoneglish.data.model.home

import com.google.firebase.database.Exclude

data class ListeningSpeaking(
    val text: String = "",
    val translatedText: String = "",
    @Exclude var isSpeaking: Boolean = false
)
