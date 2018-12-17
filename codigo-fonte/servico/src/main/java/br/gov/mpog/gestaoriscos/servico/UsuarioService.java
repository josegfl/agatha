package br.gov.mpog.gestaoriscos.servico;

import br.gov.mpog.gestaoriscos.modelo.dto.UsuarioDTO;

/**
 * Classe responsável por realizar operações com o Usuário
 * Created by Basis Tecnologia on 27/10/2016.
 */
public interface UsuarioService {

    /**
     * Método responsável por realizar o salvamento de um usuário
     *
     * @param usuarioDTO
     * @return UsuarioDTO
     */
    UsuarioDTO save(UsuarioDTO usuarioDTO);

    /**
     * Método responsável por atualizar os dados de um usuário
     *
     * @param usuarioDTO
     * @return UsuarioDTO
     */
    UsuarioDTO update(UsuarioDTO usuarioDTO);

    /**
     * Método responsável por tratar a validação de CPF repetido
     *
     * @param cpf
     * @return
     */
    Boolean isCPFCadastrado(String cpf);

    /**
     * Método responsável por tratar a validação de CPF repetido desconsiderando um usuário com determinado ID
     *
     * @param cpf
     * @param idUsuario
     * @return
     */
    Boolean isCPFCadastradoDiferenteIdUsuario(String cpf, Long idUsuario);

    /**
     * Método responsável por recuperar o usuário pelo CPF
     *
     * @param cpf
     * @return
     */
    UsuarioDTO findByCpf(String cpf);

    /**
     * Método responsável por recuperar o usuário pelo Id
     *
     * @param id
     * @return
     */
    UsuarioDTO findById(Long id);
}