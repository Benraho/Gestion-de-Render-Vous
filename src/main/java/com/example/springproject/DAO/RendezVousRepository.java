package com.example.springproject.DAO;

import com.example.springproject.Entities.RendezVous;
import com.example.springproject.Entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Integer> {
    @Query("SELECT rv FROM RendezVous rv WHERE rv.employee.id = :employeeId")
    List<RendezVous> findByEmployeeId(@Param("employeeId") Integer employeeId);

    @Query("SELECT rv FROM RendezVous rv WHERE rv.employee.admin.id = :adminId AND rv.status = :status")
    List<RendezVous> findByAdminIdAndStatus(@Param("adminId") Integer adminId, @Param("status") Status status);

    @Query("SELECT COUNT(rv) FROM RendezVous rv WHERE rv.employee.admin.id = :adminId AND rv.status = :status")
    Long countByAdminIdAndStatus(@Param("adminId") Integer adminId, @Param("status") Status status);

    @Query("SELECT COUNT(rv) FROM RendezVous rv WHERE rv.employee.id = :employeeId AND rv.status = :status")
    Long countByEmployeeIdAndStatus(@Param("employeeId") Integer employeeId, @Param("status") Status status);

    @Query("SELECT COUNT(rv) FROM RendezVous rv WHERE rv.employee.admin.id = :adminId")
    Long countAllRendezVousByAdminId(@Param("adminId") Integer adminId);

    @Query("SELECT rv FROM RendezVous rv WHERE rv.employee.admin.id = :adminId AND rv.employee.id = :employeeId AND rv.status = :status")
    List<RendezVous> findByEmployeeIdAndStatus(@Param("adminId") Integer adminId, @Param("employeeId") Integer employeeId, @Param("status") Status status);

    @Query("SELECT COUNT(rv) FROM RendezVous rv WHERE rv.employee.id = :employeeId AND rv.employee.admin.id = :adminId")
    Long countTotalRendezVousByEmployeeIdAndAdminId(@Param("employeeId") Integer employeeId, @Param("adminId") Integer adminId);


}

