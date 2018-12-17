/**
 * Created by Basis Tecnologia on 03/08/2016.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('GerenciarPermissoesService', ['$http', service]);

    function service($http){
        var baseUrl = '/gestaoriscos/api/permissaos';
        var usuarioUrl = '/gestaoriscos/api/usuarios';
        var orgaoUrl = '/gestaoriscos/api/siorg';

        var searchOrgaoByNome = function(nome){
            return $http.get(baseUrl + "/searchorgao?nome=" + nome);
        };

        var getOrgao = function(id){
            return $http.get(orgaoUrl + "/orgao/" + id);
        };

        var getUnidadePeloIdLimitadoPorIdCategoria = function(idOrgaoPai, listaIdCategorias){
            return $http.post(orgaoUrl + "/orgao/limitado-por-id-categoria", {idOrgaoPai: idOrgaoPai, listaIdCategorias: listaIdCategorias});
        };

        var getAllOrgao = function(){
            return $http.get(orgaoUrl + "/orgao/2981");
        };

        var getUsuariosByNome = function(nome){
            return $http.get(baseUrl + "/usuarios?nome=" + nome);
        };

        var findAllPerfil = function(){
            return $http.get(baseUrl + "/perfils");
        };

        var findByFilter = function(filtros){
            var endPoint = baseUrl + "?page=" + filtros.page + "&size=" + filtros.size;
            if(filtros.usuario != null){
                endPoint += "&usuario=" + filtros.usuario;
            }
            if(filtros.perfil != null){
                endPoint += "&perfil=" + filtros.perfil;
            }
            if(filtros.orgao != null){
                endPoint += "&orgao=" + filtros.orgao;
            }
            return $http.get(endPoint).success(function(data, status, headers){
                if(status == 200){
                    data.content = data;
                    data.totalElements = headers("x-total-count")
                }
            });
        };

        var findBy = function(registroId){
            return $http.get(usuarioUrl + "/findById/" + registroId);
        };

        var save = function(usuario){
            if (!usuario.id) {
                return $http.put(usuarioUrl, usuario);
            }
            return update(usuario);
        };

        var update = function(registro){
            return $http.post(usuarioUrl, registro);
        };

        var excluir = function(registroId){
            return $http.delete(baseUrl + "/" + registroId);
        };

        return {
            searchOrgaoByNome: searchOrgaoByNome,
            getOrgao: getOrgao,
            getAllOrgao: getAllOrgao,
            getUnidadePeloIdLimitadoPorIdCategoria: getUnidadePeloIdLimitadoPorIdCategoria,
            getUsuariosByNome: getUsuariosByNome,
            findAllPerfil: findAllPerfil,
            findByFilter: findByFilter,
            findBy: findBy,
            save: save,
            update: update,
            excluir: excluir
        };
    }
})();
