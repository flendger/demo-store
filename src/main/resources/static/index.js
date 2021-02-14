(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'products/products.html',
                controller: 'productsController'
            })
            .when('/products', {
                templateUrl: 'products/products.html',
                controller: 'productsController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .otherwise({
                redirectTo: '/'
            });

        // $httpProvider.interceptors.push(function ($q, $location) {
        //     return {
        //         'responseError': function (rejection, $localStorage, $http) {
        //             var defer = $q.defer();
        //             if (rejection.status == 401 || rejection.status == 403) {
        //                 console.log('error: 401-403');
        //                 $location.path('/auth');
        //                 if (!(localStorage.getItem("localUser") === null)) {
        //                     delete $localStorage.currentUser;
        //                     $http.defaults.headers.common.Authorization = '';
        //                 }
        //                 console.log(rejection.data);
        //                 var answer = JSON.parse(rejection.data);
        //                 console.log(answer);
        //                 // window.alert(answer.message);
        //             }
        //             defer.reject(rejection);
        //             return defer.promise;
        //         }
        //     };
        // });
    }

    function run($rootScope, $http, $localStorage) {
    }
})();

angular.module('app').controller('indexController', function ($scope, $http, $localStorage) {

    const contextPath = 'http://localhost:8189/store';
    $scope.authorized = false;
    $scope.username = "";
    let msgTxt = "";

    $scope.tryToAuth = function (user) {
        $http.post(contextPath + '/auth', user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    let receivedToken = 'Bearer ' + response.data.token;
                    $http.defaults.headers.common.Authorization = receivedToken;
                    $localStorage.demoStoreUsername = user.username;
                    $localStorage.demoStoreToken = receivedToken;
                    $scope.username = user.username;
                    user.username = null;
                    user.password = null;
                    $scope.authorized = true;
                }
            }, function errorCallback(response) {
                msgTxt = response.data.message;
                $('#infoModal').modal('show');
            });
    };

    $scope.logout = function () {
        $http.defaults.headers.common.Authorization = null;
        delete $localStorage.demoStoreToken;
        delete $localStorage.demoStoreUsername;
        $scope.username = null;
        $scope.authorized = false;
    }

    $scope.regUser = function () {
        $http.post(contextPath + '/reg', $scope.newUser)
            .then(function successCallback() {
                var user = {};
                user.username = $scope.newUser.username;
                user.password = $scope.newUser.password;
                $scope.tryToAuth(user);
                $scope.newUser.username = null;
                $scope.newUser.password = null;
                $scope.newUser.email = null;
            }, function errorCallback(response) {
                msgTxt = response.data.message;
                $('#infoModal').modal('show');
            });
    }

    $('#infoModal').on('show.bs.modal', function () {
        var modal = $(this)
        modal.find('.info-text').text(msgTxt)
    })

    if ($localStorage.demoStoreUsername) {
        $http.defaults.headers.common.Authorization = $localStorage.demoStoreToken;
        $scope.username = $localStorage.demoStoreUsername;
        $scope.authorized = true;
    }
});