/**
 * Created by Basis Tecnologia on 25/04/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('RespostaService', ['$http', '$q', service]);

    function service($http, $q){
        var baseUrl = '/gestaoriscos/api/processos';

        var findByFilter = function(filtros, processoId){
            var endPoint = baseUrl + "/eventosrisco?page=" + filtros.page + "&size=" + filtros.size + "&processoId=" + processoId;
            return $http.get(endPoint).success(function(data, status, headers){
                if(status == 200){
                    data.content = data;
                    data.totalElements = headers("x-total-count")
                }
            });
        };

        var findAllTiposResposta = function(){
            return $http.get(baseUrl + "/tiposresposta");
        };

        var findBy = function(registroId){
            return $http.get(baseUrl + "/" + registroId);
        };

        var findByEventoRisco = function(registroId){
            return $http.get(baseUrl + "/eventosrisco/" + registroId);
        };

        var update = function(eventoRiscoId, registro){
            return $http.put(baseUrl + "/resposta-risco/" + eventoRiscoId, registro);
        };

        var getUsuarioLogado = function(){
            return $http.get("gestaoriscos/api/usuarios/usuario-logado");

            // var data = {};
            // data.data = {cpf: "61914509153"};
            // var deferred = $q.defer();
            // deferred.resolve(data);
            // return deferred.promise;
        };

        var getPermissao = function(cpf){
            return $http.get(baseUrl + "/permissao?cpf=" + cpf);
        };

        return {
            findByFilter: findByFilter,
            findAllTiposResposta: findAllTiposResposta,
            findBy: findBy,
            findByEventoRisco: findByEventoRisco,
            update: update,
            getUsuarioLogado: getUsuarioLogado,
            getPermissao: getPermissao

        };
    }
})();
