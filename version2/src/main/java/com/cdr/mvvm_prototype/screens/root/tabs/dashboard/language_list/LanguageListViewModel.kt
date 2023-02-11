package com.cdr.mvvm_prototype.screens.root.tabs.dashboard.language_list

import com.cdr.core.uiactions.UiActions
import com.cdr.core.views.BaseViewModel
import com.cdr.mvvm_prototype.model.languages.Language
import com.cdr.mvvm_prototype.model.languages.LanguageRepository
import com.cdr.mvvm_prototype.model.languages.ListLanguageListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LanguageListViewModel(
    private val languageRepository: LanguageRepository,
    private val uiActions: UiActions
) : BaseViewModel() {

    private val _languages = MutableStateFlow(emptyList<Language>())
    val languages: StateFlow<List<Language>> = _languages.asStateFlow()

    private val _languageTitle = MutableStateFlow("")
    val languageTitle: StateFlow<String> = _languageTitle.asStateFlow()

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