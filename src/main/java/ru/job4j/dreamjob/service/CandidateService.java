package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDBStore;

import java.util.Collection;
import java.util.List;

@Service
public class CandidateService {
    private final CandidateDBStore candidateStore;
    private final CityService cityService;

    private CandidateService(CandidateDBStore candidateStore, CityService cityService) {
        this.candidateStore = candidateStore;
        this.cityService = cityService;
    }

    public Collection<Candidate> findAll() {
        List<Candidate> candidates = candidateStore.findAll();
        candidates.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return candidates;
    }

    public void add(Candidate candidate) {
        candidateStore.add(candidate);
    }

    public Candidate findById(int id) {
        return candidateStore.findById(id);
    }

    public void update(Candidate candidate) {
        candidateStore.update(candidate);
    }
}
