package ru.goodsreview.core.db.entity;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

/**
 * User: daddy-bear
 * Date: 17.06.12
 * Time: 21:21
 */
public class EntityService {

    private final static String TYPE_ID_ATTR = "typeId";
    private final static String ID_ATTR = "id";

    private JdbcTemplate jdbcTemplate;

    @Required
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void writeEntities(final Collection<JsonObject> entities) {
        final MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        for (final JsonObject entity: entities) {
            digest.update(entity.toString().getBytes());
            final String hash = new String(digest.digest());
            digest.reset();
        }
    }
}
