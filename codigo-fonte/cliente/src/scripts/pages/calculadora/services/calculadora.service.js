/**
 * Created by Basis Tecnologia on 10/04/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('CalculadoraService', ['$http', service]);

    function service($http){
        var baseUrl = '/gestaoriscos/api/calculadoras';

        var findAll = function(){
            return $http.get(baseUrl);
        };

        var findAllTiposCalculadora = function(){
            return $http.get(baseUrl + "/tipos");
        };

        var update = function(registro){
            return $http.put(baseUrl, registro);
        };

        return {
            findAll: findAll,
            findAllTiposCalculadora: findAllTiposCalculadora,
            update: update
        };
    }
})();
