package org.bait.db;

import org.bait.model.TransferInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferInfoRepository extends JpaRepository<TransferInfo, String> {

    TransferInfo save(TransferInfo transferInfo);

    TransferInfo findOne(String transferId);

    void deleteByTransferId(String transferId);

}
