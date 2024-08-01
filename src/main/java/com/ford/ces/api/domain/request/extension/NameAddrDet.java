package com.ford.ces.api.domain.request.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NameAddrDet {

    @JsonProperty("NameAddrType")
    private String nameAddrType;

    @JsonProperty("NameAddrLine")
    private String nameAddrLine;

}
