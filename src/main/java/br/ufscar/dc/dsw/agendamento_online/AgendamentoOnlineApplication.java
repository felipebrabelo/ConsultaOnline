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
import org.springframework.security.crypto.password.PasswordEncoder;

import br.ufscar.dc.dsw.agendamento_online.dao.IClienteDAO;
import br.ufscar.dc.dsw.agendamento_online.dao.IConsultaDAO;
import br.ufscar.dc.dsw.agendamento_online.dao.IProfissionalDAO;
import br.ufscar.dc.dsw.agendamento_online.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.domain.Consulta;
import br.ufscar.dc.dsw.agendamento_online.domain.FileEntity;
import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;
import br.ufscar.dc.dsw.agendamento_online.domain.Usuario;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Especialidade;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Genero;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Papel;

@SpringBootApplication
public class AgendamentoOnlineApplication {

	private static final Logger log = LoggerFactory.getLogger(AgendamentoOnlineApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AgendamentoOnlineApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IClienteDAO clienteDAO, IConsultaDAO consultaDAO, IProfissionalDAO profissionalDAO,
			IUsuarioDAO usuarioDAO, PasswordEncoder passwordEncoder) {
		return (args) -> {

			Usuario admin = new Usuario("Administrador", "admin@email.com", passwordEncoder.encode("admin"),
					"11122233344", Papel.ADMIN);

			log.info("Salvando administrador");

			usuarioDAO.save(admin);

			Cliente cliente1 = new Cliente("Antonio Fagundes", "antonio@email.com", "antonio123", "12345678900",
					"16982223344", LocalDate.of(1983, 2, 2), Genero.MASCULINO);
			cliente1.setSenha(passwordEncoder.encode(cliente1.getSenha()));

			log.info("Salvando Cliente 1");

			usuarioDAO.save(cliente1);

			Cliente cliente2 = new Cliente("Fátima Fagundes", "fatimaf@email.com", "fatima321", "98765432100",
					"11987777777", LocalDate.of(1998, 12, 12), Genero.FEMININO);
			cliente2.setSenha(passwordEncoder.encode(cliente2.getSenha()));

			log.info("Salvando Cliente 2");

			usuarioDAO.save(cliente2);

			Cliente cliente3 = new Cliente("Sávio Borges", "sbg@email.com", "borges123", "00099911123", "16999999923",
					LocalDate.of(2001, 1, 3), Genero.MASCULINO);
			cliente3.setSenha(passwordEncoder.encode(cliente3.getSenha()));

			log.info("Salvando Cliente 3");

			usuarioDAO.save(cliente3);

			byte[] data1 = Files.readAllBytes(Path.of("src/main/resources/static/uploads/curriculo_psicologo_falso.pdf"));
			FileEntity curriculo1 = new FileEntity("Curriculo psicologo", "application/pdf", data1);
			Profissional profissional1 = new Profissional("Rafael Almeida Ferreira", "rafael.almeida.psi@email.com",
					"rafael123", "12300012300", Especialidade.PSICOLOGIA, curriculo1);
			profissional1.setSenha(passwordEncoder.encode(profissional1.getSenha()));

			log.info("Salvando Profissional 1 - Psicólogo");

			usuarioDAO.save(profissional1);

			byte[] data2 = Files.readAllBytes(Path.of("src/main/resources/static/uploads/curriculo_advogada_falso.pdf"));
			FileEntity curriculo2 = new FileEntity("Curriculo advogada", "application/pdf", data2);
			Profissional profissional2 = new Profissional("Mariana Oliveira Costa", "mariana.costa.adv@email.com",
					"mariana123", "43251986700", Especialidade.ADVOCACIA, curriculo2);
			profissional2.setSenha(passwordEncoder.encode(profissional2.getSenha()));

			log.info("Salvando Profissional 2 - Advogada");

			usuarioDAO.save(profissional2);

			Consulta consulta1 = new Consulta(LocalDateTime.of(2026, 06, 27, 13, 00), "Consulta semanal Antonio",
					"https://meet.google.com/abc-defg-hij", cliente1, profissional1);

			log.info("Salvando Consulta 1 - Cliente 1 - Psicólogo");

			consultaDAO.save(consulta1);

			Consulta consulta2 = new Consulta(LocalDateTime.of(2026, 06, 27, 14, 00), "Consulta semanal Fátima",
					"https://meet.google.com/klm-nopq-rst", cliente2, profissional1);

			log.info("Salvando Consulta 2 - Cliente 2 - Psicólogo");

			consultaDAO.save(consulta2);

			Consulta consulta3 = new Consulta(LocalDateTime.of(2026, 06, 27, 15, 00), "Consulta semanal Sávio",
					"https://meet.google.com/uvw-xyza-bcd", cliente3, profissional1);

			log.info("Salvando Consulta 3 - Cliente 3 - Psicólogo");

			consultaDAO.save(consulta3);

			Consulta consulta4 = new Consulta(LocalDateTime.of(2026, 06, 24, 15, 00), "Consulta sobre pensão",
					"https://meet.google.com/efg-hijk-lmn", cliente1, profissional2);

			log.info("Salvando Consulta 4 - Cliente 1 - Advogada");

			consultaDAO.save(consulta4);

			Consulta consulta5 = new Consulta(LocalDateTime.of(2026, 06, 28, 16, 00), "",
					"https://meet.google.com/ijk-lmno-pqr", cliente2, profissional2);

			log.info("Salvando Consulta 5 - Cliente 2 - Advogada");

			consultaDAO.save(consulta5);

			Consulta consulta6 = new Consulta(LocalDateTime.of(2026, 06, 30, 15, 00), "Retorno - Consulta sobre pensão",
					"https://meet.google.com/yza-bcde-fgh", cliente1, profissional2);

			log.info("Salvando Consulta 6 - Cliente 1 - Advogada (2)");

			consultaDAO.save(consulta6);

			log.info("Imprimindo todos os usuários");

			for (Usuario u : usuarioDAO.findAll()) {
				log.info(u.toString());
			}

			log.info("Imprimindo todas as consultas");

			for (Consulta c : consultaDAO.findAll()) {
				log.info(c.toString());
			}

			log.info("Imprimindo as consultas do cliente 1");

			for (Consulta c : consultaDAO.findByCliente(cliente1)) {
				log.info(c.toString());
			}

			log.info("Imprimindo as consultas do profissional 1");

			for (Consulta c : consultaDAO.findByProfissional(profissional1)) {
				log.info(c.toString());
			}

			log.info("Imprimindo todos os profissionais");

			for (Profissional p : profissionalDAO.findAll()) {
				log.info(p.toString());
			}

			log.info("Imprimindo todos os advogados");

			for (Profissional p : profissionalDAO.findByEspecialidade(Especialidade.ADVOCACIA)) {
				log.info(p.toString());
			}

			log.info("Imprimindo todos os clientes da Advogada");

			for (Cliente c : clienteDAO.findByProfissional(profissional2)) {
				log.info(c.toString());
			}

			log.info("Imprimindo todos os profissionais que atenderam o cliente1");

			for (Profissional p : profissionalDAO.findByCliente(cliente1)) {
				log.info(p.toString());
			}

			log.info("Imprimindo consultas do cliente1 no dia 27/06/2026");

			for (Consulta c : consultaDAO.findByClienteAndDataHoraBetween(cliente1,
					LocalDateTime.of(2026, 06, 27, 00, 00), LocalDateTime.of(2026, 06, 27, 23, 59))) {
				log.info(c.toString());
			}

			log.info("Imprimindo consultas cliente1 a partir de 2026");

			for (Consulta c : consultaDAO.findByClienteAndDataHoraAfter(cliente1,
					LocalDateTime.of(2026, 01, 01, 00, 00))) {
				log.info(c.toString());
			}

			log.info("Imprimindo consultas do profissional1 no dia 27/06/2026");

			for (Consulta c : consultaDAO.findByProfissionalAndDataHoraBetween(profissional1,
					LocalDateTime.of(2026, 06, 27, 00, 00), LocalDateTime.of(2026, 06, 27, 23, 59))) {
				log.info(c.toString());
			}

			log.info("Imprimindo consultas do profissional1 a partir de 2026");

			for (Consulta c : consultaDAO.findByProfissionalAndDataHoraAfter(profissional1,
					LocalDateTime.of(2026, 01, 01, 00, 00))) {
				log.info(c.toString());
			}
		};
	}
}
