/**
 * Created by Basis Tecnologia on 16/08/2016.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('MapaRiscoService', ['$http', '$q', service]);

    function service($http, $q){
        var baseUrl = '/gestaoriscos/api/processos';

        var findBy = function(registroId){
            return $http.get(baseUrl + "/" + registroId);
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
            findBy: findBy,
            getUsuarioLogado: getUsuarioLogado,
            getPermissao: getPermissao
        };
    }
})();
