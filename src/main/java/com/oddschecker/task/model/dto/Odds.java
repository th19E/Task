package com.oddschecker.task.model.dto;

import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Value
@ToString
public class Odds {

    @Min(value = 1, message = "invalid betId")
    int betId;

    @NotBlank(message = "userId must not be blank")
    String userId;

    @Pattern(regexp="^[1-9][0-9]*\\/[0-9]$|SP", message = "invalid odds")
    String odds;

}
