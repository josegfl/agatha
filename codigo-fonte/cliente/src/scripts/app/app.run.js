var URL_AVATAR_ICONS = 'assets/img/icons/avatars.svg';
var URL_ICON_MENU = 'assets/img/icons/menu.svg';
var URL_ICON_SHARE = 'assets/img/icons/share.svg';
var URL_ICON_PERSON = 'assets/img/icons/ic_person_48px.svg';
var URL_LOGOUT = 'assets/img/icons/ic_power_settings_new_48px.svg';

angular.module('gestaoRiscosApp').run(function($rootScope, $location, $window, $http, $state, ENV, VERSION, usuarioService){

    $rootScope.ENV = ENV;
    $rootScope.VERSION = VERSION;

    $rootScope.$on('$stateChangeStart', function(event, toState, toStateParams){
        $rootScope.toState = toState;
        $rootScope.toStateParams = toStateParams;

        // Colocar aqui o código de inicialização depois de integrado com sistema de segurança

    });

    $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
        var titleKey = 'Sistema de Gestão de Riscos';
        if(toState.name != 'login' && $rootScope.previousStateName){
            $rootScope.previousStateName = fromState.name;
            $rootScope.previousStateParams = fromParams;
        }
        if(toState.data && toState.data.pageTitle){
            titleKey = toState.data.pageTitle;
        }
        $window.document.title = titleKey;
    });

    $rootScope.back = function(){
        if($rootScope.previousStateName === 'activate' || $state.get($rootScope.previousStateName) === null){
            $state.go('home');
        }else{
            $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
        }
    };

    var carregarDadosUsuarioLogado = function(){
        var urlUsuario = 'gestaoriscos/api/usuarios/usuario-logado';
        $http.get(urlUsuario).then(function(response){
            usuarioService.getMenuUsuario($rootScope.usuarioLogado);
        }, function(error){
            $state.go('usuario-nao-autenticado');
        });
    };

    function findAllGlossarios(){
        usuarioService.findAllGlossarios().then(function(objectReturn){
            $rootScope.glossarios = objectReturn.data;
        })
    }

    var init = function(){
        carregarDadosUsuarioLogado();
        findAllGlossarios();
        $rootScope.menuAcessos = [];
    };

    init();
});
