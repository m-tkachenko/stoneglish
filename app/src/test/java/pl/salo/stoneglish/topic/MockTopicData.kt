package pl.salo.stoneglish.topic

import pl.salo.stoneglish.data.model.home.*
import pl.salo.stoneglish.data.model.home.TopicType.Art
import pl.salo.stoneglish.data.model.home.TopicType.Books
import pl.salo.stoneglish.data.model.home.TopicType.Education
import pl.salo.stoneglish.data.model.home.TopicType.Health


class MockTopicData {
    val examplePhoto = "https://images.unsplash.com/photo-1501183007986"

    val mockListOfTopicArtType = listOf(
        Topic(
            title = "ArtTopicOne",
            imgUrl = examplePhoto,
            text = "Text of ArtTopicOne",
            type = listOf(Art),
            eng_level = listOf(EngLevel.A0, EngLevel.A1),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Art", translatedText = "Sztuka"),
                ListeningSpeaking(text = "Art", translatedText = "Sztuka")
            ),
            keywords = listOf(
                Keyword(word = "Art", translate = "Sztuka", phonetic = ""),
                Keyword(word = "Art", translate = "Sztuka", phonetic = ""),
                Keyword(word = "Art", translate = "Sztuka", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "ArtTopicTwo"),
                SimilarTopic(imgUrl = examplePhoto, title = "ArtTopicThree")
            ),
            horizontalGroupTitle = null
        ),
        Topic(
            title = "ArtTopicTwo",
            imgUrl = examplePhoto,
            text = "Text of ArtTopicTwo",
            type = listOf(Art),
            eng_level = listOf(EngLevel.A2),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Art", translatedText = "Sztuka"),
                ListeningSpeaking(text = "Art", translatedText = "Sztuka")
            ),
            keywords = listOf(
                Keyword(word = "Art", translate = "Sztuka", phonetic = ""),
                Keyword(word = "Art", translate = "Sztuka", phonetic = ""),
                Keyword(word = "Art", translate = "Sztuka", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "ArtTopicOne"),
                SimilarTopic(imgUrl = examplePhoto, title = "ArtTopicThree")
            ),
            horizontalGroupTitle = null
        ),
        Topic(
            title = "ArtTopicThree",
            imgUrl = examplePhoto,
            text = "Text of ArtTopicThree",
            type = listOf(Art),
            eng_level = listOf(EngLevel.C1),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Art", translatedText = "Sztuka"),
                ListeningSpeaking(text = "Art", translatedText = "Sztuka")
            ),
            keywords = listOf(
                Keyword(word = "Art", translate = "Sztuka", phonetic = ""),
                Keyword(word = "Art", translate = "Sztuka", phonetic = ""),
                Keyword(word = "Art", translate = "Sztuka", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "ArtTopicOne"),
                SimilarTopic(imgUrl = examplePhoto, title = "ArtTopicTwo")
            ),
            horizontalGroupTitle = null
        )
    )
    val mockListOfTopicBooksType = listOf(
        Topic(
            title = "BooksTopicOne",
            imgUrl = examplePhoto,
            text = "Text of BooksTopicOne",
            type = listOf(Books),
            eng_level = listOf(EngLevel.A0, EngLevel.A1),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Book", translatedText = "Książka"),
                ListeningSpeaking(text = "Book", translatedText = "Książka")
            ),
            keywords = listOf(
                Keyword(word = "Book", translate = "Książka", phonetic = ""),
                Keyword(word = "Book", translate = "Książka", phonetic = ""),
                Keyword(word = "Book", translate = "Książka", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "BookTopicTwo"),
                SimilarTopic(imgUrl = examplePhoto, title = "BookTopicThree")
            ),
            horizontalGroupTitle = null
        ),
        Topic(
            title = "BooksTopicTwo",
            imgUrl = examplePhoto,
            text = "Text of BooksTopicTwo",
            type = listOf(Books),
            eng_level = listOf(EngLevel.A1),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Book", translatedText = "Książka"),
                ListeningSpeaking(text = "Book", translatedText = "Książka")
            ),
            keywords = listOf(
                Keyword(word = "Book", translate = "Książka", phonetic = ""),
                Keyword(word = "Book", translate = "Książka", phonetic = ""),
                Keyword(word = "Book", translate = "Książka", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "BooksTopicOne"),
                SimilarTopic(imgUrl = examplePhoto, title = "BookTopicThree")
            ),
            horizontalGroupTitle = null
        ),
        Topic(
            title = "BooksTopicThree",
            imgUrl = examplePhoto,
            text = "Text of BooksTopicThree",
            type = listOf(Books),
            eng_level = listOf(EngLevel.B2),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Book", translatedText = "Książka"),
                ListeningSpeaking(text = "Book", translatedText = "Książka")
            ),
            keywords = listOf(
                Keyword(word = "Book", translate = "Książka", phonetic = ""),
                Keyword(word = "Book", translate = "Książka", phonetic = ""),
                Keyword(word = "Book", translate = "Książka", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "BookTopicTwo"),
                SimilarTopic(imgUrl = examplePhoto, title = "BooksTopicOne")
            ),
            horizontalGroupTitle = null
        )
    )

    val mockListOfTopicByType = listOf(
        TopicByType(
            topicListType = Art,
            topicList = mockListOfTopicArtType
        ),
        TopicByType(
            topicListType = Books,
            topicList = mockListOfTopicBooksType
        )
    )

    val mockFirstListOfTopicEducationType = listOf(
        Topic(
            title = "FirstEducationTopicOne",
            imgUrl = examplePhoto,
            text = "Text of FirstEducationTopicOne",
            type = listOf(Education),
            eng_level = listOf(EngLevel.A0, EngLevel.A1),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Education", translatedText = "Edukacja"),
                ListeningSpeaking(text = "Education", translatedText = "Edukacja")
            ),
            keywords = listOf(
                Keyword(word = "Education", translate = "Edukacja", phonetic = ""),
                Keyword(word = "Education", translate = "Edukacja", phonetic = ""),
                Keyword(word = "Education", translate = "Edukacja", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "FirstEducationTopicTwo"),
                SimilarTopic(imgUrl = examplePhoto, title = "FirstEducationTopicThree")
            ),
            horizontalGroupTitle = null
        ),
        Topic(
            title = "FirstEducationTopicTwo",
            imgUrl = examplePhoto,
            text = "Text of FirstEducationTopicTwo",
            type = listOf(Education),
            eng_level = listOf(EngLevel.A0),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Education", translatedText = "Edukacja"),
                ListeningSpeaking(text = "Education", translatedText = "Edukacja")
            ),
            keywords = listOf(
                Keyword(word = "Education", translate = "Edukacja", phonetic = ""),
                Keyword(word = "Education", translate = "Edukacja", phonetic = ""),
                Keyword(word = "Education", translate = "Edukacja", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "FirstEducationTopicThree"),
                SimilarTopic(imgUrl = examplePhoto, title = "FirstEducationTopicOne")
            ),
            horizontalGroupTitle = null
        ),
        Topic(
            title = "FirstEducationTopicThree",
            imgUrl = examplePhoto,
            text = "Text of FirstEducationTopicThree",
            type = listOf(Education),
            eng_level = listOf(EngLevel.B2),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Education", translatedText = "Edukacja"),
                ListeningSpeaking(text = "Education", translatedText = "Edukacja")
            ),
            keywords = listOf(
                Keyword(word = "Education", translate = "Edukacja", phonetic = ""),
                Keyword(word = "Education", translate = "Edukacja", phonetic = ""),
                Keyword(word = "Education", translate = "Edukacja", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "FirstEducationTopicTwo"),
                SimilarTopic(imgUrl = examplePhoto, title = "FirstEducationTopicOne")
            ),
            horizontalGroupTitle = null
        )
    )
    val mockSecondListOfTopicEducationType = listOf(
        Topic(
            title = "SecondEducationTopicOne",
            imgUrl = examplePhoto,
            text = "Text of SecondEducationTopicOne",
            type = listOf(Education),
            eng_level = listOf(EngLevel.A0, EngLevel.A1),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Education", translatedText = "Edukacja"),
                ListeningSpeaking(text = "Education", translatedText = "Edukacja")
            ),
            keywords = listOf(
                Keyword(word = "Education", translate = "Edukacja", phonetic = ""),
                Keyword(word = "Education", translate = "Edukacja", phonetic = ""),
                Keyword(word = "Education", translate = "Edukacja", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "SecondEducationTopicTwo"),
                SimilarTopic(imgUrl = examplePhoto, title = "SecondEducationTopicThree")
            ),
            horizontalGroupTitle = null
        ),
        Topic(
            title = "SecondEducationTopicTwo",
            imgUrl = examplePhoto,
            text = "Text of SecondEducationTopicTwo",
            type = listOf(Education),
            eng_level = listOf(EngLevel.A0),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Education", translatedText = "Edukacja"),
                ListeningSpeaking(text = "Education", translatedText = "Edukacja")
            ),
            keywords = listOf(
                Keyword(word = "Education", translate = "Edukacja", phonetic = ""),
                Keyword(word = "Education", translate = "Edukacja", phonetic = ""),
                Keyword(word = "Education", translate = "Edukacja", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "SecondEducationTopicThree"),
                SimilarTopic(imgUrl = examplePhoto, title = "SecondEducationTopicOne")
            ),
            horizontalGroupTitle = null
        ),
        Topic(
            title = "SecondEducationTopicThree",
            imgUrl = examplePhoto,
            text = "Text of SecondEducationTopicThree",
            type = listOf(Education),
            eng_level = listOf(EngLevel.B2),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Education", translatedText = "Edukacja"),
                ListeningSpeaking(text = "Education", translatedText = "Edukacja")
            ),
            keywords = listOf(
                Keyword(word = "Education", translate = "Edukacja", phonetic = ""),
                Keyword(word = "Education", translate = "Edukacja", phonetic = ""),
                Keyword(word = "Education", translate = "Edukacja", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "SecondEducationTopicTwo"),
                SimilarTopic(imgUrl = examplePhoto, title = "SecondEducationTopicOne")
            ),
            horizontalGroupTitle = null
        )
    )
    val mockFirstListOfTopicHealthType = listOf(
        Topic(
            title = "FirstHealthTopicOne",
            imgUrl = examplePhoto,
            text = "Text of FirstHealthTopicOne",
            type = listOf(Health),
            eng_level = listOf(EngLevel.C1),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Health", translatedText = "Zdrowie"),
                ListeningSpeaking(text = "Health", translatedText = "Zdrowie")
            ),
            keywords = listOf(
                Keyword(word = "Health", translate = "Zdrowie", phonetic = ""),
                Keyword(word = "Health", translate = "Zdrowie", phonetic = ""),
                Keyword(word = "Health", translate = "Zdrowie", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "FirstHealthTopicTwo"),
                SimilarTopic(imgUrl = examplePhoto, title = "FirstHealthTopicThree")
            ),
            horizontalGroupTitle = null
        ),
        Topic(
            title = "FirstHealthTopicTwo",
            imgUrl = examplePhoto,
            text = "Text of FirstHealthTopicTwo",
            type = listOf(Health),
            eng_level = listOf(EngLevel.C2),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Health", translatedText = "Zdrowie"),
                ListeningSpeaking(text = "Health", translatedText = "Zdrowie")
            ),
            keywords = listOf(
                Keyword(word = "Health", translate = "Zdrowie", phonetic = ""),
                Keyword(word = "Health", translate = "Zdrowie", phonetic = ""),
                Keyword(word = "Health", translate = "Zdrowie", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "FirstHealthTopicOne"),
                SimilarTopic(imgUrl = examplePhoto, title = "FirstHealthTopicThree")
            ),
            horizontalGroupTitle = null
        ),
        Topic(
            title = "FirstHealthTopicThree",
            imgUrl = examplePhoto,
            text = "Text of FirstHealthTopicThree",
            type = listOf(Health),
            eng_level = listOf(EngLevel.B2),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Health", translatedText = "Zdrowie"),
                ListeningSpeaking(text = "Health", translatedText = "Zdrowie")
            ),
            keywords = listOf(
                Keyword(word = "Health", translate = "Zdrowie", phonetic = ""),
                Keyword(word = "Health", translate = "Zdrowie", phonetic = ""),
                Keyword(word = "Health", translate = "Zdrowie", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "FirstHealthTopicOne"),
                SimilarTopic(imgUrl = examplePhoto, title = "FirstHealthTopicTwo")
            ),
            horizontalGroupTitle = null
        )
    )
    val mockSecondListOfTopicHealthType = listOf(
        Topic(
            title = "SecondHealthTopicOne",
            imgUrl = examplePhoto,
            text = "Text of SecondHealthTopicOne",
            type = listOf(Health),
            eng_level = listOf(EngLevel.C1),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Health", translatedText = "Zdrowie"),
                ListeningSpeaking(text = "Health", translatedText = "Zdrowie")
            ),
            keywords = listOf(
                Keyword(word = "Health", translate = "Zdrowie", phonetic = ""),
                Keyword(word = "Health", translate = "Zdrowie", phonetic = ""),
                Keyword(word = "Health", translate = "Zdrowie", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "SecondHealthTopicTwo"),
                SimilarTopic(imgUrl = examplePhoto, title = "SecondHealthTopicThree")
            ),
            horizontalGroupTitle = null
        ),
        Topic(
            title = "SecondHealthTopicTwo",
            imgUrl = examplePhoto,
            text = "Text of SecondHealthTopicTwo",
            type = listOf(Health),
            eng_level = listOf(EngLevel.C2),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Health", translatedText = "Zdrowie"),
                ListeningSpeaking(text = "Health", translatedText = "Zdrowie")
            ),
            keywords = listOf(
                Keyword(word = "Health", translate = "Zdrowie", phonetic = ""),
                Keyword(word = "Health", translate = "Zdrowie", phonetic = ""),
                Keyword(word = "Health", translate = "Zdrowie", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "SecondHealthTopicOne"),
                SimilarTopic(imgUrl = examplePhoto, title = "SecondHealthTopicThree")
            ),
            horizontalGroupTitle = null
        ),
        Topic(
            title = "SecondHealthTopicThree",
            imgUrl = examplePhoto,
            text = "Text of SecondHealthTopicThree",
            type = listOf(Health),
            eng_level = listOf(EngLevel.B2),
            exercises = null,
            listeningAndSpeaking = listOf(
                ListeningSpeaking(text = "Health", translatedText = "Zdrowie"),
                ListeningSpeaking(text = "Health", translatedText = "Zdrowie")
            ),
            keywords = listOf(
                Keyword(word = "Health", translate = "Zdrowie", phonetic = ""),
                Keyword(word = "Health", translate = "Zdrowie", phonetic = ""),
                Keyword(word = "Health", translate = "Zdrowie", phonetic = "")
            ),
            similarTopics = listOf(
                SimilarTopic(imgUrl = examplePhoto, title = "SecondHealthTopicOne"),
                SimilarTopic(imgUrl = examplePhoto, title = "SecondHealthTopicTwo")
            ),
            horizontalGroupTitle = null
        )
    )

    val mockListOfGroupsEducationType = listOf(
        HorizontalGroup(
            horizontalGroupTitle = "FirstEducationGroupTitle",
            topics = mockFirstListOfTopicEducationType
        ),
        HorizontalGroup(
            horizontalGroupTitle = "SecondEducationGroupTitle",
            topics = mockSecondListOfTopicEducationType
        )
    )
    val mockListOfGroupsHealthType = listOf(
        HorizontalGroup(
            horizontalGroupTitle = "FirstHealthGroupTitle",
            topics = mockFirstListOfTopicHealthType
        ),
        HorizontalGroup(
            horizontalGroupTitle = "SecondHealthGroupTitle",
            topics = mockSecondListOfTopicHealthType
        )
    )

    val mockListOfGroupByType = listOf(
        HorizontalGroupByType(
            horizontalGroupType = Education,
            horizontalGroups = mockListOfGroupsEducationType
        ),
        HorizontalGroupByType(
            horizontalGroupType = Health,
            horizontalGroups = mockListOfGroupsHealthType
        )
    )

    val mockListAllTopicsWithoutGroups: List<Topic>
        get() {
            val allTypeList = mutableListOf<Topic>()

            allTypeList.addAll(mockListOfTopicArtType)
            allTypeList.addAll(mockListOfTopicBooksType)

            return allTypeList.toList()
        }
    val mockListAllTopicsWithGroups: List<Topic>
        get() {
            val allTypeList = mutableListOf<Topic>()

            allTypeList.addAll(mockListOfTopicArtType)
            allTypeList.addAll(mockListOfTopicBooksType)
            allTypeList.addAll(mockFirstListOfTopicEducationType)
            allTypeList.addAll(mockSecondListOfTopicEducationType)
            allTypeList.addAll(mockFirstListOfTopicHealthType)
            allTypeList.addAll(mockSecondListOfTopicHealthType)

            return allTypeList.toList()
        }
}