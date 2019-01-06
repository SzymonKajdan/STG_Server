package com.shareThegame.STG.Repository;

import com.shareThegame.STG.Model.PaymentHisotry;
import com.shareThegame.STG.Model.SportObject;
import com.shareThegame.STG.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public
interface PaymentHistoryRepository extends JpaRepository<PaymentHisotry,Long> {
    PaymentHisotry findByUserid(Long userid);
    PaymentHisotry findBySportobjectid(Long sportobjectid);
    PaymentHisotry findBySportobjectidAndAndStartrentAndExprrent( Long sportobjectid, Date startrent,Date exprent );
    PaymentHisotry findBySportobjectidAndStartrentAndExprrent(Long sportobjectid, Date startrent,Date exprent);
    List<PaymentHisotry> findAll();
    List<PaymentHisotry>findAllBySportobjectid(Long sportobjectid);
    List<PaymentHisotry>findAllByUser( User user);
    List<PaymentHisotry>findAllByUserid(Long userid);
    List<PaymentHisotry>findAllBySportObject( SportObject sportObject );
   // List<User>findAllBySportobjectid(Long sportobjectid);
}
