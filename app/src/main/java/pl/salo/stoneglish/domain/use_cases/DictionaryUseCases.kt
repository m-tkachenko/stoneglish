package pl.salo.stoneglish.domain.use_cases

import pl.salo.stoneglish.domain.use_cases.dictionary.DictionaryGetWordDataUseCase
import pl.salo.stoneglish.domain.use_cases.dictionary.PlayAudioByUrl

data class DictionaryUseCases(
    val dictionaryGetWordDataUseCase: DictionaryGetWordDataUseCase,
    val playAudioByUrl: PlayAudioByUrl
) {
}