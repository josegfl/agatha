'use strict';

angular.module('gestaoRiscosApp').factory('successInterceptor', function($q, $rootScope, $window, $injector){

    var toastr;

    function getToaster(){
        if(!toastr){
            toastr = $injector.get("$mdToast");
        }
        return toastr;
    }

    return {
        response: function(response){
            if(response.headers("X-GestaoRiscosApp-alert")){
                getToaster().show(
                    getToaster().simple()
                        .textContent(response.headers("X-GestaoRiscosApp-alert"))
                        .hideDelay(5000)
                );
            }
            return response;
        }
    };
});
