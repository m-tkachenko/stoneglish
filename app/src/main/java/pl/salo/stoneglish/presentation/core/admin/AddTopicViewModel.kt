package pl.salo.stoneglish.presentation.core.admin

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.salo.stoneglish.data.model.home.Topic
import javax.inject.Inject

@HiltViewModel
class AddTopicViewModel @Inject constructor() : ViewModel() {
    fun sendTopic(newTopic: Topic) {
        val t = newTopic
    }
}