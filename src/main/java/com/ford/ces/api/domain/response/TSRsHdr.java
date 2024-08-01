package com.ford.ces.api.domain.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TSRsHdr  {

    @JsonProperty("MaxSeverity")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String maxSeverity;

    @JsonProperty("ProcessMessage")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String processMessage;

    @JsonProperty("NextDay")
    @Size(min =0 ,max = 36)
    @Pattern(regexp = "^[A-Za-z0-9]*$|^\\s*$")
    private String nextDay;

    @JsonProperty("TrnStatus")
    private List<TrnStatus> trnStatus;

}
