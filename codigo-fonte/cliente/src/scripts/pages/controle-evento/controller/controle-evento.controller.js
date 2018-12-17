/**
 * Created by Basis Tecnologia on 10/04/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').controller('ControleEventoController', ['$scope', '$state', '$stateParams', '$mdToast', '$mdDialog', '$q', 'ControleEventoService', controller]);

    function controller($scope, $state, $stateParams, $mdToast, $mdDialog, $q, service){
        var scope = this;

        scope.registro = {};

        scope.processoId = $stateParams.id;

        scope.init = function(){
            scope._eventoRisco = {};
            scope.getUsuarioLogado(function(){
                scope.findProcessoById();
                scope.findBy($stateParams.eventoRiscoId);
                scope.findAllDesenhos();
                scope.findAllOperacoes();
            });
        };

        scope.getUsuarioLogado = function(callback){
            service.getUsuarioLogado().then(function(retorno){
                scope.usuario = retorno.data;
                if(callback){
                    callback();
                }
            });
        };

        scope.findProcessoById = function(){
            scope.processo = {};
            service.findProcessoById(scope.processoId).then(function(objectResponse){
                scope.processo = objectResponse.data;

            })
        };

        scope.findAllDesenhos = function(callback){
            service.findAllDesenhos().then(function(retorno){
                scope._desenhos = retorno.data;
                if(callback){
                    callback();
                }
            });
        };

        scope.findAllOperacoes = function(callback){
            service.findAllOperacoes().then(function(retorno){
                scope._operacoes = retorno.data;
                if(callback){
                    callback();
                }
            });
        };

        scope.alterarControleEvento = function(item){
            item.$update = true;
            scope.registro = angular.copy(item);

            for(var i = 0; i < scope._desenhos.length; i++){
                if(scope._desenhos[i].id == scope.registro.desenho.id){
                    scope.registro.desenho = scope._desenhos[i];
                }
            }

            for(var i = 0; i < scope._operacoes.length; i++){
                if(scope._operacoes[i].id == scope.registro.operacao.id){
                    scope.registro.operacao = scope._operacoes[i];
                }
            }

        };

        scope.handleControleChange = function(ev){

            if(scope.registro.controle.descricao && scope.registro.desenho && scope.registro.operacao ){
                if(scope.registro.controle.id){
                    scope.registro.cpf = scope.usuario.cpf;
                    service.verificarControle(scope.registro).then(function(objectReturn){
                        if(objectReturn.data && objectReturn.data.nome == "Núcleo"){
                            var confirm = $mdDialog.confirm()
                                .title("Atenção!")
                                .textContent("Exitem outros processos que utilizam essa descrição de controle")
                                .ok("Alterar")
                                .cancel("Cancelar");

                            $mdDialog.show(confirm).then(function(){
                                service.atualizarControle(scope.registro.controle).then(function(objectReturn){
                                    scope.findBy($stateParams.eventoRiscoId);
                                });
                            }, function(){
                                scope.findBy($stateParams.eventoRiscoId);
                            });
                        }else if(objectReturn.data && objectReturn.data.nome == "Unidade"){
                            $mdDialog.show({
                                controller: function(){
                                    return scope;
                                },
                                controllerAs: 'ctrl',
                                templateUrl: 'scripts/pages/evento-risco/view/controle-info.tmpl.html',
                                parent: angular.element(document.body),
                                targetEvent: ev,
                                clickOutsideToClose: true,
                                fullscreen: true
                            });
                        }else{
                            scope.findBy($stateParams.eventoRiscoId);
                        }

                    });
                }else{
                    for(var i = 0; i < scope._eventoRisco.controleEventos.length; i++){
                        if(scope._eventoRisco.controleEventos[i].$update){
                            scope._eventoRisco.controleEventos[i] = angular.copy(scope.registro);
                            delete scope._eventoRisco.controleEventos[i].$update;
                            scope.registro = {};
                        }
                    }
                }
            }else{
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Campo(s) obrigatório(s) não preenchido(s)")
                        .hideDelay(3000)
                );
            }
        };

        scope.cancelarControleUpdate = function(){
            for(var i = 0; i < scope._eventoRisco.controleEventos.length; i++){
                if(scope._eventoRisco.controleEventos[i].$update){
                    scope._eventoRisco.controleEventos[i].$update = false;
                    scope.registro = {};
                }
            }
        };

        scope.atualizarTodosControles = function(){
            $mdDialog.cancel();
            service.atualizarControle(scope.registro.controle).then(function(objectReturn){
                scope.findBy($stateParams.eventoRiscoId);
            });
        };

        scope.substituirControle = function(){
            $mdDialog.cancel();
            service.substituirControle(scope.registro).then(function(objectReturn){
                scope.findBy($stateParams.eventoRiscoId);
            });
        };

        scope.cancel = function(){
            scope.findBy($stateParams.eventoRiscoId);
            $mdDialog.cancel();
        };

        scope.getControlesBySearch = function(descricao){
            if(descricao && descricao.length >= 3){
                service.getControlesBySearch(descricao, scope.usuario).then(function(objectReturn){
                    scope._controles = objectReturn.data;
                    return objectReturn.data;
                });
            }else{
                scope._controles = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.adicionarControleEvento = function(){

            if(scope.registro.controle.descricao && scope.registro.desenho && scope.registro.operacao ){
                scope._eventoRisco.controleEventos.push(scope.registro);
                scope.persistir(scope._eventoRisco);
                scope.registro = {};
                scope.incluirControle = false;


            }else{
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Campo(s) obrigatório(s) não preenchido(s)")
                        .hideDelay(3000)
                );
            }
        };

        scope.excluirControleEvento = function(item){

            var confirm = $mdDialog.confirm()
                .title("Atenção!")
                .textContent("Deseja excluir o registro?")
                .ok("OK")
                .cancel("Cancelar");

            $mdDialog.show(confirm).then(function(){
                scope.persistir(scope._eventoRisco);
                for(var i = 0; i < scope._eventoRisco.controleEventos.length; i++){
                    if(angular.equals(scope._eventoRisco.controleEventos[i], item)){
                        scope._eventoRisco.controleEventos.splice(i, 1);
                    }
                }
            });
        };

        scope.findBy = function(registroId){
            scope.registro = {};
            scope._eventoRisco = {};
            service.findBy(registroId).then(function(objectResponse){
                scope._eventoRisco = objectResponse.data;
            });
        };

        scope.persistir = function(registro){
            registro.cpf = scope.usuario.cpf;
            registro.identificacao = {
                processo: {
                    id: $stateParams.id
                }
            };
            service.update(registro, scope.usuario).then(function(){
                // $state.go("processo.avaliacao.controleEvento.alterar", {id: scope.processoId})
            });
        };

        scope.salvarTaxonomiaControle = function(item){
            if(item.controle && item.controle.descricao){
                service.salvarTaxonomiaControle(item.controle, scope.usuario).then(function(objectReturn){
                    item.controle = objectReturn.data.controle;
                });
            }
        };

        scope.init();
    }
})();
