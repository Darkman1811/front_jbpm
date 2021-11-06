package com.guestu.jbpm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompleteTaskDTO implements Serializable {
    private String containerId;
    private String  userId;
    private Long taskid;
    private Map<String,Object> variables;
}
