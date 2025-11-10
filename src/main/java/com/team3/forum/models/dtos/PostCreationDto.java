package com.team3.forum.models.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PostCreationDto {
    @NotNull
    @Size(min = 16, max = 64)
    private String title;

    @NotNull
    @Size(min = 32, max = 8192)
    private String content;

    @NotNull
    @Positive
    private int userId;
}
