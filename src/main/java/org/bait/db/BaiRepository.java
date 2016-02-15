package org.bait.db;

import org.bait.db.model.BaiDbImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaiRepository extends JpaRepository<BaiDbImpl, String> {

    BaiDbImpl save(BaiDbImpl persisted);

    BaiDbImpl findOne(String baiId);

    void deleteByBaiId(String baiId);

}
