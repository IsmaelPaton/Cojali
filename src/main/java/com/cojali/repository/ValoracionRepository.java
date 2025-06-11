package com.cojali.repository;

import com.cojali.entity.Valoracion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValoracionRepository extends JpaRepository<Valoracion, Integer> {

    @Query("SELECT v FROM Valoracion v ORDER BY v.fecha DESC")
    List<Valoracion> findTopByOrderByFechaDesc(Pageable pageable);
    List<Valoracion> findTop5ByOrderByFechaDesc();

    default List<Valoracion> findTop5Ultimas() {
        return findTopByOrderByFechaDesc(Pageable.ofSize(5));
    }
}


   
