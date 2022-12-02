package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();
    private  final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    private CandidateStore() {
        Candidate candidate1 = new Candidate(currentId.incrementAndGet(), "Мхитарянц Аветис", "Опыт работы 1 год", LocalDateTime.now());
        candidates.put(candidate1.getId(), candidate1);
        Candidate candidate2 = new Candidate(currentId.incrementAndGet(), "Ким Роман", "Опыт работы до 3-х лет", LocalDateTime.now());
        candidates.put(candidate2.getId(), candidate2);
        Candidate candidate3 = new Candidate(currentId.incrementAndGet(), "Катранов Вадим", "Опыт работы более 3-х лет", LocalDateTime.now());
        candidates.put(candidate3.getId(), candidate3);
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(currentId.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }
}
