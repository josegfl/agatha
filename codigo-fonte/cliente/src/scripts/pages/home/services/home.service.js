/**
 * Created by Basis Tecnologia on 16/08/2016.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').factory('HomeService', ['$http', '$q', service]);

    function service($http, $q) {
        var baseUrl = '/gestaoriscos/api/processos';
        var orgaoUrl = '/gestaoriscos/api/siorg';

        var findByFilter = function (filtros) {
            var endPoint = baseUrl + "?page=" + filtros.page + "&size=" + filtros.size;
            if (filtros.descricao != null) {
                endPoint += "&descricao=" + filtros.descricao;
            }
            if (filtros.orgao != null) {
                endPoint += "&orgao=" + filtros.orgao;
            }
            if (filtros.statusId != null) {
                endPoint += "&statusId=" + filtros.statusId;
            }
            if (filtros.cpf != null) {
                endPoint += "&cpf=" + filtros.cpf;
            }
            if (filtros.dtInicio != "" && filtros.dtFim != "") {
                endPoint += "&dtInicio=" + filtros.dtInicio.getTime();
                endPoint += "&dtFim=" + filtros.dtFim.getTime();
            }
            return $http.get(endPoint).success(function (data, status, headers) {
                if (status == 200) {
                    data.content = angular.copy(data);
                    data.totalElements = headers("x-total-count")
                }
            });
        };

        var getUsuarioLogado = function () {
            return $http.get("gestaoriscos/api/usuarios/usuario-logado");

            // var data = {};
            // data.data = {cpf: "61914509153"};
            // var deferred = $q.defer();
            // deferred.resolve(data);
            // return deferred.promise;
        };

        var getPermissao = function (cpf) {
            return $http.get(baseUrl + "/permissao?cpf=" + cpf);
        };

        var excluirProcesso = function (registroId) {
            return $http.delete(baseUrl + "/" + registroId);
        };

        var searchOrgaoByNome = function (nome) {
            return $http.get(baseUrl + "/searchorgao?nome=" + nome);
        };

        var getDescricaoBySearch = function (descricao) {
            return $http.get(baseUrl + "/searchdescricao?descricao=" + descricao);
        };

        var getStatus = function () {
            return $http.get(baseUrl + "/status");
        };

        var getUnidadePeloIdLimitadoPorIdCategoria = function(idOrgaoPai, listaIdCategorias){
            return $http.post(orgaoUrl + "/orgao/limitado-por-id-categoria", {idOrgaoPai: idOrgaoPai, listaIdCategorias: listaIdCategorias});
        };

        return {
            findByFilter: findByFilter,
            getUsuarioLogado: getUsuarioLogado,
            getPermissao: getPermissao,
            excluirProcesso: excluirProcesso,
            searchOrgaoByNome: searchOrgaoByNome,
            getDescricaoBySearch: getDescricaoBySearch,
            getStatus: getStatus,
            getUnidadePeloIdLimitadoPorIdCategoria: getUnidadePeloIdLimitadoPorIdCategoria
        };
    }
})();
