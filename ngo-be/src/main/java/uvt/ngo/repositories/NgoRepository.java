package uvt.ngo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import uvt.ngo.rest.entity.NGO;

public interface NgoRepository extends MongoRepository<NGO, String> {

}