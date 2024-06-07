package com.example.demo.dto.response;

import lombok.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class FriendshipResponse {
    private String id;
    private String fromId;
    private Date date;

}
