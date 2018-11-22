package com.shareThegame.STG.Repository;

import com.shareThegame.STG.Model.PaymentHisotry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public
interface PaymentHistoryRepository extends JpaRepository<PaymentHisotry,Long> {
    PaymentHisotry findByUserid(Long userid);
    PaymentHisotry findBySportobjectidAndAndStartrentAndExprrent( Long sportobjectid, Date startrent,Date exprent );
    List<PaymentHisotry> findAll();
}
