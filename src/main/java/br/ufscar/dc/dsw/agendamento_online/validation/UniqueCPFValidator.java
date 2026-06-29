package br.ufscar.dc.dsw.agendamento_online.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.agendamento_online.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.agendamento_online.domain.Usuario;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueCPFValidator implements ConstraintValidator<UniqueCPF, Usuario> {

    @Autowired
    private IUsuarioDAO dao;

    @Override
    public boolean isValid(Usuario usuario, ConstraintValidatorContext context) {
        if (usuario == null || usuario.getCpf() == null || usuario.getCpf().isBlank() || dao == null) {
            return true;
        }

        Usuario usuarioComCpf = dao.findByCpf(usuario.getCpf());
        if (usuarioComCpf == null || usuarioComCpf.getId().equals(usuario.getId())) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("{usuario.cpf.unique}")
                .addPropertyNode("cpf")
                .addConstraintViolation();
        return false;
    }
}
