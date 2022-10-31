package com.backend.service;

import com.backend.model.ForgotPasswordModel;
import com.backend.model.PeopleModel;
import com.backend.repo.ForgotPasswordRepo;
import com.backend.repo.PeopleRepo;
import com.backend.request.EmailRequest;
import com.backend.request.ForgotPasswordRequest;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ForgotPasswordService {

    @Autowired
    ForgotPasswordRepo forgotPasswordRepo;

    @Autowired
    PeopleService peopleService;

    @Autowired
    EmailService emailService;

    @Autowired
    Environment environment;

    @Autowired
    PeopleRepo peopleRepo;

    public List<ForgotPasswordModel> listAllForgotPassword() {
        return forgotPasswordRepo.findAll();
    }

    public void createForgotPasswordModel(ForgotPasswordRequest request) throws Exception {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new Exception("No email entered.");
        }

        PeopleModel people = peopleService.findPeopleByEmail(request.getEmail());
//        Optional<ForgotPasswordModel> forgotPasswordOpt = forgotPasswordRepo.getForgotPasswordByPeople(people);
        List<ForgotPasswordModel> ltForgot = forgotPasswordRepo.findAllByPeopleModel(people);

        if (ltForgot.size() > 0) {
            ltForgot.forEach(item -> forgotPasswordRepo.delete(item));
        }

        String otp = RandomString.make(8);
        ZoneId zid = ZoneId.of("Asia/Singapore");
        ZonedDateTime dtRequest = ZonedDateTime.now(zid);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        ForgotPasswordModel forgotPassword = ForgotPasswordModel.builder()
                .encryptedOtp(passwordEncoder.encode(otp))
                .peopleModel(people)
                .requested_time(dtRequest)
                .build();

        forgotPasswordRepo.save(forgotPassword);

        String content = "<p>Hi " + people.getFirstName() + ":</p>"
                + "<p>For security reason, you're required to use the following "
                + "One Time Password(OTP) to reset your password:</p>"
                + "<p><b style='color:red;'>" + otp + "</b></p>"
                + "<br>"
                + "<p>Note: this OTP will expire in <b style='color:red;'>" + environment.getProperty("OTP_LIFE") + "</b> minutes.</p>";

        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(people.getEmail())
                .msgBody(content)
                .subject("OTP email")
                .build();

        emailService.sendMail(emailRequest);

    }

    public void checkOtp(ForgotPasswordRequest request) throws Exception {
//        ForgotPasswordModel forgotPassword = new ForgotPasswordModel();
//        if (request.getEmail() == null || request.getEmail().isBlank()) {
//            throw new Exception("No email entered.");
//        }
//
//        if (request.getOtp() == null || request.getOtp().isBlank()) {
//            throw new Exception("No OTP entered.");
//        }

//        List<ForgotPasswordModel> ltForgot = forgotPasswordRepo.findAllByPeopleModelEmail(request.getEmail());
        List<ForgotPasswordModel> ltForgot = forgotPasswordRepo.findAllByPeopleModelEmailOrderById(request.getEmail());

        if (ltForgot.size() <= 0) {
            throw new Exception("No valid OTP with this email.");
        }

        ForgotPasswordModel forgotPassword = ltForgot.iterator().next();
        ZonedDateTime otpDt = forgotPassword.getRequested_time();
        ZonedDateTime otpExpire = otpDt.plusMinutes(Long.parseLong(environment.getProperty("OTP_LIFE")));
        ZoneId zid = ZoneId.of("Asia/Singapore");
        ZonedDateTime dtNow = ZonedDateTime.now(zid);

        if (dtNow.isAfter(otpExpire)) {
            throw new Exception("OTP expired. Please request OTP again.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(request.getOtp(), forgotPassword.getEncryptedOtp())) {
            throw new Exception("OTP not match");
        }

    }

    public void resetPassword(ForgotPasswordRequest request) throws Exception {

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new Exception("No email entered.");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new Exception("No password entered.");
        }

        if (request.getOtp() == null || request.getOtp().isBlank()) {
            throw new Exception("No OTP entered.");
        }

        checkOtp(request);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        PeopleModel people = peopleService.findPeopleByEmail(request.getEmail());
        people.setPassword(passwordEncoder.encode(request.getPassword()));
        peopleRepo.save(people);

        List<ForgotPasswordModel> ltForgot = forgotPasswordRepo.findAllByPeopleModelEmailOrderById(request.getEmail());

        if (ltForgot.size() > 0) {
            ltForgot.forEach(item -> forgotPasswordRepo.delete(item));
        }
    }


}
