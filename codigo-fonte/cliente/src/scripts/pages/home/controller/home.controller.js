/**
 * Created by Basis Tecnologia on 16/08/2016.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('HomeController', ['$state', '$scope', '$timeout', '$mdDialog', '$mdToast', '$q', 'HomeService', controller]);

    function controller($state, $scope, $timeout, $mdDialog, $mdToast, $q, service) {
        var scope = this;

        scope.idOrgaoPai = 2981;
        scope.idCategoriaUnidadeAdministrativa = 1;
        scope.idCategoriaOrgao = 4;
        scope.listaIdCategorias = [scope.idCategoriaUnidadeAdministrativa, scope.idCategoriaUnidadeAdministrativa];

        scope.init = function () {
            verificarUsuarioLogado();
            scope.registroInicial = 1;
            scope.tamanhoLimite = 20;
            scope.totalItems = 0;
            scope.getStatus(function () {
                scope.getPermissao();
            });
            scope.buscarOrgaos();
        };

        scope.filtrar = function () {

            if ($scope.formRegistro.$valid) {
                scope.filtros.page = scope.registroInicial - 1;
                scope.filtros.size = scope.tamanhoLimite;
                scope.filtros.cpf = scope.usuario.cpf;
                if (!scope.filtros.dtInicio || !scope.filtros.dtFim) {
                    scope.filtros.dtInicio = '';
                    scope.filtros.dtFim = '';
                }

                service.findByFilter(scope.filtros).then(function (retorno) {
                    scope._registros = retorno.data.content || [];
                    scope.totalItems = retorno.data.totalElements;

                    if (!scope._registros.length) {
                        //noinspection JSUnresolvedFunction
                        $mdToast.show(
                            $mdToast.simple()
                                .textContent('Dados não encontrados')
                                .hideDelay(3000)
                        );
                    }
                });
            } else {
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Preencha os campos corretamente!')
                        .hideDelay(3000)
                );
            }
        };

        scope.limparFiltros = function () {
            scope.filtros = {
                descricao: null,
                status: null,
                dtInicio: '',
                dtFim: ''
            };

            scope.filtros.statusId = null;

            $timeout(function () {
                if (!scope.permissao.consultarUnidade) {
                    scope.filtros.orgao = scope.permissao.orgao.nome;
                }
                scope.filtrar();
            });
        };

        scope.gerarPdf = function (id) {
            window.open('#/processo/exportar/' + id, '_blank');
        };


        scope.getStatus = function (callback) {
            service.getStatus().then(function (objectReturn) {
                scope._status = objectReturn.data;
                scope._status.unshift({
                    id: null,
                    nome: 'Todos'
                });

                if (callback) {
                    callback();
                }
            });
        };

        scope.searchOrgaoByNome = function (nome) {
            if (nome && nome.length >= 3) {
                service.searchOrgaoByNome(nome).then(function (objectReturn) {
                    $('#orgao-search').find('input').blur();
                    $timeout(function () {
                        $('#orgao-search').find('input').focus();
                    });
                    scope.nomeOrgaos = objectReturn.data;
                    return objectReturn.data;
                });
            } else {
                scope.nomeOrgaos = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.getDescricaoBySearch = function (descricao) {
            if (descricao && descricao.length >= 3) {
                service.getDescricaoBySearch(descricao).then(function (objectReturn) {
                    $('#processo-search').find('input').blur();
                    $timeout(function () {
                        $('#processo-search').find('input').focus();
                    });
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

        scope.excluirRegistro = function (registro) {
            var confirm = $mdDialog.confirm()
                .title('Atenção!')
                .textContent('Deseja excluir o registro?')
                .ok('OK')
                .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {
                service.excluirProcesso(registro.id).then(function () {
                    scope.filtrar();
                });
            });
        };

        scope.getPermissao = function () {
            service.getUsuarioLogado().then(function (objectReturn) {
                scope.usuario = objectReturn.data;
                service.getPermissao(objectReturn.data.cpf).then(function (objectReturn) {
                    scope.permissao = objectReturn.data;
                    scope.limparFiltros();
                });
            });
        };

        scope.buscarOrgaos = function(){
            service.getUnidadePeloIdLimitadoPorIdCategoria(scope.idOrgaoPai, scope.listaIdCategorias).then(function(response){
                scope.orgaos = angular.copy(response.data);
            });
        };

        function verificarUsuarioLogado() {
            service.getUsuarioLogado().then(function (response) {
                if(!response.data) {
                    $state.go('usuario-sem-permissao');
                }
            });
        }

        scope.init();
    }
})();
