package com.cdr.mvvm_prototype.screens.root.tabs.dashboard.language_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.model.languages.Language
import com.cdr.mvvm_prototype.model.languages.LanguageRepository
import com.cdr.mvvm_prototype.model.languages.ListLanguageListener

class LanguageListViewModel(
    private val languageRepository: LanguageRepository,
    private val uiActions: UiActions
) : BaseViewModel() {

    private val _languages = MutableLiveData<List<Language>>()
    val languages: LiveData<List<Language>> = _languages

    private val _languageTitle = MutableLiveData<String>()
    val languageTitle: LiveData<String> = _languageTitle

    private val listener: ListLanguageListener = { _languages.value = it }

    init {
        languageRepository.addListLanguageListener(listener)
        _languageTitle.value = languageRepository.getSelectedLanguage().name
    }

    val languageActionListener = object : LanguageActionListener {
        override fun onSelectLanguage(language: Language) {
            languageRepository.selectLanguage(language)
            _languageTitle.value = language.name
        }

        override fun onInfoLanguage(language: Language) = uiActions.showToast(language.name)
    }

    override fun onCleared() {
        super.onCleared()
        languageRepository.removeListLanguageListener(listener)
    }

}