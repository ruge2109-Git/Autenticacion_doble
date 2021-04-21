import { LoginService } from './../../services/login.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { catchError } from 'rxjs/operators';
import { UsuarioDTO } from 'src/app/model/RespuestaGenerica';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {


  public frmDatos: FormGroup;
  public spinner: boolean;
  public digitaOtp: boolean;
  private usuario:UsuarioDTO;

  constructor(private toastr: ToastrService, private loginService: LoginService, private router:Router) { }

  ngOnInit(): void {
    this.frmDatos = this.newFormGroup();
  }


  submitFormulario() {
    if (this.digitaOtp) {
      this.validarOtp();
    }
    else {
      this.iniciarSesion();
    }
  }

  iniciarSesion() {
    this.spinner = true;
    this.loginService.iniciarSesion(this.obtenerPropiedadFormGroup("usuario").value, this.obtenerPropiedadFormGroup("clave").value).subscribe((data) => {
      this.spinner = false;
      if (data.bRta) {
        this.usuario = data.list[0];
        this.toastr.info(data.sMsg, "Código de verificación");
        this.digitaOtp = true;
      }
      else {
        this.toastr.error(data.sMsg, "Error");
      }
    },
      (error) => {
        this.spinner = false;
        this.toastr.error("Ha ocurrido un error inesperado", "Error");
      })
  }


  validarOtp() {
    this.spinner = true;
    this.loginService.validarOtp(this.usuario.codUsuario.toString(), this.obtenerPropiedadFormGroup("otp").value).subscribe((data) => {
      this.spinner = false;
      if (data.bRta) {
        this.toastr.info(data.sMsg, "Datos correctos");
        this.router.navigateByUrl('dashboard');
      }
      else {
        this.toastr.error(data.sMsg, "Error");
      }
    },
      (error) => {
        this.spinner = false;
        this.toastr.error("Ha ocurrido un error inesperado", "Error");
      })
  }

  reenviarOTP(){
    this.spinner = true;
    this.loginService.enviarOTP(this.usuario.codUsuario.toString()).subscribe((data) => {
      this.spinner = false;
      if (data.bRta) {
        this.toastr.info(data.sMsg, "Código de verificación");
      }
      else {
        this.toastr.error(data.sMsg, "Error");
      }
    },
      (error) => {
        this.spinner = false;
        this.toastr.error("Ha ocurrido un error inesperado", "Error");
      })
  }

  newFormGroup() {
    return new FormGroup({
      usuario: new FormControl("", [Validators.required]),
      clave: new FormControl("", [Validators.required]),
      otp: new FormControl(""),
    });
  }

  condicionInvalid(xPropiedad) {
    if (this.obtenerPropiedadFormGroup(xPropiedad).invalid && (this.obtenerPropiedadFormGroup(xPropiedad).dirty || this.obtenerPropiedadFormGroup(xPropiedad).touched)) {
      return true;
    }
    return false;
  }

  obtenerPropiedadFormGroup(xPropiedad) {
    return this.frmDatos.get(xPropiedad)
  }

}
