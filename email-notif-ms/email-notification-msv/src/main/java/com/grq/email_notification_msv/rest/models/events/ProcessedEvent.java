package com.grq.email_notification_msv.rest.models.events;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ProcessedEvent implements Serializable {

    @Id
    @Column(unique = true, nullable = false)
    private String messageId;

    @Column(unique = false, nullable = false)
    private String key;
}
