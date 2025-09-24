package com.app.sodor24_server.util.Jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDto {
    private String message;
    private String access_token;
    private LocalDateTime timestamp;

}
