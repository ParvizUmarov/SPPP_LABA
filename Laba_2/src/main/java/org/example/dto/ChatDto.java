package org.example.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ChatDto {
    private Integer id;
    private Integer barberId;
    private Integer customerId;

}
