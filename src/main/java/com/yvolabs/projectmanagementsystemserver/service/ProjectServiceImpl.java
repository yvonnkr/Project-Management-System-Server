package com.yvolabs.projectmanagementsystemserver.service;

import com.yvolabs.projectmanagementsystemserver.exception.custom.ObjectNotFoundException;
import com.yvolabs.projectmanagementsystemserver.model.Chat;
import com.yvolabs.projectmanagementsystemserver.model.Project;
import com.yvolabs.projectmanagementsystemserver.model.User;
import com.yvolabs.projectmanagementsystemserver.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yvonne N
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ChatService chatService;

    @Override
    public Project createProject(Project project, User user) throws Exception {

        Project createdProject = new Project();
        createdProject.setName(project.getName());
        createdProject.setDescription(project.getDescription());
        createdProject.setCategory(project.getCategory());
        createdProject.setTags(project.getTags());
        createdProject.setOwner(user);

        createdProject.getTeam().add(user);

        Project savedProject = projectRepository.save(createdProject);

        Chat chat = new Chat();
        chat.setProject(savedProject);
        Chat projectChat = chatService.createChat(chat);

        savedProject.setChat(projectChat);

        return savedProject;

    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        // filter by team or owner
        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);

        // filter by category
        if (category != null) {
            projects = projects.stream()
                    .filter(p -> p.getCategory().equals(category))
                    .toList();
        }

        // filter by tag
        if (tag != null) {
            projects = projects.stream()
                    .filter(p -> p.getTags().contains(tag))
                    .toList();
        }

        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ObjectNotFoundException("project not found with id: " + projectId));

    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        getProjectById(projectId);
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project projectUpdate, Long projectId) throws Exception {
        Project project = getProjectById(projectId);

        if (projectUpdate.getName() != null) project.setName(projectUpdate.getName());
        if (projectUpdate.getDescription() != null) project.setDescription(projectUpdate.getDescription());
        if (!projectUpdate.getTags().isEmpty()) project.setTags(projectUpdate.getTags());

        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (!project.getTeam().contains(user)) {
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }

        projectRepository.save(project);

    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (project.getTeam().contains(user)) {
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }

        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {

        return projectRepository.findByNameContainingAndTeamContains(keyword, user);

//        return projectRepository.findByNameContainsAndTeamContains(keyword, user);
//        return projectRepository.findByNameContainsOrTeamContains(keyword, user);
    }

}
