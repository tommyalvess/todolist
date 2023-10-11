package br.rocketseat.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.rocketseat.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component //tenho q colocar toda vez que tem que ser gerenciado pelo spring
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

            //Pegar a autenticação (user e senha)
            var autorization = request.getHeader("Authorization");
            var authEncoded = autorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            var authString = new String(authDecode);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            var user = this.iUserRepository.findByUserName(username);
            if (user == null) {
                response.sendError(401);
            }else {
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                var passwordExist = passwordVerify.verified

                if(passwordExist){
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }

            }

    }

   
    
}
