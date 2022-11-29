package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private  final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Мхитарянц Аветис", "Опыт работы 1 год", new Date()));
        candidates.put(2, new Candidate(2, "Ким Роман", "Опыт работы до 3-х лет", new Date()));
        candidates.put(3, new Candidate(3, "Катранов Вадим", "Опыт работы более 3-х лет", new Date()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
