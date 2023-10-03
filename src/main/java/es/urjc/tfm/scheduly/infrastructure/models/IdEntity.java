package es.urjc.tfm.scheduly.infrastructure.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "unique_id") 
public class IdEntity {

    @Id 
    private String id;

    private Long uniqueId;

    public IdEntity() {
    }

    public IdEntity(String id, Long uniqueId) {
        this.id = id;
        this.uniqueId = uniqueId;
    }

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Long getUniqueId() {
        return this.uniqueId;
    }
    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }
}
