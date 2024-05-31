package com.yvolabs.projectmanagementsystemserver.service;

import com.yvolabs.projectmanagementsystemserver.dto.request.IssueRequest;
import com.yvolabs.projectmanagementsystemserver.model.Issue;
import com.yvolabs.projectmanagementsystemserver.model.User;

import java.util.List;

/**
 * @author Yvonne N
 */
public interface IssueService {
    Issue getIssueById(Long id) throws Exception;

    List<Issue> getIssuesByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issue, User user) throws Exception;

    void deleteIssue(Long issueId) throws Exception;

    Issue addUserToIssue(Long issueId, Long userId) throws Exception;

    Issue updateStatus(Long issueId, String status) throws Exception;

}
