package com.example.effective.mobile.sm.api.dto.request;

import lombok.Data;


@Data
public abstract class ApproveContactDto {
    private String contact;

    private Integer code;
}
