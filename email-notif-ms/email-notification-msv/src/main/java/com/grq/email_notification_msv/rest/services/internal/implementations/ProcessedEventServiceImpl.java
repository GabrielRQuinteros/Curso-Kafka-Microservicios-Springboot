package com.grq.email_notification_msv.rest.services.internal.implementations;

import com.grq.email_notification_msv.exceptions.ProcessedEventStoringException;
import com.grq.email_notification_msv.rest.models.events.ProcessedEvent;
import com.grq.email_notification_msv.rest.repositories.ProcessedEventRepository;
import com.grq.email_notification_msv.rest.services.internal.interfaces.ProcessedEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProcessedEventServiceImpl implements ProcessedEventService {

    private final ProcessedEventRepository repository;

    @Transactional(readOnly = false)
    @Override
    public void storeProcessesdEvent(ProcessedEvent processedEvent) {
        try{
            this.repository.save(processedEvent);
        }catch (DataAccessException e) {
            throw new ProcessedEventStoringException("Error al guardar el evento procesado en la base de datos", e);
        }
    }

    @Transactional( readOnly = true )
    @Override
    public boolean isEventProcessed(String messageId) {
        Optional<ProcessedEvent> processedEventOptional = this.repository.findById(messageId);
        return processedEventOptional.isPresent();
    }


}
