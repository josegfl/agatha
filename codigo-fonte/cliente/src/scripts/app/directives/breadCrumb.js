(function () {

  'use strict';

  angular.module('gestaoRiscosApp')
      .directive('breadCrumb', directive);

    function directive($interval, $state, usuarioService, $rootScope, $stateParams) {
        return {
            restrict: 'EA', //E = element, A = attribute, C = class, M = comment
            scope: {
                dinamico: '=',
                tituloStatico: '=',
                dados: '='
            },
            templateUrl: 'scripts/app/directives/templates/breadCrumb.html',
            //controller: controllerFunction, //Embed a custom controller in the directive
            link: function ($scope, element, attrs) {

              $scope.$watch('dados', function (newValue) {
                if (newValue) {
                  buscarTitulo();
                }
              });

              var caminho = $state.current.url;
              var buscarTitulo = function(){

                  angular.forEach($scope.dados, function(item){
                       angular.forEach(item.nos, function(subitem){
                              angular.forEach(subitem.nos, function(no){
                                if(no.url.indexOf(caminho) !== -1){
                                  $rootScope.labelBreadCrumb = no.descricaoResumida;
                                }
                              });
                       });
                  });
              };
            }
        };
    }

})();
