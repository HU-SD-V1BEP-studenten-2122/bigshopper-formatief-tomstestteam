package nl.hu.bep.shopping.webservices;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import nl.hu.bep.shopping.webservices.dto.ErrorResponse;
import nl.hu.bep.shopping.webservices.dto.LoginRequest;
import nl.hu.bep.shopping.webservices.dto.TokenResponse;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Path("login")
public class AuthenticationResource {

    public static Key key = new SecretKeySpec("blabla".getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
    public static Map<String, String> userpassword = new HashMap<>();


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest){
        String user = loginRequest.user;
        if(userpassword.containsKey(user) && userpassword.get(user).equals(loginRequest.password)){
            Date fiveMinutesFromNow = Date.from(Instant.now().plusSeconds(60 * 60));
            String token = Jwts.builder()
                    .setSubject(loginRequest.user)
                    .setIssuer("bigshopper")
                    .setExpiration(fiveMinutesFromNow)
                    .signWith(SignatureAlgorithm.HS512, key)
                    .compact();

            return Response.ok(TokenResponse.fromString(token)).build();
        }else{
            return Response.status(401).entity(ErrorResponse.fromString("Invalid Credentials")).build();
        }
    }
}

