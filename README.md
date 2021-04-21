# Verificacion_otp

Es un cliente el cual para poder iniciar sesión se requiere de una contraseña almacenada en la base de datos y un código que se envia al correo que el cliente tiene registrado. El tiempo máximo para poner el código es de 3 minutos.
 
Proyecto realizado con:
- Backend: JAVA 1.8
- Frontend: Angular 11
- Base de datos: MySQL 

# Ejecutar backend

## Se debe cambiar el correo en la ruta
- WSRestServidor > src > main > java > com > servidor > general > Utilidad.java

Abrir proyecto con Netbeans.
Clic derecho > Run

# Ejecutar Frontend

``` bash
cd cliente
npm start
```

# compilar Frontend
```bash
cd cliente
ng build --prod
```
