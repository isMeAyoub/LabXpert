package com.simplon.labxpert.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class RoleDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long roleID;
    @NotEmpty(message = "Role name is mandatory")
    private String roleName;
}
