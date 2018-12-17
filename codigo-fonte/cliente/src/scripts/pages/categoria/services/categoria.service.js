/**
 * Created by Basis Tecnologia on 03/08/2016.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('CategoriaService', ['$http', service]);

    function service($http){
        var baseUrl = '/gestaoriscos/api/categorias';

        var findByFilter = function(filtros, query){
            var endPoint = baseUrl + "?page=" + filtros.page + "&size=" + filtros.size + "&descricao=";
            if(query.descricao != null){
                endPoint += query.descricao;
            }
            if(query.status != null){
                endPoint += "&status=" + query.status;
            }
            return $http.get(endPoint).success(function(data, status, headers){
                if(status == 200){
                    data.content = data;
                    data.totalElements = headers("x-total-count")
                }
            });
        };

        var getDescricaoBySearch = function(descricao){
            return $http.get(baseUrl + "/searchdescricao?descricao=" + descricao);
        };

        var findBy = function(registroId){
            return $http.get(baseUrl + "/" + registroId);
        };

        var create = function(registro){
            return $http.post(baseUrl, registro);
        };

        var update = function(registro){
            return $http.put(baseUrl, registro);
        };

        var alterarStatus = function(registroId){
            return $http.post(baseUrl, registroId);
        };

        var excluir = function(registroId){
            return $http.delete(baseUrl + "/" + registroId);
        };

        var findAllNaturezas = function(){
            return $http.get(baseUrl + "/naturezas");
        };

        return {
            findAllNaturezas: findAllNaturezas,
            findByFilter: findByFilter,
            getDescricaoBySearch: getDescricaoBySearch,
            findBy: findBy,
            create: create,
            update: update,
            alterarStatus: alterarStatus,
            excluir: excluir
        };
    }
})();
