package com.grq.email_notification_msv.rest.services.internal.interfaces;

import com.grq.email_notification_msv.rest.models.events.ProcessedEvent;

public interface ProcessedEventService {


    public void storeProcessesdEvent( ProcessedEvent processedEvent );

    public boolean isEventProcessed( String eventId );

}
