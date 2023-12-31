data class Post(
        var id: Int = 0,
        val text: String,
        val fromId: Int, // Идентификатор автора записи
        val ownerId: Int, // Идентификатор владельца стены, на которой размещена запись
        val friendsOnly: Boolean = false,
        val postType: String = "post",
        val canDelete: Boolean = true,
        val canEdit: Boolean = true,
        val comments: Comments = Comments(0)
)

data class Comments(
        val count: Int
)

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
    val post = Post(1, "Hello", 12, 34)
    WallService.add(post)
    WallService.add(Post(2, "Hello, baby", 78, 24))
    WallService.add(Post(3, "Asta la vista, baby", 7, 14))
    WallService.printPosts()
    WallService.update(Post(2, "Big hello from you", 78, 34))
    WallService.update(Post(1, "Hello, crazy frog", 12, 34))
    WallService.printPosts()
    WallService.commentPost(2)
    WallService.printPosts()

}