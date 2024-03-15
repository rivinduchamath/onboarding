package com.spordee.user.convertor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.spordee.user.entity.primaryUserData.PrimaryUserDetails;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserWriterConverter implements Converter<PrimaryUserDetails, DBObject> {

    @Override
    public DBObject convert(final PrimaryUserDetails user) {
        final DBObject dbObject = new BasicDBObject();
        dbObject.put("name", user.getUserEmail());
        dbObject.put("age", user.getUserEmail());
        if (user.getUserEmail() != null) {
            final DBObject emailDbObject = new BasicDBObject();
//            emailDbObject.put("value", user.getUserEmail().get());
            dbObject.put("email", emailDbObject);
        }
        dbObject.removeField("_class");
        return dbObject;
    }

}