package br.ufscar.dc.dsw.agendamento_online.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.ufscar.dc.dsw.agendamento_online.domain.Usuario;

public class UsuarioDetails implements  UserDetails{
    
    private final Usuario usuario;

    public UsuarioDetails(Usuario usuario){
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getPapel());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }
 
    @Override
    public String getUsername() {
        return usuario.getEmail();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(usuario.getAtivo());
    }

	public Usuario getUsuario() {
		return usuario;
	}
    
}
