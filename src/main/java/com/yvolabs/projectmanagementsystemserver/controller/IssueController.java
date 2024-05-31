package com.yvolabs.projectmanagementsystemserver.controller;

import com.yvolabs.projectmanagementsystemserver.dto.request.IssueRequest;
import com.yvolabs.projectmanagementsystemserver.dto.response.IssueResponse;
import com.yvolabs.projectmanagementsystemserver.dto.response.MessageResponse;
import com.yvolabs.projectmanagementsystemserver.model.Issue;
import com.yvolabs.projectmanagementsystemserver.model.User;
import com.yvolabs.projectmanagementsystemserver.service.IssueService;
import com.yvolabs.projectmanagementsystemserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yvonne N
 */
@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;
    private final UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception {
        Issue issue = issueService.getIssueById(issueId);
        return ResponseEntity.ok(issue);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssuesByProjectId(@PathVariable Long projectId) throws Exception {
        List<Issue> issues = issueService.getIssuesByProjectId(projectId);
        return ResponseEntity.ok(issues);
    }

    @PostMapping
    public ResponseEntity<IssueResponse> createIssue(
            @RequestBody IssueRequest issueRequest, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Issue createdIssue = issueService.createIssue(issueRequest, user);

        IssueResponse issueResponse = IssueResponse.builder()
                .id(createdIssue.getId())
                .title(createdIssue.getTitle())
                .description(createdIssue.getDescription())
                .status(createdIssue.getStatus())
                .projectID(createdIssue.getProjectID())
                .priority(createdIssue.getPriority())
                .dueDate(createdIssue.getDueDate())
                .tags(createdIssue.getTags())
                .project(createdIssue.getProject())
                .user(user)
                .build();

        return new ResponseEntity<>(issueResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId) throws Exception {
        issueService.deleteIssue(issueId);
        return ResponseEntity.ok(MessageResponse.builder()
                .message("issue deleted successfully")
                .build());
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(
            @PathVariable Long issueId, @PathVariable Long userId) throws Exception {

        Issue issue = issueService.addUserToIssue(issueId, userId);
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateStatus(
            @PathVariable Long issueId, @PathVariable String status) throws Exception {
        Issue issue = issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(issue);
    }

}
