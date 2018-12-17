'use strict';
/**
 * Interceptor para recuperar as alertas enviadas pelo servidor em cabeçalhos http
 */
angular.module('gestaoRiscosApp')
    .factory('alertInterceptor', function ($q) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-gestaoRiscosApp-alert');
                if (angular.isString(alertKey)) {
                    // Tratar alertas
                }
                return response;
            }
        };
    });