/**
 * Created by Basis Tecnologia on 25/04/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp')
        .directive('sgrGlossario', directive);

    function directive($filter, $rootScope){
        return {
            restrict: 'E',
            scope: {
                termo: '@'
            },
            templateUrl: 'scripts/app/directives/templates/glossario.html',
            link: function(scope, element, attrs){
                scope.glossario = {};
                scope.glossario.termo = scope.termo;

                $rootScope.$watch("glossarios", function(){
                    verificaGlossario($rootScope.glossarios)
                });

                function verificaGlossario(glossarios){
                    var glossario = $filter("filter")(glossarios, {termo: scope.termo});
                    if(glossario && glossario.length){
                        var foundTermo = false;
                        for(var i = 0; i < glossario.length; i++){
                            if(glossario[i].termo == scope.termo){
                                scope.glossario = angular.copy(glossario[i]);
                                break
                            }
                        }
                        if(!foundTermo){
                            scope.glossario.termo = scope.termo;
                        }
                    }else{
                        scope.glossario.termo = scope.termo;
                    }
                }
            }
        };
    }

})();
