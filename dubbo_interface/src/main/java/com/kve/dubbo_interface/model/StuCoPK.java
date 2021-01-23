package com.kve.dubbo_interface.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class StuCoPK implements Serializable {
    private String stu_id;
    private String co_id;
    private String tea_id;
}
