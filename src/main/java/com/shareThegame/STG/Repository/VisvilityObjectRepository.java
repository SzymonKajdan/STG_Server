package com.shareThegame.STG.Repository;

import com.shareThegame.STG.Model.VisibilityObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisvilityObjectRepository extends JpaRepository<VisibilityObject,Long> {
    VisibilityObject findBySportobjectid(long sportobjectid) ;
    List<VisibilityObject> findAll();
}
