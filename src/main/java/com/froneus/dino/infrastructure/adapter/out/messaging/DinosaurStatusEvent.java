package com.froneus.dino.infrastructure.adapter.out.messaging;

import com.froneus.dino.domain.model.DinosaurStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DinosaurStatusEvent {

    private Long dinosaurId;
    private DinosaurStatus newStatus;
    private LocalDateTime timestamp;

}