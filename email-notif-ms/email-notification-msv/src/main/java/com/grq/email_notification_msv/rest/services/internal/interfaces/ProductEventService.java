package com.grq.email_notification_msv.rest.services.internal.interfaces;

import com.grq.core.events.ProductCreatedEvent;

public interface ProductEventService {

    public void handleProductCreatedEvent( ProductCreatedEvent event, String messageId, String key );


}
