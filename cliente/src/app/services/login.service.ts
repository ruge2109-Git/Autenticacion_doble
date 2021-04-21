import { RespuestaGenerica } from './../model/RespuestaGenerica';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private urlService = "http://localhost:8080";

  constructor(private http:HttpClient) { }

  iniciarSesion(usuario:string,clave:string):Observable<RespuestaGenerica>{
    return this.http.post<RespuestaGenerica>(`${this.urlService}/login?nomUsuario=${usuario}&clave=${clave}`,null,{});
  }

  validarOtp(codUsuario:string,codOtp:string){
    return this.http.post<RespuestaGenerica>(`${this.urlService}/login/validarOtp?codUsuario=${codUsuario}&xOtp=${codOtp}`,null,{});
  }

  enviarOTP(codUsuario:string){
    return this.http.post<RespuestaGenerica>(`${this.urlService}/login/enviarOTP?codUsuario=${codUsuario}`,null,{});
  }
}
