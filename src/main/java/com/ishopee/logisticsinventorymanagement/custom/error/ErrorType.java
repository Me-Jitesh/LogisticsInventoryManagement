package com.ishopee.logisticsinventorymanagement.custom.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorType {
    private String dateTime;
    private String module;
    private String messge;
}
