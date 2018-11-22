package com.shareThegame.STG.Repository;

import com.shareThegame.STG.Model.ObjectPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public
@Repository
interface ObjectPhotosRepository extends JpaRepository<ObjectPhotos,Long> {
    ObjectPhotos findBySportobjectid(Long sportobjectid);
    List<ObjectPhotos> findAllBySportobjectid(Long sportobjectid);
}
