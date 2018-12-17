/**
 * Created by Basis Tecnologia on 10/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('AvaliacaoRiscoController', ['$state', '$stateParams', 'AvaliacaoRiscoService', '$mdDialog', controller]);

    function controller($state, $stateParams, service, $mdDialog) {
        var scope = this;

        scope.processoId = $stateParams.id;

        scope.apenasLeitura = false;
        scope.disableCheck = true;

        scope.init = function () {
            scope.getIgnorarRiscoInerente();
            scope.getPermissao();
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

        scope.getIgnorarRiscoInerente = function () {
            service.getIgnorarRiscoInerente(scope.processoId).then(function (objectReturn) {
                scope.ignorar = objectReturn.data;
                scope.disableCheck = false;
            });
        };

        scope.handleIgnorarChange = function () {
            if (scope.ignorar) {
                var calculoRiscoInerente = false;
                for(var i = 0; i < scope._eventosRisco.length; i++){
                    if (scope._eventosRisco[i].calculoRiscoInerente) {
                        calculoRiscoInerente = true;
                        i = scope._eventosRisco.length;
                    }
                }
                if (calculoRiscoInerente) {
                    var confirm = $mdDialog.confirm()
                        .title("Atenção!")
                        .textContent("Existem cálculos, para ignorar essa etapa é necessário excluir os dados informados. Deseja excluir?")
                        .ok("Sim")
                        .cancel("Não");

                    $mdDialog.show(confirm).then(function () {
                        scope.disableCheck = true;
                        service.alterarIgnorarRiscoInerente(scope.processoId).then(function (objectReturn) {
                            scope.ignorar = objectReturn.data;
                            scope.disableCheck = false;
                            scope.limparFiltros();
                        });

                    }, function () {
                        scope.ignorar = false;
                        scope.disableCheck = false;
                    });
                } else {
                    scope.disableCheck = true;
                    service.alterarIgnorarRiscoInerente(scope.processoId).then(function (objectReturn) {
                        scope.ignorar = objectReturn.data;
                        scope.disableCheck = false;
                        scope.limparFiltros();
                    });
                }

            } else {
                service.alterarIgnorarRiscoInerente(scope.processoId).then(function (objectReturn) {
                    scope.ignorar = objectReturn.data;
                    scope.disableCheck = false;
                });
            }
        };

        scope.getPermissao = function () {

            service.getUsuarioLogado().then(function (objectReturn) {
                scope.usuario = objectReturn.data;
                service.getPermissao(objectReturn.data.cpf).then(function (objectReturn) {
                    scope.permissao = objectReturn.data;

                    if ($stateParams.id) {
                        scope.apenasLeitura = $state.current.name.indexOf("consultar") != -1;
                        scope.apenasLeitura = scope.permissao.criar == false ? true : scope.apenasLeitura;

                        scope.registroInicial = 1;
                        scope.tamanhoLimite = 20;
                        scope.totalItems = 0;
                        scope.limparFiltros();
                    }
                });
            });
        };

        scope.init();
    }
})();
