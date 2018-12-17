/**
 * Created by Basis Tecnologia on 24/04/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('AgrupamentoTaxonomiaService', ['$http', service]);

    function service($http){
        var baseUrl = '/gestaoriscos/api/agrupamentotaxonomias';

        var findByFilter = function(filtros){
            var endPoint = baseUrl + "?page=" + filtros.page + "&size=" + filtros.size;
            if(filtros.descricao != null){
                endPoint += "&descricao=" + filtros.descricao;
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

        var save = function(registro, taxonomia){
            if(taxonomia.id && taxonomia.descricao){
                switch(taxonomia.tipo.nome){
                    case "Evento":
                        registro.taxonomia.evento = taxonomia;
                        break;
                    case "Causa":
                        registro.taxonomia.causa = taxonomia;
                        break;
                    case "Consequência":
                        registro.taxonomia.consequencia = taxonomia;
                        break;
                    case "Controle":
                        registro.taxonomia.controle = taxonomia;
                        break;
                }
            }

            return $http.post(baseUrl, registro);
        };

        var getTipos = function(){
            return $http.get(baseUrl + "/tipos");
        };

        var getDescricaoBySearch = function(descricao){
            return $http.get(baseUrl + "/searchdescricao?descricao=" + descricao);
        };

        var getTaxonomiaBySearch = function(descricao, tipoNome){
            switch(tipoNome){
                case "Evento":
                    return $http.get(baseUrl + "/eventos?descricao=" + descricao);
                case "Causa":
                    return $http.get(baseUrl + "/causas?descricao=" + descricao);
                case "Consequência":
                    return $http.get(baseUrl + "/consequencias?descricao=" + descricao);
                case "Controle":
                    return $http.get(baseUrl + "/controles?descricao=" + descricao);
            }
        };

        var getUsuarioLogado = function(){
            return $http.get("gestaoriscos/api/usuarios/usuario-logado");
        };

        return {
            findByFilter: findByFilter,
            save: save,
            getTipos: getTipos,
            getDescricaoBySearch: getDescricaoBySearch,
            getTaxonomiaBySearch: getTaxonomiaBySearch,
            getUsuarioLogado: getUsuarioLogado
        };
    }
})();
