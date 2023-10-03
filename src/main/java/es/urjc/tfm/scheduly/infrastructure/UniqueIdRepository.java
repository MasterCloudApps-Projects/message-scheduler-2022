package es.urjc.tfm.scheduly.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;

import es.urjc.tfm.scheduly.infrastructure.models.IdEntity;

public interface UniqueIdRepository extends MongoRepository<IdEntity, String>{
	
}
