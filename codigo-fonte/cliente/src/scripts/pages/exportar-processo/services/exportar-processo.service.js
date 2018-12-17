/**
 * Created by Basis Tecnologia on 10/06/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('ExportarProcessoService', ['$http', service]);

    function service($http){
        var baseUrl = '/gestaoriscos/api/processos';

        var findBy = function(registroId){
            return $http.get(baseUrl + "/" + registroId);
        };

        var findAllEventosRisco = function(registroId){
            return $http.get(baseUrl + "/eventosriscowithoutpage/" + registroId);
        };

        return {
            findBy: findBy,
            findAllEventosRisco: findAllEventosRisco
        };
    }
})();
