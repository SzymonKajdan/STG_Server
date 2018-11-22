package com.shareThegame.STG.Repository;

import com.shareThegame.STG.Model.SportObject;
import com.shareThegame.STG.Model.TimeTable;
import com.shareThegame.STG.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportObjectReposiotry  extends  JpaRepository<SportObject,Long> {
    SportObject findByCity(String city);
    SportObject findByOwnid(Long ownid);
    SportObject findByRentprice(String rentprice);
    SportObject findByActive(int active);
    SportObject findByPpmail(String ppmail);
    SportObject getOne(Long id);
    List<SportObject> findAllByCity( String city);
    List<SportObject>findAllByActive(int active);
    List<SportObject>findAllById(Long id );
    List<SportObject>findAllByTimeTable( TimeTable timeTable );

}
