package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.CandidateService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

class CandidateControllerTest {

        CandidateService candidateService;

        CityService cityService;

        CandidateController candidateController;

        MultipartFile testFile;
        HttpSession session;

        @BeforeEach
        void initServices() {
            candidateService = mock(CandidateService.class);
            cityService = mock(CityService.class);
            candidateController = new CandidateController(candidateService, cityService);
            testFile = new MockMultipartFile("testFile.img", new byte[] {1, 2, 3});
            session = mock(HttpSession.class);
        }

        @Test
        void whenRequestCandidateListPageThenGetPageWithCandidates() {
            var candidate1 = new Candidate(1, "test1", "desc1", now(), cityService.findById(0));
            var candidate2 = new Candidate(2, "test2", "desc2", now(), cityService.findById(1));
            var expectedCandidates = List.of(candidate1, candidate2);
            when(candidateService.findAll()).thenReturn(expectedCandidates);

            var model = new ConcurrentModel();
            var view = candidateController.candidates(model, session);
            var actualCandidates = model.getAttribute("candidates");

            assertThat(view).isEqualTo("candidates");
            assertThat(actualCandidates).isEqualTo(expectedCandidates);
        }

        @Test
        void whenRequestCandidateCreationPageThenGetPageWithCities() {
            var city1 = new City(1, "Москва");
            var city2 = new City(2, "Санкт-Петербург");
            var expectedCities = List.of(city1, city2);
            when(cityService.getAllCities()).thenReturn(expectedCities);

            var model = new ConcurrentModel();
            var view = candidateController.addCandidate(model, session);
            var actualCities = model.getAttribute("cities");

            assertThat(view).isEqualTo("addCandidate");
            assertThat(actualCities).isEqualTo(expectedCities);
        }

        @Test
        void whenCreateCandidateRedirectToCandidatesPage() throws IOException {

            var candidate = new Candidate(1, "test1", "desc1", now(), cityService.findById(0));
            var candidateArgumentCaptor = ArgumentCaptor.forClass(Candidate.class);
            doNothing().when(candidateService).add(candidateArgumentCaptor.capture());

            var view = candidateController.createCandidate(candidate, 1, testFile);
            var actualCandidate = candidateArgumentCaptor.getValue();

            assertThat(view).isEqualTo("redirect:/candidates");
            assertThat(actualCandidate).isEqualTo(candidate);

        }

        @Test
        void whenRequestCandidateUpdatePageThenGetPageWithCandidateAndCities() throws IOException {

            var candidate = new Candidate(1, "test1", "desc1", now(), cityService.findById(0));
            var candidateArgumentCaptor = ArgumentCaptor.forClass(Candidate.class);
            var city1 = new City(1, "Москва");
            var city2 = new City(2, "Санкт-Петербург");
            var expectedCities = List.of(city1, city2);
            doNothing().when(candidateService).add(candidateArgumentCaptor.capture());
            when(cityService.getAllCities()).thenReturn(expectedCities);

            candidateController.createCandidate(candidate, 1, testFile);
            var capturedCandidate = candidateArgumentCaptor.getValue();
            var model = new ConcurrentModel();
            var view = candidateController.formUpdateCandidate(model, capturedCandidate.getId());
            var actualCities = model.getAttribute("cities");

            assertThat(view).isEqualTo("updateCandidate");
            assertThat(actualCities).isEqualTo(expectedCities);

        }

        @Test
        void whenUpdateCandidateRedirectToCandidatesPage() throws IOException {

            var candidate = new Candidate(1, "test1", "desc1", now(), cityService.findById(0));
            var candidateCaptorUpdate = ArgumentCaptor.forClass(Candidate.class);
            doNothing().when(candidateService).add(candidate);
            doNothing().when(candidateService).update(candidateCaptorUpdate.capture());

            candidateController.createCandidate(candidate, 1, testFile);
            candidate.setName("test2");
            var viewUpdate = candidateController.updateCandidate(candidate, 2, testFile);
            var actualCandidate = candidateCaptorUpdate.getValue();

            assertThat(viewUpdate).isEqualTo("redirect:/candidates");
            assertThat(actualCandidate.getName()).isEqualTo("test2");

        }

}