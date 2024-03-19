package com.spordee.user.dto.objects;

import com.spordee.user.enums.UserImageType;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class UserImagesDto {
    private String id;
    private String imageUrl;
    private boolean isActive;
    private UserImageType imageType; // Enum
    private String userName;
    private String description;
    private String createdDate;
    private String updatedDate;
}
