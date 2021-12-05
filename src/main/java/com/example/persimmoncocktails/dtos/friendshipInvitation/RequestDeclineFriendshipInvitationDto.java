package com.example.persimmoncocktails.dtos.friendshipInvitation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDeclineFriendshipInvitationDto {
    private Long personId;
}
