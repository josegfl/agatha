/**
 * Created by Basis Tecnologia on 10/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').factory('AvaliacaoService', ['$http', '$q', service]);

    function service($http, $q){
        var baseUrl = '/gestaoriscos/api/processos';

        var findByFilter = function (filtros, processoId) {
            var endPoint = baseUrl + "/eventosrisco?page=" + filtros.page + "&size=" + filtros.size + "&processoId=" + processoId;
            return $http.get(endPoint).success(function (data, status, headers) {
                if (status == 200) {
                    data.content = data;
                    data.totalElements = headers("x-total-count")
                }
            });
        };

        var findBy = function (registroId) {
            return $http.get(baseUrl + "/" + registroId);
        };

        var excluirEventoRisco = function (registroId) {
            return $http.delete(baseUrl + "/eventosrisco/" + registroId);
        };

        var getUsuarioLogado = function(){
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

        var getIgnorarRiscoInerente = function (processoId) {
            return $http.get(baseUrl + "/avaliacao/ignorar/" + processoId);
        };

        var alterarIgnorarRiscoInerente = function (processoId) {
            var objectRequest = {};
            objectRequest.id = processoId;

            return $http.post(baseUrl + "/avaliacao/alterarignorar", objectRequest);
        };

        var verificaRiscoInerente = function (processoId) {
            return $http.get(baseUrl + "/avaliacao/risco-inerente/verifica/" + processoId);
        };

        var verificaControleEvento = function (processoId) {
            return $http.get(baseUrl + "/avaliacao/controle-evento/verifica/" + processoId);
        };

        return {
            findByFilter: findByFilter,
            findBy: findBy,
            excluirEventoRisco: excluirEventoRisco,
            getUsuarioLogado: getUsuarioLogado,
            getPermissao: getPermissao,
            getIgnorarRiscoInerente: getIgnorarRiscoInerente,
            alterarIgnorarRiscoInerente: alterarIgnorarRiscoInerente,
            verificaRiscoInerente: verificaRiscoInerente,
            verificaControleEvento: verificaControleEvento

        };
    }
})();
