package com.shareThegame.STG.Repository;

import com.shareThegame.STG.Model.ObjectStars;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public
interface ObjectStarsRepository extends JpaRepository<ObjectStars,Long> {
List<ObjectStars> findAllBySportobjectid(Long sportobjectid);
}
