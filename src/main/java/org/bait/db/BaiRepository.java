package org.bait.db;

import org.bait.model.Bai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaiRepository extends JpaRepository<Bai, String> {

    Bai save(Bai persisted);

    Bai findOne(String baiId);

    void delete(Bai deleted);

}
