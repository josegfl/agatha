/**
 * Created by Basis Tecnologia on 25/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('RespostaController', ['$rootScope', '$state', '$stateParams', '$mdDialog', 'RespostaService', controller]);

    function controller($rootScope, $state, $stateParams, $mdDialog, service) {
        var scope = this;

        scope.registro = {};

        scope.processoId = $state.params.id;

        scope.apenasLeitura = false;

        scope.init = function () {
            scope._eventoRisco = {};
            scope.getUsuarioLogado(function () {

                if ($state.current.name.indexOf("consultar") != -1 || $state.current.name.indexOf("alterar") != -1) {
                    scope.getPermissao();
                } else {
                    scope.findAllTiposResposta(function () {
                        scope.findByEventoRisco($stateParams.eventoRiscoId);
                    });
                }
            });

        };

        scope.filtrar = function () {
            scope.filtros.page = scope.registroInicial - 1;
            scope.filtros.size = scope.tamanhoLimite;

            service.findByFilter(scope.filtros, scope.processoId).then(function (retorno) {
                scope._eventosRisco = retorno.data.content || [];
                scope.totalItems = retorno.data.totalElements;
            });
        };

        scope.limparFiltros = function () {
            scope.filtros = {};

            scope.filtrar();
        };

        scope.getUsuarioLogado = function (callback) {
            service.getUsuarioLogado().then(function (retorno) {
                scope.usuario = retorno.data;
                if (callback) {
                    callback();
                }
            });
        };

        scope.findAllTiposResposta = function (callback) {
            service.findAllTiposResposta().then(function (retorno) {
                scope._tiposResposta = retorno.data;
                if (callback) {
                    callback();
                }
            });
        };

        scope.cancel = function () {
            scope.findBy($stateParams.eventoRiscoId);
            $mdDialog.cancel();
        };


        scope.findByEventoRisco = function (registroId) {
            scope._eventoRisco = {};
            service.findByEventoRisco(registroId).then(function (objectResponse) {
                scope._eventoRisco = objectResponse.data;
                scope.registro.justificativaRespostaRisco = scope._eventoRisco.justificativaRespostaRisco;
                for (var i = 0; i < scope._tiposResposta.length; i++) {
                    if (scope._tiposResposta[i].id == scope._eventoRisco.respostaRisco.id) {
                        scope.registro.respostaRisco = scope._tiposResposta[i];
                    }
                }
            });
        };

        scope.persistir = function (registro) {
            var item = {};
            item.justificativaRespostaRisco = registro.justificativaRespostaRisco;
            item.respostaRisco = registro.respostaRisco;
            service.update($stateParams.eventoRiscoId, item).then(function () {
                $state.go("processo.respostaRisco.alterar", {id: scope.processoId}, {reload: true})
            });
        };

        scope.getPermissao = function () {
            service.getPermissao(scope.usuario.cpf).then(function (objectReturn) {
                scope.permissao = objectReturn.data;

                scope.apenasLeitura = $state.current.name.indexOf("consultar") != -1;
                scope.apenasLeitura = scope.permissao.criar == false ? true : scope.apenasLeitura;

                scope.registroInicial = 1;
                scope.tamanhoLimite = 20;
                scope.totalItems = 0;
                scope.limparFiltros();
            });
        };


        scope.init();
    }
})();
