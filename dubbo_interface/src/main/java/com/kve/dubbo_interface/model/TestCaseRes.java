package com.kve.dubbo_interface.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseRes implements Serializable {
    private Integer case_num;
    private String result;
    private String run_time;
    private String memory;
}
