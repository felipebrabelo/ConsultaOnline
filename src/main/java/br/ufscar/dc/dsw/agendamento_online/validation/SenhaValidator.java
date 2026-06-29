package br.ufscar.dc.dsw.agendamento_online.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class SenhaValidator {

    public void validarObrigatoria(String senha, BindingResult result) {
        if (senha == null || senha.isBlank()) {
            result.rejectValue("senha", "usuario.senha.notBlank");
            result.rejectValue("senha", "usuario.senha.size");
            return;
        }
        validarTamanho(senha, "senha", result);
    }

    public void validarOpcional(String senha, BindingResult result) {
        if (senha != null && !senha.isBlank()) {
            validarTamanho(senha, "senha", result);
        }
    }

    private void validarTamanho(String senha, String campo, BindingResult result) {
        if (senha.length() < 6 || senha.length() > 64) {
            result.rejectValue(campo, "usuario.senha.size");
        }
    }
}
