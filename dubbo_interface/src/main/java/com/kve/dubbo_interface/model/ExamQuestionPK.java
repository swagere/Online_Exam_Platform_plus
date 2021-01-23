package com.kve.dubbo_interface.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExamQuestionPK implements Serializable {
    private Long exam_id;
    private Long question_id;
}
