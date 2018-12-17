package br.gov.mpog.gestaoriscos.controller;

import br.gov.mpog.gestaoriscos.servico.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/perfis")
public class PerfilController {

    private PerfilService perfilService;

    @Autowired
    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @RequestMapping(value = "/findByCPF/{cpf}", method = RequestMethod.GET)
    public ResponseEntity findByCPF(@PathVariable("cpf") String cpf) {
        return ResponseEntity.ok(perfilService.findByCPF(cpf));
    }

}
