/**
 * Created by Basis Tecnologia on 24/04/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').controller('AgrupamentoTaxonomiaController', ['$scope', '$localStorage', '$timeout', '$state', '$mdToast', '$q', 'AgrupamentoTaxonomiaService', controller]);

    function controller($scope, $localStorage, $timeout, $state, $mdToast, $q, service){
        var scope = this;

        scope.init = function(){
            if($state.current.name.indexOf("listar") != -1){
                scope.registroInicial = 1;
                scope.tamanhoLimite = 20;
                scope.totalItems = 0;
                scope.findAllTipos();
                scope.limparFiltros();
            }else{
                if($localStorage.taxonomias){
                    service.getUsuarioLogado().then(function(objectReturn){
                        scope.taxonomia = {};
                        scope.registro = {};
                        scope.registro.cpf = objectReturn.data.cpf;
                        scope.registro.taxonomias = angular.copy($localStorage.taxonomias);
                    });
                }else{
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent("Nenhuma taxonomia encontrada!")
                            .hideDelay(3000)
                    );
                    $state.go("taxonomia.listar")
                }
            }
        };

        scope.findAllTipos = function(){
            service.getTipos().then(function(objectReturn){
                scope._tipos = objectReturn.data;
            });
        };

        scope.filtrar = function(){
            scope.filtros.page = scope.registroInicial - 1;
            scope.filtros.size = scope.tamanhoLimite;

            service.findByFilter(scope.filtros).then(function(retorno){
                scope._registros = retorno.data.content || [];
                scope.totalItems = retorno.data.totalElements;

                if(!scope._registros.length){
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent("Dados não encontrados")
                            .hideDelay(3000)
                    );
                }
            });
        };

        scope.limparFiltros = function(){
            scope.filtros = {};
            scope.filtrar();
        };

        scope.getDescricaoBySearch = function(descricao){
            if(descricao && descricao.length >= 3){
                service.getDescricaoBySearch(descricao).then(function(objectReturn){
                    scope.descricoes = objectReturn.data;
                    return objectReturn.data;
                });
            }else{
                scope.descricoes = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.imprimir = function(){
            getURLGrafico(function(){
                $timeout(function(){
                    pdfMake.createPdf(geraJson()).download("Agrupamento de Taxonomias.pdf");
                });
            });
        };

        scope.handleDtInicioChange = function(){
            if(scope.filtros.dtInicio && scope.filtros.dtFim){
                if(scope.filtros.dtInicio > scope.filtros.dtFim){
                    scope.filtros.dtFim = null;
                }
            }
        };

        scope.getTaxonomiaBySearch = function(descricao){
            scope.taxonomia.id = null;
            if(descricao && descricao.length >= 3){
                service.getTaxonomiaBySearch(descricao, scope.registro.taxonomias[0].tipo.nome).then(function(objectReturn){
                    scope._taxonomias = objectReturn.data;
                    return objectReturn.data;
                });
            }else{
                scope._taxonomias = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.excluirTaxonomia = function(item){
            for(var i = 0; i < scope.registro.taxonomias.length; i++){
                if(angular.equals(scope.registro.taxonomias[i], item)){
                    scope.registro.taxonomias.splice(i, 1);
                }
            }

            if(!scope.registro.taxonomias || !scope.registro.taxonomias.length){
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Nenhuma taxonomia encontrada!")
                        .hideDelay(3000)
                );
                $state.go("taxonomia.listar");
            }
        };

        scope.persistir = function(registro){
            if($scope.formRegistro.$valid){
                scope.taxonomia.tipo = scope.registro.taxonomias[0].tipo;
                service.save(registro, scope.taxonomia).then(function(){
                    $state.go("taxonomia.listar")
                });
            }else{
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Campo(s) obrigatório(s) não preenchido(s)")
                        .hideDelay(3000)
                );
            }
        };

        scope.init();

        function getURLGrafico(callback){
            html2canvas(document.getElementById('table-print'), {
                onrendered: function(canvas){
                    scope.image = canvas.toDataURL('image/png');
                    callback();
                }
            });
        }

        function geraJson(){
            return {
                content: [
                    {
                        text: "Agrupamento de Taxonomias", alignment: "center", margin: [0, 10, 0, 10], fontSize: 14, bold: true
                    },
                    {
                        image: scope.image,
                        width: 550
                    }
                ],
                styles: {
                    fs9: {
                        fontSize: 9
                    },
                    bold: {
                        bold: true
                    },
                    header: {
                        alignment: "right"
                    },
                    table: {
                        margin: [0, 0, 0, 10],
                        fontSize: 7
                    },
                    tableHeader: {
                        bold: true,
                        fontSize: 8,
                        color: 'black'
                    }
                },
                pageSize: 'A4',
                pageMargins: [20, 20]
            };
        }
    }
})();
