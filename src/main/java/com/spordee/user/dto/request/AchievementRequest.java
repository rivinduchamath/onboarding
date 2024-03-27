package com.spordee.user.dto.request;

import com.spordee.user.entity.objects.Achievements;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class AchievementRequest {
    private String userName;
    private List<Achievements> achievements;
}
