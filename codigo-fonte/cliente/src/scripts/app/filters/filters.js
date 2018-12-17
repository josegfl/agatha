(function(){
    'use strict';
    angular.module('gestaoRiscosApp').filter('nivelRisco', function(){
        return function(nivel){
            if(!nivel){
                return;
            }
            nivel = parseInt(nivel);
            if(nivel <= 3){
                return "Risco Pequeno"
            }else if(nivel >= 4 && nivel <= 6){
                return "Risco Moderado"
            }else if(nivel >= 7 && nivel <= 15){
                return "Risco Alto"
            }else{
                return "Risco Crítico"
            }
        };
    });

    angular.module('gestaoRiscosApp').filter('corNivelRisco', function(){
        return function(nivel){
            if(!nivel){
                return;
            }
            nivel = parseInt(nivel);
            if(nivel <= 3){
                return "#00b052"
            }else if(nivel >= 4 && nivel <= 6){
                return "#ffff00"
            }else if(nivel >= 7 && nivel <= 15){
                return "#e26f01"
            }else{
                return "#fe0100"
            }
        };
    });

    angular.module('gestaoRiscosApp').filter('parseFloat', function(){
        return function(data){
            if(!data){
                data = 0;
            }
            return parseFloat(data);
        };
    });

})();
