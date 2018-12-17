/**
 * Created by Basis Tecnologia on 25/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('PlanoControleController', ['$scope', '$rootScope', '$state', '$stateParams', '$mdToast', '$mdDialog', '$q', 'PlanoControleService', controller]);

    function controller($scope, $rootScope, $state, $stateParams, $mdToast, $mdDialog, $q, service) {
        var scope = this;

        scope.registro = {};
        scope.registro.dtInicio = "";
        scope.registro.dtConclusao = "";

        scope.processoId = $state.params.id;

        scope.apenasLeitura = false;

        scope.init = function () {
            scope._eventoRisco = {};
            scope._eventoRisco.controles = [];
            scope.getUsuarioLogado(function () {

                if ($state.current.name.indexOf("consultar") != -1 || $state.current.name.indexOf("alterar") != -1) {
                    scope.getPermissao();
                } else {
                    scope.registro.dtInicio = "";
                    scope.registro.dtConclusao = "";
                    scope.findByEventoRisco($stateParams.eventoRiscoId);
                    scope.findAllTiposControle();
                    scope.findAllObjetivosControle();
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

        scope.findByEventoRisco = function (registroId) {
            scope._eventoRisco = {};
            service.findByEventoRisco(registroId).then(function (objectResponse) {
                scope._eventoRisco = objectResponse.data;
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

        scope.handleControleChange = function (ev) {
            $scope.formRegistro.$setSubmitted();
            if ($scope.formRegistro.$valid) {
                scope.registro.eventoRisco = {};
                scope.registro.eventoRisco.id = $stateParams.eventoRiscoId;
                scope.registro.cpf = scope.usuario.cpf;
                service.update(scope.registro).then(function (objectReturn) {
                    for (var i = 0; i < scope._eventoRisco.controles.length; i++) {
                        if (scope._eventoRisco.controles[i].$update) {
                            scope._eventoRisco.controles[i] = angular.copy(objectReturn.data);
                            delete scope._eventoRisco.controles[i].$update;
                            scope.registro = {};
                            scope.registro.dtInicio = "";
                            scope.registro.dtConclusao = "";
                        }
                    }
                    scope.registro = {};
                    scope.registro.dtInicio = "";
                    scope.registro.dtConclusao = "";
                    $scope.formRegistro.$setPristine();
                    $scope.formRegistro.$setUntouched();
                });
            } else {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Campo(s) obrigatório(s) não preenchido(s)")
                        .hideDelay(3000)
                );
            }
        };

        scope.cancelarControleUpdate = function () {
            for (var i = 0; i < scope._eventoRisco.controles.length; i++) {
                if (scope._eventoRisco.controles[i].$update) {
                    delete scope._eventoRisco.controles[i].$update;
                    scope.registro = {};
                    scope.registro.dtInicio = "";
                    scope.registro.dtConclusao = "";
                }
            }
        };

        scope.adicionarControleEvento = function () {
            $scope.formRegistro.$setSubmitted();
            if ($scope.formRegistro.$valid) {
                scope.registro.eventoRisco = {};
                scope.registro.eventoRisco.id = $stateParams.eventoRiscoId;
                scope.registro.cpf = scope.usuario.cpf;
                service.create(scope.registro).then(function (objectReturn) {
                    scope._eventoRisco.controles.push(objectReturn.data);
                    scope.registro = {};
                    scope.registro.dtInicio = "";
                    scope.registro.dtConclusao = "";
                    $scope.formRegistro.$setPristine();
                    $scope.formRegistro.$setUntouched();
                });
            } else {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Campo(s) obrigatório(s) não preenchido(s)")
                        .hideDelay(3000)
                );
            }
        };

        scope.excluirControleEvento = function (item) {

            var confirm = $mdDialog.confirm()
                .title("Atenção!")
                .textContent("Deseja excluir o registro?")
                .ok("OK")
                .cancel("Cancelar");

            $mdDialog.show(confirm).then(function () {
                service.excluir(item.id).then(function () {
                    for (var i = 0; i < scope._eventoRisco.controles.length; i++) {
                        if (angular.equals(scope._eventoRisco.controles[i], item)) {
                            scope._eventoRisco.controles.splice(i, 1);
                        }
                    }
                });
            });
        };


        scope.findAllTiposControle = function (callback) {
            service.findAllTiposControle().then(function (retorno) {
                scope._tiposControle = retorno.data;
                if (callback) {
                    callback();
                }
            });
        };

        scope.findAllObjetivosControle = function (callback) {
            service.findAllObjetivosControle().then(function (retorno) {
                scope._objetivosControle = retorno.data;
                if (callback) {
                    callback();
                }
            });
        };


        scope.alterarControleEvento = function (item) {
            item.$update = true;
            scope.registro = angular.copy(item);

            if (scope.registro.dtInicio) {
                scope.registro.dtInicio = new Date(scope.registro.dtInicio);
            } else {
                scope.registro.dtInicio = "";
            }
            if (scope.registro.dtConclusao) {
                scope.registro.dtConclusao = new Date(scope.registro.dtConclusao);
            } else {
                scope.registro.dtConclusao = "";
            }

            for (var i = 0; i < scope._tiposControle.length; i++) {
                if (scope._tiposControle[i].id == scope.registro.tipoControle.id) {

                    scope.registro.tipoControle = scope._tiposControle[i];
                }
            }
            for (var i = 0; i < scope._objetivosControle.length; i++) {
                if (scope._objetivosControle[i].id == scope.registro.objetivo.id) {

                    scope.registro.objetivo = scope._objetivosControle[i];
                }
            }
        };


        scope.getControlesBySearch = function (descricao) {
            if (descricao && descricao.length >= 3) {
                service.getControlesBySearch(descricao, scope.usuario).then(function (objectReturn) {
                    scope._controles = objectReturn.data;
                    return objectReturn.data;
                });
            } else {
                scope._controles = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.voltar = function () {
            $state.go("processo.planoControle.alterar", {id: scope.processoId}, {reload: true});
        }


        scope.init();
    }
})();
