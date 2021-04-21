/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servidor.dao;

import com.servidor.dto.SesionOtp;
import com.servidor.dto.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jonathan
 */
@Repository("sesionOtpDAO")
public interface SesionOtpDAO extends JpaRepository<SesionOtp,Integer>{
    @Query("SELECT u FROM SesionOtp u WHERE u.codUsuario = :codUsuario")
    List<SesionOtp> findByCodUsuario(@Param("codUsuario") Usuario codUsuario);
}
