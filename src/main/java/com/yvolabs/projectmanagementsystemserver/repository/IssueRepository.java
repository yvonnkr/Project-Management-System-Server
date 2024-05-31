package com.yvolabs.projectmanagementsystemserver.repository;

import com.yvolabs.projectmanagementsystemserver.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Yvonne N
 */
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<List<Issue>> findAllByProjectId(Long projectId);
}
