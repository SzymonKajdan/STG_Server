package com.shareThegame.STG.Repository;

import com.shareThegame.STG.Model.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface TimeTableReposiotry extends JpaRepository<TimeTable,Long> {
    TimeTable findBySportobjectid(Long sportobjectid);
    ArrayList<TimeTable> findAllBySportobjectid( Long sportobjectid);
    TimeTable findByRenteridAndStartrentAndEndrend(Long retnerid, Date startrent,Date endrent);
    TimeTable  findByStartrentAndEndrendAndSportobjectid( Date startrent,Date endrent,Long sportobjectid );
    List<TimeTable> findAllBysportobjectid(Long sportobjectid);
    List<TimeTable>findAllByRenterid(Long renterid);
    List<TimeTable>findAllBySportobjectidAndStartrentAfterAndEndrendBefore(Long sportobjectid,Date startrent,Date endrent);
    TimeTable findBySportobjectidAndId(Long sportojbectid,Long id);
}
