/**
 * Created by Basis Tecnologia on 25/04/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('PlanoControleService', ['$http', service]);

    function service($http){
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

        var findBy = function(registroId){
            return $http.get(baseUrl + "/" + registroId);
        };

        var findByEventoRisco = function(registroId){
            return $http.get(baseUrl + "/eventosrisco/" + registroId);
        };

        var create = function(registro){
            return $http.post(baseUrl + "/plano-controle", registro);
        };

        var update = function(registro){
            return $http.put(baseUrl + "/plano-controle/" + registro.id, registro);
        };

        var excluir = function(registroId){
            return $http.delete(baseUrl + "/plano-controle/" + registroId);
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

        var findAllTiposControle = function(){
            return $http.get(baseUrl + "/tiposcontrole");
        };

        var findAllObjetivosControle = function(){
            return $http.get(baseUrl + "/objetivoscontrole");
        };

        var getControlesBySearch = function(descricao, usuario){
            return $http.get(baseUrl + "/controles?descricao=" + descricao + "&cpf=" + usuario.cpf);
        };

        return {
            findByFilter: findByFilter,
            findBy: findBy,
            findByEventoRisco: findByEventoRisco,
            create: create,
            update: update,
            excluir: excluir,
            getUsuarioLogado: getUsuarioLogado,
            getPermissao: getPermissao,
            findAllTiposControle: findAllTiposControle,
            findAllObjetivosControle: findAllObjetivosControle,
            getControlesBySearch: getControlesBySearch

        };
    }
})();
