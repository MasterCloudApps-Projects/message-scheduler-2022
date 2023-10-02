package es.urjc.tfm.scheduly.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.urjc.tfm.scheduly.infrastructure.models.MessageEntityMongo;

public interface MessageMongoRepository extends MongoRepository<MessageEntityMongo, Long>{
	
}
