package com.unlimint.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OutputOrder extends Order {

    @JsonProperty("filename")
    private String filename;
    @JsonProperty("line")
    private long lineNum;
}
