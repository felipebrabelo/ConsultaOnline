package br.ufscar.dc.dsw.agendamento_online;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufscar.dc.dsw.agendamento_online.dao.IClienteDAO;
import br.ufscar.dc.dsw.agendamento_online.dao.IConsultaDAO;
import br.ufscar.dc.dsw.agendamento_online.dao.IProfissionalDAO;
import br.ufscar.dc.dsw.agendamento_online.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.domain.Consulta;
import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;
import br.ufscar.dc.dsw.agendamento_online.domain.Usuario;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Genero;

@SpringBootApplication
public class AgendamentoOnlineApplication {

	private static final Logger log = LoggerFactory.getLogger(AgendamentoOnlineApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AgendamentoOnlineApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IClienteDAO clienteDAO, IConsultaDAO consultaDAO, IProfissionalDAO profissionalDAO,
			IUsuarioDAO usuarioDAO) {
		return (args) -> {

			Cliente cliente1 = new Cliente("Antonio Fagundes", "antonio@email.com", "atonio123", "12345678900",
					"16982223344", LocalDate.of(1983, 2, 2), Genero.MASCULINO);

			log.info("Salvando Cliente 1");

			usuarioDAO.save(cliente1);

			Cliente cliente2 = new Cliente("Fátima Fagundes", "fatimaf@email.com", "fatima321", "98765432100",
					"11987777777", LocalDate.of(1998, 12, 12), Genero.FEMININO);

			log.info("Salvando Cliente 2");

			usuarioDAO.save(cliente2);

			Cliente cliente3 = new Cliente("Sávio Borges", "sbg@email.com", "borgess124", "00099911123", "16999999923",
					LocalDate.of(2001, 1, 3), Genero.MASCULINO);

			log.info("Salvando Cliente 3");

			usuarioDAO.save(cliente3);

			byte[] curriculo1 = Files.readAllBytes(Path.of("src/main/resources/uploads/curriculo_psicologo_falso.pdf"));
			Profissional profissional1 = new Profissional("Rafael Almeida Ferreira", "rafael.almeida.psi@email.com ",
					"rafafer1", "12300012300", "Psicologia", curriculo1);

			log.info("Salvando Profissional 1 - Psicólogo");

			usuarioDAO.save(profissional1);

			byte[] curriculo2 = Files.readAllBytes(Path.of("src/main/resources/uploads/curriculo_advogada_falso.pdf"));
			Profissional profissional2 = new Profissional("Mariana Oliveira Costa", "mariana.costa.adv@email.com",
					"marimari2", "43251986700", "Advocacia", curriculo2);

			log.info("Salvando Profissional 2 - Advogada");

			usuarioDAO.save(profissional2);

			Consulta consulta1 = new Consulta(LocalDateTime.of(2026, 06, 27, 13, 00), "Consulta semanal Antonio","https://meet.google.com/abc-defg-hij", cliente1, profissional1);

			log.info("Salvando Consulta 1 - Cliente 1 - Psicólogo");

			consultaDAO.save(consulta1);

			Consulta consulta2 = new Consulta(LocalDateTime.of(2026, 06, 27, 14, 00), "Consulta semanal Fátima","https://meet.google.com/klm-nopq-rst", cliente2, profissional1);

			log.info("Salvando Consulta 2 - Cliente 2 - Psicólogo");

			consultaDAO.save(consulta2);

			Consulta consulta3 = new Consulta(LocalDateTime.of(2026, 06, 27, 15, 00), "Consulta semanal Sávio","https://meet.google.com/uvw-xyza-bcd", cliente3, profissional1);

			log.info("Salvando Consulta 3 - Cliente 3 - Psicólogo");

			consultaDAO.save(consulta3);

			Consulta consulta4 = new Consulta(LocalDateTime.of(2026, 06, 24, 15, 00), "Consulta sobre pensão","https://meet.google.com/efg-hijk-lmn", cliente1, profissional2);

			log.info("Salvando Consulta 4 - Cliente 1 - Advogada");

			consultaDAO.save(consulta4);

			Consulta consulta5 = new Consulta(LocalDateTime.of(2026, 06, 28, 16, 00), "","https://meet.google.com/opq-rstu-vwx", cliente2, profissional2);

			log.info("Salvando Consulta 5 - Cliente 2 - Advogada");

			consultaDAO.save(consulta5);

			Consulta consulta6 = new Consulta(LocalDateTime.of(2026, 06, 30, 15, 00), "Retorno - Consulta sobre pensão","https://meet.google.com/yza-bcde-fgh", cliente1, profissional2);

			log.info("Salvando Consulta 6 - Cliente 1 - Advogada (2)");

			consultaDAO.save(consulta6);

			log.info("Imprimindo todos os usuários");

			for(Usuario u : usuarioDAO.findAll()){
				log.info(u.toString());
			}

			log.info("Imprimindo todas as consultas");

			for(Consulta c: consultaDAO.findAll()){
				log.info(c.toString());
			}

			log.info("Imprimindo as consultas do cliente 1");

			for(Consulta c: consultaDAO.findByCliente(cliente1)){
				log.info(c.toString());
			}

			log.info("Imprimindo as consultas do profissional 1");

			for(Consulta c: consultaDAO.findByProfissional(profissional1)){
				log.info(c.toString());
			}

			log.info("Imprimindo todos os profissionais");

			for(Profissional p: profissionalDAO.findAll()){
				log.info(p.toString());
			}

			log.info("Imprimindo todos os advogados");

			for(Profissional p: profissionalDAO.findByEspecialidade("Advocacia")){
				log.info(p.toString());
			}

		};
	}
}
