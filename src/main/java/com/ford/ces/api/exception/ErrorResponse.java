package com.ford.ces.api.exception;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class ErrorResponse {

	@Size(max = 100)
	private Set<@Pattern(regexp = "^[a-zA-Z]+(([\\''\\,\\.\\- ][a-zA-Z ])?[a-zA-Z]*)*$")  @Size(max = 100) String> errors = new LinkedHashSet<>();

	private Set<String> errorCodes = new LinkedHashSet<>();
}
