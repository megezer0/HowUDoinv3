package sabanciuniv.edu.howudoin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sabanciuniv.edu.howudoin.model.UserModel;

public interface UsersRepository extends MongoRepository<UserModel, String> {
    UserModel findByEmail(String username);
}
