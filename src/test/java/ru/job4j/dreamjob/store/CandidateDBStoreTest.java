package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.config.JdbcConfiguration;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDBStoreTest {
    @Test
    public void whenCreateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new JdbcConfiguration().loadPool());
        Candidate candidate = new Candidate(0, "John Smith", "опыт 2 года",
                LocalDateTime.now(), new City(0, "Москва"));
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
        assertThat(candidateInDb.getDescription(), is(candidate.getDescription()));
        assertThat(candidateInDb.getCity(), is(candidateInDb.getCity()));
    }

    @Test
    public void whenUpdatePost() {
        CandidateDBStore store = new CandidateDBStore(new JdbcConfiguration().loadPool());
        Candidate candidate = new Candidate(0, "John Smith", "опыт 2 года",
                LocalDateTime.now(), new City(0, "Москва"));
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
        candidate.setName("Джон Смит");
        candidate.setCity(new City(1, "Спб"));
        store.update(candidate);
        candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
        assertThat(candidateInDb.getDescription(), is(candidate.getDescription()));
        assertThat(candidateInDb.getCity().getId(), is(candidate.getCity().getId()));
    }
}