package com.shareThegame.STG.Repository;

import com.shareThegame.STG.Model.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface TimeTableReposiotry extends JpaRepository<TimeTable,Long> {
    TimeTable findBySportobjectid(Long sportobjectid);
    List<TimeTable>findAllBySportobjectid(Long sportobjectid);
    TimeTable  findByStartrentAndEndrendAndSportobjectid( Date startrent,Date endrent,Long sportobjectid );

}
