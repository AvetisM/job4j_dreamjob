package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class PostControllerTest {

    PostService postService;

    CityService cityService;

    PostController postController;

    MultipartFile testFile;
    HttpSession session;

    @BeforeEach
    void initServices() {
        postService = mock(PostService.class);
        cityService = mock(CityService.class);
        postController = new PostController(postService, cityService);
        testFile = new MockMultipartFile("testFile.img", new byte[] {1, 2, 3});
        session = mock(HttpSession.class);
    }

    @Test
    void whenRequestPostListPageThenGetPageWithPosts() {
        var post1 = new Post(1, "test1", "desc1", now(), cityService.findById(0));
        var post2 = new Post(2, "test2", "desc2", now(), cityService.findById(1));
        var expectedPosts = List.of(post1, post2);
        when(postService.findAll()).thenReturn(expectedPosts);

        var model = new ConcurrentModel();
        var view = postController.posts(model, session);
        var actualPosts = model.getAttribute("posts");

        assertThat(view).isEqualTo("posts");
        assertThat(actualPosts).isEqualTo(expectedPosts);
    }

    @Test
    void whenRequestPostCreationPageThenGetPageWithCities() {
        var city1 = new City(1, "Москва");
        var city2 = new City(2, "Санкт-Петербург");
        var expectedCities = List.of(city1, city2);
        when(cityService.getAllCities()).thenReturn(expectedCities);

        var model = new ConcurrentModel();
        var view = postController.formAddPost(model, session);
        var actualPosts = model.getAttribute("cities");

        assertThat(view).isEqualTo("addPost");
        assertThat(actualPosts).isEqualTo(expectedCities);
    }

    @Test
    void whenPostWithFileThenSameDataAndRedirectToPostsPage() throws Exception {

        var post = new Post(1, "test1", "desc1", now(), cityService.findById(0));
        var postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        when(postService.add(postArgumentCaptor.capture())).thenReturn(post);

        var model = new ConcurrentModel();
        var view = postController.createPost(post, 1);
        var actualPost = postArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/posts");
        assertThat(actualPost).isEqualTo(post);

    }

    /*@Test
    void whenPostPostWithFileThenSameDataAndRedirectToPostPage2222() {

        var expectedException = new RuntimeException("Failed to write file");
        var postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        when(postService.add(any()).thenThrow(expectedException);

        var model = new ConcurrentModel();
        var view = postController.create(new Post(), testFile, model);
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }*/
}
