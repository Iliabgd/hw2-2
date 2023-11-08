import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class PostingTest {
    @Before
    fun clearBeforeTest() {
        WallService.clearWall()
    }

    @Test
    fun addPost() {
        val addingPost = Post(223, "Hello, my friend!", 234, 3)
        val addResult = WallService.add(addingPost)
        assertEquals(1, addResult.id)
    }

    @Test
    fun updateExistId() {
        val service = WallService
        service.add(Post(31, "Hello", 12, 34))
        service.add(Post(572, "Hello, baby", 78, 24))
        service.add(Post(13, "Asta la vista, baby", 7, 14))

        val updPost = Post(2, "Hello, crazy frog", 78, 24)
        val result = service.update(updPost)
        WallService.printPosts()
        assertTrue(result)
    }

    @Test
    fun updateNonExistId() {
        val service = WallService
        service.add(Post(4, "Hello", 12, 34))
        service.add(Post(5, "Hello, baby", 78, 24))
        service.add(Post(6, "Asta la vista, baby", 7, 14))

        val updPost = Post(5, "Hello, crazy frog", 78, 24)
        val result = service.update(updPost)

        assertFalse(result)
    }

    @Test
    fun checkComment() {
        // Напишите два теста на созданную функцию:
        //
        //Функция отрабатывает правильно, если добавляется комментарий к правильному посту.
        //В другом тесте assert нужен - это обычный тест, как и раньше.
        // Функция отрабатывает правильно - имеется ввиду отсутствие исключения и осуществление
        // сохранения комментария в сервисе.
        val service = WallService
        val idCommentPost = 3
        val textCommentPost = "Hey, teacher, leave that kid alone"
        val newServiceComment = Comment(1, 1457, "Comment to service comment")
        val updPostWithComment = Post(idCommentPost, textCommentPost, 0, 0, comment = newServiceComment)
        var checkFunRes = true

        service.add(Post(14, "Hello", 12, 34))
        service.add(Post(15, "Hello, baby", 78, 24))
        service.add(Post(16, "Asta la vista, baby", 7, 14))
        try {
            service.createComment(idCommentPost, newServiceComment)
            service.update(updPostWithComment)
        } catch (e: PostNotFoundException) {
            println("Нет такого поста c id = $idCommentPost, чтобы прокомментировать!")
            checkFunRes = false
        } finally {
            service.printPosts()
            }
        assertTrue(checkFunRes)
    }

    @Test(expected = PostNotFoundException ::class)
    fun shouldThrow() {
        val service = WallService
        val idCommentPost = 15
        val newServiceComment = Comment(1, 1457,
                "Hey, teacher, leave that kid alone")
        service.add(Post(14, "Hello", 12, 34))
        service.add(Post(15, "Hello, baby", 78, 24))
        service.add(Post(16, "Asta la vista, baby", 7, 14))

        service.createComment(idCommentPost, newServiceComment)
        service.update(Post(idCommentPost, "",0,0, comment = newServiceComment))
        service.printPosts()
    }
}