'use strict';
angular.module('gestaoRiscosApp').controller('MenuController', function($cookies, $mdSidenav, comumService, $rootScope, $state){
    var self = this;

    init();
    function init(){
        /**
         * Essa classe é adicionada automaticamente pela diretiva md-position-mode
         * essa diretiva é necessária em cada md-menu para nao printar erro do angular material no console
         * a remoção dessa classe é necessária para não alterar o tamanho do menu
         */
        angular.element(".md-menu-bar-menu").removeClass("md-dense");

        $rootScope.usuarioLogado = {permissoes: [], jurisdicoes: []};

        //comumService.buscarUrlsMenusPorAmbiente().then(function(response){
        //    self.url = response.data;
        //});

    }

    self.logout = function () {
        window.location.href = '/gestaoriscos/api/logout';
    };

    self.openLink = function (link) {
        window.open(link);
    };

    self.toggleMenu = function(){
        $mdSidenav('left').toggle();
    };
    self.closeMenu = function(){
        $mdSidenav('left').close();
    };

    var sections = [];
    sections.push({
        name: 'Imóvel',
        type: 'heading',
        children: [
            {
                name: 'Dados Gerais',
                type: 'toggle',
                pages: [{
                    name: 'Manter Dados do Imóvel',
                    state: 'inserirCadastro',
                    type: 'link'
                }, {
                    name: 'Homologar Imóvel',
                    state: 'homologarImovel',
                    type: 'link'
                }, {
                    name: 'Vetorizar terreno',
                    state: 'vetorizarTerreno',
                    type: 'link'
                }, {
                    name: 'Consultar Imóvel',
                    state: 'imovelConsultar',
                    type: 'link'
                }]
            }, {
                name: 'Dados Contábeis',
                type: 'toggle',
                pages: [
                    {
                        name: 'Manter Laudo de Avaliação',
                        state: 'manterLaudo',
                        type: 'link'
                    },
                    {
                        name: 'Atualizar valor do imóvel',
                        state: 'atualizarValor',
                        type: 'link'
                    },
                    {
                        name: 'Depreciar imóvel',
                        state: 'depreciarImovel',
                        type: 'link'
                    },
                    {
                        name: 'Manter valor da PVG',
                        state: 'manterValorPVG',
                        type: 'link'
                    },
                    {
                        name: 'Enviar dados contábeis ao SIAFI',
                        state: 'enviarDadosSiafi',
                        type: 'link'
                    }
                ]
            }
        ]
    });
    sections.push({
        name: 'License',
        url: 'license',
        type: 'link',

        // Add a hidden section so that the title in the toolbar is properly set
        hidden: true
    });

    self.menu = {
        sections: sections,

        selectSection: function(section){
            self.openedSection = section;
        },
        toggleSelectSection: function(section){
            self.openedSection = (self.openedSection === section ? null : section);
        },
        isSectionSelected: function(section){
            return self.openedSection === section;
        },

        selectPage: function(section, page){
            self.currentSection = section;
            self.currentPage = page;
        },
        isPageSelected: function(page){
            return self.currentPage === page;
        }
    };

    // Methods used by menuLink and menuToggle directives
    self.isOpen = isOpen;
    self.isSelected = isSelected;
    self.toggleOpen = toggleOpen;
    self.autoFocusContent = false;

    function isSelected(page){
        return menu.isPageSelected(page);
    }

    function isSectionSelected(section){
        var selected = false;
        var openedSection = menu.openedSection;
        if(openedSection === section){
            selected = true;
        }
        else if(section.children){
            section.children.forEach(function(childSection){
                if(childSection === openedSection){
                    selected = true;
                }
            });
        }
        return selected;
    }

    function isOpen(section){
        return menu.isSectionSelected(section);
    }

    function toggleOpen(section){
        menu.toggleSelectSection(section);
    }
});
