package br.ufscar.dc.dsw.agendamento_online.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.agendamento_online.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.agendamento_online.domain.Usuario;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, Usuario> {

    @Autowired
    private IUsuarioDAO dao;

    @Override
    public boolean isValid(Usuario usuario, ConstraintValidatorContext context) {
        if (usuario == null || usuario.getEmail() == null || usuario.getEmail().isBlank() || dao == null) {
            return true;
        }

        Usuario usuarioComEmail = dao.findByEmail(usuario.getEmail());
        if (usuarioComEmail == null || usuarioComEmail.getId().equals(usuario.getId())) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("{usuario.email.unique}")
                .addPropertyNode("email")
                .addConstraintViolation();
        return false;
    }
}
