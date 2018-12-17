/**
 * Created by Basis Tecnologia on 16/08/2016.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('AnaliseAmbienteController', ['$scope', '$rootScope', '$state', '$stateParams', '$mdToast', '$mdDialog', '$timeout', 'AnaliseAmbienteService', controller]);

    function controller($scope, $rootScope, $state, $stateParams, $mdToast, $mdDialog, $timeout, service) {
        var scope = this;

        var delayPromise;

        $rootScope.currentTab = 0;
        scope.processoId = $stateParams.id;

        scope.apenasLeitura = false;

        scope.init = function () {
            scope.getPermissao();
        };

        $scope.$watch('scope.files.length', function(){
            if (delayPromise) {
                $timeout.cancel(delayPromise);
            }

            delayPromise = $timeout(function() {
                if(scope.files && scope.files.length){
                    validaExtencoes();
                    if(scope.registro.anexos && scope.registro.anexos.length){
                        validaTamanho();
                    }

                    service.uploadFile(scope.files).then(function(objectReturn){
                        if(!scope.registro.anexos){
                            scope.registro.anexos = [];
                        }
                        angular.forEach(objectReturn.data, function(obj){
                            var anexo = {};
                            anexo.arquivoAnexo = obj;
                            scope.registro.anexos.push(anexo);
                        });
                        scope.uploadApi.removeAll();
                    });
                }
            }, 1000);

            function validaExtencoes() {
                var removedFile = false;
                for (var i = 0; i < scope.files.length; i++) {
                    if (scope.files[i].lfFileType != "image/png" &&
                        scope.files[i].lfFileType != "image/jpeg" &&
                        scope.files[i].lfFileType != "image/bpmn" &&
                        scope.files[i].lfFileType != "application/pdf" &&
                        scope.files[i].lfFileType != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" &&
                        scope.files[i].lfFileType != "application/vnd.ms-excel" &&
                        scope.files[i].lfFileType != "application/msword" &&
                        scope.files[i].lfFileType != "application/vnd.openxmlformats-officedocument.wordprocessingml.document" &&
                        scope.files[i].lfFileType != "application/x-rar-compressed" &&
                        scope.files[i].lfFileType != "application/zip" &&
                        scope.files[i].lfFileType != "application/x-zip-compressed" &&
                        scope.files[i].lfFileType != "application/octet-stream"
                    ) {
                        scope.files.splice(i, 1);
                        removedFile = true;
                    }
                }

                if (removedFile) {
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent("Um o mais arquivos foram removidos pois não são suportados pelo sistema")
                            .hideDelay(3000)
                    );
                }
            }

            function validaTamanho() {
                var tamanhoAtual = 0;
                for (var i = 0; i < scope.registro.anexos.length; i++) {
                    tamanhoAtual += scope.registro.anexos[i].arquivoAnexo.tamanho;
                }

                var removedFile = false;
                for (var i = 0; i < scope.files.length; i++) {
                    //var tamanhoRelativo = (tamanhoAtual / 1024 / 1024);
                    var tamanhoFuturo = ((tamanhoAtual + scope.files[i].lfFile.size) / 1024 / 1024);

                    if (tamanhoFuturo > 25) {
                        scope.files.splice(i, 1);
                        removedFile = true;
                    } else {
                        tamanhoAtual += scope.files[i].lfFile.size;
                    }
                }

                if (removedFile) {
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent("Um o mais arquivos foram removidos pois iriam extrapolar o limite de 25MB por processo")
                            .hideDelay(3000)
                    );
                }
            }
        });

        scope.excluirAnexo = function (item) {
            var confirm = $mdDialog.confirm()
                .title("Atenção!")
                .textContent("Deseja excluir o anexo?")
                .ok("OK")
                .cancel("Cancelar");

            $mdDialog.show(confirm).then(function () {
                for (var i = 0; i < scope.registro.anexos.length; i++) {
                    if (angular.equals(scope.registro.anexos[i], item)) {
                        scope.registro.anexos.splice(i, 1);
                    }
                }
            });
        };

        scope.novoRegistro = function () {
            scope.registro = {};
            scope.registro.responsaveis = [];
            scope.registro.responsaveis.push({});
            scope.registro.dtInicio = "";
            scope.registro.dtFim = "";

            scope.registro.analise = {};
            scope.registro.analise.matrizes = [];

            scope.registro.usuario = $rootScope.usuarioLogado;

            service.getAnalistas().then(function (objectReturn) {
                scope._analistas = objectReturn.data;
            });
            service.getGestores().then(function (objectReturn) {
                scope._gestores = objectReturn.data;
            });
            service.getTiposSwot().then(function (objectReturn) {
                scope._tipos = objectReturn.data;
            });
            service.getOrgaosByCPF(scope.usuario.cpf).then(function (objectReturn) {
                scope.registro.analise.orgao = objectReturn.data.orgao;
                scope.registro.analise.secretaria = objectReturn.data.secretaria;
            });
            scope.getMacroprocessos();
        };

        scope.adicionarResponsavel = function () {
            scope.registro.responsaveis.push({});
        };

        scope.removerResponsavel = function (index) {
            scope.registro.responsaveis.splice(index, 1);
        };

        scope.verificarReponsavel = function (responsavel, index) {
            for(var i in scope.registro.responsaveis){
                if (index != i) {
                    if (scope.registro.responsaveis[i].usuario.id == responsavel.usuario.id) {
                        //noinspection JSUnresolvedFunction
                        $mdToast.show(
                            $mdToast.simple()
                                .textContent("O responsável já está selecionado.")
                                .hideDelay(3000)
                        );
                        scope.registro.responsaveis[index] = null;
                        responsavel = null;
                        break;
                    }
                }
            }

        };

        scope.handleDtInicioChange = function () {
            if (scope.registro.dtInicio && scope.registro.dtFim) {
                if (scope.registro.dtInicio > scope.registro.dtFim) {
                    scope.registro.dtFim = null;
                }
            }
        };

        scope.findBy = function (registroId) {
            scope.registro = {};
            service.findBy(registroId).then(function (objectResponse) {
                scope.registro = objectResponse.data;

                scope.registro.analise.etica = scope.registro.analise.etica.toString();
                scope.registro.analise.estrutura = scope.registro.analise.estrutura.toString();
                scope.registro.analise.recursosHumanos = scope.registro.analise.recursosHumanos.toString();
                scope.registro.analise.atribuicoes = scope.registro.analise.atribuicoes.toString();
                scope.registro.analise.normasInternas = scope.registro.analise.normasInternas.toString();
                scope.registro.analise.missao = scope.registro.analise.missao.toString();
                scope.registro.analise.visao = scope.registro.analise.visao.toString();
                scope.registro.analise.objetivos = scope.registro.analise.objetivos.toString();

                if (scope.registro.dtInicio) {
                    scope.registro.dtInicio = new Date(scope.registro.dtInicio);
                } else {
                    scope.registro.dtInicio = "";
                }
                if (scope.registro.dtFim) {
                    scope.registro.dtFim = new Date(scope.registro.dtFim);
                } else {
                    scope.registro.dtFim = "";
                }

                scope.registro.isGestor = scope.usuario.cpf == scope.registro.gestor.cpf;

                service.getGestores().then(function (objectReturn) {
                    scope._gestores = objectReturn.data;

                    var selecionarGestor = false;
                    for (var i = 0; i < scope._gestores.length; i++) {
                        if (scope._gestores[i].id == scope.registro.gestor.id) {
                            selecionarGestor = true;
                            scope.registro.gestor = scope._gestores[i];
                        }
                    }
                    if (!selecionarGestor) {
                        scope._gestores.push(scope.registro.gestor);
                    }
                });

                var isAnalista = false;

                for (var i in scope.registro.responsaveis) {
                    if (scope.registro.responsaveis[i].usuario.cpf == scope.usuario.cpf) {
                        isAnalista = true;
                        break;
                    }
                }

                scope.registro.isAnalista = isAnalista;

                service.getAnalistas().then(function (objectReturn) {
                    scope._analistas = objectReturn.data;

                    for (var j = 0; j < scope.registro.responsaveis.length; j++) {
                        var selecionarAnalista = false;
                        for (var i = 0; i < scope._analistas.length; i++) {
                            if (scope.registro.responsaveis[j].usuario.id == scope._analistas[i].id) {
                                selecionarAnalista = true;
                                scope.registro.responsaveis[j].usuario = scope._analistas[i];
                            }
                        }
                        if (!selecionarAnalista) {
                            scope._analistas.push(scope.registro.responsaveis[j].usuario);
                        }
                    }
                });

                service.getTiposSwot().then(function (objectReturn) {
                    scope._tipos = objectReturn.data;
                });

                scope.getMacroprocessos(function () {
                    if (scope.registro.macroprocesso && scope.registro.macroprocesso.id) {
                        for (var i = 0; i < scope._macroprocessos.length; i++) {
                            if (scope._macroprocessos[i].id == scope.registro.macroprocesso.id) {
                                scope.registro.macroprocesso = scope._macroprocessos[i];
                            }
                        }
                    }
                });
            });
        };

        scope.persistir = function (registro) {
            $scope.formRegistro.$setSubmitted();
            if ($scope.formRegistro.$valid) {
                persistirProcesso();
            } else {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Campo(s) obrigatório(s) não preenchido(s)")
                        .hideDelay(3000)
                );
            }

            function persistirProcesso() {
                if (registro.id) {
                    service.update(registro).then(function () {
                        $state.go("processo.analiseAmbiente.alterar", {id: registro.id});
                    });
                } else {
                    service.create(registro).then(function (objectReturn) {
                        $state.go("processo.analiseAmbiente.alterar", {id: objectReturn.data.id}, {reload: true});
                    });
                }
            }
        };

        scope.findByMatriz = function (matriz) {
            $scope.formMatriz.$setPristine();
            $scope.formMatriz.$setUntouched();

            matriz.$update = true;
            scope.matriz = angular.copy(matriz);
        };

        scope.persistirMatriz = function (matriz) {
            $scope.formMatriz.$setSubmitted();
            if ($scope.formMatriz.$valid) {
                if (matriz.$update) {
                    for (var i = 0; i < scope.registro.analise.matrizes.length; i++) {
                        if (scope.registro.analise.matrizes[i].$update) {
                            scope.registro.analise.matrizes[i] = matriz;
                            delete scope.registro.analise.matrizes[i].$update;
                        }
                    }

                    $scope.formMatriz.$setPristine();
                    $scope.formMatriz.$setUntouched();
                    scope.matriz = {};
                } else {
                    scope.registro.analise.matrizes.push(matriz);

                    $scope.formMatriz.$setPristine();
                    $scope.formMatriz.$setUntouched();
                    scope.matriz = {};
                }
            } else {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Campo(s) obrigatório(s) não preenchido(s)")
                        .hideDelay(3000)
                );
            }
        };

        scope.excluirMatriz = function (registro) {
            registro.$delete = true;

            var confirm = $mdDialog.confirm()
                .title("Atenção!")
                .textContent("Deseja excluir o registro?")
                .ok("OK")
                .cancel("Cancelar");

            $mdDialog.show(confirm).then(function () {
                for (var i = 0; i < scope.registro.analise.matrizes.length; i++) {
                    if (scope.registro.analise.matrizes[i].$delete) {
                        scope.registro.analise.matrizes.splice(i, 1);
                    }
                }
            }, function () {
                for (var i = 0; i < scope.registro.analise.matrizes.length; i++) {
                    if (scope.registro.analise.matrizes[i].$delete) {
                        scope.registro.analise.matrizes.splice(i, 1);
                    }
                }
            });
        };

        scope.cancelarEdicaoMatriz = function () {
            for (var i = 0; i < scope.registro.analise.matrizes.length; i++) {
                if (scope.registro.analise.matrizes[i].$update) {
                    delete scope.registro.analise.matrizes[i].$update;
                }
            }

            $scope.formMatriz.$setPristine();
            $scope.formMatriz.$setUntouched();
            scope.matriz = {};
        };

        scope.getPermissao = function () {
            service.getUsuarioLogado().then(function (objectReturn) {
                scope.usuario = objectReturn.data;
                service.getPermissao(objectReturn.data.cpf).then(function (objectReturn) {
                    scope.permissao = objectReturn.data;

                    if ($stateParams.id) {
                        scope.apenasLeitura = $state.current.name.indexOf("consultar") != -1;
                        scope.apenasLeitura = scope.permissao.criar == false ? true : scope.apenasLeitura;
                        scope.findBy($stateParams.id);
                    } else {
                        scope.novoRegistro();
                    }
                });
            });
        };

        scope.getMacroprocessos = function (callback) {
            service.getMacroprocessos().then(function (objectReturn) {
                scope._macroprocessos = objectReturn.data;
                if (callback) {
                    callback();
                }
            });
        };

        scope.adicionarPontoFonte = function (tipo) {
            if(scope.pontoForte) {
                var matriz = {};
                matriz.descricao = angular.copy(scope.pontoForte);
                matriz.tipoMatriz = tipo;

                scope.registro.analise.matrizes.push(matriz);
                scope.pontoForte = null;
                scope.incluirPontoForte = false;
            }else{
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Preencha o campo corretamente")
                        .hideDelay(3000)
                );
            }
        };

        scope.adicionarOportunidade = function (tipo) {
            if(scope.oportunidade) {
                var matriz = {};
                matriz.descricao = angular.copy(scope.oportunidade);
                matriz.tipoMatriz = tipo;

                scope.registro.analise.matrizes.push(matriz);
                scope.oportunidade = null;
                scope.incluirOportunidade = false;
            }else{
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Preencha o campo corretamente")
                        .hideDelay(3000)
                );
            }
        };

        scope.adicionarPontoFraco = function (tipo) {
            if(scope.pontoFraco) {
                var matriz = {};
                matriz.descricao = angular.copy(scope.pontoFraco);
                matriz.tipoMatriz = tipo;

                scope.registro.analise.matrizes.push(matriz);
                scope.pontoFraco = null;
                scope.incluirPontoFraco = false;
            }else{
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Preencha o campo corretamente")
                        .hideDelay(3000)
                );
            }
        };

        scope.adicionarAmeaca = function (tipo) {
            if(scope.ameaca) {
                var matriz = {};
                matriz.descricao = angular.copy(scope.ameaca);
                matriz.tipoMatriz = tipo;

                scope.registro.analise.matrizes.push(matriz);
                scope.ameaca = null;
                scope.incluirAmeaca = false;
            }else{
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Preencha o campo corretamente")
                        .hideDelay(3000)
                );
            }
        };

        scope.editarPontoForte = function () {
            if(scope.pontoForteEditado) {
                for(var i in scope.registro.analise.matrizes) {
                    if(scope.registro.analise.matrizes[i].$edit){
                        scope.registro.analise.matrizes[i].descricao = angular.copy(scope.pontoForteEditado);

                        scope.pontoForteEditado = null;
                        scope.registro.analise.matrizes[i].$edit = false;
                    }
                }

            }else{
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Preencha o campo corretamente")
                        .hideDelay(3000)
                );
            }
        };

        scope.editarOportunidade = function () {
            if(scope.oportunidadeEditado) {
                for(var i in scope.registro.analise.matrizes) {
                    if(scope.registro.analise.matrizes[i].$edit){
                        scope.registro.analise.matrizes[i].descricao = angular.copy(scope.oportunidadeEditado);

                        scope.oportunidadeEditado = null;
                        scope.registro.analise.matrizes[i].$edit = false;
                    }
                }

            }else{
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Preencha o campo corretamente")
                        .hideDelay(3000)
                );
            }
        };

        scope.editarPontoFraco = function () {
            if(scope.pontoFracoEditado) {
                for(var i in scope.registro.analise.matrizes) {
                    if(scope.registro.analise.matrizes[i].$edit){
                        scope.registro.analise.matrizes[i].descricao = angular.copy(scope.pontoFracoEditado);

                        scope.pontoFracoEditado = null;
                        scope.registro.analise.matrizes[i].$edit = false;
                    }
                }

            }else{
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Preencha o campo corretamente")
                        .hideDelay(3000)
                );
            }
        };

        scope.editarAmeaca = function () {
            if(scope.ameacaEditado) {
                for(var i in scope.registro.analise.matrizes) {
                    if(scope.registro.analise.matrizes[i].$edit){
                        scope.registro.analise.matrizes[i].descricao = angular.copy(scope.ameacaEditado);

                        scope.ameacaEditado = null;
                        scope.registro.analise.matrizes[i].$edit = false;
                    }
                }

            }else{
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Preencha o campo corretamente")
                        .hideDelay(3000)
                );
            }
        };

        scope.init();
    }
})();
