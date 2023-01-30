package com.demo.app.service;

import com.demo.app.enums.ExceptionMessage;
import com.demo.app.exception.ImageFileNotFoundException;
import com.demo.app.exception.SendMessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value(value = "${spring.resources.images}")
    private String resourceLocation;

    @Value(value = "${spring.mail.username}")
    private String senderMail;

    @Override
    public BigDecimal sendActivationEmail(String sendTo) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            BigDecimal activationCode = BigDecimal.valueOf(Math.random() * 1000000)
                    .setScale(0, RoundingMode.HALF_DOWN);

            mimeMessageHelper.setFrom(senderMail);
            mimeMessageHelper.setTo(sendTo);
            mimeMessageHelper.setText("Hello, " + sendTo + "!\n" +
                    "Thanks for registration at rental service. Here is your activation code: " + "R-" + activationCode);
            mimeMessageHelper.setSubject("RentalService Registration");

            File resourceFile = ResourceUtils.getFile(resourceLocation);

            FileSystemResource attachmentFile =
                    new FileSystemResource(resourceFile);

            mimeMessageHelper.addAttachment(
                    Objects.requireNonNull(attachmentFile.getFilename()),
                    attachmentFile
            );

            javaMailSender.send(mimeMessage);

            return activationCode;

        } catch (SendMessageException | MailAuthenticationException | MessagingException e) {
            throw new SendMessageException(ExceptionMessage.ERROR_MAIL_MESSAGE.getExceptionMessage());
        } catch (FileNotFoundException e) {
            throw new ImageFileNotFoundException(ExceptionMessage.IMAGE_FILE_NOT_FOUND.getExceptionMessage());
        }

    }
}
