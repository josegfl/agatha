/**
 * Created by Basis Tecnologia on 25/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('GlossarioController', ['$scope', '$mdToast', '$mdDialog', '$q', 'GlossarioService', 'replace-string', controller]);

    function controller($scope, $mdToast, $mdDialog, $q, service, replaceString) {
        var scope = this;

        scope.init = function () {
            scope.registroInicial = 1;
            scope.tamanhoLimite = 20;
            scope.totalItems = 0;
            scope.findAllStatus();
            scope.limparFiltros();
        };

        scope.findAllStatus = function () {
            scope._status = [
                {status: null, nome: "Todos"},
                {status: true, nome: "Ativo"},
                {status: false, nome: "Inativo"}
            ];
        };

        scope.filtrar = function () {
            if ($scope.formRegistro) {
                $scope.formRegistro.$setPristine();
                $scope.formRegistro.$setUntouched();
            }

            scope.filtros.page = scope.registroInicial - 1;
            scope.filtros.size = scope.tamanhoLimite;

            service.findByFilter(scope.filtros, scope.registro).then(function (retorno) {
                scope._registros = retorno.data.content || [];
                scope.totalItems = retorno.data.totalElements;

                if (!scope._registros.length) {
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent("Dados não encontrados")
                            .hideDelay(3000)
                    );
                }
            });
        };

        scope.limparFiltros = function () {
            if ($scope.formRegistro) {
                $scope.formRegistro.$setPristine();
                $scope.formRegistro.$setUntouched();
            }

            scope.filtros = {};
            scope.registro = {};

            scope.filtrar();
        };

        scope.alterarStatus = function (registro) {
            registro.status = !registro.status;
            service.update(registro).then(function () {
                scope.filtrar();
            });
        };

        scope.excluirRegistro = function (registro) {
            scope.registro = {};

            var confirm = $mdDialog.confirm()
                .title("Atenção!")
                .textContent("Deseja excluir o registro?")
                .ok("OK")
                .cancel("Cancelar");

            $mdDialog.show(confirm).then(function () {
                service.excluir(registro.id).then(function () {
                    scope.filtrar();
                });
            });
        };

        scope.getTermoBySearch = function(termo){
            scope.replaceStringTermo(scope.registro.termo);
            if(termo && termo.length >= 3){

                if (scope.registro.termo.length > 200) {
                    scope.registro.termo = scope.registro.termo.substring(0, 199);
                }
                service.getTermoBySearch(termo).then(function(objectReturn){
                    scope.termos = objectReturn.data;
                    return objectReturn.data;
                });
            } else {
                scope.termos = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.replaceStringTermo = function(){
            _.forEach(replaceString, function(value, key) {
                scope.registro.termo = _.replace(scope.registro.termo, key, value);
            });
        };

        scope.replaceStringDescricao = function(){
            _.forEach(replaceString, function(value, key) {
                scope.registro.descricao = _.replace(scope.registro.descricao, key, value);
            });
        };

        scope.getDescricaoBySearch = function(descricao){
            scope.replaceStringDescricao(scope.registro.descricao);
            if(descricao && descricao.length >= 3){
                if (scope.registro.descricao.length > 1000) {
                    scope.registro.descricao = descricao.substring(0, 999);
                }

                service.getDescricaoBySearch(descricao).then(function(objectReturn){
                    scope.descricoes = objectReturn.data;
                    return objectReturn.data;
                });
            } else {
                scope.descricoes = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.findBy = function (registroId) {
            $scope.formRegistro.$setPristine();
            $scope.formRegistro.$setUntouched();

            scope.registro = {};
            service.findBy(registroId).then(function (objectResponse) {
                scope.registro = objectResponse.data;
            })
        };

        scope.persistir = function (registro) {
            if ($scope.formRegistro.$valid) {
                registro.status = registro.status == null ? true : registro.status;

                if (scope.registro.termo.length > 200) {
                    scope.registro.termo = scope.registro.termo.substring(0, 199);
                }

                if (scope.registro.descricao.length > 1000) {
                    scope.registro.descricao = descricao.substring(0, 999);
                }

                if(registro.id){
                    service.update(registro).then(function(){
                        scope.registro = {};
                        scope.filtrar();
                    });
                } else {
                    service.create(registro).then(function () {
                        scope.registro = {};
                        scope.filtrar();
                    });
                }
            } else {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Campo(s) obrigatório(s) não preenchido(s)")
                        .hideDelay(3000)
                );
            }
        };

        scope.init();
    }
})();
