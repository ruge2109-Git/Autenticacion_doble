/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servidor.general;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author Jonathan
 */
public class Utilidad {
    
    
    public String enviarCodigoOTP(String destinatario) {
        String otp = generarOTP();
        boolean enviado = enviarEmail(destinatario, "Código para iniciar sesión", otp);
        if (!enviado) {
            System.err.println("Ha ocurrido un error al enviar el código OTP");
            return null;
        }
        return otp;
    }

    public String generarOTP() {
        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        return otp;
    }

    public boolean enviarEmail(String destinatario, String asunto, String cuerpo) {
        boolean rta = false;
        Properties properties = new Properties();
        String correoEnvia = "jonathan.ruge.02@gmail.com";
        String contrasena = "Solorock123";
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.user", correoEnvia);
        properties.put("mail.password", contrasena);

        Session session = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(correoEnvia));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", correoEnvia, contrasena);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            rta = true;
        }
        catch (Exception me) {
            me.printStackTrace();
        }
        return rta;
    }
    
    
    public long compararFechasMinutos(Date fechaReciente,Date fechaAntigua ){
        return (fechaReciente.getTime() - fechaAntigua.getTime()) / (60 * 1000);
    }
}
