package com.yvolabs.projectmanagementsystemserver.repository;

import com.yvolabs.projectmanagementsystemserver.model.Project;
import com.yvolabs.projectmanagementsystemserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Yvonne N
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByNameContainingAndTeamContains(String partialName, User user);

    List<Project> findByTeamContainingOrOwner(User user, User owner);

    /*
    List<Project> findByOwner(User owner);

    @Query("SELECT p FROM Project p JOIN p.team t WHERE t=:user ")
    List<Project> findByTeam(@Param("user") User user);

     */
}
