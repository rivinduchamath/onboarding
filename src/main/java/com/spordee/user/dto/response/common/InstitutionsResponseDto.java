package com.spordee.user.dto.response.common;

import com.spordee.user.entity.objects.InstituteDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class InstitutionsResponseDto {
    private String userName;
    private List<InstituteDetails> instituteDetails;
}
