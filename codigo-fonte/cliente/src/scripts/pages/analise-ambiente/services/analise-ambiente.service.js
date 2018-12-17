/**
 * Created by Basis Tecnologia on 16/08/2016.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('AnaliseAmbienteService', ['$http', '$q', service]);

    function service($http, $q){
        var baseUrl = '/gestaoriscos/api/processos';

        var findBy = function(registroId){
            return $http.get(baseUrl + "/" + registroId);
        };

        var create = function(registro){
            return $http.post(baseUrl, registro);
        };

        var update = function(registro){
            return $http.put(baseUrl, registro);
        };

        var getAnalistas = function(){
            return $http.get(baseUrl + "/analistas");
        };

        var getGestores = function(){
            return $http.get(baseUrl + "/gestores");
        };

        var getTiposSwot = function(){
            return $http.get(baseUrl + "/tiposmatriz");
        };

        var getOrgaosByCPF = function(cpf){
            return $http.get(baseUrl + "/orgaosbycpf?cpf=" + cpf);
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

        var getMacroprocessos = function(){
            return $http.get(baseUrl + "/macroprocesso");
        };

        var uploadFile = function(files){
            var names = [];
            var formData = new FormData();

            angular.forEach(files, function(obj){
                if(!angular.isUndefined(obj)){
                    formData.append('files', obj.lfFile);

                    names.push(obj.lfFileName);
                }
            });

            formData.append('names', new Blob([JSON.stringify(names)], {
                type: "application/json"
            }));

            return $http.post(baseUrl + "/upload", formData, {
                headers: {'Content-Type': undefined}
            })
        };

        return {
            findBy: findBy,
            create: create,
            update: update,
            getAnalistas: getAnalistas,
            getGestores: getGestores,
            getTiposSwot: getTiposSwot,
            getOrgaosByCPF: getOrgaosByCPF,
            getUsuarioLogado: getUsuarioLogado,
            getPermissao: getPermissao,
            getMacroprocessos: getMacroprocessos,
            uploadFile: uploadFile
        };
    }
})();
