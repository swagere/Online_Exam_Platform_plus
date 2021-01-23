package com.kve.dubbo_interface.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StuScoreInfo implements Serializable {
    private Question.Type type;
    private List<Map<String, Object>> detail;
    private int get;
    private int total;


}
