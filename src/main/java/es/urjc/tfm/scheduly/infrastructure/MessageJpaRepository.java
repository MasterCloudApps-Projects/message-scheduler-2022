package es.urjc.tfm.scheduly.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import es.urjc.tfm.scheduly.infrastructure.models.MessageEntity;

public interface MessageJpaRepository extends JpaRepository<MessageEntity, Long> {

}