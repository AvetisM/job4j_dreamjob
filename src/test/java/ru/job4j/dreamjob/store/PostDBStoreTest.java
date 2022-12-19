package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.config.JdbcConfiguration;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDBStoreTest {
    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new JdbcConfiguration().loadPool());
        Post post = new Post(0, "Java Job", "опыт 3 мес", LocalDateTime.now(), new City(0, "Москва"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
        assertThat(postInDb.getDescription(), is(post.getDescription()));
        assertThat(postInDb.getCity(), is(post.getCity()));
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new JdbcConfiguration().loadPool());
        Post post = new Post(0, "Java Job", "опыт 3 мес", LocalDateTime.now(), new City(0, "Москва"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
        post.setName("Java Job 1");
        post.setCity(new City(1, "Спб"));
        store.update(post);
        postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
        assertThat(postInDb.getDescription(), is(post.getDescription()));
        assertThat(postInDb.getCity().getId(), is(post.getCity().getId()));
    }
}