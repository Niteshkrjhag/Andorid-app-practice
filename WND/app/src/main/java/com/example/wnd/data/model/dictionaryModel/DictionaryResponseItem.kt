package com.example.wnd.data.model.dictionaryModel

data class DictionaryResponseItem(
    val meanings: List<Meaning>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val word: String
)