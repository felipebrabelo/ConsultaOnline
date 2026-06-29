package br.ufscar.dc.dsw.agendamento_online.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.ufscar.dc.dsw.agendamento_online.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.agendamento_online.domain.Usuario;

public class UsuarioDetailsService implements UserDetailsService {

    private final IUsuarioDAO dao;

    public UsuarioDetailsService(IUsuarioDAO dao) {
        this.dao = dao;
    }
     
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Usuario usuario = dao.findByEmail(email);
         
        if (usuario == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new UsuarioDetails(usuario);
    }
}
