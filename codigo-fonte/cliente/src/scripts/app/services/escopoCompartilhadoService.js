/**
 * Created by Basis Tecnologia on 08/08/2016.
 */
'use strict';
(function(){

    function service(){
        var _objetos = {};

        var setObjetos = function(nomeObjeto, objeto){
            _objetos[nomeObjeto] = objeto;
        };

        var getObjeto = function(nomeObjeto){
            return _objetos[nomeObjeto];
        };

        var limparEscopo = function(){
            _objetos = {};
        };

        return {
            setObjetos: setObjetos,
            getObjeto: getObjeto,
            limparEscopo: limparEscopo
        }
    };

    angular
        .module('gestaoRiscosApp')
        .factory('escopoCompartilhadoService', [service]);

})();