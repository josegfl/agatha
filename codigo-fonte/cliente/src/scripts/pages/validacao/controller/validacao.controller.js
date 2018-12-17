/**
 * Created by Basis Tecnologia on 25/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('ValidacaoController', ['$scope', '$mdToast', '$rootScope', '$state', '$stateParams', '$mdDialog', 'ValidacaoService', controller]);

    function controller($scope, $mdToast, $rootScope, $state, $stateParams, $mdDialog, service) {
        var scope = this;

        scope.registro = {};

        scope.processoId = $state.params.id;

        scope.apenasLeitura = false;

        scope.init = function () {

            scope.apenasLeitura = $state.current.name.indexOf('consultar') != -1;

            scope.getUsuarioLogado(function () {
                scope.findBy(function () {
                    scope.getDecisoes();
                });
            });

        };

        scope.findBy = function (callback) {
            scope.registro = {};
            service.findBy(scope.processoId).then(function (objectResponse) {
                scope.registro = objectResponse.data;
                if (callback) {
                    callback();
                }

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

        scope.getDecisoes = function () {
            service.getDecisoes().then(function (objectReturn) {
                scope._decisoes = objectReturn.data;
                if (scope.registro.decisao && scope.registro.decisao.id) {
                    for (var i = 0; i < scope._decisoes.length; i++) {
                        if (scope._decisoes[i].id == scope.registro.decisao.id) {
                            scope.registro.decisao = scope._decisoes[i];
                        }
                    }
                }
            });
        };


        scope.persistir = function (registro) {
            if ($scope.formRegistro.$valid) {
                service.update(registro).then(function () {
                    $state.go('home');
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent('Registro salvo com sucesso')
                            .hideDelay(3000)
                    );
                });
            } else {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Campo(s) obrigatório(s) não preenchido(s)')
                        .hideDelay(3000)
                );
            }
        };

        scope.init();
    }
})();
