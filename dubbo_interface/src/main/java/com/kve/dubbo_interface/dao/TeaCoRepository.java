package com.kve.dubbo_interface.dao;

import com.kve.dubbo_interface.model.TeaCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * common
 */
@Repository
public interface TeaCoRepository extends JpaRepository<TeaCo, String> {

    @Query("select u.co_id from TeaCo u where u.tea_id = ?1")
    List<String> findCo_idByTea_Id(String tea_id);

    @Query("select u.tea_id from TeaCo u where u.co_id = ?1")
    List<String> getTea_idByCo_id(String co_id);

    @Query("select u from TeaCo u where u.co_id in (:coIdList)")
    List<TeaCo> findByCo_id(List<String> coIdList);


}