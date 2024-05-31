package com.yvolabs.projectmanagementsystemserver.dto.response;

import com.yvolabs.projectmanagementsystemserver.model.Project;
import com.yvolabs.projectmanagementsystemserver.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yvonne N
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long projectID;
    private String priority;
    private LocalDateTime dueDate;
    private List<String> tags = new ArrayList<>();
    private Project project;
    // exclude assignee during serialization
    private User user;
}
