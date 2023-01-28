package com.api.pessoa.domain.service;

import com.api.pessoa.domain.model.dto.PersonDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {

    /**
     * Cadastra uma pessoa de acordo com os dados de personDTO.
     * @param personDTO
     * @return personDTO
     */
    public PersonDTO save(PersonDTO personDTO);

    /**
     * Lista todas as pessoas cadastradas no sistema.
     * @return List<personDTO>
     */
    List<PersonDTO> list();

    /**
     * Busca uma pessoa de acordo com o id informado, caso essa pessoa não esteja cadastrada
     * é lançado uma exception EntityNotFound
     * @param idPerson
     * @return personDTO
     */
    PersonDTO getPersonById(Long idPerson);

    /**
     * Atualiza uma pessoa, mas primeiro valida se essa pessoa está cadastrada e caso não esteja,
     * é lançado uma exception EntityNotFound
     * @param idPerson
     * @param personDTO
     * @return
     */
    PersonDTO update(Long idPerson, PersonDTO personDTO);

    /**
     * Deleta uma pessoa do banco caso esteja cadastrada, se não existir o id infomado para deleção,
     * é lançado a exception EntityNotFound
     * @param idPerson
     */
    void delete(Long idPerson);
}
