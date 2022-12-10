package ru.job4j.dreamjob.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostStore {

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    private PostStore() {
        CityService cityService = new CityService();
        City city = cityService.getAllCities().get(0);
        Post post1 = new Post(currentId.incrementAndGet(), "Junior Java Job", "Требуемый опыт - не требуется", LocalDateTime.now(), city);
        posts.put(post1.getId(), post1);
        Post post2 = new Post(currentId.incrementAndGet(), "Middle Java Job", "Требуемый опыт - меньше года", LocalDateTime.now(), city);
        posts.put(post2.getId(), post2);
        Post post3 = new Post(currentId.incrementAndGet(), "Senior Java Job", "Требуемый опыт - год и более", LocalDateTime.now(), city);
        posts.put(post3.getId(), post3);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(currentId.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }
}
