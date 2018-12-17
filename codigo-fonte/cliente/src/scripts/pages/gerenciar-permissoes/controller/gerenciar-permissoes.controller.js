/**
 * Created by Basis Tecnologia on 03/08/2016.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').controller('GerenciarPermissoesController', ['$scope', '$state', '$stateParams', '$mdToast', '$mdDialog', '$element', '$q', 'GerenciarPermissoesService', controller]);

    function controller($scope, $state, $stateParams, $mdToast, $mdDialog, $element, $q, service){
        var scope = this;

        scope.mensagemErroPerfil = 'Selecione um perfil!';
        scope.orgaoPesquisa = '';
        scope._permissoes = [];
        scope.usuario = {
            permissoes: []
        };
        scope.isEdicao = false;
        scope.regexEmail = new RegExp(/(\d)*(\w?\-?\_?)*(^$#%&!@\?\^\+\-\=)*@\w+(\.\w{1,3}){1,2}$/);
        scope.idOrgaoPai = 2981;
        scope.idCategoriaUnidadeAdministrativa = 1;
        scope.idCategoriaOrgao = 4;
        scope.listaIdCategorias = [scope.idCategoriaUnidadeAdministrativa, scope.idCategoriaUnidadeAdministrativa];

        scope.init = function(){
            if($state.current.name.indexOf('list') > -1){
                scope.registroInicial = 1;
                scope.tamanhoLimite = 20;
                scope.totalItems = 0;

                scope.nomeOrgao = 'Ministério do Planejamento';

                scope.limparFiltros();
                scope.findAllPerfil();
                scope.buscarOrgaos();
            } else {
                scope.findAllPerfil();
                scope.buscarOrgaos();
                if ($stateParams.id) {
                    scope.findBy($stateParams.id);
                    scope.isEdicao = true;
                }
            }
        };

        $element.find('input').on('keydown', function(ev){
            ev.stopPropagation();
        });

        scope.searchOrgaoByNome = function(nome){
            if(nome && nome.length >= 3){
                service.searchOrgaoByNome(nome).then(function(objectReturn){
                    scope.nomeOrgaos = objectReturn.data;
                    return objectReturn.data;
                });
            }else{
                scope.nomeOrgaos = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.limparPesquisas = function(){
            scope.orgaoPesquisa = '';
            scope._orgaos = [];
        };

        scope.findAllPerfil = function(){
            service.findAllPerfil().then(function(retorno){
                scope._perfis = retorno.data;
            });
        };

        scope.getUsuariosByNome = function(nome){
            if(nome && nome.length >= 3){
                service.getUsuariosByNome(nome).then(function(objectReturn){
                    scope._usuarios = objectReturn.data;
                    return objectReturn.data;
                });
            }else{
                scope.descricoes = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.filtrar = function(){
            scope.filtros.page = scope.registroInicial - 1;
            scope.filtros.size = scope.tamanhoLimite;

            service.findByFilter(scope.filtros).then(function(retorno){
                scope._registros = retorno.data.content || [];
                scope.totalItems = retorno.data.totalElements;

                if(!scope._registros.length){
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent("Dados não encontrados")
                            .hideDelay(3000)
                    );
                }
            });
        };

        scope.limparFiltros = function(){
            scope.filtros = {
                orgao: null,
                perfil: null,
                usuario: null
            };

            scope.limparPesquisas();

            scope.filtrar();
        };

        scope.excluirRegistro = function(registro){
            var confirm = $mdDialog.confirm()
                .title("Atenção!")
                .textContent("Deseja excluir o registro?")
                .ok("OK")
                .cancel("Cancelar");

            $mdDialog.show(confirm).then(function(){
                service.excluir(registro.id).then(function(){
                    scope.filtrar();
                });
            });
        };

        scope.novoRegistro = function(){
            resetForm();

            scope.registro = {};
        };

        scope.findBy = function(registroId){
            service.findBy(registroId).then(function(objectResponse){
                $scope.usuarioFormCreate.cpf = objectResponse.data.cpf;
                $scope.usuarioFormCreate.nome = objectResponse.data.nome;
                $scope.usuarioFormCreate.email = objectResponse.data.email;
                if (objectResponse.data.orgao) {
                    $scope.usuarioFormCreate.secretaria = objectResponse.data.orgao.id;
                }
                scope.usuario.permissoes = objectResponse.data.permissoes;
                scope.removerPerfisInseridos();
                scope.ordenarArrayPerfis();
            })
        };

        scope.persistir = function(){
            if (!$scope.usuarioFormCreate.$valid) {
                $mdToast.show(
                    $mdToast.simple().textContent("Campo(s) obrigatório(s) não preenchido(s)").hideDelay(3000)
                );
                return;
            }
            if (!scope.isArrayPerfisValido()) {
                $mdToast.show(
                    $mdToast.simple().textContent(scope.mensagemErroPerfil).hideDelay(3000)
                );
                return;
            }
            scope.usuario.id = $stateParams.id;
            scope.usuario.nome = $scope.usuarioFormCreate.nome;
            scope.usuario.email = $scope.usuarioFormCreate.email;
            scope.usuario.cpf = $scope.usuarioFormCreate.cpf.match(new RegExp(/\d/g)).join('');
            scope.usuario.orgao = {};
            scope.usuario.orgao.id = $scope.usuarioFormCreate.secretaria;

            service.save(scope.usuario).then(function(response){
                $mdToast.show(
                    $mdToast.simple()
                        .textContent(response)
                        .hideDelay(3000)
                );
                $state.go('gerenciarPermissoes.list');
            });
        };

        scope.adicionarPerfil = function(perfil) {
            if (!perfil) {
                $mdToast.show(
                    $mdToast.simple()
                        .textContent(scope.mensagemErroPerfil)
                        .hideDelay(3000)
                );
                return;
            }
            const permissaoExistente = scope.usuario.permissoes.filter(function (permissao) {
                if (permissao.perfil.id === perfil.id) {
                    return permissao;
                }
            });
            if (permissaoExistente && permissaoExistente.length > 0) {
                const indexPermissao = scope.usuario.permissoes.indexOf(permissaoExistente[0]);
                scope.usuario.permissoes[indexPermissao].excluido = false;
            } else {
                scope.usuario.permissoes.push({perfil: perfil, excluido: false});
            }
            const indexPerfil = scope._perfis.indexOf(perfil);
            scope._perfis.splice(indexPerfil, 1);
            $scope.usuarioFormCreate.perfil = null;
            scope.ordenarArrayPerfis();
        };

        scope.removerPerfil = function(permissao){
            var confirm = $mdDialog.confirm()
                .title("Atenção!")
                .textContent("Deseja excluir o registro?")
                .ok("OK")
                .cancel("Cancelar");

            $mdDialog.show(confirm).then(function(){
                const indexPerfil = scope.usuario.permissoes.indexOf(permissao);
                if (permissao.id) {
                    scope.usuario.permissoes[indexPerfil].excluido = true;
                    scope.ordenarArrayPerfis();
                } else {
                    scope.usuario.permissoes.splice(indexPerfil, 1);
                }
                scope._perfis.push(permissao.perfil);
                $scope.usuarioFormCreate.perfil = null;

                $mdToast.show(
                    $mdToast.simple().textContent("Registro excluído com sucesso!")
                )
            });
        };

        scope.ordenarArrayPerfis = function () {
            scope.usuario.permissoes.sort(function (permissao1, permissao2) {
                if (permissao1.excluido) {
                    return 1;
                }
                if (permissao2.excluido) {
                    return -1;
                }
                return permissao1.perfil.prioridade > permissao2.perfil.prioridade;
            });
        };

        scope.buscarOrgaos = function(){
            service.getUnidadePeloIdLimitadoPorIdCategoria(scope.idOrgaoPai, scope.listaIdCategorias).then(function(response){
                scope.orgaos = angular.copy(response.data);
            });
        };

        scope.removerPerfisInseridos = function () {
            if (!scope._perfis || scope._perfis.length === 0) {
                scope.findAllPerfil();
            }
            for (var permissao in scope.usuario.permissoes) {
                if (scope.usuario.permissoes[permissao].excluido) {
                    continue;
                }
                const perfil = scope._perfis.filter(function (perfil) {
                    if (perfil.id === scope.usuario.permissoes[permissao].perfil.id) {
                        return perfil;
                    }
                });
                const indexPerfil = scope._perfis.indexOf(perfil[0]);
                scope._perfis.splice(indexPerfil, 1);
            }
        };

        scope.isArrayPerfisValido = function () {

            return scope.usuario.permissoes
                && scope.usuario.permissoes.length > 0
                && scope.usuario.permissoes.filter(function (permissao) {
                    if (!permissao.excluido) {
                        return permissao;
                    }
                }).length > 0;
        };

        scope.limparCadastro = function (){
            document.getElementById("formUsuario").reset();
            for (var cont = scope.usuario.permissoes.length - 1; cont >= 0; cont--) {
                scope._perfis.push(scope.usuario.permissoes[cont].perfil);

                if (scope.usuario.permissoes[cont].id) {
                    scope.usuario.permissoes[cont].excluido = true;
                } else {
                    scope.usuario.permissoes.splice(cont, 1);
                }
            }
            $scope.usuarioFormCreate.secretaria = null;
            scope.ordenarArrayPerfis();
        };

        scope.verificarModCPF = function(cpf) {
            if (!cpf) {
                return;
            }
            cpf = cpf.match(new RegExp(/\d/g)).join('');
            if (scope.isCPFValido(cpf)) {
                $scope.usuarioFormCreate.usercpf.$setValidity('cpfValido', true);
                return;
            }
            $scope.usuarioFormCreate.usercpf.$setValidity('cpfValido', false);
        }

        scope.isCPFValido = function(cpf) {

            var soma;
            var resto;
            soma = 0;
            if( cpf === "00000000000" || cpf === "11111111111" ||
                cpf === "22222222222" || cpf === "33333333333" ||
                cpf === "44444444444" || cpf === "55555555555" ||
                cpf === "66666666666" || cpf === "77777777777" ||
                cpf === "88888888888" || cpf === "99999999999" ){
                return false;
            }

            for (var i=1; i<=9; i++) {
                soma = soma + parseInt(cpf.substring(i-1, i)) * (11 - i);
            }
            resto = (soma * 10) % 11;

            if ((resto === 10) || (resto === 11)){
                resto = 0;
            }
            if (resto !== parseInt(cpf.substring(9, 10)) ) {
                return false;
            }

            soma = 0;
            for (i = 1; i <= 10; i++) soma = soma + parseInt(cpf.substring(i-1, i)) * (12 - i);
            resto = (soma * 10) % 11;

            if ((resto === 10) || (resto === 11)){
                resto = 0;
            }
            if (resto !== parseInt(cpf.substring(10, 11) ) ){
                return false;
            }
            return true;

        };

        scope.init();
    }
})();
