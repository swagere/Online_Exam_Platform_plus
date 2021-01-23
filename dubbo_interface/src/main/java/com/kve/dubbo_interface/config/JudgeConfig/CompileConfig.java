package com.kve.dubbo_interface.config.JudgeConfig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * common
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompileConfig {
    String src_name;

    String exe_name;

    long max_cpu_time;

    long max_real_time;

    long max_memory;

    String compile_command;

}
