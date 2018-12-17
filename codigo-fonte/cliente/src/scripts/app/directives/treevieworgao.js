angular.module('gestaoRiscosApp').directive('treeViewOrgao', function($compile, GerenciarPermissoesService) {
  return {
    restrict : 'E',
    scope : {
      localNodes : '=model',
      selecionaFilhos : '=selecionafilhos',
      categoriaUnidade : '=categoriaunidade',
      exibirUsuario : '=comusuario',
      checkboxDesabilitado:'=checkboxdesabilitado',
      semIcone : '=semicone',
      localClickUsuario : '&clickusuario',
      localClickOrgao : '&clickorgao',
      localCheckNovoNo : '&aposcarregarno',
      nodeId 	 :  '=id',
      idOrgaousuario : "@orgaousuario"
    },
    link : function (scope, tElement, tAttrs, transclude) {

      var maxLevels = (angular.isUndefined(tAttrs.maxlevels)) ? 10 : tAttrs.maxlevels;
      var hasCheckBox = (angular.isUndefined(tAttrs.checkbox)) ? false : true;
      scope.showItems = [];

      scope.showHide = function(node) {
        node.show = !node.show;
        if (node.orgaosFilhos.length == 0) {
          GerenciarPermissoesService.getOrgao(node.id).then(function (response) {
            if (response.data[0]) {
              node.orgaosFilhos = response.data[0].orgaosFilhos;
              node.usuarios = response.data[0].usuarios;
              if(scope.localCheckNovoNo){
                scope.localCheckNovoNo({filhos: node.orgaosFilhos});
              }
            }
          });
        }
      };

      scope.showIcon = function(node) {
        if (scope.semIcone || ((angular.isUndefined(node.orgaosFilhos) || node.orgaosFilhos.length == 0) && node.show && !scope.exibirUsuario)
          || ((angular.isUndefined(node.usuarios) || node.usuarios.length == 0) && node.show && scope.exibirUsuario && ((angular.isUndefined(node.orgaosFilhos) || node.orgaosFilhos.length == 0)))) {
          return false;
        }
        return true;
      }

      scope.checkIfChildren = function(node) {
        if (!angular.isUndefined(node.orgaosFilhos) && node.orgaosFilhos.length > 0 && node.show) return true;
      }

      scope.disableCheck = function(node) {
        if (scope.categoriaUnidade != null && node.idCategoriaUnidade != scope.categoriaUnidade) {
          return true;
        }
        return false;
      }

      function parentCheckChange(item) {
        for (var i in item.orgaosFilhos) {
          item.orgaosFilhos[i].checked = item.checked;
          if (item.orgaosFilhos[i].children) {
            parentCheckChange(item.orgaosFilhos[i]);
          }
        }
      }

      scope.checkChange = function(node) {
        if (scope.selecionaFilhos && node.orgaosFilhos) {
          parentCheckChange(node);
        }
        if (scope.localClickOrgao) {
          scope.localClickOrgao({node: node});
        }
      }
      /////////////////////////////////////////////////

      function renderTreeView(collection, level, max) {
        var text = '';

        if (hasCheckBox) {
          text += '<li style="white-space: nowrap" ng-repeat="n in ' + collection + '" >';
          text += '<span class="show-hide" ng-show="showIcon(n)" ng-click="showHide(n);"><i class="material-icons">&#xE147;</i></span>';
          text += '<span style="padding-right: 21px" ng-show="!showIcon(n)"></span>';
          text += '<md-checkbox ng-disabled="disableCheck(n) || n.id == idOrgaousuario" class="tree-checkbox" ng-model="n.checked" ng-change=checkChange(n)><p style="white-space: nowrap">{{n.nome}}</p></md-checkbox>';
        } else {
          text += '<li style="white-space: nowrap" mg-if="n.checked" ng-repeat="n in ' + collection + '" >';
          text += '<span class="show-hide" ng-if="showIcon(n)" ng-click=showHide(n);><i class="material-icons">&#xE147;</i></span>';
          text += '<span style="padding-right: 21px" ng-if="(!exibirUsuario)" ng-class="!exibirUsuario ? \'hide\' : \'\'"></span>';
          text += '<label ng-if="n.checked || exibirUsuario" style="line-height:40px; white-space: nowrap">{{n.nome}}</label>';
        }

        if (level < max) {
          text += '<ul id="'+scope.$id+'-{{n.id}}" ng-if=checkIfChildren(n)>'+ renderUsuariosTreeView() + renderTreeView('n.orgaosFilhos', level + 1, max)+'</ul></li>';
        } else {
          text += '</li>';
        }

        return text;
      }// end renderTreeView();


      function renderUsuariosTreeView() {
        var text = "";
        text += '<ul ng-if="exibirUsuario"><li ng-repeat="n in n.usuarios" >';
        text += '<md-checkbox ng-disabled="checkboxDesabilitado" ng-change=localClickUsuario({node:n}) class="tree-checkbox" ng-model="n.checked" ><p style="white-space: nowrap">{{n.nome}}</p></md-checkbox>';
        text += '</li></ul>';
        return text;

      }// end renderUsuariosTreeView();

      try {
        var text = '<ul class="tree-view-wrapper">';
        text += renderTreeView('localNodes', 1, maxLevels);
        text += '</ul>';
        tElement.html(text);
        $compile(tElement.contents())(scope);
      }
      catch(err) {
        tElement.html('<b>ERROR!!!</b> - ' + err);
        $compile(tElement.contents())(scope);
      }
    }
  };
});
