/**
 * Created by Basis Tecnologia on 01/08/2016.
 */
/**
 * Created by Basis Tecnologia on 01/08/2016.
 */

(function(){

    angular
        .module('gestaoRiscosApp')
        .directive('menu', ['$mdSidenav', '$rootScope', 'comumService', directive]);

    function directive($mdSidenav, $rootScope, comumService){
        return {
            restrict: 'EA',
            templateUrl: 'scripts/app/directives/utilitarios/templates/menu.html',
            link: function($scope, $rootScope){
                $scope.toggleMenu = function(){
                    $mdSidenav('left').toggle();
                };
                $scope.closeMenu = function(){
                    $mdSidenav('left').close();
                };

                comumService.buscarUrlsMenusPorAmbiente().then(function(response){
                    $scope.url = response.data;
                });

                $scope.$watch($rootScope.menuAcessos, function(newValue){
                    if(newValue){
                        $scope.menuAcessos = $rootScope.menuAcessos;
                    }
                });

            }
        };
    }

}());
