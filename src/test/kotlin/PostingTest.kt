import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class PostingTest {
    @Before
    fun clearBeforeTest() {
        WallService.clearWall()
    }

    @Test
    fun addPost() {
        val addingPost = Post(2, "Hello, my friend!", 234, 3)
        WallService.add(addingPost)
        assertEquals(2, addingPost.id)
    }

    @Test
    fun updateExistId() {
        val service = WallService
        service.add(Post(1, "Hello", 12, 34))
        service.add(Post(2, "Hello, baby", 78, 24))
        service.add(Post(3, "Asta la vista, baby", 7, 14))

        val updPost = Post(2, "Hello, crazy frog", 78, 24)
        val result = service.update(updPost)

        assertTrue(result)
    }

    @Test
    fun updateNonExistId() {
        val service = WallService
        service.add(Post(4, "Hello", 12, 34))
        service.add(Post(5, "Hello, baby", 78, 24))
        service.add(Post(6, "Asta la vista, baby", 7, 14))

        val updPost = Post(6, "Hello, crazy frog", 78, 24)
        val result = service.update(updPost)

        assertFalse(result)
    }
}