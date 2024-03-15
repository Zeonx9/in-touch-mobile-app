package com.example.intouchmobileapp.domain.model

data class Chat(
    val id: Int,
    val group: Group?,
    val isPrivate: Boolean,
    val lastMessage: Message?,
    val members: List<User>,
    val unreadCounter: Int = 0
) {
    fun getName(selfId: Int): String  =
        if (isPrivate) {
            val otherUser = members.find { it.id != selfId }!!
            "${otherUser.realName} ${otherUser.surname}"
        } else {
            group!!.name
        }

    fun getAbbreviation(selfId: Int): String {
        val abbr = if (isPrivate) {
            val otherUser = members.find { it.id != selfId }!!
            "${otherUser.realName[0]}${otherUser.surname[0]}"
        } else {
            val words = group!!.name.split(" ")
            if (words.size >= 2)
                "${words[0][0]}${words[1][0]}"
            else
                "${group.name[0]}"
        }
        return abbr.uppercase()
    }

    fun getThumbnailUrl(selfId: Int): String? {
        return if (isPrivate) {
            val otherUser = members.find { it.id != selfId }
            otherUser?.thumbnailUrl
        } else {
            null
        }
    }
}