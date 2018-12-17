/**
 * Created by Basis Tecnologia on 10/06/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('ExportarProcessoController', ['$timeout', '$stateParams', '$filter', 'ExportarProcessoService', controller]);

    function controller($timeout, $stateParams, $filter, service) {
        var scope = this;

        scope.registro = {};

        scope.init = function () {
            scope.findBy( function () {});
            $timeout(function () {
                scope.imprimir();
            }, 2500)
        };

        scope.findBy = function (callback) {
            var processoId = $stateParams.id;
            service.findBy(processoId).then(function (objectReturn) {
                scope.registro = objectReturn.data;

                document.title = "Agatha - " + scope.registro.analise.secretaria.nome + "_" + $filter('date')(new Date(), 'dd-MM-yyyy');

                service.findAllEventosRisco(processoId).then(function (objectReturn) {
                    scope.registro.identificacao.eventos = objectReturn.data;
                    if (callback) {
                        callback();
                    }
                });
            });
        };

        scope.imprimir = function(){
           window.print();
           window.close();
        };

        scope.init();
    }
})();
