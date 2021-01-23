package com.kve.dubbo_interface.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetHandInScore implements Serializable {
    public String stu_id;
    public Long exam_id;
    List<StuExam> scoreList;
}
