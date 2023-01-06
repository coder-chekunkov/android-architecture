package com.cdr.mvvm_prototype.model

import com.cdr.core.model.Repository
import com.github.javafaker.Faker

typealias ListLanguageListener = (languages: List<Language>) -> Unit
typealias SelectedLanguageListener = (language: Language) -> Unit

class LanguageRepository : Repository {

    private var languages = mutableListOf<Language>()
    private val listListener = mutableListOf<ListLanguageListener>()

    private lateinit var selectedLanguage: Language
    private val selectedListener = mutableListOf<SelectedLanguageListener>()

    private val faker = Faker.instance()

    init {
        languages = LANGUAGES.toMutableList()
        createRandomSelectedLanguage()
    }

    private fun createRandomSelectedLanguage() {
        val randomId = faker.random().nextInt(1, 15)
        val index = languages.indexOfFirst { it.id == randomId.toLong() }

        if (index == -1) throw IllegalArgumentException("Error with random language. Unknown ID.")
        languages[index] = languages[index].copy(isSelected = true)
        selectedLanguage = getSelectedLanguage()
        notifyChanges()
    }

    fun getSelectedLanguage(): Language = languages.find { it.isSelected }!!

    fun selectLanguage(language: Language) {
        val index = languages.indexOfFirst { it.id == language.id }
        if (index == -1) return

        languages = ArrayList(languages)
        for (id in 0 until languages.size) languages[id] =
            languages[id].copy(isSelected = false)
        languages[index] = languages[index].copy(isSelected = true)

        selectedLanguage = getSelectedLanguage()
        notifyChanges()
    }

    fun createNewRandomSelectedLanguage() {
        val randomId = faker.random().nextInt(1, 15)
        val index = languages.indexOfFirst { it.id == randomId.toLong() }

        if (index == -1) throw IllegalArgumentException("Error with random language. Unknown ID.")
        selectLanguage(languages[index])
    }

    fun addListLanguageListener(listener: ListLanguageListener) {
        listListener.add(listener)
        listener.invoke(languages)
    }

    fun removeListLanguageListener(listener: ListLanguageListener) {
        listListener.remove(listener)
        listener.invoke(languages)
    }

    fun addSelectedLanguageListener(listener: SelectedLanguageListener) {
        selectedListener.add(listener)
        listener.invoke(selectedLanguage)
    }

    fun removeSelectedLanguageListener(listener: SelectedLanguageListener) {
        selectedListener.remove(listener)
        listener.invoke(selectedLanguage)
    }

    private fun notifyChanges() {
        listListener.forEach { it.invoke(languages) }
        selectedListener.forEach { it.invoke(selectedLanguage) }
    }

    companion object {
        private val LANGUAGES = listOf(
            Language(
                1,
                "Kotlin",
                "https://cms-assets.tutsplus.com/uploads/users/1499/posts/29590/preview_image/kotlin.jpg",
                false
            ),
            Language(2, "Java", "https://proxys.io/files/blog/Java/javalogo.png", false),
            Language(
                3,
                "Python",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Python-logo-notext.svg/1200px-Python-logo-notext.svg.png",
                false
            ),
            Language(
                4,
                "C#",
                "https://static.wikia.nocookie.net/wikies/images/4/43/Logo-csharp.png/revision/latest/scale-to-width-down/500?cb=20180617092325&path-prefix=ru",
                false
            ),
            Language(
                5,
                "C++",
                "https://cdn.pvs-studio.com/media/docx/blog/0329_CppPopularity_ru/image1_thm_intoblank_1200x630.png",
                false
            ),
            Language(
                6,
                "C",
                "data:image/png;base64,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",
                false
            ),
            Language(
                7,
                "PHP",
                "https://free-png.ru/wp-content/uploads/2022/02/free-png.ru-566.png",
                false
            ),
            Language(
                8,
                "Dart",
                "https://ih1.redbubble.net/image.1153489299.7327/st,small,845x845-pad,1000x1000,f8f8f8.jpg",
                false
            ),
            Language(
                9,
                "Swift",
                "https://www.macworld.com/wp-content/uploads/2022/04/swift_1200home.jpg?quality=50&strip=all&w=1024",
                false
            ),
            Language(
                10,
                "Objective C",
                "https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/objective-c/objective-c.png",
                false
            ),
            Language(
                11,
                "Scala",
                "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAABAlBMVEX/////MRw/BQL/HwD/q6T/tK81AAAvAAAyAAA8BAH/Lxk6AwAuAAA4AgAxAAAsAAD/LBQ9AAApAAD/JwvuLRr6MBv/FgD/JAT/+PeVGg7oLBn/2NX/5eMlAADZKRdVCwaJFw3AIxT/Vkj/7uz/z8t7FAugHBDOJhbqLBmsHxH/pJ3/STn/k4tOCARtEQlfDQf/vbj/aFz/iID/mZL/PSr/w7//cWX/enH/ysb/YVS+sLB0EwpQCQS6IhOol5bj3Nz/T0CjEwCYdnVvUE97Xl1bNjW6qqrVrantGwDKAAAdAABWKSech4bc1NNKHBtiQkGlgHyBQDuFbGtlREPrpJ7DUkgFCk4XAAAJOklEQVR4nO3diVrqSBoGYEggG1nYEghLIGwhLGEVcOFo97HPTHfrsceeuf9bmaqgtgouIJWq5KnvApDX/6+APnwhFgtnmrifAOoUk7ifAepMe7ifAeIMjATup4A0zQnLRlqYiOfiURaO+6wUj66wmIiz6Xg8ssLytADnF1Vhpdcwcg++CAqbyUZus56RFFYSDYNNS/F4JIXF8gzy4q8TEWHzZNlnd/AiImz1GgU2J+3ihV9YLPcW7NbRi4qwCXRz4+3hhVtYORk0dPZDXTiFxVVvUTDYwnubGVZhsdLqLfvGx4sZRmGzPB5M+oU9cSERlk9mi77Efn4tQyMsNlvJGbhYGuCVXDoMR6qwWSknZ8tTaDt0bqQKm+WTxHRyNc+BseWOYCNGWKyskr3pZJ5jWSg7Gg2vsNhsVlrj3mw66acNYzOzI8uCFxbB+Sq3TpKz6XLR6MdZ6Cocf2TBCJutVmu1Wp2Mx8lEbwBEp42r/jyeLoA3yWBckIXahViY9BfvIQUogqYAVeiFLBbL7lAhFVIh/lAhFVIh/lAhFVIh/lAhFVIh/lAhFVIh/lAhFVIh/kRfaIwjLkz3i9EWptkyAiA5Qom9QtOYIUQosRKqNgkhwvgAWeWJCGH+l19R+UgQ6k67KgjcWVSFljnUFIZhMtp9FIVWp6ZyIrOJ+j1qQjA9Xkgx/0S9jpAwv64z2afpPUZDchaDF+Ydc8TxymsePIu/RUBodUYMz6W2dZs9vQy1UHc6o6zGpXYM7zEyijUNRKiXbNPjtDdn9zTDH2EU5p3O+QUnf6iDyf4rZELdcdtDRRV2XVV2hfPC8ze+DibXHlblT+NglOG/QyHMW2vzfCjvh4PhR3nS/0+jW3bnvMbw6rvXy7cGmO3oBP8nSrfWHY+RefBKJ+6Pgz71vBsn8X9ter5ku3WPyWoHze0hIse1Lf/xSBJ2wUbWvZqo8YJyuA0mJTNm6eFRSRDqJcc1wWETBfmrNH98Qtaz9adHxyfU9bxlQ5lczcrqoYdta3oCN3Lzz39O0EJwykrgEmK2vQulCtbxC0dtF4/3OtarnxiMMF+yHNvt1M9HtaHIa6rAHWEbX0ZU+Kzndrd/p0iErd8dx7bXaxfOajRkUgonqLzPOs4ubvE4lWk/O3vIhbEz4Q+NV8GoAOpIB+wdnayMzNe7iVoYK94wcgYpbKNTVK1Wt3fsJnIhMJ79WeUyCJUix1eZ+jq/ezcDEIJc3vwpCCiMoiKozMi08x/gkAtBbm/uwJ9yR1QCnMzV2q7zKV0AQpDLb99lmTsCEuKqTLvjdD9azICFMXgm7zO8evgoxRSnqvxwn8kFLIS5/fH9J7/3wooKp8rihWfa1l6DwyEEKd5++57JflIJdpLPghNnrq3SobjAhQ/KawEq32KKKUDTqsqo7tqlD18KCBT6ARvLcOqLV0sxBd7aqSonDiHt4JUkRQhzefbrXUYD71XBe1ZZU4Y1DyykUzrgUkKq0M/tX/85r5tgZt1j7CNBwmYFdrqXk0a/P0cFC1JYBGlWyqtxAvbvr+YFY1OEDqjSjebvw+lgCrJcLianjf5cfzQFpkIuTBqF3CYPPfVgTUEIsX/a5FmokAqpEH+okAqpEH+okAqpEH+okAqpEH+okAqpEH+okAqpEH+iLzRaEReySwRAgoSSsUABJEeYYxGVuQkRpo1FBQ2QEGEamY8MYd4d3SGrchMgdNqMKma0axR3/SBAaJnMQ2GWY1AUSPEKdatz8azunFHQEHEJ8069Jr9sc2d+IllULMKS6zHqVlmdEW4iIeyu20pW2FnhyDChv8dQya7XNFV582Oc2m2Yhda6XlPk7dV8Hj6sbXXdcc9FQf24NhvGPn7ecusXVXn3udsKF662eh7s5UjM7tF4ln9B8d3qSIRdx23XGFX91G0GnpJtsyEQ6tbaHAkyDya3Z0ksJXTiRAv1rt/D9zvP+9H8iJpnxUkVwmpw3aulvtDnFrWaDR+KOGHXAWO7YJSv9blFRRvZmwckRei38OFGHqP1nOKZ+lPlErcQHDXHNdujoZCVj1N7TgmCt35WasAlzFuODZvPQ4aXj9h7TgnVmlt68ZOCFYILiO12wMQ21WflqB1hMaUKr3nIhbqez3d9lQnnpcJ7CyBoqkOeIjPn6y0eKmHFrLfbbc8b1YYMp2Vl3mchcD3yhOyw/lZnFokwVrz+g+dgTx1xUx0kxcnVkftOFQyNMBa7vM+oqOvqYorjxZrpvF+ZQiUEc/z2t4qkyb3RKYLGeKbzbhUfsTDm19WzwvE7+WB0WabtWp9r8iEVQuS3n1/pOL+KX3nmRqa966KJSQiRP65/8l++vYKocLIwBLi374CBTRiD7d+bO/XgUj4YnCbU2h37oIZpMEJfeXafqapvV5x3zQ3aqopngjN3cMc0OCEMXFjuExvrF7o5ZuiZ6y+XnoMV+sqz+ztF3q0URdh7lpmaV3edT14ryRNulDd/c9qzew+AfRTkbJUZtU3XOUoNH7MQBt57IJPlVV7WqlztHMgsHUWhG58QIiv//Z9rW3u8uJEvLDYrlfIq2RssFw1Y6i6grwijFBbhd8TDL1RfjYFpupg0ruZ62v/28SC+VB2lMJGGB0qS0ulc7uUXkGPodaMRstJjgvYEJsTNehYqpEIqxB8qpEIqxB8qpEIqxB8qpEIqxB8qpEIqxB8qpEIqxB8k3zxOklCSUHztMUFCCckICRLm2BMUQGKEaXaCqOxMhjBtNFZofEQIpYJxisyHXyjlWH2AroyPWwh40gLN9YUEIeCxyzGKV0AShFK6YLDLE1Q3GcAtBMPLNQYobrZDghDojP5yjPTSgk8owc/nLBLlQHYzcGEaXFbSp73AVjNQIRxd7mqZLGPQoRemC6yhL2YnQZ67oIQSxBmNwbgS8LELQCjBM5ebnw6wTu6fHFUIbYbeWPZaZOD8HEkI34IZbH85G1fQvw/bL18Uwk9twg9s9pc9/Cdudw4VSvDzqAarX02mSZJ2cjt7C/0LiWFIV4tpokXcSu7IJ4WS9CAzpNPlLFluFslcyR15Xyg9nrNcfH4KjtqK6H3cnR1Cn1UALKOg908X01lyVQ6h7DFQCFfwYVZwD3PzxmQJXCflSjM0u/h2Ern5fN5vTBbLQS+RHK/gCQsD6/8uYz2vnH4DywAAAABJRU5ErkJggg==",
                false
            ),
            Language(
                12,
                "Go",
                "https://www.nicepng.com/png/detail/380-3801538_go-go-programming-language-logo.png",
                false
            ),
            Language(
                13,
                "JavaScript",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPzoV28dZKq4WMx38JzdZgnrJUOLVJwvnW1flawsIt&s",
                false
            ),
            Language(
                14,
                "Ruby",
                "data:image/jpeg;base64,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",
                false
            ),
            Language(
                15,
                "Pascal ABC",
                "data:image/png;base64,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",
                false
            )
        )
    }
}

data class Language(val id: Long, val name: String, val res: String, val isSelected: Boolean) {
    override fun toString(): String = "Language:{id:$id; name: $name}"
}