/**
 * Created by Basis Tecnologia on 08/06/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').controller('MeusDadosController', ['MeusDadosService', controller]);

    function controller(service){
        var scope = this;

        scope.init = function(){
            service.getUsuarioLogado().then(function(objectResponse){

                service.getPermissoesByCPF(objectResponse.data.cpf).then(function(objectResponse){
                    scope._registros = objectResponse.data;

                    if(scope._registros && scope._registros.length) {
                        scope.registro = scope._registros[0];
                    }
                });
            });
        };

        scope.init();
    }
})();
