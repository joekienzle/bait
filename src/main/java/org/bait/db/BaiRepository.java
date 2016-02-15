package org.bait.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaiRepository extends JpaRepository<BaiImpl, String> {

    BaiImpl save(BaiImpl persisted);

    BaiImpl findOne(String baiId);

    void deleteByBaiId(String baiId);

}
