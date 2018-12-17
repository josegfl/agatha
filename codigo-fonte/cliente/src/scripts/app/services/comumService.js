'use strict';
angular.module('gestaoRiscosApp').factory('comumService', ['$http', '$mdToast', service]);

function service($http, $mdToast){

    /**
     * Passar uma string ou uma array de strings para várias mensagens
     * @param mensagens
     */
    var mostrarMensagemError = function(mensagens){
        $mdToast.show({
            controller: toastController(mensagens),
            templateUrl: 'toast-template-error.html',
            focusOnOpen: false,
            hideDelay: 30000,
            position: 'top right'
        });
    };

    var exibirMesagemErroVariosCampos = function(titulo, mensagens){
        $mdToast.show({
            controller: toastController(mensagens, titulo),
            templateUrl: 'toast-template-error.html',
            focusOnOpen: false,
            hideDelay: 30000,
            position: 'top right'
        });
    };

    var mostrarMensagemSucesso = function(mensagens){
        mensagens = (typeof mensagens == 'string') ? [mensagens] : mensagens
        mensagens.forEach(function(msg){
            $mdToast.show({
                controller: toastController(msg),
                templateUrl: 'toast-template-success.html',
                focusOnOpen: false,
                hideDelay: 30000,
                position: 'top right'
            });
        });
    };

    var buscarUrlsMenusPorAmbiente = function(){
        return $http.get('/gestaoriscos/api/cliente/menu');
    };

    var limparCamposVazios = function(obj){
        angular.forEach(obj, function(item, index){
            if(obj == "" || obj == null){
                delete obj[index];
            }
        });

        return obj;
    };

    var toastController = function(mensagens, titulo){
        return function($scope, $mdToast){

            $scope.show = false;
            $scope.titulo = titulo;
            $scope.msgs = mensagens;
            $scope.isArray = $scope.msgs instanceof Array;

            $scope.$on("toast-close", function(){
                $scope.show = false;
                $mdToast.hide();
            });

            function showToast($event){
                if(!$scope.show){
                    $scope.show = true;
                }else{
                    $scope.$broadcast('toast-close');
                }
            }

            function cleanUp(){
                angular.element(window).off('click', showToast);
            }

            angular.element(window).on('click', showToast);
            $scope.$on('$destroy', cleanUp);
        }
    };

    var obterPermissoes = function(cpf, modulo){
        return $http.get('gestaoriscos/comum/buscar-permissoes/' + cpf + '/' + modulo);
    };

    var buscarIdSistema = function(){
        return $http.get('gestaoriscos/comum/buscar-id-sistema');
    };

    var logout = function(){
        return $http.get('gestaoriscos/api/saml/logout');
    };

    return {
        mostrarMensagemError: mostrarMensagemError,
        mostrarMensagemSucesso: mostrarMensagemSucesso,
        buscarUrlsMenusPorAmbiente: buscarUrlsMenusPorAmbiente,
        exibirMesagemErroVariosCampos: exibirMesagemErroVariosCampos,
        obterPermissoes: obterPermissoes,
        buscarIdSistema: buscarIdSistema,
        logout: logout
    };
}
