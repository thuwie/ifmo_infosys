package com.konovalov.edu.authentication;

import java.time.Instant;
import java.util.Date;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.konovalov.edu.dao.EmployeeDao;
import com.konovalov.edu.dao.RoleDao;
import com.konovalov.edu.dao.UserDao;
import com.konovalov.edu.entity.Employee;
import com.konovalov.edu.entity.Role;
import com.konovalov.edu.entity.User;

@Service
public class AutService {
    
    private final static String SECRET = "secret";
    private final static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    
    private final int EXPIRES_DELTA_IN_SECS = 6000;
    
    private final static JWTVerifier verifier = JWT.require(ALGORITHM)
            .withIssuer("auth0")
            .build();
    
    private UserDao userDao;
    private EmployeeDao employeeDao;
    private RoleDao roleDao;
    
    @Autowired
    public AutService(UserDao userDao, EmployeeDao employeeDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.employeeDao = employeeDao;
        this.roleDao = roleDao;
    }
    
    public String auth(String username, String password) throws AuthenticationException {
        User user = userDao.getUserByName(username);
        if (user.getPassword().equals(password)) {
            int roleId = employeeDao.getRoleId(user.getEmployeeId());
            Role role = roleDao.getRoleById(roleId);
            return generateJwtToken(user, role);
        }
        throw new AuthenticationException("incorrect password, try again");
    }
    
    private String generateJwtToken(User user, Role role) {
        Date expiresAt = Date.from(Instant.now().plusSeconds(EXPIRES_DELTA_IN_SECS));
        
        return JWT.create()
                .withIssuer("auth0")
                .withSubject(user.getUsername())
                .withClaim("role", role.getName())
                .withExpiresAt(expiresAt)
                .sign(ALGORITHM);
    }
}
