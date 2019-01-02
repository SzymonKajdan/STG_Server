package com.shareThegame.STG.Repository;

import com.shareThegame.STG.Model.OpenHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public
@Repository
interface OpenHoursRepository extends JpaRepository<OpenHours,Long> {
    OpenHours findBySportobjectid(long sportobjectid);
}
