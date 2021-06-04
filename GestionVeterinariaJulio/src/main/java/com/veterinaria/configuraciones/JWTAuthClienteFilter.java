package com.veterinaria.configuraciones;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTAuthClienteFilter extends OncePerRequestFilter {
	private final String CABECERA = "Authorization";
	private final String PREFIJO = "Bearer ";
	private final String CONTRASENIA = "secret";
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			if(getTokenCliente(request,response)) {
				Claims tokenReclamado = validarToken(request);
				
				if(tokenReclamado.get("authorities") != null)
					setAuthCliente(tokenReclamado);
				else
					SecurityContextHolder.clearContext();
			}
			else
				SecurityContextHolder.clearContext();
			
			filterChain.doFilter(request, response);
		}
		catch(MalformedJwtException ex) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse ) response).sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return;
		}
		catch(UnsupportedJwtException ex) {
			System.out.println("Token no soportado");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse ) response).sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return;
		}
		catch(ExpiredJwtException ex) {
			System.out.println("Token expirado");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse ) response).sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return;
		}
		catch(IllegalArgumentException ex) {
			System.out.println("Token vacío");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse ) response).sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return;
		}
		catch(SignatureException ex) {
			System.out.println("Error producido en la firma");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse ) response).sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return;
		}
	}
	
	
	private Claims validarToken(HttpServletRequest request) {
		String clienteToken = request.getHeader(CABECERA).replace(PREFIJO, "");
		return Jwts.parser().setSigningKey(CONTRASENIA.getBytes()).parseClaimsJws(clienteToken).getBody();
	}

	
	private void setAuthCliente(Claims tokenReclamado) {
		@SuppressWarnings("unchecked")
		List<String> auths = (List<String>) tokenReclamado.get("authorities");
	
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(tokenReclamado.getSubject(),null,
				auths.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);
	}


	private boolean getTokenCliente(HttpServletRequest request, HttpServletResponse response) {
		String authentication = request.getHeader(CABECERA);
		
		if(authentication == null || !authentication.startsWith(PREFIJO))
			return false;
		return true;
	}
}
