/**
 * Created by Basis Tecnologia on 04/04/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').controller('ProcessoController', ['$timeout', '$state', '$rootScope', 'ProcessoService', controller]);

    function controller($timeout, $state, $rootScope, service){
        var scope = this;

        scope.processoId = $state.params.id;

        scope.apenasLeitura = false;
        scope.gestor = false;

        scope.etapas = {};
        scope.etapas.identificacao = $state.current.name.indexOf("incluir") != -1;

        if($state.current.name.indexOf("analiseAmbiente") != -1){
            scope.currentTab = 0;
        } else  if($state.current.name.indexOf("identificacao") != -1) {
            scope.currentTab = 1;
        }else if($state.current.name.indexOf("avaliacao") != -1) {
            scope.currentTab = 2;
        } else if($state.current.name.indexOf("respostaRisco") != -1) {
            scope.currentTab = 3;
        } else  if($state.current.name.indexOf("planoControle") != -1) {
            scope.currentTab = 4;
        } else  if($state.current.name.indexOf("validacao") != -1) {
            scope.currentTab = 5;
        }

        scope.changeToAnalise = function(){
            if($state.current.name.indexOf("analiseAmbiente") == -1){
                if($state.current.name.indexOf("consultar") != -1){
                    $state.go("processo.analiseAmbiente.consultar", {id: $state.params.id})
                }else{
                    $state.go("processo.analiseAmbiente.alterar", {id: $state.params.id})
                }
            }
        };

        scope.changeToIdentificacao = function(){
            if($state.current.name.indexOf("identificacao") == -1){
                scope.currentTab = 1;
                if($state.current.name.indexOf("consultar") != -1){
                    $state.go("processo.identificacao.consultar", {id: $state.params.id})
                }else{
                    $state.go("processo.identificacao.alterar", {id: $state.params.id})
                }
            }
        };

        scope.changeToAvaliacao = function(){
            if($state.current.name.indexOf("avaliacao") == -1){
                scope.currentTab = 2;
                if($state.current.name.indexOf("consultar") != -1){
                    $state.go("processo.avaliacao.riscoInerente.consultar", {id: $state.params.id})
                }else{
                    $state.go("processo.avaliacao.riscoInerente.alterar", {id: $state.params.id})
                }
            }
        };

        scope.changeToResposta = function(){
            if($state.current.name.indexOf("respostaRisco") == -1){
                scope.currentTab = 3;
                if($state.current.name.indexOf("consultar") != -1){
                    $state.go("processo.respostaRisco.consultar", {id: $state.params.id})
                }else{
                    $state.go("processo.respostaRisco.alterar", {id: $state.params.id})
                }
            }
        };

        scope.changeToPlanoControle = function(){
            if($state.current.name.indexOf("planoControle") == -1){
                scope.currentTab = 4;
                if($state.current.name.indexOf("consultar") != -1){
                    $state.go("processo.planoControle.consultar", {id: $state.params.id})
                }else{
                    $state.go("processo.planoControle.alterar", {id: $state.params.id})
                }
            }
        };
        scope.changeTovalidacao = function(){
            if($state.current.name.indexOf("validacao") == -1){
                scope.currentTab = 5;
                if($state.current.name.indexOf("consultar") != -1){
                    $state.go("processo.validacao.consultar", {id: $state.params.id})
                }else{
                    $state.go("processo.validacao.alterar", {id: $state.params.id})
                }
            }
        };

        scope.init = function(){

            scope.getUsuarioLogado(function () {
                scope.getPermissao();
                    scope.findBy($state.params.id);
            });
            $timeout(function () {
                scope.getProcessoEtapa();
            })

        };

        scope.findBy = function(registroId){
            scope.registro = {};
            service.findBy(registroId).then(function(objectResponse){
                scope.registro = objectResponse.data;
                if(scope.registro.gestor.cpf == scope.usuario.cpf){
                    scope.gestor = true;
                }

            });
        };

        scope.getProcessoEtapa = function(){
            service.getProcessoEtapa($state.params.id).then(function(objectResponse){
                scope.etapas = objectResponse.data;

                $timeout(function () {
                    if($state.current.name.indexOf("analiseAmbiente") != -1){
                        scope.currentTab = 0;
                    } else  if($state.current.name.indexOf("identificacao") != -1) {
                        scope.currentTab = 1;
                    }else if($state.current.name.indexOf("avaliacao") != -1) {
                        scope.currentTab = 2;
                    } else if($state.current.name.indexOf("respostaRisco") != -1) {
                        scope.currentTab = 3;
                    } else  if($state.current.name.indexOf("planoControle") != -1) {
                        scope.currentTab = 4;
                    } else  if($state.current.name.indexOf("validacao") != -1) {
                        scope.currentTab = 5;
                    }
                })

            });
        };

        scope.getPermissao = function () {
            service.getPermissao(scope.usuario.cpf).then(function (objectReturn) {
                scope.permissao = objectReturn.data;
            });
        };

        scope.getUsuarioLogado = function (callback) {
            service.getUsuarioLogado().then(function (retorno) {
                scope.usuario = retorno.data;
                if (callback) {
                    callback();
                }
            });
        };


        scope.init();

    }
})();
