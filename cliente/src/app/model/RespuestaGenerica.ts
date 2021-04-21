export class RespuestaGenerica{
  bRta:boolean;
  sMsg:string;
  list:UsuarioDTO[];
}

export class UsuarioDTO{
  codUsuario:number;
  nomUsuario:string;
  claveUsuario:string;
  emailUsuario:string;
}
