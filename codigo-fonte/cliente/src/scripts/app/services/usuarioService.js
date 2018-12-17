/**
 * Created by Basis Tecnologia on 24/08/2016.
 */
'use strict';
(function(){

    angular.module('gestaoRiscosApp').factory('usuarioService', ['$http', '$rootScope', 'comumService', service]);

    function service($http, $rootScope){
        var urlUsuario = 'gestaoriscos/api/usuarios/usuario-logado';
        var urlMenuUsuario = 'gestaoriscos/api/cliente/';
        var getUsuarioLogado = function(){
            return $http.get(urlUsuario);
        };

        var getMenuUsuario = function(usuario){

            var menuProcesso = {
                descricaoResumida: 'PROCESSO',
                url: '',
                nos: [
                    {
                        descricaoResumida: 'Gerenciar Processo',
                        url: '',
                        nos: [
                            {
                                descricaoResumida: 'GERENCIAR',
                                url: '/#/',
                                nos: []
                            }
                        ]
                    }
                ]
            };
            var menuPermissao = {
                descricaoResumida: 'PERMISSÃO',
                url: '',
                nos: [
                    {
                        descricaoResumida: 'Gerenciar Permissão',
                        url: '',
                        nos: [
                            {
                                descricaoResumida: 'GERENCIAR',
                                url: '/#/gerenciarPermissoes/',
                                nos: []
                            }
                        ]
                    }
                ]
            };
            var menuNucleo = {
                descricaoResumida: 'NÚCLEO',
                url: '',
                nos: [
                    {
                        descricaoResumida: 'Gerenciar Eventos de Risco',
                        url: '',
                        nos: [
                            {
                                descricaoResumida: 'EVENTO DE RISCO',
                                url: '/#/manterEventoRisco',
                                nos: []
                            },
                            {
                                descricaoResumida: 'CAUSAS DE EVENTOS DE RISCO',
                                url: '/#/manterCausaEventoRisco',
                                nos: []
                            },
                            {
                                descricaoResumida: 'CONSEQUÊNCIA DE EVENTOS DE RISCO',
                                url: '/#/manterConsequenciaEventoRisco',
                                nos: []
                            },
                            {
                                descricaoResumida: 'CATEGORIA E NATUREZA DE RISCO',
                                url: '/#/manterCategoria',
                                nos: []
                            },
                            {
                                descricaoResumida: 'MANTER CALCULADORA',
                                url: '/#/manterCalculadora',
                                nos: []
                            },
                            {
                                descricaoResumida: 'CONTROLE DE RISCO',
                                url: '/#/manterControle',
                                nos: []
                            },
                            {
                                descricaoResumida: 'DESENHO DE CONTROLE',
                                url: '/#/manterDesenho',
                                nos: []
                            },
                            {
                                descricaoResumida: 'OPERAÇÃO DE CONTROLE',
                                url: '/#/manterOperacao',
                                nos: []
                            },
                            {
                                descricaoResumida: 'TAXONOMIA',
                                url: '/#/taxonomia/',
                                nos: []
                            },
                            {
                                descricaoResumida: 'GLOSSÁRIO',
                                url: '/#/manterGlossario',
                                nos: []
                            },
                            {
                                descricaoResumida: 'MACROPROCESSO',
                                url: '/#/manterMacroprocesso',
                                nos: []
                            }
                        ]
                    }
                ]
            };

            var ID_PERFIL_NUCLEO = 3;

            getUsuarioLogado().success(function(usuario) {
                if (usuario.permissoes.filter(function(permissao) {
                        if (!permissao.excluido && permissao.perfil.id === ID_PERFIL_NUCLEO) {
                            return permissao;
                        }
                    }).length > 0) {
                    $rootScope.menuAcessos = [menuProcesso, menuPermissao, menuNucleo];
                    return;
                }
                $rootScope.menuAcessos = [menuProcesso];
            });

        };

        var findAllPerfisByCPF = function (cpf) {
            return $http.get('/gestaoriscos/api/perfis/findByCPF/' + cpf);
        }

        var findAllGlossarios = function(){
            return $http.get("/gestaoriscos/api/glossarios/findall");
        };

        return {
            getUsuarioLogado: getUsuarioLogado,
            getMenuUsuario: getMenuUsuario,
            findAllGlossarios: findAllGlossarios,
            findAllPerfisByCPF: findAllPerfisByCPF
        };
    }

}());
