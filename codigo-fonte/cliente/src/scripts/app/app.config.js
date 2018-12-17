(function () {
    'use strict';
    angular.module('gestaoRiscosApp')
        .config(function ($httpProvider, $locationProvider, httpRequestInterceptorCacheBusterProvider) {
            $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
            $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
            //Cache everything except rest api requests
            httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);
            $httpProvider.interceptors.push('errorHandlerInterceptor');
            $httpProvider.interceptors.push('successInterceptor');
            $httpProvider.interceptors.push('authExpiredInterceptor');
            $httpProvider.interceptors.push('alertInterceptor');
        })

        .config(['$urlMatcherFactoryProvider', function ($urlMatcherFactory) {
            $urlMatcherFactory.type('boolean', {
                name: 'boolean',
                decode: function (val) {
                    return val == true ? true : val == "true" ? true : false
                },
                encode: function (val) {
                    return val ? 1 : 0;
                },
                equals: function (a, b) {
                    return this.is(a) && a === b;
                },
                is: function (val) {
                    return [true, false, 0, 1].indexOf(val) >= 0
                },
                pattern: /bool|true|0|1/
            });
        }])

        .config(function ($mdThemingProvider) {
            $mdThemingProvider.theme("success-toast");
            $mdThemingProvider.theme('default').primaryPalette('green');
        })

        .config(function ($compileProvider) {
            $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|file|ftp|blob):|data:image\//);
        })

        .config(function ($mdDateLocaleProvider) {

            var myShortMonths = ['jan', 'fev', 'mar', 'abr', 'mai', 'jun', 'jul', 'ago', 'set', 'out', 'nov', 'dez'];

            $mdDateLocaleProvider.shortMonths = myShortMonths;

            $mdDateLocaleProvider.shortDays = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'];
            $mdDateLocaleProvider.firstDayOfWeek = 1;

            $mdDateLocaleProvider.monthHeaderFormatter = function (date) {
                return myShortMonths[date.getMonth()] + ' ' + date.getFullYear();
            };

            $mdDateLocaleProvider.weekNumberFormatter = function (weekNumber) {
                return 'Semana ' + weekNumber;
            };

            $mdDateLocaleProvider.formatDate = function (date) {
                return date ? moment(date).format('DD/MM/YYYY') : '';
            };

            $mdDateLocaleProvider.parseDate = function (dateString) {
                if (dateString == null || dateString.length == 0) {
                    return null;
                }

                var m = moment(dateString, 'DD/MM/YYYY', true);
                return m.isValid() ? m.toDate() : new Date(NaN);
            }
        })

        .config(function ($mdIconProvider) {
            $mdIconProvider
                .defaultIconSet(URL_AVATAR_ICONS, 128)
                .icon('menu', URL_ICON_MENU, 24)
                .icon('share', URL_ICON_SHARE, 24)
                .icon('person', URL_LOGOUT, 48)
                .icon('person', URL_ICON_PERSON, 48);
        })
        .config(['$mdThemingProvider', function ($mdThemingProvider) {
            $mdThemingProvider.definePalette('mpogPrimaryPalette', {
                '50': '#3ba4e6',
                '100': '#2499e4',
                '200': '#1b8cd4',
                '300': '#187dbd',
                '400': '#156ea7',
                '500': '#4CAF50',
                '600': '#4CAF50',
                '700': '#38813C',
                '800': '#0a324c',
                '900': '#072335',
                'A100': '#52aee9',
                'A200': '#68b9ec',
                'A400': '#7fc3ef',
                'A700': '#04141f',
                'contrastDefaultColor': 'light',
                'contrastDarkColors': ['50', '100', '200', '300', '400', 'A100'],
                'contrastLightColors': 'dark'
            });
            $mdThemingProvider.definePalette('mpogAccentPalette', {
                '50': '#666',
                '100': '#666',
                '200': '#666',
                '300': '#666',
                '400': '#666',
                '500': '#666',
                '600': '#666',
                '700': '#666',
                '800': '#666',
                '900': '#666',
                'A100': '#666',
                'A200': '#666',
                'A400': '#666',
                'A700': '#666',
                'contrastDefaultColor': 'light',
                'contrastDarkColors': ['50', '100', '200', '300', '400', 'A100'],
                'contrastLightColors': 'dark'
            });
            $mdThemingProvider.theme('default')
                .primaryPalette('mpogPrimaryPalette', {
                    'hue-1': '100',
                    'hue-2': '700',
                    'hue-3': 'A100'
                })
                .accentPalette('mpogAccentPalette');

        }])
        .config(['$translateProvider', '$translatePartialLoaderProvider', function ($translateProvider, $translatePartialLoaderProvider) {

            $translateProvider.useLoader('$translatePartialLoader', {
                urlTemplate: '{part}/{lang}.json'
            });

            $translateProvider.preferredLanguage('pt-BR');
            $translateProvider.useSanitizeValueStrategy('escapeParameters');

        }])

        .config(function ($urlRouterProvider) {
            $urlRouterProvider.when('', '/');
        })

        .config(function ($breadcrumbProvider) {
            $breadcrumbProvider.setOptions({
                templateUrl: 'scripts/pages/geral/view/breadcrumb.html'
            });
        });
})();
