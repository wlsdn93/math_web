package com.math.weakness.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ProblemTag {

    @EmbeddedId
    private ProblemTagId problemTagId = new ProblemTagId();

    @ManyToOne
    @MapsId("problemId")
    @JoinColumn(name = "PROBLEM_ID")
    private Problem problem;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

}
