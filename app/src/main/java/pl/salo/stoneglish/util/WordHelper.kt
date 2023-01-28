package pl.salo.stoneglish.util

import pl.salo.stoneglish.data.model.dictionary.Definition
import pl.salo.stoneglish.data.model.dictionary.Meaning

class WordHelper

fun List<Meaning>.getNoun():List<Definition>{
    return this.firstOrNull() { it.partOfSpeech == "noun" }?.definitions ?: emptyList()
}

fun List<Meaning>.getVerb():List<Definition>{
    return this.firstOrNull() { it.partOfSpeech == "verb" }?.definitions ?: emptyList()
}
