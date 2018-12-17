/**
 * Created by Basis Tecnologia on 10/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('AvaliacaoController', ['$scope', '$rootScope', '$state', 'AvaliacaoService', '$mdToast', controller]);

    function controller($scope, $rootScope, $state, service, $mdToast) {
        var scope = this;


        scope.processoId = $state.params.id;

        scope.registro = {};
        scope.registro.processo = {};
        scope.registro.processo.id = scope.processoId;

        scope.apenasLeitura = false;
        scope.disableCheck = true;

        if ($state.current.name.indexOf("riscoInerente") != -1) {
            scope.avaliacaoStates = 0;
        } else if ($state.current.name.indexOf("controleEvento") != -1) {
            scope.avaliacaoStates = 1;
        } else if ($state.current.name.indexOf("riscoResidual") != -1) {
            scope.avaliacaoStates = 2
        }

        scope.changeToRiscoInerente = function () {
            if ($state.current.name.indexOf("riscoInerente") == -1) {
                if ($state.current.name.indexOf("consultar") != -1) {
                    $state.go("processo.avaliacao.riscoInerente.consultar", {id: $state.params.id})
                } else {
                    $state.go("processo.avaliacao.riscoInerente.alterar", {id: $state.params.id})
                }
            }
        };

        scope.changeToControleEvento = function () {
            service.verificaRiscoInerente(scope.processoId).then(function (objectReturn) {
                if (!objectReturn.data) {
                    scope.avaliacaoStates = 0;
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent("Preencha a Avaliação do Risco Inerente para prosseguir")
                            .hideDelay(3000)
                    );
                } else {
                    if ($state.current.name.indexOf("controleEvento") == -1) {
                        if ($state.current.name.indexOf("consultar") != -1) {
                            $state.go("processo.avaliacao.controleEvento.consultar", {id: $state.params.id})
                        } else {
                            $state.go("processo.avaliacao.controleEvento.alterar", {id: $state.params.id})
                        }
                    }
                }
            });
        };

        scope.changeToRiscoResidual = function () {
            service.verificaRiscoInerente(scope.processoId).then(function (objectReturn) {
                if (objectReturn.data) {
                    service.verificaControleEvento(scope.processoId).then(function (objectReturn) {
                        if (!objectReturn.data) {
                            scope.avaliacaoStates = 1;
                            $mdToast.show(
                                $mdToast.simple()
                                    .textContent("Preencha a Avaliação do Controles Existentes para prosseguir")
                                    .hideDelay(3000)
                            );
                            if ($state.current.name.indexOf("controleEvento") == -1) {
                                if ($state.current.name.indexOf("consultar") != -1) {
                                    $state.go("processo.avaliacao.controleEvento.consultar", {id: $state.params.id})
                                } else {
                                    $state.go("processo.avaliacao.controleEvento.alterar", {id: $state.params.id})
                                }
                            }
                        } else {
                            if ($state.current.name.indexOf("riscoResidual") == -1) {
                                if ($state.current.name.indexOf("consultar") != -1) {
                                    $state.go("processo.avaliacao.riscoResidual.consultar", {id: $state.params.id})
                                } else {
                                    $state.go("processo.avaliacao.riscoResidual.alterar", {id: $state.params.id})
                                }
                            }
                        }
                    });

                } else {
                    scope.avaliacaoStates = 0;
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent("Preencha a Avaliação do Risco Inerente para prosseguir")
                            .hideDelay(3000)
                    );
                }
            });
        };

        scope.getPermissao = function () {
            service.getUsuarioLogado().then(function (objectReturn) {
                scope.usuario = objectReturn.data;
                service.getPermissao(objectReturn.data.cpf).then(function (objectReturn) {
                    scope.permissao = objectReturn.data;

                    if (scope.permissao.validar) {
                        scope.getAvaliacaoByProcessoId();
                    }
                });
            });
        };

        scope.getAvaliacaoByProcessoId = function () {
            service.findBy(scope.processoId).then(function (objectReturn) {
                scope.registro = objectReturn.data.avaliacao;
                scope.registro.processo = {};
                scope.registro.processo.id = scope.processoId;

                var isAnalista = false;

                for (var i in objectReturn.data.responsaveis) {
                    if (objectReturn.data.responsaveis[i].usuario.cpf == scope.usuario.cpf) {
                        isAnalista = true;
                        break;
                    }
                }

                scope.registro.isAnalista = isAnalista;

            });
        };

        scope.init = function () {
            scope.getPermissao();
        };

        scope.init();
    }
})();
