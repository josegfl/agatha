/**
 * Created by Basis Tecnologia on 10/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('RiscoInerenteController', ['$scope', '$mdToast', '$mdDialog', '$state', '$stateParams', 'RiscoInerenteService', controller]);

    function controller($scope, $mdToast, $mdDialog, $state, $stateParams, service) {
        var scope = this;

        scope.processoId = $stateParams.id;

        if ($state.current.name.indexOf("probabilidade") != -1) {
            scope.riscoInerenteTab = 0;
        } else if ($state.current.name.indexOf("impacto") != -1) {
            scope.riscoInerenteTab = 1;
        }

        scope.init = function () {
            scope.registro = {};
            scope.tipoCalculadora = "";
            scope.getCalculadora(function () {
                scope.getEventosRisco();
            });

            scope._frequencias = [
                {frequencia: 1, descricao: "Muito baixa (< 10%)"},
                {frequencia: 2, descricao: "Baixa (>=10% <=30%)"},
                {frequencia: 3, descricao: "Média (>30% <=50%)"},
                {frequencia: 4, descricao: "Alta (>50% ,<=90%)"},
                {frequencia: 5, descricao: "Muito alta (>90%)"}
            ];

            scope._pesos = [
                {peso: 1},
                {peso: 2},
                {peso: 3},
                {peso: 4},
                {peso: 5}
            ];
        };

        scope.getCalculadora = function (callback) {
            service.getCalculadora($stateParams.id).then(function (retorno) {
                scope.calculadora = retorno.data;
                callback();
            });
        };

        scope.getCalculadoraResidual = function (callback) {
            service.getCalculadoraResidual($stateParams.id).then(function (retorno) {
                scope.calculadoraResidual = retorno.data;
                if (callback) {
                    callback();
                }
            });
        };

        scope.getEventosRisco = function () {
            service.getEventosRisco($stateParams.id).then(function (retorno) {
                scope._eventosRisco = retorno.data;

                for (var i = 0; i < scope._eventosRisco.length; i++) {
                    if (!scope._eventosRisco[i].calculoRiscoInerente) {
                        scope._eventosRisco[i].calculoRiscoInerente = {};
                        scope._eventosRisco[i].calculoRiscoInerente.pesos = [];
                        for (var j = 0; j < 6; j++) {
                            scope._eventosRisco[i].calculoRiscoInerente.pesos[j] = {};
                            if (scope.calculadora.impactos[j].desabilitado) {
                                scope._eventosRisco[i].calculoRiscoInerente.pesos[j].peso = 0;
                            }
                        }
                    }
                }
            });
        };

        scope.handlePesoChange = function (item) {
            var dividendo = 0;
            var divisor = 0;

            for (var i = 0; i < item.calculoRiscoInerente.pesos.length; i++) {
                if (item.calculoRiscoInerente.pesos[i].peso != 0 && !scope.calculadora.impactos[i].desabilitado) {
                    dividendo += (scope.calculadora.impactos[i].peso / 100) * (item.calculoRiscoInerente.pesos[i].peso || 0);
                    divisor += (scope.calculadora.impactos[i].peso / 100);
                }
            }

            var media = parseFloat(dividendo / divisor);

            if ((media % 1) >= 0.5) {
                item.calculoRiscoInerente.mediaPeso = Math.ceil(media);
            } else if ((media % 1) < 0.5) {
                item.calculoRiscoInerente.mediaPeso = Math.floor(media);
            }

            scope.calculaNivel(item);
        };

        scope.calculaNivel = function (item) {
            var nivel = parseFloat(item.calculoRiscoInerente.mediaPeso * (item.calculoRiscoInerente.frequencia || 0));
            if ((nivel % 1) >= 0.5) {
                item.calculoRiscoInerente.nivel = Math.ceil(nivel);
            } else if ((nivel % 1) < 0.5) {
                item.calculoRiscoInerente.nivel = Math.floor(nivel);
            }
        };

        scope.handlePesoCalculadoraChange = function (index) {
            for (var i = 0; i < scope._eventosRisco.length; i++) {
                scope._eventosRisco[i].calculoRiscoInerente.pesos[index].peso = 0;
                scope.handlePesoChange(scope._eventosRisco[i]);
            }
        };

        function verificaRisco() {
            var residualMenor = true;
            for (var i = 0; i < scope._eventosRisco.length; i++) {
                if (scope._eventosRisco[i].calculoRiscoResidual && scope._eventosRisco[i].calculoRiscoResidual.nivel && scope._eventosRisco[i].calculoRiscoInerente && scope._eventosRisco[i].calculoRiscoInerente.nivel) {
                    if (parseFloat(scope._eventosRisco[i].calculoRiscoResidual.nivel) > parseFloat(scope._eventosRisco[i].calculoRiscoInerente.nivel)) {
                        residualMenor = false;
                        break;
                    }
                }
            }
            return residualMenor;
        }

        function clonarRiscoInerenteParaResidual(callback) {
            scope.getCalculadoraResidual(function () {
                for (var i = 0; i < scope._eventosRisco.length; i++) {
                    if (!scope._eventosRisco[i].calculoRiscoResidual || !scope._eventosRisco[i].calculoRiscoResidual.nivel) {
                        scope._eventosRisco[i].calculoRiscoResidual = {};
                        scope._eventosRisco[i].calculoRiscoResidual.pesos = angular.copy(scope._eventosRisco[i].calculoRiscoInerente.pesos);
                        scope._eventosRisco[i].calculoRiscoResidual.frequencia = angular.copy(scope._eventosRisco[i].calculoRiscoInerente.frequencia);

                        for (var pr = 0; pr < scope._eventosRisco[i].calculoRiscoResidual.pesos.length; pr++) {
                            if (scope._eventosRisco[i].calculoRiscoResidual.pesos[pr].id) {
                                delete scope._eventosRisco[i].calculoRiscoResidual.pesos[pr].id;
                            }
                        }

                        handlePesoResidualChange(scope._eventosRisco[i]);
                    }
                }
                if (callback) {
                    callback();
                }
            });
        }

        function handlePesoResidualChange(item) {
            var dividendo = 0;
            var divisor = 0;

            for (var cr = 0; cr < item.calculoRiscoResidual.pesos.length; cr++) {
                if (item.calculoRiscoResidual.pesos[cr].peso != 0 && !scope.calculadoraResidual.impactos[cr].desabilitado) {
                    dividendo += (scope.calculadoraResidual.impactos[cr].peso / 100) * (item.calculoRiscoResidual.pesos[cr].peso || 0);
                    divisor += (scope.calculadoraResidual.impactos[cr].peso / 100);
                }
            }

            var media = parseFloat(dividendo / divisor);

            if ((media % 1) >= 0.5) {
                item.calculoRiscoResidual.mediaPeso = Math.ceil(media);
            } else if ((media % 1) < 0.5) {
                item.calculoRiscoResidual.mediaPeso = Math.floor(media);
            }

            calculaNivelResidual(item);
        }

        function calculaNivelResidual(item) {
            var nivel = parseFloat(item.calculoRiscoResidual.mediaPeso * (item.calculoRiscoResidual.frequencia || 0));
            if ((nivel % 1) >= 0.5) {
                item.calculoRiscoResidual.nivel = Math.ceil(nivel);
            } else if ((nivel % 1) < 0.5) {
                item.calculoRiscoResidual.nivel = Math.floor(nivel);
            }
        }

        function verificaColunasDesabilitadas(callback) {
            service.alterarStatus(scope.processoId, scope.calculadora).then(function () {
                for (var i = 0; i < scope.calculadora.impactos.length; i++) {
                    if (scope.calculadora.impactos[i].desabilitado === true) {
                        for (var x = 0; x < scope._eventosRisco.length; x++) {
                            scope._eventosRisco[x].calculoRiscoResidual.pesos[i].peso = 0;
                            handlePesoResidualChange(scope._eventosRisco[x]);
                        }
                    }
                }
                callback();
            })
        }

        scope.persistir = function () {
            if ($scope.formRegistro.$valid) {
                if (verificaRisco()) {
                    clonarRiscoInerenteParaResidual(function () {
                        verificaColunasDesabilitadas(function () {
                            service.salvarCalculo(scope._eventosRisco).then(function () {
                                if ($state.current.name.indexOf("probabilidade") != -1) {
                                    scope.riscoInerenteTab = 0;
                                } else if ($state.current.name.indexOf("impacto") != -1) {
                                    scope.riscoInerenteTab = 1;
                                }
                            });
                        });
                    });
                } else {
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent("O risco residual não pode ser maior que o risco inerente!")
                            .hideDelay(3000)
                    );
                }
            } else {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent("Todos os campo são obrigatórios para efetuar o calculo")
                        .hideDelay(3000)
                );
            }
        };

        scope.visualizarLegenda = function (ev) {
            $mdDialog.show({
                controller: function () {
                    return scope;
                },
                controllerAs: 'ctrl',
                templateUrl: 'scripts/pages/geral/view/calculo-legenda.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: true
            });
        };

        scope.visualizarLegendaProbabilidade = function (ev) {
            $mdDialog.show({
                controller: function () {
                    return scope;
                },
                controllerAs: 'ctrl',
                templateUrl: 'scripts/pages/geral/view/calculo-legenda-probabilidade.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: true
            });
        };

        scope.changeToProbabilidade = function(){
            if($state.current.name.indexOf("probabilidade") == -1){
                    $state.go("riscoInerente.probabilidade", {id: $state.params.id})
            }
        };

        scope.changeToImpacto = function(){
            if($state.current.name.indexOf("impacto") == -1){
                $state.go("riscoInerente.impacto", {id: $state.params.id})
            }
        };

        scope.cancel = function () {
            $mdDialog.cancel();
        };

        scope.init();
    }
})();
