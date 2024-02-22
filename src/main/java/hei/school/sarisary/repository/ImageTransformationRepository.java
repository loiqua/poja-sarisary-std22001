package school.hei.sary.repository;


import hei.school.sarisary.repository.model.ImageTransformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageTransformationRepository extends CrudRepository<ImageTransformation, String> {
    @Override
    Optional<ImageTransformation> findById(String id);
}
