package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDBStore {

    private final BasicDataSource pool;
    public static final Logger LOG = LoggerFactory.getLogger(CandidateDBStore.class.getName());
    public static final String SELECT_ALL_CANDIDATE = "SELECT * FROM candidate";
    public static final String INSERT_CANDIDATE = "INSERT INTO candidate(name, description, created, city_id, photo) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE_CANDIDATE = "UPDATE candidate set name = ?, description = ?, city_id = ?, photo = ? where id = ?";
    public static final String SELECT_CANDIDATE_BY_ID = "SELECT * FROM candidate WHERE id = ?";

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_ALL_CANDIDATE)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(getNewCandidate(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(INSERT_CANDIDATE,
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(4, candidate.getCity().getId());
            ps.setBytes(5, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidate;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(UPDATE_CANDIDATE)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setInt(3, candidate.getCity().getId());
            ps.setBytes(4, candidate.getPhoto());
            ps.setInt(5, candidate.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_CANDIDATE_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                   return getNewCandidate(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    private Candidate getNewCandidate(ResultSet it) throws SQLException {
        Candidate candidate = new Candidate(
                it.getInt("id"),
                it.getString("name"),
                it.getString("description"),
                it.getTimestamp("created").toLocalDateTime(),
                new City(it.getInt("city_id"), ""));
        candidate.setPhoto(it.getBytes("photo"));
        return candidate;
    }
}
