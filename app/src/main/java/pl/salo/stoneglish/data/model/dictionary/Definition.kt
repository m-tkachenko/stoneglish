package pl.salo.stoneglish.data.model.dictionary

data class Definition(
    val antonyms: List<Any>,
    val definition: String,
    val example: String,
    val synonyms: List<String>
)