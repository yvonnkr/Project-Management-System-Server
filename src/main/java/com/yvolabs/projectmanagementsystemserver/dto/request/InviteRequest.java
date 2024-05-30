package com.yvolabs.projectmanagementsystemserver.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yvonne N
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InviteRequest {

    private Long projectId;

    private String email;
}

