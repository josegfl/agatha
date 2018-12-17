package br.gov.mpog.gestaoriscos.servico.impl;

import br.gov.mpog.gestaoriscos.modelo.Orgao;
import br.gov.mpog.gestaoriscos.modelo.dto.OrgaoConsultaDTO;
import br.gov.mpog.gestaoriscos.modelo.dto.OrgaoDTO;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoCustomRepositorio;
import br.gov.mpog.gestaoriscos.repositorio.OrgaoRepository;
import br.gov.mpog.gestaoriscos.servico.OrgaoServico;
import br.gov.mpog.gestaoriscos.servico.mapper.OrgaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrgaoServicoImpl implements OrgaoServico {

    @Autowired
    private OrgaoCustomRepositorio orgaoCustomRepositorio;

    @Autowired
    private OrgaoRepository orgaoRepository;

    @Autowired
    private OrgaoMapper orgaoMapper;

    public List<OrgaoDTO> listarOrgaos(final Long id) {
        List<Orgao> orgaos = orgaoCustomRepositorio.listarOrgaos(id);
        List<OrgaoDTO> orgaoDTOS = new ArrayList<>(1);
        orgaos.stream().forEach(orgao -> {
            orgaoDTOS.add(new OrgaoDTO(orgao));
        });
        return orgaoDTOS;
    }

    public List<OrgaoDTO> getUnidadePeloIdLimitadoPorIdCategoria(OrgaoConsultaDTO orgaoConsultaDTO) {
        if (Objects.isNull(orgaoConsultaDTO)
                || Objects.isNull(orgaoConsultaDTO.getIdOrgaoPai())) {
            return null;
        }

        if (Objects.isNull(orgaoConsultaDTO.getListaIdCategorias())
                || orgaoConsultaDTO.getListaIdCategorias().isEmpty()) {
            return null;
        }

        return orgaoRepository.findUnidadePeloIdLimitadoPorIdCategoria(orgaoConsultaDTO.getIdOrgaoPai(), orgaoConsultaDTO.getListaIdCategorias());
    }

    public List<OrgaoDTO> filtrar(String nomeOrgao) {
        List<Orgao> orgaos = orgaoCustomRepositorio.filtrar(nomeOrgao);
        List<OrgaoDTO> orgaoDTOS = new ArrayList<>(1);
        orgaos.stream().forEach(orgao -> {
            orgaoDTOS.add(new OrgaoDTO(orgao));
        });
        return orgaoDTOS;
    }

}
