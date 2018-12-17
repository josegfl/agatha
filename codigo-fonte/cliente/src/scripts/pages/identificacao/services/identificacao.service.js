/**
 * Created by Basis Tecnologia on 16/08/2016.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('IdentificacaoService', ['$http', '$q', service]);

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

        var excluirEventoRisco = function(registroId){
            return $http.delete(baseUrl + "/eventosrisco/" + registroId);
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

        var findById = function(processoId){
            return $http.get(baseUrl + "/" + processoId);
        };

        return {
            findByFilter: findByFilter,
            excluirEventoRisco: excluirEventoRisco,
            getUsuarioLogado: getUsuarioLogado,
            getPermissao: getPermissao,
            findById: findById
        };
    }
})();
