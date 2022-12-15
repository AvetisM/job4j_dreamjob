package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostDBStore;


import java.util.Collection;
import java.util.List;

@Service
public class PostService {
    private final PostDBStore postStore;
    private final CityService cityService;

    private PostService(PostDBStore postStore, CityService cityService) {
        this.postStore = postStore;
        this.cityService = cityService;
    }

    public Collection<Post> findAll() {
        List<Post> posts = postStore.findAll();
        posts.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return posts;
    }

    public void add(Post post) {
        postStore.add(post);
    }

    public Post findById(int id) {
        return postStore.findById(id);
    }

    public void update(Post post) {
        postStore.update(post);
    }
}
