package com.chay.couponprojectspring.repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chay.couponprojectspring.entities.Log;

/**
 * * The interface inherits from JpaRepository and contains the functions
 * necessary to manage the Log object in the database. The functions will be
 * exercised by Spring behind the scenes.
 * 
 * @author Chay Mizrahi
 *
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {

	Collection<Log> findTop20ByDate(Date date);

	Collection<Log> findFrist20ByDateBetween(Date startDate, Date endDate);

	@Query("select l from Log l where l.success = 0 ")
	Collection<Log> FindFailedLogs();

	@Query("select l from Log l where l.success = 1 ")
	Collection<Log> FindSuccessedLogs();

}
