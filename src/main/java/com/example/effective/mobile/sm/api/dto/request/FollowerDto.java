package com.example.effective.mobile.sm.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FollowerDto {

    @JsonProperty("contact")
    private String publisherContact;

}
