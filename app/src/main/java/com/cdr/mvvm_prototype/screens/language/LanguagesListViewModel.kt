package com.cdr.mvvm_prototype.screens.language

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.model.Language
import com.cdr.mvvm_prototype.model.LanguageRepository
import com.cdr.mvvm_prototype.model.ListLanguageListener

class LanguagesListViewModel(
    private val uiActions: UiActions,
    private val languagesRepository: LanguageRepository,
) : BaseViewModel() {

    private val _languages = MutableLiveData<List<Language>>()
    val languages: LiveData<List<Language>> = _languages

    private val _screenTitle = MutableLiveData<String>()
    val screenTitle: LiveData<String> = _screenTitle

    private val listener: ListLanguageListener = { _languages.value = it }

    init {
        languagesRepository.addListLanguageListener(listener)
        _screenTitle.value = languagesRepository.getSelectedLanguage().name
    }

    val languageActionListener = object : LanguageActionListener {
        override fun onSelectLanguage(language: Language) {
            languagesRepository.selectLanguage(language)
            _screenTitle.value = language.name
        }

        override fun onInfoLanguage(language: Language) = uiActions.showToast(language.name)
    }

    fun createRandomLanguage() {
        languagesRepository.createNewRandomSelectedLanguage()
        uiActions.showToast("New random language: ${languagesRepository.getSelectedLanguage()}")
    }

    override fun onCleared() {
        super.onCleared()
        languagesRepository.removeListLanguageListener(listener)
    }
}