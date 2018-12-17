/**
 * Created by Basis Tecnologia on 24/04/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('TaxonomiaService', ['$http', '$q', service]);

    function service($http, $q){
        var baseUrl = '/gestaoriscos/api/taxonomias';

        var findByFilter = function(filtros){
            var endPoint = baseUrl + "?page=" + filtros.page + "&size=" + filtros.size;
            if(filtros.descricao != null){
                endPoint += "&descricao=" + filtros.descricao;
            }
            if(filtros.orgao != null){
                endPoint += "&orgao=" + filtros.orgao;
            }
            if(filtros.tipoId != null){
                endPoint += "&tipoId=" + filtros.tipoId;
            }
            if(filtros.dtInicio != null && filtros.dtFim != null){
                endPoint += "&dtInicio=" + filtros.dtInicio.getTime();
                endPoint += "&dtFim=" + filtros.dtFim.getTime();
            }
            return $http.get(endPoint).success(function(data, status, headers){
                if(status == 200){
                    data.content = data;
                    data.totalElements = headers("x-total-count")
                }
            });
        };

        var aprovar = function(registros){
            var objectRequest = {};
            objectRequest.taxonomias = angular.copy(registros);

            return $http.post(baseUrl + "/aprovar", objectRequest);
        };

        var reprovar = function(registros, justificativa){
            var objectRequest = {};
            objectRequest.taxonomias = angular.copy(registros);
            objectRequest.justificativa = justificativa;

            return $http.post(baseUrl + "/reprovar", objectRequest);
        };

        var getTipos = function(){
            return $http.get(baseUrl + "/tipos");
        };

        var getDescricaoBySearch = function(descricao){
            return $http.get(baseUrl + "/searchdescricao?descricao=" + descricao);
        };

        var searchOrgaoByNome = function(nome){
            return $http.get(baseUrl + "/searchorgao?nome=" + nome);
        };

        var getSecretariaByPerfil = function(cpf){
            return $http.get(baseUrl + "/secretariabyperfil?cpf=" + cpf);
        };

        var getUsuarioLogado = function(){
            return $http.get("gestaoriscos/api/usuarios/usuario-logado");

            // var data = {};
            // data.data = {cpf: "61914509153"};
            // var deferred = $q.defer();
            // deferred.resolve(data);
            // return deferred.promise;
        };

        return {
            findByFilter: findByFilter,
            aprovar: aprovar,
            reprovar: reprovar,
            getTipos: getTipos,
            getDescricaoBySearch: getDescricaoBySearch,
            searchOrgaoByNome: searchOrgaoByNome,
            getSecretariaByPerfil: getSecretariaByPerfil,
            getUsuarioLogado: getUsuarioLogado
        };
    }
})();
