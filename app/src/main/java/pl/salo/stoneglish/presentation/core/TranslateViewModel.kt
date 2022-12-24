package pl.salo.stoneglish.presentation.core

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor() : ViewModel() {
    private val _translatedWord = MutableLiveData<String>()
    val translatedWord: LiveData<String>
        get() = _translatedWord

    private val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.POLISH)
        .build()
    private val englishPolishTranslator = Translation.getClient(options)

    private var conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()

    private val translator = Translation.getClient(options)
    init {
        englishPolishTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                Log.d(TAG, "Model downloaded successfully. Okay to start translating.")
            }
            .addOnFailureListener { _ ->
                Log.e(TAG, "Model couldn’t be downloaded or other internal error.")
            }
    }

    fun translate(text:String) {
        englishPolishTranslator.translate(text)
            .addOnSuccessListener { translatedText ->
                _translatedWord.postValue(translatedText)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error: ${exception.message}")
            }
    }

    override fun onCleared() {
        translator.close()
        super.onCleared()
    }

    companion object{
        const val TAG = "TranslateViewModel"
    }
}