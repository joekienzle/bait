package org.bait.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferInfoRepository extends JpaRepository<TransferInfoImpl, String> {

    TransferInfoImpl save(TransferInfoImpl transferInfoImpl);

    TransferInfoImpl findOne(String transferId);

    void deleteByTransferId(String transferId);

}
