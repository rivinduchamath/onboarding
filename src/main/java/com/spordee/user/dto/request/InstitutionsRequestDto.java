package com.spordee.user.dto.request;

import com.spordee.user.entity.objects.InstituteDetails;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionsRequestDto {
    private String userName;
    private List<InstituteDetails> instituteDetails;
}
