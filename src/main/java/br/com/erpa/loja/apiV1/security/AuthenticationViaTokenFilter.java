package br.com.erpa.loja.apiV1.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.erpa.loja.apiV1.login.model.Login;
import br.com.erpa.loja.apiV1.login.repository.LoginRepository;



	
public class AuthenticationViaTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	private LoginRepository repository;

	public AuthenticationViaTokenFilter(TokenService tokenService, LoginRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recoverToken(request);
		boolean valido = tokenService.isTokenValido(token);
	
		if (valido) {
			authentiCliente(token);
		}
		
		filterChain.doFilter(request, response);
	}

	private void authentiCliente(String token) {
		Long idLogin = tokenService.getIdLogin(token);
		Login login = repository.findById(idLogin).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(login, null, login.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String recoverToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}


}
