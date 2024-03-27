package com.spordee.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateSkillsRequest {
    private String userName;
    private Set<String> skills;
}
