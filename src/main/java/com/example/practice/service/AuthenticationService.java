package com.example.practice.service;

import com.example.practice.dto.request.AuthenticationRequest;
import com.example.practice.dto.request.IntrospectRequest;
import com.example.practice.dto.response.AuthenticationResponse;
import com.example.practice.dto.response.IntrospectResponse;
import com.example.practice.entity.User;
import com.example.practice.exception.AppException;
import com.example.practice.exception.ErrorCode;
import com.example.practice.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {
    UserRepository userRepository;
    @NonFinal
    @Value("${jwt.signingKey}")
    protected String SIGNING_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        boolean authenticate = encoder.matches(request.getPassword(), user.getPassword());
        if (!authenticate) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNING_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (expirationDate.before(new Date())) {
            return IntrospectResponse.builder()
                    .valid(false)
                    .build();
        }
        if (signedJWT.verify(verifier)) {
            return IntrospectResponse.builder()
                    .valid(true)
                    .build();
        }
        return IntrospectResponse.builder()
                .valid(false)
                .build();
    }

    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().
                subject(user.getUsername())
                .issuer("http://localhost:8080")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNING_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can't create token");
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }
}
