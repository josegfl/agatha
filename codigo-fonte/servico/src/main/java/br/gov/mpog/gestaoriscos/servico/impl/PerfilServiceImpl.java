package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.dto.PerfilDTO;
import br.gov.mpog.gestaoriscos.repositorio.PerfilRepository;
import br.gov.mpog.gestaoriscos.servico.PerfilService;
import br.gov.mpog.gestaoriscos.servico.mapper.PerfilMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class PerfilServiceImpl implements PerfilService {

    private final PerfilRepository perfilRepository;

    private final PerfilMapper perfilMapper;

    @Autowired
    public PerfilServiceImpl(PerfilRepository perfilRepository, PerfilMapper perfilMapper) {
        this.perfilRepository = perfilRepository;
        this.perfilMapper = perfilMapper;
    }

    @Override
    public List<PerfilDTO> findByCPF(String cpf) {
        if (Objects.isNull(cpf) || cpf.isEmpty()) {
            return null;
        }
        return perfilMapper.perfilsToPerfilDTOs(perfilRepository.findByCPF(cpf));
    }
}
