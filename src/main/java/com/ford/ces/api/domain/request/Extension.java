package com.ford.ces.api.domain.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Extension {

    @Min(value = 1, message = "Number of Months should accept Only 1 digit between 1 to 6 - {NotBlank.noOfMonths}")
    @Max(value = 6, message = "Number of Months should accept Only 1 digit between 1 to 6 - {NotBlank.noOfMonths}")
    @JsonProperty("numberOfMonths")
    @JsonAlias("maximumMonths")
    private Integer numberOfMonths;

    @NotBlank(message = "Extension Reason should not be blank -{NotBlank.reasonCd}")
    @Pattern(regexp = "^D$", message = "Extension Reason should accept Only one alphabet 'D' - {Pattern.reasonCd}")
    private String reasonCd;

}
