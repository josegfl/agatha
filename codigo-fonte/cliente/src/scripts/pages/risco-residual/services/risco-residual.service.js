/**
 * Created by Basis Tecnologia on 10/04/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('RiscoResidualService', ['$http', service]);

    function service($http){
        var baseUrl = '/gestaoriscos/api/processos';

        var getCalculadora = function(processoId){
            return $http.get(baseUrl + "/calculadoraresidual/" + processoId);
        };

        var getEventosRisco = function(processoId){
            return $http.get(baseUrl + "/eventosriscowithoutpage/" + processoId);
        };

        var salvarCalculo = function(eventos){
            var objectRequest = {};
            objectRequest.eventos = angular.copy(eventos);

            return $http.post(baseUrl + "/salvarcalculo", objectRequest);
        };

        var getIgnorarRiscoInerente = function(processoId){
            return $http.get(baseUrl + "/avaliacao/ignorar/" + processoId);
        };

        var alterarStatus = function(calculadora){
            var objectRequest = angular.copy(calculadora);

            return $http.post(baseUrl + "/impacto-calculadora/alterar-status", objectRequest);
        };

        return {
            getCalculadora: getCalculadora,
            getEventosRisco: getEventosRisco,
            salvarCalculo: salvarCalculo,
            getIgnorarRiscoInerente: getIgnorarRiscoInerente,
            alterarStatus: alterarStatus
        };
    }
})();
