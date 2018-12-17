package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.controller.util.HeaderUtil;
import br.gov.mpog.gestaoriscos.modelo.dto.UsuarioDTO;
import br.gov.mpog.gestaoriscos.servico.UsuarioService;
import br.gov.mpog.gestaoriscos.util.Mensagens;
import br.gov.mpog.gestaoriscos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping (value = "/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @RequestMapping(value = "/usuario-logado", method = RequestMethod.GET)
    public UsuarioDTO getUsuarioLogado(HttpServletRequest request) {
        br.gov.nuvem.ecidadao.security.Usuario usuarioBrasilCidadao =
                (br.gov.nuvem.ecidadao.security.Usuario) request.getAttribute("user");
        return usuarioService.findByCpf(StringUtil.removerMascaraCpf(usuarioBrasilCidadao.getCpf()));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity save(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        if (usuarioService.isCPFCadastrado(usuarioDTO.getCpf())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("usuario", "error", Mensagens.US001_14)).body(null);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_2, "")).body(usuarioService.save(usuarioDTO));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity update(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        if (usuarioService.isCPFCadastradoDiferenteIdUsuario(usuarioDTO.getCpf(), usuarioDTO.getId())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("usuario", "error", Mensagens.US001_14)).body(null);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(Mensagens.US015_3, "")).body(usuarioService.update(usuarioDTO));
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

}
