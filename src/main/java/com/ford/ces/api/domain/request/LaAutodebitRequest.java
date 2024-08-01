package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LaAutodebitRequest {

    @JsonProperty("WIARBILOperation")
    private WIARBILOperation wiarbilOperation;
}
