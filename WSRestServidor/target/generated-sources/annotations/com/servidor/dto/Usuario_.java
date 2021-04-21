package com.servidor.dto;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ {

	public static volatile SingularAttribute<Usuario, Integer> codUsuario;
	public static volatile SingularAttribute<Usuario, String> claveUsuario;
	public static volatile SingularAttribute<Usuario, String> nomUsuario;
	public static volatile SingularAttribute<Usuario, String> emailUsuario;
	public static volatile ListAttribute<Usuario, SesionOtp> sesionOtpList;

}

