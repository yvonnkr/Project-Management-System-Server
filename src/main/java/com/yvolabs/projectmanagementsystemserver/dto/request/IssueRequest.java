package com.yvolabs.projectmanagementsystemserver.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Yvonne N
 */
@Data
public class IssueRequest {
    private String title;
    private String description;
    private String status;
    private Long projectID;
    private String priority;
    private LocalDateTime dueDate;

}
