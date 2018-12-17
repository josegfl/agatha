/**
 * Created by Basis Tecnologia on 10/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('IdentificacaoController', ['$rootScope', '$state', '$stateParams', '$mdDialog', 'IdentificacaoService', controller]);

    function controller($rootScope, $state, $stateParams, $mdDialog, service) {
        var scope = this;


        scope.processoId = $stateParams.id;

        scope.registro = {};
        scope.registro.id = scope.processoId;

        scope.apenasLeitura = false;

        scope.init = function () {
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

        scope.excluirEventoRisco = function (registro) {

            var confirm = $mdDialog.confirm()
                .title("Atenção!")
                .textContent("Deseja excluir o registro?")
                .ok("OK")
                .cancel("Cancelar");

            $mdDialog.show(confirm).then(function () {
                service.excluirEventoRisco(registro.id).then(function () {
                    scope.filtrar();
                });
            });
        };

        scope.init();
    }
})();
