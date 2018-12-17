/**
 * Created by Basis Tecnologia on 24/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('TaxonomiaController', ['$localStorage', '$state', '$mdToast', '$filter', '$q', '$mdDialog', 'TaxonomiaService', controller]);

    function controller($localStorage, $state, $mdToast, $filter, $q, $mdDialog, service) {
        var scope = this;

        scope.init = function () {
            scope.filtros = {};
            scope.selectedData = [];
            scope.tipoSelecionado = null;

            service.getUsuarioLogado().then(function (usuarioLogado) {
                service.getSecretariaByPerfil(usuarioLogado.data.cpf).then(function (objectReturn) {
                    scope.secretaria = objectReturn.data;
                    if (scope.secretaria) {
                        scope.filtros.orgao = scope.secretaria.nome;
                    }

                    $localStorage.taxonomias = null;
                    scope._registros = [];
                    scope.registroInicial = 1;
                    scope.tamanhoLimite = 20;
                    scope.totalItems = 0;
                    scope.findAllTipos();
                    scope.filtrar();
                });
            })
        };

        scope.findAllTipos = function () {
            service.getTipos().then(function (objectReturn) {
                scope._tipos = objectReturn.data;
            });
        };

        scope.filtrar = function () {
            scope.filtros.page = scope.registroInicial - 1;
            scope.filtros.size = scope.tamanhoLimite;

            service.findByFilter(scope.filtros).then(function (retorno) {
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
            scope.filtros = {};
            scope.selectedData = [];
            scope.tipoSelecionado = null;

            for (var i = 0; i < scope._registros.length; i++) {
                scope._registros[i].$selected = false;
            }

            scope.filtrar();
        };

        scope.getDescricaoBySearch = function (descricao) {
            if (scope.secretaria) {
                return;
            }

            if (descricao && descricao.length >= 3) {
                service.getDescricaoBySearch(descricao).then(function (objectReturn) {
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

        scope.searchOrgaoByNome = function (nome) {
            if (nome && nome.length >= 3) {
                service.searchOrgaoByNome(nome).then(function (objectReturn) {
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

        scope.aprovar = function () {
            service.aprovar(scope.selectedData).then(function () {
                scope.selectedData = [];
                scope.tipoSelecionado = null;
                scope.filtrar();
            });
        };

        scope.agrupar = function () {
            $localStorage.taxonomias = angular.copy(scope.selectedData);
            $state.go("taxonomia.agrupamento.detalhar");
        };

        scope.adicionarJustificativa = function (ev) {
            $mdDialog.show({
                controller: function () {
                    return scope;
                },
                controllerAs: 'ctrl',
                templateUrl: 'scripts/pages/taxonomia/view/justificativa-reprovacao.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: true
            });
        };

        scope.reprovar = function (justificativa) {
            if (scope.formJustificativa.$valid) {
                service.reprovar(scope.selectedData, justificativa).then(function () {
                    $mdDialog.cancel();
                    scope.selectedData = [];
                    scope.tipoSelecionado = null;
                    scope.filtrar();
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

        scope.cancel = function () {
            $mdDialog.cancel();
        };


        scope.handleItemSelecionado = function () {
            scope.selectedData = $filter("filter")(scope._registros, {$selected: true});

            if (scope.selectedData && scope.selectedData.length) {
                scope.tipoSelecionado = scope.selectedData[0].tipo;
            } else {
                scope.tipoSelecionado = null;
            }
        };

        scope.handleDtInicioChange = function () {
            if (scope.filtros.dtInicio && scope.filtros.dtFim) {
                if (scope.filtros.dtInicio > scope.filtros.dtFim) {
                    scope.filtros.dtFim = null;
                }
            }
        };

        scope.init();
    }
})();
