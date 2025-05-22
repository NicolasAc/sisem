package org.cnasm.Sisem.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarCorreoActivacion(String destinatario, String tokenActivacion) {
        String asunto = "Activación de cuenta - SISEM";
        String cuerpo = "Bienvenido. Haga clic en el siguiente enlace para activar su cuenta:\n\n" +
                "http://localhost:3000//activar?token=" + tokenActivacion;

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);

        mailSender.send(mensaje);
    }
}