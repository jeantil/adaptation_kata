/*
 * Copyright 2013  Séven Le Mesle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package services;

import api.domain.AudioEvent;
import api.domain.Event;
import api.domain.FileEvent;
import api.domain.SmsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistent.*;

/**
 * Created by slemesle on 29/04/2014.
 */
@Service
public class EntityToResourceMapper {

    private final EventMapper eventMapper;

    @Autowired
    public EntityToResourceMapper(EventMapper eventMapper ) {
        this.eventMapper = eventMapper;
    }

    public EventEntity asEventEntity(Event event, UserEntity user) {
        EventEntity entity = null;
        if (FileEvent.class.equals( event.getClass())){
            entity = eventMapper.asFileEntity((FileEvent)event);
        }else if (SmsEvent.class.equals(event.getClass())){
            entity = eventMapper.asSmsEntity((SmsEvent)event);
        }else if (AudioEvent.class.equals(event.getClass())){
            entity = eventMapper.asAudioEntity((AudioEvent) event);
        }
        entity.setUserId(user.getUserId());
        return entity;
    }


    public Event asResource(EventEntity eventEntity) {
        Event res = null;
        switch (eventEntity.getType()){
            case file:
                res = eventMapper.asFileEvent((FileEventEntity)eventEntity);
                break;
            case sms:
                res = eventMapper.asSmsEvent((SmsEventEntity) eventEntity);
                break;
            case text: // Don't know what to do here so do nothing
                //       res = eventMapper.asXmppEvent((XmppEventEntity) eventEntity);
                break;
            case audio:
                res = eventMapper.asAudioEvent((AudioEventEntity) eventEntity);
                break;
        }
        return res;
    }
}
