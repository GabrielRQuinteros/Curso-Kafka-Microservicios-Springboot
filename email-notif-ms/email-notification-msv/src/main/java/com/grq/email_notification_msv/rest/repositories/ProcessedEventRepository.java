package com.grq.email_notification_msv.rest.repositories;

import com.grq.email_notification_msv.rest.models.events.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {
}
