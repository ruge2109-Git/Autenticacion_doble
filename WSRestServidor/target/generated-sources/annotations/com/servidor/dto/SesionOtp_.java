package com.servidor.dto;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SesionOtp.class)
public abstract class SesionOtp_ {

	public static volatile SingularAttribute<SesionOtp, Integer> codSesionOtp;
	public static volatile SingularAttribute<SesionOtp, Usuario> codUsuario;
	public static volatile SingularAttribute<SesionOtp, String> codigoOtp;
	public static volatile SingularAttribute<SesionOtp, Date> fechaExpuesta;

}

