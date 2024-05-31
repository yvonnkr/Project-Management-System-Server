package com.yvolabs.projectmanagementsystemserver.service;

import com.yvolabs.projectmanagementsystemserver.dto.request.IssueRequest;
import com.yvolabs.projectmanagementsystemserver.exception.custom.ObjectNotFoundException;
import com.yvolabs.projectmanagementsystemserver.model.Issue;
import com.yvolabs.projectmanagementsystemserver.model.Project;
import com.yvolabs.projectmanagementsystemserver.model.User;
import com.yvolabs.projectmanagementsystemserver.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yvonne N
 */
@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final ProjectService projectService;
    private final UserService userService;


    @Override
    public Issue getIssueById(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Issue with id " + id + " not found!"));
    }

    @Override
    public List<Issue> getIssuesByProjectId(Long projectId) {
        return issueRepository.findAllByProjectId(projectId)
                .orElseThrow(() -> new ObjectNotFoundException("Issue with projectId " + projectId + " not found"));
    }

    @Override
    public Issue createIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project = projectService.getProjectById(issueRequest.getProjectID());

        Issue newIssue = new Issue();
        newIssue.setTitle(issueRequest.getTitle());
        newIssue.setDescription(issueRequest.getDescription());
        newIssue.setStatus(issueRequest.getStatus());
        newIssue.setProjectID(issueRequest.getProjectID());
        newIssue.setPriority(issueRequest.getPriority());
        newIssue.setDueDate(issueRequest.getDueDate());
        newIssue.setAssignee(user);

        newIssue.setProject(project);

        return issueRepository.save(newIssue);
    }

    @Override
    public void deleteIssue(Long issueId) {
        getIssueById(issueId);
        issueRepository.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(issueId);
        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issue updateStatus(Long issueId, String status) {
        Issue issue = getIssueById(issueId);
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
