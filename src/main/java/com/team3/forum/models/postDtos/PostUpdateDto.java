package com.team3.forum.models.postDtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostUpdateDto {
    @NotNull
    @Size(min = 16, max = 64)
    private String title;

    @NotNull
    @Size(min = 32, max = 8192)
    private String content;

    @Size(max = 50, message = "Tag must be less than 50 characters")
    @Pattern(regexp = "[a-z0-9-]{0,50}", message = "Tag must be one word containing only letters, numbers and dash")
    private String tag1;

    @Size(max = 50, message = "Tag must be less than 50 characters")
    @Pattern(regexp = "[a-z0-9-]{0,50}", message = "Tag must be one word containing only letters, numbers and dash")
    private String tag2;

    @Size(max = 50, message = "Tag must be less than 50 characters")
    @Pattern(regexp = "[a-z0-9-]{0,50}", message = "Tag must be one word containing only letters, numbers and dash")
    private String tag3;
}
