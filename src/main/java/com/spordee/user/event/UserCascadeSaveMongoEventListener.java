package com.spordee.user.event;

import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class UserCascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Object> event) {
        final Object source = event.getSource();
        if ((source instanceof PrimaryUserDetails) && (((PrimaryUserDetails) source).getUserEmail() != null)) {
            mongoOperations.save(((PrimaryUserDetails) source).getUserEmail());
        }
    }
}