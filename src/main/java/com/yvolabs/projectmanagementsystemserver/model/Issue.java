package com.yvolabs.projectmanagementsystemserver.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Yvonne N
 */
@Entity
@Data
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User assignee;
}
