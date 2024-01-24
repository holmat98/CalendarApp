package com.mateuszholik.domain.models

sealed interface Description {
    val description: String

    fun copyWith(newDescription: String): Description

    companion object {
        private const val GOOGLE_MEET_DESCRIPTION_SEPARATOR = "-::~:~::~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~::~:~::-"
        private const val GOOGLE_MEET_URL = "meet.google.com"
        private val URL_REGEX = """(http|ftp|https):\/\/([\w_-]+(?:(?:\.[\w_-]+)+))([\w.,@?^=%&:\/~+#-]*[\w@?^=%&\/~+#-])""".toRegex()

        fun from(description: String): Description {
            if (description.contains(GOOGLE_MEET_DESCRIPTION_SEPARATOR)) {
                val splitDescription = description.split(GOOGLE_MEET_DESCRIPTION_SEPARATOR)

                val shortDescription = splitDescription[0].trim()
                val urls = URL_REGEX.findAll(description)
                val googleMeetUrl = urls.first { it.value.contains(GOOGLE_MEET_URL) }.value
                val otherUrls = urls
                    .map { it.value }
                    .minus(googleMeetUrl)
                    .toList()

                return GoogleMeet(
                    description = shortDescription,
                    originalDescription = description,
                    meetingUrl = googleMeetUrl,
                    otherUrls = otherUrls
                )
            }

            return Generic(description)
        }
    }
}

data class Generic(override val description: String): Description {

    override fun copyWith(newDescription: String): Description =
        copy(description = newDescription)
}

data class GoogleMeet(
    override val description: String,
    val originalDescription: String,
    val meetingUrl: String,
    val otherUrls: List<String>,
): Description {

    override fun copyWith(newDescription: String): Description =
        copy(
            description = newDescription,
            originalDescription = originalDescription.replace(description, newDescription)
        )
}
