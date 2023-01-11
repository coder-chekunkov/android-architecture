package com.cdr.mvvm_prototype.screens.root.tabs.dashboard.language_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cdr.mvvm_prototype.model.languages.Language
import com.cdr.version2.R
import com.cdr.version2.databinding.ItemLanguageBinding

class LanguageDiffUtil(
    private val oldList: List<Language>, private val newList: List<Language>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldLanguage = oldList[oldItemPosition]
        val newLanguage = newList[newItemPosition]

        return oldLanguage.id == newLanguage.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldLanguage = oldList[oldItemPosition]
        val newLanguage = newList[newItemPosition]

        return oldLanguage == newLanguage
    }
}

interface LanguageActionListener {
    fun onSelectLanguage(language: Language)
    fun onInfoLanguage(language: Language)
}

class LanguageAdapter(private val languageActionListener: LanguageActionListener) :
    RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    var data: List<Language> = emptyList()
        set(newValue) {
            val languageDiffUtil = LanguageDiffUtil(field, newValue)
            val diffUtilResult = DiffUtil.calculateDiff(languageDiffUtil)
            field = newValue
            diffUtilResult.dispatchUpdatesTo(this@LanguageAdapter)
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLanguageBinding.inflate(inflater, parent, false)

        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val language = data[position]
        val context = holder.itemView.context


        with(holder.binding) {
            if (language.isSelected) {
                cardView.strokeColor = ContextCompat.getColor(context, R.color.green)
                root.isClickable = false
            } else {
                cardView.strokeColor = ContextCompat.getColor(context, R.color.grey)
                root.isClickable = true

                root.setOnClickListener { onClick(language) }
                root.setOnLongClickListener { onLongClick(language) }
            }

            languageName.text = language.name
            Glide.with(context).load(language.res).circleCrop().error(R.mipmap.ic_launcher_round)
                .placeholder(R.mipmap.ic_launcher_round).into(languagePhoto)
        }
    }

    private fun onClick(language: Language) = languageActionListener.onSelectLanguage(language)

    private fun onLongClick(language: Language): Boolean {
        languageActionListener.onInfoLanguage(language)
        return true
    }

    class LanguageViewHolder(val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root)
}