import java.util.Date

data class Post(
    var id: Int = 0,
    val text: String,
    val fromId: Int, // Идентификатор автора записи
    val ownerId: Int, // Идентификатор владельца стены, на которой размещена запись
    val createdBy: Int = 0,
    val date: Int = 0,
    val replyOwnerId: Int = 0,
    val replyPostId: Int = 0,
    val copyright: String? = null,
    val friendsOnly: Boolean = false,
    val postType: String = "post",
    val canDelete: Boolean = true,
    val canEdit: Boolean = true,
    val likes: Int = 0,
    val reposts: Post? = null,
    val views: Int = 0,
    val postSource: Int = 0,
    val geo: String = "geo label",
    val signerId: Int = 0,
    var copyHistory: Int = 0,//не понял, как описать переменную
    val canPin: Boolean = true,
    val isPinned: Boolean = false,
    val markedAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val postponedId: Int = 0,
    val comments: Comments = Comments(0),
    val attachments: Array<Attachment> = emptyArray()
)

data class Comments(
    val count: Int
)

interface Attachment {
    val type: String

}

data class Photo(
    val id: Int,
    val ownerId: Int,
    val photo130: String,
    val photo604: String
)

data class PhotoAttachment(
    val photo: Photo
) : Attachment {
    override val type: String = "photo"
    override fun toString(): String {
        return "type: $type and photo: $photo"
    }
}

data class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    val title: String,
    val duration: Int
)

data class AudioAttachment(
    val audio: Audio
) : Attachment {
    override val type: String = "audio"
    override fun toString(): String {
        return "type: $type and audio: $audio"
    }
}

data class Video(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val duration: Int
)

data class VideoAttachment(
    val video: Video
) : Attachment {
    override val type: String = "video"
    override fun toString(): String {
        return "type: $type and video: $video"
    }
}

data class Sticker(
    val productId: Int,
    val stickerId: Int,
    val animationUrl: String,
    val isAllowed: Boolean
)

data class StickerAttachment(
    val sticker: Sticker
) : Attachment {
    override val type: String = "sticker"
    override fun toString(): String {
        return "type: $type and sticker: $sticker"
    }
}

data class Gift(
    val id: Int,
    val thumb256: String,
    val thumb96: String,
    val thumb48: String
)

data class GiftAttachment(
    val gift: Gift
) : Attachment {
    override val type: String = "gift"
    override fun toString(): String {
        return "type: $type and gift: $gift"
    }
}

object WallService {
    private var posts = emptyArray<Post>()
    private var lastPubId = 0
    fun add(post: Post): Post {
        posts += post.copy(id = ++lastPubId)
        return posts.last()
    }

    fun update(newPost: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (post.id == newPost.id) {
                posts[index] = newPost.copy()
                return true
            }
        }
        return false
    }

    fun clearWall() {
        posts = emptyArray()
        lastPubId = 0 // обнуляем счетчик id для постов
        println("Clearing the Wall")
    }

    fun printPosts() {
        for (post in posts) {
            print(post)
            println()
        }
        println()
    }

    fun commentPost(id: Int) {
        for ((index, post) in posts.withIndex()) {
            if (post.id == id) {
                posts[index] = post.copy(comments = Comments(id))
            }
        }
    }
}

fun main() {
    val post = Post(
        1, "Hello", 12, 34,
        attachments = arrayOf(
            PhotoAttachment(
                Photo(
                    1, 2,
                    "https://vk.com/some_photo_link", "https://vk.com/another_photo_link"
                )
            )
        )
    )
    WallService.add(post)

    WallService.add(
        Post(
            2, "Hello, baby", 78, 24,
            attachments = arrayOf(
                AudioAttachment(
                    Audio(1, 11, "AC-DC", "Thunderstorm", 300)
                ),
                VideoAttachment(
                    Video(1, 34, "How to cook pretty cookies", 457)
                )
            )
        )
    )

    WallService.add(
        Post(
            3, "Asta la vista, baby", 7, 14,
            attachments = arrayOf(
                VideoAttachment(
                    Video(1, 34, "How to cook pretty cookies", 457)
                ),
                StickerAttachment(
                    Sticker(23, 3, "www.sticker.com", true)
                ),
                GiftAttachment(
                    Gift(1, "picture256", "picture96", "picture48")
                )
            )
        )
    )
    WallService.printPosts()
    WallService.update(
        Post(
            2, "Big hello from you", 78, 34,
            attachments = arrayOf(GiftAttachment(Gift(2, "picture256", "picture96", "picture48")))
        )
    )
    WallService.update(
        Post(
            1, "Hello, crazy frog", 12, 34,
            attachments = arrayOf(
                VideoAttachment(
                    Video(1, 34, "How to cook pretty cookies", 457)
                ),
                StickerAttachment(
                    Sticker(23, 3, "www.sticker.com", true)
                ),
                GiftAttachment(
                    Gift(1, "picture256", "picture96", "picture48")
                ),
                AudioAttachment(
                    Audio(1, 11, "AC-DC", "Thunderstorm", 300)
                ),
                PhotoAttachment(
                    Photo(
                        1, 2,
                        "https://vk.com/some_photo_link", "https://vk.com/another_photo_link"
                    )
                )
            )
        )
    )
    WallService.printPosts()
    //WallService.commentPost(2)
    //    WallService.printPosts()
}