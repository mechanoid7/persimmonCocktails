package com.example.persimmoncocktails.dtos.friendshipInvitation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipInvitationResponseDto {
    private Long personId;
    private String name;
    private String message;
    private Long photoId;
    private Long blogId;
    private LocalDateTime localDateTime;
}
