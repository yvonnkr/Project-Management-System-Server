package com.yvolabs.projectmanagementsystemserver.controller;

import com.yvolabs.projectmanagementsystemserver.dto.request.InviteRequest;
import com.yvolabs.projectmanagementsystemserver.dto.response.MessageResponse;
import com.yvolabs.projectmanagementsystemserver.model.Chat;
import com.yvolabs.projectmanagementsystemserver.model.Invitation;
import com.yvolabs.projectmanagementsystemserver.model.Project;
import com.yvolabs.projectmanagementsystemserver.model.User;
import com.yvolabs.projectmanagementsystemserver.service.InvitationService;
import com.yvolabs.projectmanagementsystemserver.service.ProjectService;
import com.yvolabs.projectmanagementsystemserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yvonne N
 */
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;
    private final InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(@RequestParam(required = false) String category,
                                                     @RequestParam(required = false) String tag, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectByTeam(user, category, tag);

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long projectId) throws Exception {
        Project project = projectService.getProjectById(projectId);
        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestBody Project projectUpdate, @RequestHeader("Authorization") String jwt) throws Exception {
        Project updatedProject = projectService.updateProject(projectUpdate, projectId);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());
        MessageResponse response = MessageResponse.builder()
                .message("project deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam(required = false) String keyword,
                                                        @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword, user);

        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectById(@PathVariable Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        return ResponseEntity.ok(chat);
    }

    @PostMapping("/send_invitation")
    public ResponseEntity<?> sendInvitationRequest(@RequestBody InviteRequest request) throws Exception {
        invitationService.sendInvitation(request.getEmail(), request.getProjectId());
        MessageResponse response = MessageResponse.builder()
                .message("user invitation sent successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptInvitationProject(@RequestParam String token, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token, user.getId());

        projectService.addUserToProject(invitation.getProjectId(), user.getId());

        return ResponseEntity.ok(invitation);
    }


}
