package org.bait.db;

import org.bait.db.model.TransferInfoDbImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferInfoRepository extends JpaRepository<TransferInfoDbImpl, String> {

    TransferInfoDbImpl save(TransferInfoDbImpl transferInfoDbImpl);

    TransferInfoDbImpl findOne(String transferId);

    void deleteByTransferId(String transferId);

}
