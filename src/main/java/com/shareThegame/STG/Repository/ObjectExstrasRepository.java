package com.shareThegame.STG.Repository;

import com.shareThegame.STG.Model.ObjectExtras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectExstrasRepository extends JpaRepository<ObjectExtras,Long> {
    ObjectExtras findBySportobjectid(Long sportobjectid);
}
