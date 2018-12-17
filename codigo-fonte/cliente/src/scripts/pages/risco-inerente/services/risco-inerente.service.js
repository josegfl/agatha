/**
 * Created by Basis Tecnologia on 10/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').factory('RiscoInerenteService', ['$http', service]);

    function service($http) {
        var baseUrl = '/gestaoriscos/api/processos';

        var getCalculadora = function (processoId) {
            return $http.get(baseUrl + "/calculadorainerente/" + processoId);
        };

        var getCalculadoraResidual = function (processoId) {
            return $http.get(baseUrl + "/calculadoraresidual/" + processoId);
        };

        var getEventosRisco = function (processoId) {
            return $http.get(baseUrl + "/eventosriscowithoutpage/" + processoId);
        };

        var salvarCalculo = function (eventos) {
            var objectRequest = {};
            objectRequest.eventos = angular.copy(eventos);

            return $http.post(baseUrl + "/salvarcalculo", objectRequest);
        };

        var alterarStatus = function (processoId, calculadora) {
            var objectRequest = angular.copy(calculadora);

            return $http.post(baseUrl + "/impacto-calculadora/alterar-status/" + processoId, objectRequest);
        };

        return {
            getCalculadora: getCalculadora,
            getCalculadoraResidual: getCalculadoraResidual,
            getEventosRisco: getEventosRisco,
            salvarCalculo: salvarCalculo,
            alterarStatus: alterarStatus
        };
    }
})();
