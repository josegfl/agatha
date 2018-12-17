/**
 * Created by Basis Tecnologia on 03/08/2016.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('EventoRiscoService', ['$http', '$q', service]);

    function service($http, $q){
        var baseUrl = '/gestaoriscos/api/processos';

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

        var getEventoBySearch = function(descricao, usuario){
            return $http.get(baseUrl + "/eventos?descricao=" + descricao + "&cpf=" + usuario.cpf);
        };

        var getCausasBySearch = function(descricao, usuario){
            return $http.get(baseUrl + "/causas?descricao=" + descricao + "&cpf=" + usuario.cpf);
        };

        var getConsequenciasBySearch = function(descricao, usuario){
            return $http.get(baseUrl + "/consequencias?descricao=" + descricao + "&cpf=" + usuario.cpf);
        };

        var findAllCategorias = function(){
            return $http.get(baseUrl + "/categorias");
        };

        var findAllNaturezas = function(){
            return $http.get(baseUrl + "/naturezas");
        };

        var findBy = function(registroId){
            return $http.get(baseUrl + "/eventosrisco/" + registroId);
        };

        var findProcessoById = function(registroId){
            return $http.get(baseUrl + "/" + registroId);
        };

        var create = function(registro){
            return $http.post(baseUrl + "/eventosrisco", registro);
        };

        var update = function(registro){
            return $http.put(baseUrl + "/eventosrisco", registro);
        };

        var getUsuarioLogado = function(){
            return $http.get("gestaoriscos/api/usuarios/usuario-logado");

            // var data = {};
            // data.data = {cpf: "61914509153"};
            // var deferred = $q.defer();
            // deferred.resolve(data);
            // return deferred.promise;
        };

        var verificarEvento = function(registro){
            return $http.post(baseUrl + "/eventosrisco/evento/verificar", registro);
        };

        var atualizarEvento = function(registro){
            return $http.post(baseUrl + "/eventosrisco/evento/atualizar", registro);
        };

        var substituirEvento = function(registro){
            return $http.post(baseUrl + "/eventosrisco/evento/substituir", registro);
        };

        var verificarCausa = function(registro){
            return $http.post(baseUrl + "/eventosrisco/causa/verificar", registro);
        };

        var atualizarCausa = function(registro){
            return $http.post(baseUrl + "/eventosrisco/causa/atualizar", registro);
        };

        var substituirCausa = function(registro){
            return $http.post(baseUrl + "/eventosrisco/causa/substituir", registro);
        };

        var verificarConsequencia = function(registro){
            return $http.post(baseUrl + "/eventosrisco/consequencia/verificar", registro);
        };

        var atualizarConsequencia = function(registro){
            return $http.post(baseUrl + "/eventosrisco/consequencia/atualizar", registro);
        };

        var substituirConsequencia = function(registro){
            return $http.post(baseUrl + "/eventosrisco/consequencia/substituir", registro);
        };

        var salvarTaxonomiaEvento = function(registro, usuario){
            var objectRequest = angular.copy(registro);
            objectRequest.cpf = usuario.cpf;

            return $http.post(baseUrl + "/taxonomia/evento", objectRequest);
        };

        var salvarTaxonomiaCausa = function(registro, usuario){
            var objectRequest = angular.copy(registro);
            objectRequest.cpf = usuario.cpf;

            return $http.post(baseUrl + "/taxonomia/causa", objectRequest);
        };

        var salvarTaxonomiaConsequencia = function(registro, usuario){
            var objectRequest = angular.copy(registro);
            objectRequest.cpf = usuario.cpf;

            return $http.post(baseUrl + "/taxonomia/consequencia", objectRequest);
        };

        return {
            findByFilter: findByFilter,
            getEventoBySearch: getEventoBySearch,
            getCausasBySearch: getCausasBySearch,
            getConsequenciasBySearch: getConsequenciasBySearch,
            findAllCategorias: findAllCategorias,
            findAllNaturezas: findAllNaturezas,
            findBy: findBy,
            findProcessoById: findProcessoById,
            create: create,
            update: update,
            getUsuarioLogado: getUsuarioLogado,
            verificarEvento: verificarEvento,
            atualizarEvento: atualizarEvento,
            substituirEvento: substituirEvento,
            verificarCausa: verificarCausa,
            atualizarCausa: atualizarCausa,
            substituirCausa: substituirCausa,
            verificarConsequencia: verificarConsequencia,
            atualizarConsequencia: atualizarConsequencia,
            substituirConsequencia: substituirConsequencia,
            salvarTaxonomiaEvento: salvarTaxonomiaEvento,
            salvarTaxonomiaCausa: salvarTaxonomiaCausa,
            salvarTaxonomiaConsequencia: salvarTaxonomiaConsequencia
        };
    }
})();
