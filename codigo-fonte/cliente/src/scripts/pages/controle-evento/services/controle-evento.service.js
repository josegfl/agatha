/**
 * Created by Basis Tecnologia on 10/04/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('ControleEventoService', ['$http', '$q', service]);

    function service($http, $q){
        var baseUrl = '/gestaoriscos/api/processos';

        var getControlesBySearch = function(descricao, usuario){
            return $http.get(baseUrl + "/controles?descricao=" + descricao + "&cpf=" + usuario.cpf);
        };

        var findAllDesenhos = function(){
            return $http.get(baseUrl + "/desenhos");
        };

        var findAllOperacoes = function(){
            return $http.get(baseUrl + "/operacoes");
        };

        var findBy = function(registroId){
            return $http.get(baseUrl + "/eventosrisco/" + registroId);
        };

        var update = function(registro){
            return $http.put(baseUrl + "/eventosrisco", registro);
        };

        var getUsuarioLogado = function(){
            return $http.get("gestaoriscos/api/usuarios/usuario-logado");

            // var data = {};
            // data.data = {cpf: "61914509153"};
            // var deferred = $q.defer();
            // deferred.resolve(data);
            // return deferred.promise;
        };

        var verificarControle = function(registro){
            return $http.post(baseUrl + "/eventosrisco/controle/verificar", registro);
        };

        var atualizarControle = function(registro){
            return $http.post(baseUrl + "/eventosrisco/controle/atualizar", registro);
        };

        var substituirControle = function(registro){
            return $http.post(baseUrl + "/eventosrisco/controle/substituir", registro);
        };

        var salvarTaxonomiaControle = function(registro, usuario){
            var objectRequest = angular.copy(registro);
            objectRequest.cpf = usuario.cpf;

            return $http.post(baseUrl + "/taxonomia/controle", objectRequest);
        };

        var findProcessoById = function(registroId){
            return $http.get(baseUrl + "/" + registroId);
        };

        return {
            getControlesBySearch: getControlesBySearch,
            findAllDesenhos: findAllDesenhos,
            findAllOperacoes: findAllOperacoes,
            findBy: findBy,
            update: update,
            getUsuarioLogado: getUsuarioLogado,
            verificarControle: verificarControle,
            atualizarControle: atualizarControle,
            substituirControle: substituirControle,
            salvarTaxonomiaControle: salvarTaxonomiaControle,
            findProcessoById: findProcessoById
        };
    }
})();
