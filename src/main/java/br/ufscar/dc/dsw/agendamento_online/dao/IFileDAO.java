package br.ufscar.dc.dsw.agendamento_online.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.agendamento_online.domain.FileEntity;;

@SuppressWarnings("unchecked")
public interface IFileDAO extends CrudRepository<FileEntity, Long> {

    FileEntity findById(long id);

    List<FileEntity> findAll();

    FileEntity save(FileEntity file);

    void deleteById(Long id);
}