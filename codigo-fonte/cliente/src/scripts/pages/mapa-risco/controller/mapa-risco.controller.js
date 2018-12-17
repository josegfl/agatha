/**
 * Created by Basis Tecnologia on 16/08/2016.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').controller('MapaRiscoController', ['$rootScope', '$state', 'MapaRiscoService', controller]);

    function controller($rootScope, $state, service){
        var scope = this;

        $rootScope.mapaStates = 1;
        scope.processoId = $state.params.id;

        scope.apenasLeitura = false;

        scope.disableAvaliacao = false;
        scope.disableResposta = false;
        scope.disablePlanoControle = false;

        $rootScope.$watch('mapaStates', function(){
            scope.mapaStates = $rootScope.mapaStates;
        });

        scope.changeToIdentificacao = function(){
            if($state.current.name.indexOf("identificacao") == -1){
                if($state.current.name.indexOf("consultar") != -1){
                    $state.go("processo.mapaRisco.identificacao.consultar", {id: $state.params.id})
                }else{
                    $state.go("processo.mapaRisco.identificacao.alterar", {id: $state.params.id})
                }
            }
        };

        scope.changeToAvaliacao = function(){
            if($state.current.name.indexOf("avaliacao") == -1){
                if($state.current.name.indexOf("consultar") != -1){
                    $state.go("processo.mapaRisco.avaliacao.riscoInerente.consultar", {id: $state.params.id})
                }else{
                    $state.go("processo.mapaRisco.avaliacao.riscoInerente.alterar", {id: $state.params.id})
                }
            }
        };

        scope.changeToResposta = function(){
            if($state.current.name.indexOf("respostaRisco") == -1){
                if($state.current.name.indexOf("consultar") != -1){
                    $state.go("processo.mapaRisco.respostaRisco.consultar", {id: $state.params.id})
                }else{
                    $state.go("processo.mapaRisco.respostaRisco.alterar", {id: $state.params.id})
                }
            }
        };

        scope.changeToPlanoControle = function(){
            if($state.current.name.indexOf("planoControle") == -1){
                if($state.current.name.indexOf("consultar") != -1){
                    $state.go("processo.mapaRisco.planoControle.consultar", {id: $state.params.id})
                }else{
                    $state.go("processo.mapaRisco.planoControle.alterar", {id: $state.params.id})
                }
            }
        };

        scope.init = function(){
            scope.getPermissao();
        };

        scope.findBy = function(registroId){
            scope.registro = {};
            service.findBy(registroId).then(function(objectResponse){
                scope.registro = objectResponse.data;

                scope.registro.analise.etica = scope.registro.analise.etica.toString();
                scope.registro.analise.estrutura = scope.registro.analise.estrutura.toString();
                scope.registro.analise.recursosHumanos = scope.registro.analise.recursosHumanos.toString();
                scope.registro.analise.atribuicoes = scope.registro.analise.atribuicoes.toString();
                scope.registro.analise.normasInternas = scope.registro.analise.normasInternas.toString();
                scope.registro.analise.missao = scope.registro.analise.missao.toString();
                scope.registro.analise.visao = scope.registro.analise.visao.toString();
                scope.registro.analise.objetivos = scope.registro.analise.objetivos.toString();

                if(scope.registro.dtInicio){
                    scope.registro.dtInicio = new Date(scope.registro.dtInicio);
                }
                if(scope.registro.dtFim){
                    scope.registro.dtFim = new Date(scope.registro.dtFim);
                }

                scope.disableAvaliacao = scope.registro.identificacao.status.nome.toLowerCase() == "não iniciada";
                scope.disableResposta = scope.registro.avaliacao.status.nome.toLowerCase() == "não iniciada";
                scope.disablePlanoControle = scope.registro.resposta.status.nome.toLowerCase() == "não iniciada";

            })
        };

        scope.getPermissao = function(){
            service.getUsuarioLogado().then(function(objectReturn){
                scope.usuario = objectReturn.data;
                service.getPermissao(objectReturn.data.cpf).then(function(objectReturn){
                    scope.permissao = objectReturn.data;

                    if($state.params.id){
                        scope.apenasLeitura = $state.current.name.indexOf("consultar") != -1;
                        scope.apenasLeitura = scope.permissao.criar == false ? true : scope.apenasLeitura;
                        scope.findBy($state.params.id)
                    }
                });
            });
        };

        scope.init();
    }
})();
