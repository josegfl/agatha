/**
 * Created by Basis Tecnologia on 08/06/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('MeusDadosService', ['$http', '$q', service]);

    function service($http, $q){
        var baseUrl = '/gestaoriscos/api/permissaos';

        var getUsuarioLogado = function(){
            return $http.get("gestaoriscos/api/usuarios/usuario-logado");
        };

        var getPermissoesByCPF = function(cpf){
            return $http.get(baseUrl + "/permissoesbycpf?cpf=" + cpf);
        };

        return {
            getUsuarioLogado: getUsuarioLogado,
            getPermissoesByCPF: getPermissoesByCPF
        };
    }
})();
