package com.shareThegame.STG.Repository;

import com.shareThegame.STG.Model.FavouriteObjects;
import com.shareThegame.STG.Model.ObjectExtras;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public
interface FavouriteObjectsRepository extends JpaRepository<FavouriteObjects,Long> {
    List<FavouriteObjects> findByUserid(Long userid  );
}
